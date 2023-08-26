package Game;

import java.util.Random;

public class Board {

    // Used this link to be able to print output in color.
    // This makes reading the board a lot easier for the player.
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";


    private final Cell[][] cell;

    public Board(int height, int width){
        cell = new Cell[height][width];
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                cell[i][j] = Cell.ClosedField;
            }
        }
    }

    public int height(){
        return cell.length;
    }
    public int width(){
        return cell[0].length;
    }

    public void randomize(){
        for (int i = 0; i < height(); i++){
            for (int j = 0; j < width(); j++) {
                Random r = new Random();
                int rand = r.nextInt(99);
                if (rand < 15){
                    cell[i][j] = Cell.ClosedBomb;
                }
            }
        }
    }

    public Cell whatKind(int y, int x){
        return cell[y][x];
    }
    public boolean validSpot(int y, int x){
        if ((-1<x) && (-1<y)) {
            return ((y < height()) && (x < width()));
        }
        else{return false;}
    }




    public void changeFlagStatus(int y, int x){
        if (cell[y][x] == Cell.FlaggedBomb){
            cell[y][x] = Cell.ClosedBomb;
        }
        else if (cell[y][x] == Cell.FlaggedField){
            cell[y][x] = Cell.ClosedField;
        }
        else if (cell[y][x] == Cell.ClosedBomb){
            cell[y][x] = Cell.FlaggedBomb;
        }
        else if (cell[y][x] == Cell.ClosedField){
            cell[y][x] = Cell.FlaggedField;
        }
    }

    // Checks surrounding neighbors for bombs
    public int bombCount(int y, int x) {
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (validSpot(y+i,x+j)){
                    if ((cell[y+i][x+j]==Cell.ClosedBomb) || (cell[y+i][x+j]==Cell.FlaggedBomb)){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // Refers to the number of surrounding bombs
    public void number(int y, int x){
        int count = bombCount(y, x);
        if (count == 1) { cell[y][x] = Cell.One; }
        if (count == 2) { cell[y][x] = Cell.Two; }
        if (count == 3) { cell[y][x] = Cell.Three; }
        if (count == 4) { cell[y][x] = Cell.Four; }
        if (count == 5) { cell[y][x] = Cell.Five; }
        if (count == 6) { cell[y][x] = Cell.Six; }
        if (count == 7) { cell[y][x] = Cell.Seven; }
        if (count == 8) { cell[y][x] = Cell.Eight; }
    }

    // If a neighbor cell is a Field, it reveals the cell
    // If the neighbor cell has no surrounding bombs, it runs the method again
    // with the new cell. This allows for the large openings that stop at numbers
    // that you get in the normal version of the game.
    public void openNeighbors(int y, int x) {
        if (bombCount(y, x) == 0) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if ((validSpot(y + i, x + j)) && ((cell[y + i][x + j] != Cell.OpenField))) {
                        Cell spot = cell[y + i][x + j];
                        if ((spot == Cell.ClosedField) || (spot == Cell.FlaggedField)) {
                            cell[y + i][x + j] = Cell.OpenField;
                            number(y+i,x+j);
                            if (cell[y + i][x + j] == Cell.OpenField) {
                                openNeighbors(y + i, x + j);
                            }
                        }
                    }
                }
            }
        }
        else{number(y,x);}
    }

    public boolean winCheck(){
        boolean win = true;
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                if ((cell[i][j] == Cell.ClosedField) || (cell[i][j] == Cell.FlaggedField)){
                    win = false;
                }
                else if(cell[i][j] == Cell.ShownBomb){
                    win = false;
                }
            }
        }
        return win;
    }

    public int boardBombCount(){
        int count = 0;
        for (int i = 0; i < height(); i++){
            for (int j = 0; j < width(); j++){
                if (cell[i][j] == Cell.ClosedBomb){
                    count++;
                }
            }
        }
        return count;
    }
}
