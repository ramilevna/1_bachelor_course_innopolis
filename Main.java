import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * A class Main which contains main basic operations and data input and output occurs.
 */
public class Main {
    /**
     * The instance of Board class.
     */
    private static Board chessBoard;

    /**
     * The main method in which all the functions of the program are called.
     * @param args Array of all elements used in the method.
     * @throws FileNotFoundException
     */

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("C:\\Users\\renat\\IdeaProjects\\chess\\src\\input.txt"));
        PrintWriter out = new PrintWriter(new FileOutputStream("C:\\Users\\renat\\IdeaProjects\\chess\\src\\output.txt"));
        int n = 0;
        // checking for integer value
        InvalidBoardSizeException bs = new InvalidBoardSizeException();
        try {
            n = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            out.println(bs.getMessage());
            out.close();
            System.exit(0);
        }
        int int3 = 3;
        int int1000 = 1000;
        if ((n < int3) | (n > int1000)) {
            out.println(bs.getMessage());
            out.close();
            System.exit(0);
        }
        String strM = scanner.nextLine();
        int m = 0;
        // checking for integer value
        InvalidNumberOfPiecesException nop = new InvalidNumberOfPiecesException();
        try {
            m = Integer.parseInt(strM);
        } catch (NumberFormatException e) {
            out.println(nop.getMessage());
            out.close();
            System.exit(0);
        }
        if ((m < 2) | (m > n * n)) {
            out.println(nop.getMessage());
            out.close();
            System.exit(0);
        }
        PiecePosition[] arrayPositions = new PiecePosition[m];
        chessBoard = new Board(n);
        int countBlackKing = 0;
        int countWhiteKing = 0;
        int count = 0;
        String inputStr;
        while (scanner.hasNextLine()) {
            inputStr = scanner.nextLine();
            String pieceType = inputStr.split(" ")[0];
            if (pieceType == null) {
                out.println(nop.getMessage());
                out.close();
                System.exit(0);
            }
            if (!(pieceType.equals("Knight")) & !(pieceType.equals("King")) & !(pieceType.equals("Pawn"))
                    & !(pieceType.equals("Bishop")) & !(pieceType.equals("Rook")) & !(pieceType.equals("Queen"))) {
                InvalidPieceNameException pn = new InvalidPieceNameException();
                out.println(pn.getMessage());
                out.close();
                System.exit(0);
            }
            String colorStr = inputStr.split(" ")[1];
            if (!(colorStr.equals("Black")) & !(colorStr.equals("White"))) {
                InvalidPieceColorException pc = new InvalidPieceColorException();
                out.println(pc.getMessage());
                out.close();
                System.exit(0);
            }
            PieceColor color = PieceColor.parse(colorStr);
            String strX = inputStr.split(" ")[2];
            int x = 0;
            int y = 0;
            String strY = inputStr.split(" ")[int3];
            // checking for integer value
            InvalidPiecePositionException pp = new InvalidPiecePositionException();
            try {
                x = Integer.parseInt(strX);
                y = Integer.parseInt(strY);
            } catch (NumberFormatException e) {
                out.println(pp.getMessage());
                out.close();
                System.exit(0);
            }
            if (((x < 1) | (x > n)) | ((y < 1) | (y > n))) {
                out.println(pp.getMessage());
                out.close();
                System.exit(0);
            }
            if (chessBoard.containPiece(x + " " + y)) {
                out.println(pp.getMessage());
                out.close();
                System.exit(0);
            }
            PiecePosition position = new PiecePosition(x, y);
            arrayPositions[count] = position;
            ChessPiece piece;
            switch (pieceType) {
                case "Knight":
                    piece = new Knight(position, color);
                    chessBoard.addPiece(piece);
                    break;
                case "King":
                    if (color == PieceColor.BLACK) {
                        countBlackKing += 1;
                    } else {
                        countWhiteKing += 1;
                    }
                    piece = new King(position, color);
                    chessBoard.addPiece(piece);
                    break;
                case "Pawn":
                    piece = new Pawn(position, color);
                    chessBoard.addPiece(piece);
                    break;
                case "Bishop":
                    piece = new Bishop(position, color);
                    chessBoard.addPiece(piece);
                    break;
                case "Rook":
                    piece = new Rook(position, color);
                    chessBoard.addPiece(piece);
                    break;
                default:
                    piece = new Queen(position, color);
                    chessBoard.addPiece(piece);
                    break;
            }
            count++;
        }
        if (m != count) {
            out.println(nop.getMessage());
            out.close();
            System.exit(0);
        }
        if (countBlackKing != 1 | countWhiteKing != 1) {
            InvalidGivenKingsException gk = new InvalidGivenKingsException();
            out.println(gk.getMessage());
            out.close();
            System.exit(0);
        }
        for (int i = 0; i < m; i++) {
            ChessPiece thisPiece = chessBoard.getPiece(arrayPositions[i].toString());
            out.print(chessBoard.getPiecePossibleMovesCount(thisPiece));
            out.print(" ");
            out.println(chessBoard.getPiecePossibleCapturesCount(thisPiece));
        }
        out.close();
    }
}
/**
 * A class which has everything about the board.
 */
