/*
Homework 7 : Connect Four GUI
File Name : ConnectFourGUI.java
 */
package connectfour.gui;

import connectfour.model.ConnectFourBoard;
import connectfour.model.Observer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import static connectfour.model.ConnectFourBoard.COLS;
import static connectfour.model.ConnectFourBoard.ROWS;
import connectfour.model.ConnectFourBoard.Player;

/**
 * The JavaFX compliant GUI version of the Connect Four game.
 *
 * @author Meenu Gigi, mg2578@rit.edu
 * @author Vedika Vishwanath Painjane, vp2312@rit.edu
 */
public class ConnectFourGUI extends Application implements Observer<ConnectFourBoard> {

    /** the board */
    private ConnectFourBoard board;
    /** the layout for game */
    private BorderPane borderPane;
    /** the layout to display buttons */
    private GridPane gridPane;
    /** the current player to take next turn */
    private Label currentPlayer;
    /** the number of moves made in the game */
    private Label numMovesMade;
    /** the status of game */
    private Label gameStatus;
    /** the layout for game */
    private HBox hBox;

    /** image corresponding to player 1 */
    private javafx.scene.image.Image p1black =
            new Image(getClass().getResourceAsStream(
                    "p1black.png"));
    /** image corresponding to player 2 */
    private javafx.scene.image.Image p2red =
            new Image(getClass().getResourceAsStream("p2red.png"));
    /** image corresponding to empty disc */
    private javafx.scene.image.Image empty =
            new Image(getClass().getResourceAsStream("empty.png"));

    /**
     * Function which builds a grid of the buttons to return
     *
     * @return grid pane
     */
    private GridPane makeGridPane(){
        gridPane = new GridPane();

        for (int row=0; row<ROWS; ++row) {
            for (int col=0; col<COLS; ++col) {
                //the player who occupies that specific disc location.
                Player player = board.getContents(row, col);
                //create a button of that player, initially NONE
                GridButton button = new GridButton(player);
                int finalCol = col;
                button.setOnAction(event -> {
                    if (this.board.isValidMove(finalCol)) {
                        this.board.makeMove(finalCol);
                    }
                });
                gridPane.add(button, col, row);
            }
        }
        return gridPane;
    }

    /**
     * Class which creates the button.
     */
    private class GridButton extends Button {
        /**
         * Create the button according to the player
         *
         * @param player        the player
         */
        public GridButton(Player player) {
            Image image;
            switch (player) {
                case P1:
                    image = p1black;
                    break;
                case P2:
                    image = p2red;
                    break;
                default:
                    image = empty;
            }
            this.setGraphic(new ImageView(image));
        }
    }

    /**
     * Creates the model and add ourselves as an observer.
     */
    @Override
    public void init() {
        this.board = new ConnectFourBoard();
        board.addObserver( this );
    }

    /**
     * Creates the BorderPane and GridPane.
     * Calls function to display game statistics.
     * Sets scene and stage title.
     *
     * @param stage         the stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        borderPane = new BorderPane();
        gridPane = makeGridPane();
        borderPane.setCenter(gridPane);
        displayInfo();
        Scene scene = new Scene(borderPane);
        stage.setTitle("ConnectFourGUI");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Creates layout to display game statistics.
     */
    private void displayInfo() {
        hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        //display number of moves made
        this.numMovesMade =
                new Label( String.valueOf( this.board.getMovesMade() ) );
        createSpacing(numMovesMade);
        Label numMoves = new Label(" moves made \t\t\t");
        createSpacing(numMoves);
        //display current player
        Label playerLabel = new Label(" \t\t\t Current player: ");
        createSpacing(playerLabel);
        this.currentPlayer =
                new Label( String.valueOf( this.board.getCurrentPlayer() ) );
        createSpacing(currentPlayer);
        //display game status
        Label status = new Label("\t\t\t\t Status: ");
        createSpacing(status);
        this.gameStatus =
                new Label( String.valueOf( this.board.getGameStatus() ) );
        createSpacing(gameStatus);
        borderPane.setBottom(hBox);
    }

    /**
     * Specifies the spacing to be left at top, left, right and bottom edge.
     * Adds label to Hbox.
     *
     * @param label         label to be added
     */
    private void createSpacing(Label label) {
        hBox.setMargin(label, new Insets(40, 0, 0, 0));
        hBox.getChildren().add(label);
    }

    /**
     * Updates current player, number of moves and game status by getting
     * current values from the model.
     * Disables GUI components when a player wins or Tie occurs.
     *
     * @param board         the board
     */
    private void refresh( ConnectFourBoard board ) {
        borderPane.setCenter(makeGridPane());
        currentPlayer.setText( String.valueOf( board.getCurrentPlayer() ) );
        numMovesMade.setText(String.valueOf(board.getMovesMade()));
        gameStatus.setText(String.valueOf(board.getGameStatus()));
        //disable GUI components until user closes window.
        if((board.getGameStatus() == ConnectFourBoard.Status.P1_WINS) ||
                (board.getGameStatus() == ConnectFourBoard.Status.P2_WINS) ||
                (board.getGameStatus() == ConnectFourBoard.Status.TIE)){
            borderPane.setDisable(true);
        }
    }

    /**
     * Update model if current thread is JavaFX Application thread.
     * Else wait until current thread is JavaFX Application thread.
     *
     * @param connectFourBoard         the board
     */
    @Override
    public void update(ConnectFourBoard connectFourBoard) {
        if (Platform.isFxApplicationThread()){
            this.refresh(connectFourBoard);
        }
        else{
            Platform.runLater(() -> this.refresh(connectFourBoard));
        }
    }

    /**
     * Main method to launch the application.
     * @param args Ignored
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}

