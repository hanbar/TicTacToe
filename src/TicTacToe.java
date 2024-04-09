import java.util.Scanner;

public class TicTacToe {
    final static int SIZE = 3; // TODO do konstruktoru?

    enum Symbol {
        X, O
    }

    static Symbol[][] fields = new Symbol[SIZE][SIZE];

    public static void main(String[] args) {
        printGameBoard();
        playGame();
    }

    private static void printGameBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (isEmptyField(fields[row][col])) {
                    System.out.print("_|");
                } else {
                    System.out.print(fields[row][col] + "|");
                }
            }
            System.out.println();
        }
    }

    private static void playGame() {
        System.out.println("Let's start a new game!");
        Scanner scanner = new Scanner(System.in);
        Symbol currentSymbol = Symbol.X;
        boolean gameOver = false;

        while (!gameOver) {
            System.out.println("Enter the field number (1 - 9) to draw " + currentSymbol);
            int position = getValidPosition(scanner);
            int index = position - 1;
            int columnIndex = index % SIZE;
            int rowIndex = index / SIZE;
            fields[rowIndex][columnIndex] = currentSymbol;
            printGameBoard();

            if (checkWin()) {
                System.out.println("Player " + currentSymbol + " wins!");
                gameOver = true;
            } // TODO nelze vyhrat
            else if (checkDraw()) {
                System.out.println("It's a draw!");
                gameOver = true;
            }

            currentSymbol = (currentSymbol == Symbol.X) ? Symbol.O : Symbol.X;
        }
    }

    private static int getValidPosition(Scanner scanner) {
        int position;
        while (true) {
            try {
                position = scanner.nextInt();
                if (position >= 1 && position <= SIZE * SIZE) {
                    if (isEmptyField(getSymbolFromPosition(position))) {
                        break;
                    } else {
                        System.out.println("The position is just filled");
                    }
                } else {
                    System.out.println("Must be between 1 and " + SIZE * SIZE);
                }
            } catch (Exception e) {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        }
        return position;
    }

    private static boolean isEmptyField(Symbol symbol) {
        return symbol != Symbol.X && symbol != Symbol.O;
    }

    private static Symbol getSymbolFromPosition(int position) {
        int index = position - 1;
        int columnIndex = index % SIZE;
        int rowIndex = index / SIZE;
        return fields[rowIndex][columnIndex];
    }

    private static boolean checkWin() {
        return checkRows() || checkCols() || checkDiagonals();
    }

    private static boolean checkDraw() {
        for (int r = 0; r < fields.length; r++) {
            for (int c = 0; c < fields[r].length; c++) {
                if (isEmptyField(fields[r][c])) {
                    return false;
                }
            }
        }
        return true; // all fields occupied
    }

    private static boolean checkRows() {
        for (int r = 0; r < fields.length; r++) {
            boolean win = true;
            Symbol firstSymbolOnRow = fields[r][0];
            if (isEmptyField(firstSymbolOnRow)) continue;

            for (int c = 1; c < fields[r].length; c++) {
                if (fields[r][c] != firstSymbolOnRow) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }
        return false;
    }

    private static boolean checkCols() {
        for (int c = 0; c < fields[0].length; c++) {
            boolean win = true;
            Symbol firstSymbolInCol = fields[0][c];
            if (isEmptyField(firstSymbolInCol)) continue;

            for (int r = 1; r < fields.length; r++) {
                if (fields[r][c] != firstSymbolInCol) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }
        return false;
    }

    private static boolean checkDiagonals() {
        boolean mainDiagonalWin = true;
        boolean counterDiagonalWin = true;

        // Check main diagonal
        for (int r = 0; r < fields.length - 1; r++) {
            Symbol firstField = fields[r][r];
            if (isEmptyField(firstField)) {
                mainDiagonalWin = false;
                break;
            }

            if (firstField != fields[r + 1][r + 1]) {
                mainDiagonalWin = false;
                break;
            }
        }

        // Check counter diagonal
        for (int r = 0; r < fields.length - 1; r++) {
            Symbol firstField = fields[r][fields.length - 1 - r]; // fields[0][2], fields[1][1], (fields[2][0])
            if (isEmptyField(firstField)) {
                counterDiagonalWin = false;
                break;
            }

            if (firstField != fields[r + 1][fields.length - 2 - r]) {
                counterDiagonalWin = false;
                break;
            }
        }

        return mainDiagonalWin || counterDiagonalWin;
    }
}