class Board {
    /**
     * The Hashmap, in which we will store the coordinates of the chess piece and the piece itself.
     */
    private Map<String, ChessPiece> positionsToPieces = new HashMap<>();
    /**
     * Size is used to store and transmit to methods the size of the board.
     */
    private int size;

    Board(int boardSize) {
        this.size = boardSize;
    }

    public int getPiecePossibleMovesCount(ChessPiece piece) {
        return piece.getMovesCount(positionsToPieces, size);
    }

    public int getPiecePossibleCapturesCount(ChessPiece piece) {
        return piece.getCapturesCount(positionsToPieces, size);
    }

    public void addPiece(ChessPiece piece) {
        positionsToPieces.put(piece.position.toString(), piece);
    }

    public ChessPiece getPiece(String position) {
        return positionsToPieces.get(position.toString());
    }
    public boolean containPiece(String key) {
        return positionsToPieces.containsKey(key);
    }
}
/**
 * A class which has everything about the position of chess piece.
 */
class PiecePosition {
    /**
     * A variable of X coordinate of some chess piece.
     */
    private int x;
    /**
     * A variable of Y coordinate of some chess piece.
     */
    private int y;
    PiecePosition(int onX, int onY) {
        this.x = onX;
        this.y = onY;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public String toString() {
        return x + " " + y;
    }
}

/**
 * Enumeration which contains white and black colors of piece.
 */
enum PieceColor {
    /**
     * White color of piece.
     */
    WHITE,
    /**
     * Black color of piece.
     */
    BLACK;
    public static PieceColor parse(String colorStr) {
        if (colorStr.equals("White")) {
            return WHITE;
        } else {
            return BLACK;
        }
    }
}
/**
 * Interface which counts diagonal moves and captures for bishop and queen.
 */
interface BishopMovement {

