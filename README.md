| Student                   | MD5                                                  | Homework ID | Project     |
| ------------------------- | ---------------------------------------------------- | ----------- | ----------- |
| Ivan Buccella 05225 01063 | "ivanbuccella-25" = 1fb081fb2e141d6e8388dc4cb8346549 | 4           | Sudoku Game |

# Sommario

- **[Descrizione del progetto](#descrizione-del-progetto)**
- **[Prerequisiti e Tecnologie](#prerequisiti-e-tecnologie)**
- **[Struttura del Progetto](#struttura-del-progetto)**
  - **[Classi e Interfacce](#classi-e-interfacce)**
  - **[Metodi](#metodi)**
  - **[Testing con JUnit](#testing-con-junit)**
- **[Deploy](#deploy)**
  - **[Repository](#repository)**
  - **[Build](#build)**
  - **[Start](#start)**

## Descrizione del progetto

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/220px-Sudoku-by-L2G-20050714.svg.png"/>
Design and develop a Sudoku challenge game on a P2P network. Each user can place a number of the sudoku game; if it is not already placed takes 1 point, if it is already placed and it is rights take 0 points, in other case receive -1 point. The games are based on a 9 x 9 matrix. All users that play to a game are automatically informed when a user increment its score and when the game is finished. The system allows the users to generate (automatically) a new Sudoku challenge identified by a name, join in a challenge using a nickname, get the integer matrix describing the Sudoku challenge, and place a solution number. As described in the [SudokuGame Java API](https://github.com/spagnuolocarmine/distributedsystems-unisa/blob/master/homework/SudokuGame.java).

## Prerequisiti e Tecnologie

## Struttura del Progetto

### Classi e Interfacce

### Metodi

### Testing con JUnit

## Deploy

### Repository

Clone the repository:

```sh
$ git clone https://github.com/IvanBuccella/ivan_buccella_adc_2021
```

### Build

Build the local environment with Docker:

```sh
$ docker-compose build
```

### Start

```sh
$ docker-compose up
```

### Enjoy :-)
