package fr.umontpellier.iut.M3302.UI;

import fr.umontpellier.iut.M3302.sudoku.Case;
import fr.umontpellier.iut.M3302.sudoku.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Math.sqrt;

public class GameController {
    private final Game game;

    @FXML
    private StackPane gameBoard;
    @FXML
    private Game g;
    @FXML
    private Button valider;
    @FXML
    private Button recommencer;
    @FXML
    private Button fermerJeu;
    @FXML
    private Button pause;
    @FXML
    private Button reprendre;
    @FXML
    private Button indice;
    @FXML
    private Button abandonner;
    @FXML
    private Button note;

    private boolean annotating = false;
    private int currentRow = 0, currentCol = 0;

    public GameController(Game game) {
        this.game = game;
    }

    public void initialize() {
        double size = (600.0 / game.getSize()) * game.getSize();
        this.gameBoard.setPrefSize(size, size);
        this.gameBoard.setMaxSize(size, size);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        gameBoard.getChildren().add(gridPane);

        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                StackPane stackPane = new StackPane();
                stackPane.setPrefSize(600.0 / game.getSize(), 600.0 / game.getSize());
                stackPane.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-font-size:" + 600.0/game.getSize()/3 +";");
                int finalI = i;
                int finalJ = j;
                stackPane.setOnMouseClicked(event -> caseClicked(finalI, finalJ));
                if (game.hasValue(i, j)) {
                    Text text = new Text(String.valueOf(game.getCase(i, j).getValue()));
                    stackPane.getChildren().add(text);
                }
                gridPane.add(stackPane, j, i);
            }
        }
        affichageLigneBlock();

    }

    public void caseClicked(int i, int j) {
        StackPane stackPane = (StackPane) ((GridPane) (gameBoard.getChildren().get(0))).getChildren()
                .get(currentRow * game.getSize() + currentCol);
        stackPane.setStyle(
                stackPane.getStyle() + "-fx-border-color: grey; " + "-fx-border-width: 1; -fx-border-radius: 0;");
        currentRow = i;
        currentCol = j;
        stackPane = (StackPane) ((GridPane) (gameBoard.getChildren().get(0))).getChildren()
                .get(currentRow * game.getSize() + currentCol);
        stackPane.setStyle(
                stackPane.getStyle() + "-fx-border-width: 2px;" + "-fx-border-color: orange;" + "-fx-border-radius: " +
                        "3;");
    }

    private void affichageLigneBlock() {
        double block = 600.0 / game.getSize() * sqrt(game.getSize());
        double size = (600.0 / game.getSize()) * game.getSize();

        for (int i = 1; i < (int) sqrt(game.getSize()); i++) {
            Line linev = new Line();
            Line lineh = new Line();

            linev.setEndY(size);
            linev.setTranslateX(-size / 2 + i * block);
            linev.setStrokeWidth(3);

            lineh.setEndX(size);
            lineh.setTranslateY(-size / 2 + i * block);
            lineh.setStrokeWidth(3);

            gameBoard.getChildren().add(linev);
            gameBoard.getChildren().add(lineh);
        }
    }

    public void shortcuts(KeyEvent keyEvent) {
        switch (keyEvent.getText()) {
            case "1" -> game.setValue(1,currentRow,currentCol);
            case "2" -> game.setValue(2,currentRow,currentCol);
            case "3" -> game.setValue(3,currentRow,currentCol);
            case "4" -> game.setValue(4,currentRow,currentCol);
            case "5" -> game.setValue(5,currentRow,currentCol);
            case "6" -> game.setValue(6,currentRow,currentCol);
            case "7" -> game.setValue(7,currentRow,currentCol);
            case "8" -> game.setValue(8,currentRow,currentCol);
            case "9" -> game.setValue(9,currentRow,currentCol);
            case "0" -> game.setValue(0,currentRow,currentCol);
        }
        if (game.getCase(currentRow, currentCol) != null) {
            switch (keyEvent.getCode()) {
                case BACK_SPACE -> game.setValue(0,currentRow,currentCol);
                case UP, Z -> {
                    if (currentRow >= 1)
                        caseClicked(currentRow - 1, currentCol);
                    else
                        caseClicked(game.getSize() - 1, currentCol);
                }

                case DOWN, S -> {
                    if (currentRow < game.getSize() - 1)
                        caseClicked(currentRow + 1, currentCol);
                    else
                        caseClicked(0, currentCol);
                }

                case RIGHT, D -> {
                    if (currentCol < game.getSize() - 1)
                        caseClicked(currentRow, currentCol + 1);
                    else
                        caseClicked(currentRow, 0);
                }

                case LEFT, Q -> {
                    if (currentCol >= 1)
                        caseClicked(currentRow, currentCol - 1);
                    else
                        caseClicked(currentRow, game.getSize() - 1);
                }

                case TAB -> {
                    if (currentCol < game.getSize() - 1) {
                        caseClicked(currentRow, currentCol + 1);
                    } else if (currentRow < game.getSize() - 1)
                        caseClicked(currentRow + 1, 0);
                    else {
                        caseClicked(0, 0);
                    }
                }

            }
            updateAllCases();
            keyEvent.consume();
        }


    }

    public void updateSelCase() {
        updateCase(game.getCase(currentRow, currentCol),
                (StackPane) ((GridPane) (gameBoard.getChildren().get(0))).getChildren()
                        .get(currentRow * game.getSize() + currentCol));
    }

    public void updateCase(Case c, StackPane cPane) {
        cPane.getChildren().clear();
        if (c.getValue() != 0) {
            Text text = new Text(String.valueOf(c.getValue()));
            cPane.getChildren().add(text);
            if (c.isError())
                cPane.setStyle(cPane.getStyle() + "-fx-background-color: #fd7575;");
            else
                cPane.setStyle(cPane.getStyle() + "-fx-background-color: white;");

        } else {
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            for (int i = 0; i < game.getSize(); i++) {
                StackPane stackPane = new StackPane();
                stackPane.setPrefSize(600.0 / Math.pow(game.getSize(), 1.5), 600.0 / Math.pow(game.getSize(), 1.5));
                if (c.getNotes()[i])
                    stackPane.getChildren().add(new Text(String.valueOf(i + 1)));
                gridPane.add(stackPane, (int) (i % Math.sqrt(game.getSize())), (int) (i / Math.sqrt(game.getSize())));
            }
            cPane.getChildren().add(gridPane);
        }
    }

    @FXML
    private void recommencer(MouseEvent event) {
        game.restart();
        updateAllCases();
    }

    public void updateAllCases() {
        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                updateCase(game.getCase(i, j),
                        (StackPane) ((GridPane) (gameBoard.getChildren().get(0))).getChildren()
                                .get(i * game.getSize() + j));
            }
        }
    }

    //
    @FXML
    private void pause(MouseEvent event) {
        //timer stop
        //timer required
    }

    @FXML
    private void reprendre(MouseEvent event) {
        //timer play
        //timer required
    }

    @FXML
    private void indice(MouseEvent event) {
//        g.getCaseselectionne().setValeur(g.getSolution()[g.getCaseselectionne().getLigne()][g.getCaseselectionne().getColonne()]);
//        g.getCaseselectionne().setIndiceStyle();
    }

    @FXML
    private void abandonner(MouseEvent event) {
        game.solve();
        updateAllCases();
    }

    @FXML
    private void valider(MouseEvent event) {
        System.out.println(game.validate());
    }

    @FXML
    private void note(MouseEvent event) {
        annotating = !annotating;
        if (annotating)
            note.setStyle("-fx-background-color: grey");
        else
            note.setStyle("-fx-background-color: white");
        updateSelCase();
    }

    @FXML
    private void actionBouton_1() {
        if (annotating) {
            game.getCase(currentRow, currentCol).setValue(0);
            game.getCase(currentRow, currentCol).toggleNote(1);
        } else
            game.getCase(currentRow, currentCol).toggleValue(1);
        updateSelCase();
    }

    @FXML
    private void actionBouton_2() {
        if (annotating) {
            game.getCase(currentRow, currentCol).setValue(0);
            game.getCase(currentRow, currentCol).toggleNote(2);
        } else
            game.getCase(currentRow, currentCol).toggleValue(2);
        updateSelCase();
    }

    @FXML
    private void actionBouton_3() {
        if (annotating) {
            game.getCase(currentRow, currentCol).setValue(0);
            game.getCase(currentRow, currentCol).toggleNote(3);
        } else
            game.getCase(currentRow, currentCol).toggleValue(3);
        updateSelCase();
    }

    @FXML
    private void actionBouton_4() {
        if (annotating) {
            game.getCase(currentRow, currentCol).setValue(0);
            game.getCase(currentRow, currentCol).toggleNote(4);
        } else
            game.getCase(currentRow, currentCol).toggleValue(4);
        updateSelCase();
    }

    @FXML
    private void actionBouton_5() {
        if (annotating) {
            game.getCase(currentRow, currentCol).setValue(0);
            game.getCase(currentRow, currentCol).toggleNote(5);
        } else
            game.getCase(currentRow, currentCol).toggleValue(5);
        updateSelCase();
    }

    @FXML
    private void actionBouton_6() {
        if (annotating) {
            game.getCase(currentRow, currentCol).setValue(0);
            game.getCase(currentRow, currentCol).toggleNote(6);
        } else
            game.getCase(currentRow, currentCol).toggleValue(6);
        updateSelCase();
    }

    @FXML
    private void actionBouton_7() {
        if (annotating) {
            game.getCase(currentRow, currentCol).setValue(0);
            game.getCase(currentRow, currentCol).toggleNote(7);
        } else
            game.getCase(currentRow, currentCol).toggleValue(7);
        updateSelCase();
    }

    @FXML
    private void actionBouton_8() {
        if (annotating) {
            game.getCase(currentRow, currentCol).setValue(0);
            game.getCase(currentRow, currentCol).toggleNote(8);
        } else
            game.getCase(currentRow, currentCol).toggleValue(8);
        updateSelCase();
    }

    @FXML
    private void actionBouton_9() {
        if (annotating) {
            game.getCase(currentRow, currentCol).setValue(0);
            game.getCase(currentRow, currentCol).toggleNote(9);
        } else
            game.getCase(currentRow, currentCol).toggleValue(9);
        updateSelCase();
    }

    @FXML
    private void effacer() {
        if (annotating) {
            game.getCase(currentRow, currentCol).setValue(0);
            game.getCase(currentRow, currentCol).clearNotes();
        } else
            game.getCase(currentRow, currentCol).setValue(0);
        updateSelCase();
    }

    @FXML
    private void mainMenu(MouseEvent event) throws IOException {
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));

        Parent gamePane = gameLoader.load();

        Scene sceneGrille = new Scene(gamePane, 900, 720);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(sceneGrille);
    }

    @FXML
    private void fermerJeu(MouseEvent event) {
        Platform.exit();
    }

}