    default int getDiagonalMovesCount(PiecePosition position, PieceColor color, Map<String,
            ChessPiece> positions, int boardSize) {
        int countMoves = 0;
        int i = 0;
        int x = position.getX();
        int y = position.getY();
        boolean flagFigure1 = true;
        boolean flagFigure2 = true;
        boolean flagFigure3 = true;
        boolean flagFigure4 = true;
        while (i != boardSize) {
            i++;
            if (flagFigure1) {
                if (positions.containsKey((x - i) + " " + (y + i))) {
                    flagFigure1 = false;
                    if (positions.get(x + " " + y).color != positions.get((x - i) + " " + (y + i)).color) {
                        countMoves++;
                    }
                } else {
                    if (x - i >= 1 && x - i <= boardSize && y + i >= 1 && y + i <= boardSize) {
                        countMoves++;
                    }
                }
            }
            if (flagFigure2) {
                if (positions.containsKey((x + i) + " " + (y + i))) {
                    flagFigure2 = false;
                    if (positions.get(x + " " + y).color != positions.get((x + i) + " " + (y + i)).color) {
                        countMoves++;
                    }
                } else {
                    if (x + i >= 1 && x + i <= boardSize && y + i >= 1 && y + i <= boardSize) {
                        countMoves++;
                    }
                }
            }
            if (flagFigure3) {
                if (positions.containsKey((x + i) + " " + (y - i))) {
                    flagFigure3 = false;
                    if (positions.get(x + " " + y).color != positions.get((x + i) + " " + (y - i)).color) {
                        countMoves++;
                    }
                } else {
                    if (x + i >= 1 && x + i <= boardSize && y - i >= 1 && y - i <= boardSize) {
                        countMoves++;
                    }
                }
            }
            if (flagFigure4) {
                if (positions.containsKey((x - i) + " " + (y - i))) {
                    flagFigure4 = false;
                    if (positions.get(x + " " + y).color != positions.get((x - i) + " " + (y - i)).color) {
                        countMoves++;
                    }
                } else {
                    if (x - i >= 1 && x - i <= boardSize && y - i >= 1 && y - i <= boardSize) {
                        countMoves++;
                    }
                }
            }
        }
        return countMoves;
    }
    default int getDiagonalCapturesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
                                         int boardSize) {
        int countCaptures = 0;
        int i = 0;
        int x = position.getX();
        int y = position.getY();
        boolean flagFigure1 = true;
        boolean flagFigure2 = true;
        boolean flagFigure3 = true;
        boolean flagFigure4 = true;
        while (i != boardSize) {
            i++;
            if (flagFigure1) {
                if (positions.containsKey((x - i) + " " + (y + i))) {
                    flagFigure1 = false;
                    // mistake
                    if (positions.get(x + " " + y).color != positions.get((x - i) + " " + (y + i)).color) {
                        countCaptures++;
                    }
                }
            }
            if (flagFigure2) {
                if (positions.containsKey((x + i) + " " + (y + i))) {
                    flagFigure2 = false;
                    if (positions.get(x + " " + y).color != positions.get((x + i) + " " + (y + i)).color) {
                        countCaptures++;
                    }
                }
            }
            if (flagFigure3) {
                if (positions.containsKey((x + i) + " " + (y - i))) {
                    flagFigure3 = false;
                    if (positions.get(x + " " + y).color != positions.get((x + i) + " " + (y - i)).color) {
                        countCaptures++;
                    }
                }
            }
            if (flagFigure4) {
                if (positions.containsKey((x - i) + " " + (y - i))) {
                    flagFigure4 = false;
                    if (positions.get(x + " " + y).color != positions.get((x - i) + " " + (y - i)).color) {
                        countCaptures++;
                    }
                }
            }
        }
        return countCaptures;
    }
}
/**
 * Interface which counts orthogonal moves and captures for rook and queen.
 */
interface RookMovement {
    default int getOrthogonalMovesCount(PiecePosition position, PieceColor color, Map<String,
            ChessPiece> positions, int boardSize) {
        int countMoves = 0;
        int i = 0;
        int x = position.getX();
        int y = position.getY();
        boolean flagFigure1 = true;
        boolean flagFigure2 = true;
        boolean flagFigure3 = true;
        boolean flagFigure4 = true;
        while (i != boardSize) {
            i++;
            if (flagFigure1) {
                if (positions.containsKey(x + " " + (y + i))) {
                    flagFigure1 = false;
                    if (positions.get(x + " " + y).color != positions.get(x + " " + (y + i)).color) {
                        countMoves++;
                    }
                } else {
                    if (y + i >= 1 && y + i <= boardSize) {
                        countMoves++;
                    }
                }
            }
            if (flagFigure2) {
                if (positions.containsKey(x + " " + (y - i))) {
                    flagFigure2 = false;
                    if (positions.get(x + " " + y).color != positions.get(x + " " + (y - i)).color) {
                        countMoves++;
                    }
                } else {
                    if (y - i >= 1 && y - i <= boardSize) {
                        countMoves++;
                    }
                }
            }
            if (flagFigure3) {
                if (positions.containsKey((x + i) + " " + y)) {
                    flagFigure3 = false;
                    if (positions.get(x + " " + y).color != positions.get((x + i) + " " + y).color) {
                        countMoves++;
                    }
                } else {
                    if (x + i >= 1 && x + i <= boardSize) {
                        countMoves++;
                    }
                }
            }
            if (flagFigure4) {
                if (positions.containsKey((x - i) + " " + y)) {
                    flagFigure4 = false;
                    if (positions.get(x + " " + y).color != positions.get((x - i) + " " + y).color) {
                        countMoves++;
                    }
                } else {
                    if (x - i >= 1 && x - i <= boardSize) {
                        countMoves++;
                    }
                }
            }
        }
        return countMoves;
    }
    default int getOrthogonalCapturesCount(PiecePosition position, PieceColor color, Map<String,
            ChessPiece> positions, int boardSize) {
        int countCaptures = 0;
        int i = 0;
        int x = position.getX();
        int y = position.getY();
        boolean flagFigure1 = true;
        boolean flagFigure2 = true;
        boolean flagFigure3 = true;
        boolean flagFigure4 = true;
        while (i != boardSize) {
            i++;
            if (flagFigure1) {
                if (positions.containsKey(x + " " + (y + i))) {
                    flagFigure1 = false;
                    if (positions.get(x + " " + y).color != positions.get(x + " " + (y + i)).color) {
                        countCaptures++;
                    }
                }
            }
            if (flagFigure2) {
                if (positions.containsKey(x + " " + (y - i))) {
                    flagFigure2 = false;
                    if (positions.get(x + " " + y).color != positions.get(x + " " + (y - i)).color) {
                        countCaptures++;
                    }
                }
            }
            if (flagFigure3) {
                if (positions.containsKey((x + i) + " " + y)) {
                    flagFigure3 = false;
                    if (positions.get(x + " " + y).color != positions.get((x + i) + " " + y).color) {
                        countCaptures++;
                    }
                }
            }
            if (flagFigure4) {
                if (positions.containsKey((x - i) + " " + y)) {
                    flagFigure4 = false;
                    if (positions.get(x + " " + y).color != positions.get((x - i) + " " + y).color) {
                        countCaptures++;
                    }
                }
            }
        }
        return countCaptures;
    }
}
/**
 * A class which has everything about the chess piece(color, position, possible moves and captures counts).
 */
