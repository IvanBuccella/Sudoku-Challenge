package it.ivanbuccella.sudoku;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import it.ivanbuccella.sudoku.implementations.Message;
import it.ivanbuccella.sudoku.implementations.SudokuGameImpl;
import it.ivanbuccella.sudoku.interfaces.MessageListener;

public class Example {

    @Option(name = "-m", aliases = "--masterip", usage = "the master peer ip address", required = true)
    private static String master;

    @Option(name = "-id", aliases = "--identifierpeer", usage = "the unique identifier for this peer", required = true)
    private static int id;

    public static void main(String[] args) throws Exception {

        class MessageListenerImpl implements MessageListener {
            int peerid;

            public MessageListenerImpl(int peerid) {
                this.peerid = peerid;
            }

            public Object parseMessage(Object obj) {
                TextIO textIO = TextIoFactory.getTextIO();
                TextTerminal terminal = textIO.getTextTerminal();
                if (!(obj instanceof Message))
                    return "failed";

                Message message = (Message) obj;
                terminal.printf(message.getMessage(peerid));

                return "success";
            }
        }

        Example example = new Example();
        final CmdLineParser parser = new CmdLineParser(example);

        try {
            parser.parseArgument(args);
            String gameName;
            Integer[][] matrix;
            TextIO textIO = TextIoFactory.getTextIO();
            TextTerminal terminal = textIO.getTextTerminal();
            SudokuGameImpl peer = new SudokuGameImpl(id, master, new MessageListenerImpl(id));

            terminal.printf("\nStaring peer id: %d on master node: %s\n",
                    id, master);
            while (true) {
                printMenu(terminal);

                int option = textIO.newIntInputReader()
                        .withMaxVal(5)
                        .withMinVal(1)
                        .read("Option");
                switch (option) {
                    case 1:
                        terminal.printf("\nENTER GAME NAME\n");
                        gameName = textIO.newStringInputReader()
                                .withDefaultValue("default-game")
                                .read("Name:");
                        matrix = peer.generateNewSudoku(gameName);
                        if (matrix == null) {
                            terminal.printf("\nCANNOT CRATE THE GAME. TRY TO CHANGE ITS OWN NAME. \n", gameName);
                        } else {
                            terminal.printf("\nGAME %s SUCCESSFULLY CREATED\n", gameName);
                        }
                        printSudoku(terminal, matrix);
                        break;
                    case 2:
                        terminal.printf("\nENTER GAME NAME\n");
                        gameName = textIO.newStringInputReader()
                                .withDefaultValue("default-game")
                                .read("Name:");
                        terminal.printf("\nENTER NICKNAME\n");
                        String nickName = textIO.newStringInputReader()
                                .withDefaultValue("default-nickname")
                                .read(" Nickname:");
                        if (peer.join(gameName, nickName))
                            terminal.printf("\n SUCCESSFULLY JOINED THE GAME %s\n", gameName);
                        else
                            terminal.printf("\nERROR IN JOINING THE GAME %s\n", gameName);
                        break;
                    case 3:
                        terminal.printf("\nENTER GAME NAME\n");
                        gameName = textIO.newStringInputReader()
                                .withDefaultValue("default-game")
                                .read("Name:");
                        matrix = peer.getSudoku(gameName);
                        if (matrix == null) {
                            terminal.printf("\nCANNOT GET THE MATRIX\n", gameName);
                        } else {
                            printSudoku(terminal, matrix);
                        }
                        break;
                    case 4:
                        terminal.printf("\nENTER GAME NAME\n");
                        gameName = textIO.newStringInputReader()
                                .withDefaultValue("default-game")
                                .read("Name:");

                        terminal.printf("\nENTER i\n");
                        Integer i = textIO.newIntInputReader()
                                .withMaxVal(8)
                                .withMinVal(0)
                                .read(" i:");

                        terminal.printf("\nENTER j\n");
                        Integer j = textIO.newIntInputReader()
                                .withMaxVal(8)
                                .withMinVal(0)
                                .read(" j:");

                        terminal.printf("\nENTER number\n");
                        Integer number = textIO.newIntInputReader()
                                .withMaxVal(9)
                                .withMinVal(1)
                                .read(" number:");

                        Integer score = peer.placeNumber(gameName, i, j, number);
                        if (score == null) {
                            terminal.printf(
                                    "\n YOU CANNOT OPERATE ON THE GAME. IT DOES NOT EXIST OR HAS ALREADY BEEN COMPLETED. %s \n",
                                    gameName);
                        } else {
                            terminal.printf("\n RECEIVED A SCORE OF %d\n", score);
                        }
                        break;
                    case 5:
                        terminal.printf("\nENTER GAME NAME\n");
                        gameName = textIO.newStringInputReader()
                                .withDefaultValue("default-game")
                                .read("Name:");

                        terminal.printf("\nARE YOU SURE TO LEAVE THE GAME?\n");
                        boolean exit = textIO.newBooleanInputReader().withDefaultValue(false).read("exit?");
                        if (exit) {
                            peer.leaveGame(gameName);
                            System.exit(0);
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (CmdLineException clEx) {
            System.err.println("ERROR: Unable to parse command-line options: " + clEx);
        }

    }

    public static void printSudoku(TextTerminal terminal, Integer[][] matrix) {
        for (Integer i = 0; i < 9; i++) {
            terminal.printf("\n");
            for (Integer j = 0; j < 9; j++) {
                terminal.printf(" %d", matrix[i][j].intValue());
            }
        }
    }

    public static void printMenu(TextTerminal terminal) {
        terminal.printf("\n1 - CREATE GAME\n");
        terminal.printf("\n2 - JOIN GAME\n");
        terminal.printf("\n3 - GET MATRIX\n");
        terminal.printf("\n4 - PLACE NUMBER\n");
        terminal.printf("\n5 - LEAVE GAME\n");
    }

}