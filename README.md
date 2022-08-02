| Student                   | MD5                                                  | Homework ID | Project     |
| ------------------------- | ---------------------------------------------------- | ----------- | ----------- |
| Ivan Buccella 05225 01063 | "ivanbuccella-25" = 1fb081fb2e141d6e8388dc4cb8346549 | 4           | Sudoku Game |

# Sommario

- **[Project Description](#project-description)**
- **[Prerequisites and Technologies](#prerequisites-and-technologies)**
- **[Project Structure](#project-structure)**
  - **[Classes and Interfaces](#classes-and-interfaces)**
  - **[Methods](#methods)**
  - **[Testing using JUnit](#testing-using-junit)**
- **[Deploy](#deploy)**
  - **[Repository](#repository)**
  - **[Environment Variables](#environment-variables)**
  - **[Build](#build)**
  - **[Test](#test)**
  - **[Start](#start)**

## Project Description

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/220px-Sudoku-by-L2G-20050714.svg.png"/>

Design and develop a Sudoku challenge game on a P2P network.<br>
Each user can place a number of the sudoku game; if it is not already placed takes 1 point, if it is already placed and it is right take 0 points, in other cases receive -1 point.<br>
The games are based on a 9 x 9 matrix. All users that play a game are automatically informed when a user increments their score and when the game is finished.<br>
The system allows the users to generate (automatically) a new Sudoku challenge identified by a name, join in a challenge using a nickname, get the integer matrix describing the Sudoku challenge, and place a solution number.<br><br>
The main goal of the project is to realize the above-described functionality through using async and anonymous communication, with the advantages offers by the Publish/Subscribe paradigm and the TomP2P library.

## Prerequisites and Technologies

- TomP2P: a P2P-based high performance key-value pair storage library used for the application network;
- Java: object oriented programming language used for developing the application;
- Maven: project management software used for managing the software's project;
- JUnit: unit testing framework used for testing the application;
- Docker: software platform that allows to build, test, and deploy the application into standardized units called containers;
- Docker Compose: tool for defining and running the multiple container Docker that compose the application.

## Project Structure

The starting point for defining and developing the classes and methods was indicated into the [references API](https://github.com/spagnuolocarmine/distributedsystems-unisa/blob/master/homework/SudokuGame.java).<br>

The project uses `Docker Compose` for organizing the application in multiple services, using the following files for building the containers:

- [docker/Dockerfile.dev](https://github.com/IvanBuccella/ivan_buccella_adc_2021/blob/58d3691073e271c434db3ca9d5d3238b6d6fa78c/docker/Dockerfile.dev): defines the main and generic peer service container;
- [docker/Dockerfile.test.dev](https://github.com/IvanBuccella/ivan_buccella_adc_2021/blob/58d3691073e271c434db3ca9d5d3238b6d6fa78c/docker/Dockerfile.test.dev): defines application tester service container.

The project, as described before, was developed using Java and Maven uses the [code/pom.xml](https://github.com/IvanBuccella/ivan_buccella_adc_2021/blob/58d3691073e271c434db3ca9d5d3238b6d6fa78c/code/pom.xml) file for defining the project dependencies. In particular, the following dependencies are used:

- `net.tomp2p`: TomP2P;
- `org.junit.jupiter`: Jupiter;
- `args4j`: Args4J;
- `org.beryx`: Text IO.

### Classes and Interfaces

The project source code folder `code/src/main/java/it/ivanbuccella/sudoku` is mainly composed from:

- the `interfaces` folder:

  - [SudokuGame.java](https://github.com/IvanBuccella/ivan_buccella_adc_2021/blob/58d3691073e271c434db3ca9d5d3238b6d6fa78c/code/src/main/java/it/ivanbuccella/sudoku/interfaces/SudokuGame.java): describes the mainly implemented methods.
  - [MessageListener.java](https://github.com/IvanBuccella/ivan_buccella_adc_2021/blob/58d3691073e271c434db3ca9d5d3238b6d6fa78c/code/src/main/java/it/ivanbuccella/sudoku/interfaces/MessageListener.java): describes the methods for parsing the messages received from the peers.

- the `implementations` folder:

  - [Message.java](https://github.com/IvanBuccella/ivan_buccella_adc_2021/blob/58d3691073e271c434db3ca9d5d3238b6d6fa78c/code/src/main/java/it/ivanbuccella/sudoku/implementations/Message.java): defines the message instances structure that is sent into the network.
  - [User.java](https://github.com/IvanBuccella/ivan_buccella_adc_2021/blob/58d3691073e271c434db3ca9d5d3238b6d6fa78c/code/src/main/java/it/ivanbuccella/sudoku/implementations/User.java): defines the user instances structure that is used for the peer players in the network.
  - [Sudoku.java](https://github.com/IvanBuccella/ivan_buccella_adc_2021/blob/58d3691073e271c434db3ca9d5d3238b6d6fa78c/code/src/main/java/it/ivanbuccella/sudoku/implementations/Sudoku.java): defines the instance structure of the Sudoku (game, rules, users, etc.) that are used for games between peers.
  - [SudokuGameImpl.java](https://github.com/IvanBuccella/ivan_buccella_adc_2021/blob/58d3691073e271c434db3ca9d5d3238b6d6fa78c/code/src/main/java/it/ivanbuccella/sudoku/implementations/SudokuGameImpl.java): defines the `SudokuGame.java` interface methods implementation.

- the [Example.java](https://github.com/IvanBuccella/ivan_buccella_adc_2021/blob/58d3691073e271c434db3ca9d5d3238b6d6fa78c/code/src/main/java/it/ivanbuccella/sudoku/Example.java) file: defines the Main class for the project.

### Methods

In the `SudokuGameImpl.java` the following methods have been implemented, and some of their definitions inherited from the `SudokuGame.java` interface:

- `generateNewSudoku`: allows a peer to create a new Sudoku game;
- `join`: allows a peer to join an existing Sudoku game;
- `getSudoku`: allows a peer to get the Sudoku game matrix;
- `placeNumber`: allows a peer to place a new solution number in the Sudoku game;
- `sendMessage`: allows a peer to send update messages to all the joined peers in the Sudoku game;
- `findGame`: allows to find an existing Sudoku game;
- `leaveGame`: allows a peer to leave the Sudoku game including the associated network.

### Testing using JUnit

The unit testing has been implemented using JUnit, in particular:

- 4 peers have been created for being used in the different unit tests;
- a test case has been placed for every method of the `SudokuGame.java` interface;
- every test case tries to explore all the possible input and compare it with the expected output;
- every created test case contains in its name the method name that tries to test;

The total implemented test cases are five and they can be explored by reading the file [src/test/java/it/ivanbuccella/sudoku/TestSudokuGameImpl.java](https://github.com/IvanBuccella/ivan_buccella_adc_2021/blob/58d3691073e271c434db3ca9d5d3238b6d6fa78c/code/src/test/java/it/ivanbuccella/sudoku/TestSudokuGameImpl.java).

The following code describes one of the implemented test cases in order to show their general structure:

```java
@Test
void testCasePlaceNumber(TestInfo testInfo) {
  Integer score;

  peer0.generateNewSudoku("Game 1");
  score = peer0.placeNumber("Game 1", 0, 0, 7);
  assertEquals(-1, score);

  peer1.join("Game 1", "PeerName1");
  score = peer1.placeNumber("Game 1", 0, 0, 8);
  assertEquals(1, score);

  peer2.generateNewSudoku("Game 2");
  score = peer2.placeNumber("Game 2", 0, 1, 6);
  assertEquals(0, score);

  peer3.join("Game 2", "PeerName3");
  score = peer3.placeNumber("Game 2", 1, 1, 5);
  assertEquals(1, score);
}
```

## Deploy

### Repository

Clone the repository:

```sh
$ git clone https://github.com/IvanBuccella/ivan_buccella_adc_2021
```

### Environment Variables

Set your own environment variables by using the `.env-sample` file. You can just duplicate and rename it in `.env`.<br>
In particular, these environment variables have to be set:

- `GROUP_ID`: it is replaced with the value `mycustomgroupid` in the `pom.xml` file. Used for identifying the classes.
- `ARTIFACT_ID`: it is replaced with the value `mycustomartifactid` in the `pom.xml` file. Used for identifying the classes.
- `PROJECT_NAME`: it is replaced with the value `mycustomprojectname` in the `pom.xml` file. Indicates the project name.
- `MAIN_CLASS`: it is replaced with the value `mycustommainclass` in the `pom.xml` file. Indicates the main class of the project.

### Build

Build the local environment with Docker:

```sh
$ docker-compose build
```

### Test

For testing the code with Docker, you can use the following command:

```sh
$ docker-compose run test
```

### Start

For executing the master peer, you can use the following command:

```sh
$ docker-compose run master
```

For executing the other generic peers, you can use the following command (note that the PEER ID = 0 is reserved to the master peer):

```sh
$ docker-compose run -e GENERIC_PEER_ID=1 generic
$ docker-compose run -e GENERIC_PEER_ID=2 generic
$ docker-compose run -e GENERIC_PEER_ID=... generic
```

For stopping all containers, you can use the following command:

```sh
$ docker-compose down
```

### Enjoy :-)