abstract class ChessPiece {
    /**
     * A varible store the coordinates of some chess piece.
     */
    protected PiecePosition position;
    /**
     * A varible store the color of some chess piece.
     */
    protected PieceColor color;
    ChessPiece(PiecePosition piecePosition, PieceColor pieceColor) {
        this.position = piecePosition;
        this.color = pieceColor;
    }
    public PiecePosition getPosition() {
        return position;
    }
    public PieceColor getColor() {
        return color;
    }
    public abstract int getMovesCount(Map<String, ChessPiece> positions, int boardSize);
    public abstract int getCapturesCount(Map<String, ChessPiece> positions, int boardSize);
}
/**
 * A subclass knight which contain methods for counting answers for knight piece.
 */
class Knight extends ChessPiece {
    Knight(PiecePosition piecePosition, PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }
    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int countMoves = 0;
        int x = position.getX();
        int y = position.getY();
        if (positions.containsKey((x - 1) + " " + (y - 2))) {
            if (positions.get(x + " " + y).color != positions.get((x - 1) + " " + (y - 2)).color) {
                countMoves++;
            }
        } else {
            if (x - 1 >= 1 && x - 1 <= boardSize && y - 2 >= 1 && y - 2 <= boardSize) {
                countMoves++;
            }
        }
        if (positions.containsKey((x + 2) + " " + (y - 1))) {
            if (positions.get(x + " " + y).color != positions.get((x + 2) + " " + (y - 1)).color) {
                countMoves++;
            }
        } else {
            if (x - 2 >= 1 && x - 2 <= boardSize && y - 1 >= 1 && y - 1 <= boardSize) {
                countMoves++;
            }
        }
        if (positions.containsKey((x - 2) + " " + (y + 1))) {
            if (positions.get(x + " " + y).color != positions.get((x - 2) + " " + (y + 1)).color) {
                countMoves++;
            }
        } else {
            if (x - 2 >= 1 && x - 2 <= boardSize && y + 1 >= 1 && y + 1 <= boardSize) {
                countMoves++;
            }
        }
        if (positions.containsKey((x - 1) + " " + (y + 2))) {
            if (positions.get(x + " " + y).color != positions.get((x - 1) + " " + (y + 2)).color) {
                countMoves++;
            }
        } else {
            if (x - 1 >= 1 && x - 1 <= boardSize && y + 2 >= 1 && y + 2 <= boardSize) {
                countMoves++;
            }
        }
        if (positions.containsKey((x + 1) + " " + (y + 2))) {
            if (positions.get(x + " " + y).color != positions.get((x + 1) + " " + (y + 2)).color) {
                countMoves++;
            }
        } else {
            if (x + 1 >= 1 && x + 1 <= boardSize && y + 2 >= 1 && y + 2 <= boardSize) {
                countMoves++;
            }
        }
        if (positions.containsKey((x + 2) + " " + (y + 1))) {
            if (positions.get(x + " " + y).color != positions.get((x + 2) + " " + (y + 1)).color) {
                countMoves++;
            }
        } else {
            if (x + 2 >= 1 && x + 2 <= boardSize && y + 1 >= 1 && y + 1 <= boardSize) {
                countMoves++;
            }
        }
        if (positions.containsKey((x + 2) + " " + (y - 1))) {
            if (positions.get(x + " " + y).color != positions.get((x + 2)  + " " + (y - 1)).color) {
                countMoves++;
            }
        } else {
            if (x + 2 >= 1 && x + 2 <= boardSize && y - 1 >= 1 && y - 1 <= boardSize) {
                countMoves++;
            }
        }
        if (positions.containsKey((x + 1) + " " + (y - 2))) {
            if (positions.get(x + " " + y).color != positions.get((x + 1) + " " + (y - 2)).color) {
                countMoves++;
            }
        } else {
            if (x + 1 >= 1 && x + 1 <= boardSize && y - 2 >= 1 && y - 2 <= boardSize) {
                countMoves++;
            }
        }
        return countMoves;
    }
    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int countCaptures = 0;
        int x = position.getX();
        int y = position.getY();
        if (positions.containsKey((x - 1) + " " + (y - 2))) {
            if (positions.get(x + " " + y).color != positions.get((x - 1) + " " + (y - 2)).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey((x - 2) + " " + (y - 1))) {
            if (positions.get(x + " " + y).color != positions.get((x - 2) + " " + (y - 1)).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey((x - 2) + " " + (y + 1))) {
            if (positions.get(x + " " + y).color != positions.get((x - 2) + " " + (y + 1)).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey((x - 1) + " " + (y + 2))) {
            if (positions.get(x + " " + y).color != positions.get((x - 1) + " " + (y + 2)).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey((x + 1) + " " + (y + 2))) {
            if (positions.get(x + " " + y).color != positions.get((x + 1) + " " + (y + 2)).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey((x + 2) + " " + (y + 1))) {
            if (positions.get(x + " " + y).color != positions.get((x + 2) + " " + (y + 1)).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey((x + 2) + " " + (y - 1))) {
            if (positions.get(x + " " + y).color != positions.get((x + 2)  + " " + (y - 1)).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey((x + 1) + " " + (y - 2))) {
            if (positions.get(x + " " + y).color != positions.get((x + 1) + " " + (y - 2)).color) {
                countCaptures++;
            }
        }
        return countCaptures;
    }
}
/**
 * A subclass King which contain methods for counting answers for King piece.
 */
class King extends ChessPiece {
    King(PiecePosition piecePosition, PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int countMoves = 0;
        int x = position.getX();
        int y = position.getY();
        if (positions.containsKey((x - 1) + " " + (y - 1))) {
            if (positions.get(x + " " + y).color != positions.get((x - 1) + " " + (y - 1)).color) {
                countMoves++;
            }
        } else {
            if ((x - 1 >= 1) && (x - 1 <= boardSize) && (y - 1 >= 1) && (y - 1 <= boardSize)) {
                countMoves++;
            }
        }
        if (positions.containsKey((x - 1) + " " + y)) {
            if (positions.get(x + " " + y).color != positions.get((x - 1) + " " + y).color) {
                countMoves++;
            }
        } else {
            if ((x - 1 >= 1) && (x - 1 <= boardSize) && (y >= 1) && (y <= boardSize)) {
                countMoves++;
            }
        }
        if (positions.containsKey((x - 1) + " " + (y + 1))) {
            if (positions.get(x + " " + y).color != positions.get((x - 1) + " " + (y + 1)).color) {
                countMoves++;
            }
        } else {
            if ((x - 1 >= 1) && (x - 1 <= boardSize) && (y + 1 >= 1) && (y + 1 <= boardSize)) {
                countMoves++;
            }
        }
        if (positions.containsKey(x + " " + (y + 1))) {
            if (positions.get(x + " " + y).color != positions.get(x + " " + (y + 1)).color) {
                countMoves++;
            }
        } else {
            if ((x >= 1) && (x <= boardSize) && (y + 1 >= 1) && (y + 1 <= boardSize)) {
                countMoves++;
            }
        }
        if (positions.containsKey((x + 1) + " " + (y + 1))) {
            if (positions.get(x + " " + y).color != positions.get((x + 1) + " " + (y + 1)).color) {
                countMoves++;
            }
        } else {
            if ((x + 1 >= 1) && (x + 1 <= boardSize) && (y + 1 >= 1) && (y + 1 <= boardSize)) {
                countMoves++;
            }
        }
        if (positions.containsKey((x + 1) + " " + y)) {
            if (positions.get(x + " " + y).color != positions.get((x + 1) + " " + y).color) {
                countMoves++;
            }
        } else {
            if ((x + 1 >= 1) && (x + 1 <= boardSize) && (y >= 1) && (y <= boardSize)) {
                countMoves++;
            }
        }
        if (positions.containsKey((x + 1) + " " + (y - 1))) {
            if (positions.get(x + " " + y).color != positions.get((x + 1) + " " + (y - 1)).color) {
                countMoves++;
            }
        } else {
            if ((x + 1 >= 1) && (x + 1 <= boardSize) && (y - 1 >= 1) && (y - 1 <= boardSize)) {
                countMoves++;
            }
        }
        if (positions.containsKey(x + " " + (y - 1))) {
            if (positions.get(x + " " + y).color != positions.get(x + " " + (y - 1)).color) {
                countMoves++;
            }
        } else {
            if ((x >= 1) && (x <= boardSize) && (y - 1 >= 1) && (y - 1 <= boardSize)) {
                countMoves++;
            }
        }
        return countMoves;
    }
    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int countCaptures = 0;
        int x = position.getX();
        int y = position.getY();
        if (positions.containsKey((x - 1) + " " + (y - 1))) {
            if (positions.get(x + " " + y).color != positions.get((x - 1) + " " + (y - 1)).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey((x - 1) + " " + y)) {
            if (positions.get(x + " " + y).color != positions.get((x - 1) + " " + y).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey((x - 1) + " " + (y + 1))) {
            if (positions.get(x + " " + y).color != positions.get((x - 1) + " " + (y + 1)).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey(x + " " + (y + 1))) {
            if (positions.get(x + " " + y).color != positions.get(x + " " + (y + 1)).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey((x + 1) + " " + (y + 1))) {
            if (positions.get(x + " " + y).color != positions.get((x + 1) + " " + (y + 1)).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey((x + 1) + " " + y)) {
            if (positions.get(x + " " + y).color != positions.get((x + 1) + " " + y).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey((x + 1) + " " + (y - 1))) {
            if (positions.get(x + " " + y).color != positions.get((x + 1) + " " + (y - 1)).color) {
                countCaptures++;
            }
        }
        if (positions.containsKey(x + " " + (y - 1))) {
            if (positions.get(x + " " + y).color != positions.get(x + " " + (y - 1)).color) {
                countCaptures++;
            }
        }
        return countCaptures;
    }
}
/**
 * A subclass Pawn which contain methods for counting answers for Pawn piece.
 */
class Pawn extends ChessPiece {
    Pawn(PiecePosition piecePosition, PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }
    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int x = position.getX();
        int y = position.getY();
        int countMoves = 0;
        if (positions.get(x + " " + y).color == PieceColor.BLACK) {
            if (!positions.containsKey(x + " " + (y - 1))) {
                if (y - 1 >= 1 && y - 1 <= boardSize) {
                    countMoves++;
                }
            }
        }
        if (positions.get(x + " " + y).color == PieceColor.WHITE) {
            if (!positions.containsKey(x + " " + (y + 1))) {
                if (y + 1 >= 1 && y + 1 <= boardSize) {
                    countMoves++;
                }
            }
        }
        return countMoves + getCapturesCount(positions, boardSize);
    }
    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int x = position.getX();
        int y = position.getY();
        int countCaptures = 0;
        if (positions.get(x + " " + y).color == PieceColor.BLACK) {
            if ((x - 1) > 0 && (y - 1 > 0)) {
                if (positions.containsKey((x - 1) + " " + (y - 1)) && positions.get((x - 1) + " " + (y - 1)).color
                        == PieceColor.WHITE) {
                    countCaptures++;
                }
            }
            if ((x + 1) <= boardSize && (y - 1) > 0) {
                if (positions.containsKey((x + 1) + " " + (y - 1)) && positions.get((x + 1) + " " + (y - 1)).color
                        == PieceColor.WHITE) {
                    countCaptures++;
                }
            }

        }
        if (positions.get(x + " " + y).color == PieceColor.WHITE) {
            if ((x - 1) > 0 && (y + 1) <= boardSize) {
                if (positions.containsKey((x - 1) + " " + (y + 1)) && positions.get((x - 1) + " " + (y + 1)).color
                        == PieceColor.BLACK) {
                    countCaptures++;
                }
            }
            if ((x + 1) <= boardSize && (y + 1) <= boardSize) {
                if (positions.containsKey((x + 1) + " " + (y + 1)) && positions.get((x + 1) + " " + (y + 1)).color
                        == PieceColor.BLACK) {
                    countCaptures++;
                }
            }
        }
        return countCaptures;
    }
}
/**
 * A subclass Bishop which contain methods for counting answers for Bishop piece.
 */
class Bishop extends ChessPiece implements BishopMovement {
    Bishop(PiecePosition piecePosition, PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }
    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalMovesCount(position, color, positions, boardSize);
    }
    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalCapturesCount(position, color, positions, boardSize);
    }
    @Override
    public int getDiagonalMovesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
                                     int boardSize) {
        return BishopMovement.super.getDiagonalMovesCount(position, color, positions, boardSize);
    }
    @Override
    public int getDiagonalCapturesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
                                        int boardSize) {
        return BishopMovement.super.getDiagonalCapturesCount(position, color, positions, boardSize);
    }
}
/**
 * A subclass Rook which contain methods for counting answers for Rook piece.
 */
class Rook extends ChessPiece implements RookMovement {
    Rook(PiecePosition piecePosition, PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }
    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalMovesCount(position, color, positions, boardSize);
    }
    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalCapturesCount(position, color, positions, boardSize);
    }
    @Override
    public int getOrthogonalMovesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
                                       int boardSize) {
        return RookMovement.super.getOrthogonalMovesCount(position, color, positions, boardSize);
    }
    @Override
    public int getOrthogonalCapturesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
                                          int boardSize) {
        return RookMovement.super.getOrthogonalCapturesCount(position, color, positions, boardSize);
    }
}
/**
 * A subclass Queen which contain methods for counting answers for Queen piece.
 */
