package it.ivanbuccella.sudoku.implementations;

import it.ivanbuccella.sudoku.interfaces.MessageListener;
import it.ivanbuccella.sudoku.interfaces.SudokuGame;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;

import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.futures.FutureDirect;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.rpc.ObjectDataReply;
import net.tomp2p.storage.Data;

public class SudokuGameImpl implements SudokuGame {
	final private Peer peer;
	final private PeerDHT peerDHT;
	final private int DEFAULT_MASTER_PORT = 4000;
	private HashSet<String> mySudokuGamesList = new HashSet<>();

	public SudokuGameImpl(int _id, String _master_peer, MessageListener _listener) throws Exception {
		peer = new PeerBuilder(Number160.createHash(_id)).ports(DEFAULT_MASTER_PORT + _id).start();
		peerDHT = new PeerBuilderDHT(peer).start();

		FutureBootstrap fb = peer.bootstrap()
				.inetAddress(InetAddress.getByName(_master_peer))
				.ports(DEFAULT_MASTER_PORT)
				.start();
		fb.awaitUninterruptibly();
		if (fb.isSuccess()) {
			peer.discover().peerAddress(fb.bootstrapTo().iterator().next()).start().awaitUninterruptibly();
		} else {
			throw new Exception("Error in master peer bootstrap.");
		}

		peer.objectDataReply(new ObjectDataReply() {
			public Object reply(PeerAddress sender, Object request) throws Exception {
				return _listener.parseMessage(request);
			}
		});
	}

	@Override
	public Integer[][] generateNewSudoku(String _game_name) {
		Sudoku sudokuInstance = new Sudoku(_game_name, new HashSet<>());
		sudokuInstance.addUser(peerDHT.peer().peerAddress(), "dealer");
		try {
			peerDHT.put(Number160.createHash(_game_name))
					.data(new Data(sudokuInstance)).start().awaitUninterruptibly();
			mySudokuGamesList.add(_game_name);
			return sudokuInstance.getBoard();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean join(String _game_name, String _nickname) {
		Sudoku sudokuInstance = this.findGame(_game_name);
		if (sudokuInstance == null)
			return false;

		try {
			sudokuInstance.addUser(peerDHT.peer().peerAddress(), _nickname);
			peerDHT.put(Number160.createHash(_game_name))
					.data(new Data(sudokuInstance)).start().awaitUninterruptibly();
			mySudokuGamesList.add(_game_name);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Integer[][] getSudoku(String _game_name) {
		Sudoku sudokuInstance = this.findGame(_game_name);
		if (sudokuInstance == null) {
			return null;
		}
		return sudokuInstance.getBoard();
	}

	@Override
	public Integer placeNumber(String _game_name, int _i, int _j, int _number) {
		Sudoku sudokuInstance = this.findGame(_game_name);
		if (sudokuInstance == null) {
			return null;
		}

		User sudokuUser = sudokuInstance.getUser(peerDHT.peer().peerAddress());
		if (sudokuUser == null) {
			return null;
		}

		if (sudokuInstance.isFinished()) {
			return null;
		}

		Integer score = sudokuInstance.add(_i, _j, _number);
		sudokuUser.addScore(score);
		try {
			peerDHT.put(Number160.createHash(_game_name))
					.data(new Data(sudokuInstance)).start().awaitUninterruptibly();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sendMessage(_game_name, score, sudokuInstance.isFinished(), sudokuUser);

		return score;
	}

	public boolean sendMessage(String _game_name, Integer score, boolean isFinished, User user) {
		if (!mySudokuGamesList.contains(_game_name))
			return false;

		Sudoku sudokuInstance = this.findGame(_game_name);
		if (sudokuInstance == null)
			return false;

		if (sudokuInstance.getUsers() == null)
			return false;

		Message msg = new Message(_game_name, score, isFinished, user);
		for (User peer : sudokuInstance.getUsers()) {
			if (!peer.getPeerAddress().equals(peerDHT.peer().peerAddress())) {
				FutureDirect futureDirect = peerDHT.peer().sendDirect(peer.getPeerAddress()).object(msg).start();
				futureDirect.awaitUninterruptibly();
			}
		}
		return true;
	}

	public Sudoku findGame(String gameName) {
		if (gameName == null)
			return null;

		try {
			FutureGet futureGet = peerDHT.get(Number160.createHash(gameName)).start();
			futureGet.awaitUninterruptibly();
			if (!futureGet.isSuccess())
				return null;
			if (futureGet.isEmpty())
				return null;

			return (Sudoku) futureGet.dataMap().values().iterator().next().object();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}