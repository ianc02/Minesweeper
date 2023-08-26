package Game;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.text.CollationElementIterator;

public class GameController {

    @FXML
    private TextField flagNumber;

    @FXML
    private ChoiceBox<String> mode;

    @FXML
    private Pane grid;

    @FXML
    private TextField timer;

    @FXML
    private TextArea guide;

    private Board board;

    private Square[][] squares;

    private int squareSize;

    private boolean gameOver = false;

    private int flags;

    private Movement clock;

    private boolean clockON = false;

    private class Movement extends AnimationTimer {

        private long FRAMES_PER_SEC = 50L;
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;

        private long last = 0;
        private int time = 0;

        @Override
        public void handle(long now) {
            if (now - last > 1000000000L){
                last = now;
                time++;
                timer.setText("Time: " + time);
            }
        }
    }

    @FXML
    public void initialize() {
        timer.setEditable(false);
        flagNumber.setEditable(false);
        mode.getItems().removeAll(mode.getItems());
        mode.getItems().addAll("Easy", "Medium", "Hard");
        mode.getSelectionModel().select("Easy");
        guide.setEditable(false);
        guide.setText("Guide\nLeft click to reveal tile\nRight click to place flag" +
                "\nBlack: Flag\nYellow: 1\nOrange: 2\nRed: 3\nPurple: 4\nBlue: 5" +
                "\nCyan: 6\nLimegreen: 7\nBrown: 8");
    }

    public void gridSetup(){
        if(clockON){
            clock.stop();
        }
        grid.getChildren().clear();
        clock = new Movement();
        clock.start();
        clockON = true;
        gameOver = false;
        int panesize = 420;
        int size = 10;
        if (mode.getValue().equals("Medium")){
            size = 15;
        }
        if (mode.getValue().equals("Hard")){
            size = 20;
        }
        board = new Board(size,size);
        board.randomize();
        flags = board.boardBombCount();
        flagNumber.setText("FLAGS: " + flags);
        squareSize = panesize/size;
        squares = new Square[size][size];
        for (int i = 0; i < panesize; i+= squareSize){
            for (int j = 0; j < panesize; j+= squareSize){
                Rectangle r = new Rectangle(i, j, squareSize, squareSize);
                r.setFill(Color.LIGHTGRAY);
                r.setStroke(Color.BLACK);
                grid.getChildren().add(r);
                Square square = new Square(i,j,r,grid, squareSize);
                squares[i/squareSize][j/squareSize] = square;
                r.setOnMousePressed(event -> pressed(event, square));
            }
        }
        updateSquares();
    }
    public void updateSquares(){
        for(int i = 0; i<squares.length; i++){
            for(int j = 0; j<squares.length; j++){
                squares[i][j].setType(board.whatKind(i,j));
                squares[i][j].typeColor();
                if (squares[i][j].getType() == Cell.ClosedBomb || squares[i][j].getType() == Cell.ClosedField){
                    squares[i][j].setClosed(true);
                }
                else{
                    squares[i][j].setClosed(false);
                }
            }
        }
    }
    public void pressed(MouseEvent event, Square square){
        if (!gameOver) {
            if (event.getButton() == MouseButton.SECONDARY && square.isClosed()){
                if (square.getFlagStatus()){
                    square.changeFlagStatus();
                    square.setColor(Color.LIGHTGRAY);
                    flags++;
                    flagNumber.setText("FLAGS: "+flags);
                }
                else {
                    square.setColor(Color.BLACK);
                    square.changeFlagStatus();
                    flags--;
                    flagNumber.setText("FLAGS: "+flags);
                }
            }
            if (event.getButton() == MouseButton.PRIMARY && !square.getFlagStatus()) {
                if (square.getType() == Cell.ClosedBomb) {
                    flagNumber.setText("You Failed");
                    clock.stop();
                    gameOver = true;
                    for (int i = 0; i < squares.length; i++){
                        for (int j = 0; j < squares.length; j++){
                            if (board.whatKind(i,j) == Cell.ClosedBomb){
                                squares[i][j].initialize();
                                squares[i][j].start();
                                squares[i][j].setColor(Color.DARKRED);
                            }
                        }
                    }
                }
                if (square.getType() == Cell.ClosedField) {
                    board.openNeighbors(square.getY() / squareSize, square.getX() / squareSize);
                    updateSquares();

                    if (board.winCheck()){
                        gameOver = true;
                        flagNumber.setText("You Win!");
                        clock.stop();

                    }
                }
            }
        }
        else{
            for (int i = 0; i < squares.length; i++) {
                for (int j = 0; j < squares.length; j++) {
                    if (board.whatKind(i,j) == Cell.ClosedBomb){
                        squares[i][j].stop();
                    }
                }
            }
        }
    }
}