class Queen extends ChessPiece implements BishopMovement, RookMovement {
    Queen(PiecePosition piecePosition, PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }
    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalMovesCount(position, color, positions, boardSize) + getOrthogonalMovesCount(position, color,
                positions, boardSize);
    }
    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalCapturesCount(position, color, positions, boardSize) + getOrthogonalCapturesCount(position,
                color, positions, boardSize);
    }
    @Override
    public int getOrthogonalMovesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
                                       int boardSize) {
        return RookMovement.super.getOrthogonalMovesCount(position, color, positions, boardSize);
    }
    @Override
    public int getDiagonalMovesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
                                     int boardSize) {
        return BishopMovement.super.getDiagonalMovesCount(position, color, positions, boardSize);
    }
    @Override
    public int getOrthogonalCapturesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
                                          int boardSize) {
        return RookMovement.super.getOrthogonalCapturesCount(position, color, positions, boardSize);
    }
    @Override
    public int getDiagonalCapturesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
                                        int boardSize) {
        return BishopMovement.super.getDiagonalCapturesCount(position, color, positions, boardSize);
    }
}
/**
 * A class for exceptions.
 */
class Exception {
    public String getMessage() {
        return null;
    }
}
/**
 * A subclass for board size exceptions.
 */
class InvalidBoardSizeException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid board size";
    }
}
/**
 * A subclass for number of pieces exceptions.
 */
class InvalidNumberOfPiecesException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid number of pieces";
    }
}
/**
 * A subclass for piece name exceptions.
 */
class InvalidPieceNameException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece name";
    }
}
/**
 * A subclass for piece color exceptions.
 */
class InvalidPieceColorException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece color";
    }
}
/**
 * A subclass for piece position exceptions.
 */
class InvalidPiecePositionException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece position";
    }
}
/**
 * A subclass for given kings exceptions.
 */
class InvalidGivenKingsException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid given Kings";
    }
}
/**
 * A subclass for other exceptions.
 */
class InvalidInputException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid input";
    }
}
