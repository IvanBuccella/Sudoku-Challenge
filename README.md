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
Design and develop a Sudoku challenge game on a P2P network. Each user can place a number of the sudoku game; if it is not already placed takes 1 point, if it is already placed and it is rights take 0 points, in other case receive -1 point. The games are based on a 9 x 9 matrix. All users that play to a game are automatically informed when a user increment its score and when the game is finished. The system allows the users to generate (automatically) a new Sudoku challenge identified by a name, join in a challenge using a nickname, get the integer matrix describing the Sudoku challenge, and place a solution number. As described in the [SudokuGame Java API](https://github.com/spagnuolocarmine/distributedsystems-unisa/blob/master/homework/SudokuGame.java).

## Prerequisites and Technologies

## Project Structure

### Classes and Interfaces

### Methods

### Testing using JUnit

## Deploy

### Repository

Clone the repository:

```sh
$ git clone https://github.com/IvanBuccella/ivan_buccella_adc_2021
```

### Environment Variables

Set your own environment variables by using the `.env-sample` file. You can just duplicate and rename it in `.env`.

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
