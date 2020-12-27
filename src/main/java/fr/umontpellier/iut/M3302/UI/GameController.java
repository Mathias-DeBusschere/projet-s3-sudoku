package fr.umontpellier.iut.M3302.UI;

import fr.umontpellier.iut.M3302.sudoku.Case;
import fr.umontpellier.iut.M3302.sudoku.Game;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Random;

import static java.lang.Math.sqrt;

public class GameController {
    private final Game game;
    private boolean annotating = false;
    private int currentRow = 0, currentCol = 0;

    @FXML
    private StackPane gameBoard;
    @FXML
    private Button note;

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
                stackPane.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-font-size:" +
                        ((int) 600.0 / game.getSize() / 3) + ";");
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
        printBlockSeparator();
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

    private void printBlockSeparator() {
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
            case "1" -> {
                game.setValue(1, currentRow, currentCol);
                playWriteSound();
            }
            case "2" -> {
                game.setValue(2, currentRow, currentCol);
                playWriteSound();
            }
            case "3" -> {
                game.setValue(3, currentRow, currentCol);
                playWriteSound();
            }
            case "4" -> {
                game.setValue(4, currentRow, currentCol);
                playWriteSound();
            }
            case "5" -> {
                game.setValue(5, currentRow, currentCol);
                playWriteSound();
            }
            case "6" -> {
                game.setValue(6, currentRow, currentCol);
                playWriteSound();
            }
            case "7" -> {
                game.setValue(7, currentRow, currentCol);
                playWriteSound();
            }
            case "8" -> {
                game.setValue(8, currentRow, currentCol);
                playWriteSound();
            }
            case "9" -> {
                game.setValue(9, currentRow, currentCol);
                playWriteSound();
            }
            case "0" -> {
                game.setValue(0, currentRow, currentCol);
                playWriteSound();
            }
        }
        if (game.getCase(currentRow, currentCol) != null) {
            switch (keyEvent.getCode()) {
                case BACK_SPACE -> {
                    game.setValue(0, currentRow, currentCol);
                    playEraseSound();
                }
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

    public void playWriteSound() {
        Clip sonEcriture;
        try {

            Random random = new Random();
            int randomInt = random.nextInt(5 - 1) + 1;

            sonEcriture = AudioSystem.getClip();
            AudioInputStream inputStream =
                    AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/write" + randomInt + ".wav"));
            sonEcriture.open(inputStream);
            sonEcriture.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void playEraseSound() {
        Clip sonEfface;
        try {
            sonEfface = AudioSystem.getClip();
            AudioInputStream inputStream =
                    AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/efface.wav"));
            sonEfface.open(inputStream);
            sonEfface.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
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

    public void updateCase(Case c, StackPane cPane) {
        cPane.getChildren().clear();
        if (c.getValue() != 0) {
            Text text = new Text(String.valueOf(c.getValue()));
            cPane.getChildren().add(text);
            if (c.isError())
                cPane.setStyle(cPane.getStyle() + "-fx-background-color: #fd7575;");
            else
                cPane.setStyle(cPane.getStyle() + "-fx-background-color: white;");
            if (c.isInitial())
                cPane.setStyle(cPane.getStyle() + "-fx-font-weight: bold;");
            else
                cPane.setStyle(cPane.getStyle() + "-fx-font-weight: normal;");
            if (c.isHint()) {
                System.out.println(cPane.getChildren());
                ((Text) cPane.getChildren().get(0)).setFill(new Color(0, 0.75, 0, 1));
            } else
                cPane.setStyle(cPane.getStyle());

        } else {
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            for (int i = 0; i < game.getSize(); i++) {
                StackPane stackPane = new StackPane();
                stackPane.setPrefSize(600.0 / Math.pow(game.getSize(), 1.5), 600.0 / Math.pow(game.getSize(), 1.5));
                if (c.getNotes()[i]) {
                    Text text = new Text((String.valueOf(i + 1)));
                    text.setStyle("-fx-font-size: " + ((int) 600.0 / Math.pow(game.getSize(), 1.75)) + ";");
                    stackPane.getChildren().add(text);
                }
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
    private void hint(MouseEvent event) {
        try {
            game.addIndice(currentRow, currentCol);
        } catch (Throwable throwable) {
            System.out.println(throwable.getMessage());
        }
        updateAllCases();
    }

    @FXML
    private void giveUp(MouseEvent event) {
        game.solve();
        updateAllCases();
    }

    @FXML
    private void validate(MouseEvent event) {
        finished(game.validate());
    }

    public void finished(boolean correct) {
        if (correct) {
            Clip sonVictoire;
            try {
                sonVictoire = AudioSystem.getClip();
                AudioInputStream inputStream =
                        AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/victoire.wav"));
                sonVictoire.open(inputStream);
                sonVictoire.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }


            ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/images/congratulations.png")));
            img.setPreserveRatio(true);
            img.setFitWidth(600 - (600.0 / game.getSize()));
            img.setTranslateY(-100);

            Label labelTimer = new Label();
            labelTimer.setText("39.56 min");
            labelTimer.setStyle(
                    "-fx-background-color:rgba(31,31,31,0.95);-fx-font-size:30px;-fx-padding: 20;-fx-text-fill: white;-fx-border-width: 3px;-fx-border-color: white");
            labelTimer.setTranslateY(50);

//            Label labelScore = new Label();
//            labelScore.setText("9670 points");
//            labelScore.setStyle("-fx-background-color:rgba(31,31,31,0.95);-fx-font-size:30px;-fx-padding: 20;-fx-text-fill: white;-fx-border-width: 3px;-fx-border-color: white");
//            labelScore.setTranslateY(150);

            Task<Void> sleep500ms = new Task<>() {
                @Override
                protected Void call() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            sleep500ms.setOnSucceeded(workerStateEvent -> gameBoard.getChildren().add(labelTimer));

//            Task<Void> sleep1000ms = new Task<>() {
//                @Override
//                protected Void call() {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return null;
//                }
//            };
//            sleep1000ms.setOnSucceeded(workerStateEvent -> gameBoard.getChildren().add(labelScore));

            gameBoard.getChildren().add(img);
            new Thread(sleep500ms).start();
//            new Thread(sleep1000ms).start();
        }
    }

    @FXML
    private void note(MouseEvent event) {
        annotating = !annotating;
        if (annotating)
            note.setStyle("-fx-background-color: grey");
        else
            note.setStyle("-fx-background-color: white");
        updateAllCases();
    }

    @FXML
    private void actionBouton_1() {
        actionBoutonRel(1);
    }

    public void actionBoutonRel(int btnNb) {
        if (annotating) {
            game.setValue(0, currentRow, currentCol);
            game.getCase(currentRow, currentCol).toggleNote(btnNb);
        } else
            game.setValue(btnNb, currentRow, currentCol);
        playWriteSound();
        updateAllCases();
    }

    @FXML
    private void actionBouton_2() {
        actionBoutonRel(2);
    }

    @FXML
    private void actionBouton_3() {
        actionBoutonRel(3);
    }

    @FXML
    private void actionBouton_4() {
        actionBoutonRel(4);
    }

    @FXML
    private void actionBouton_5() {
        actionBoutonRel(5);
    }

    @FXML
    private void actionBouton_6() {
        actionBoutonRel(6);
    }

    @FXML
    private void actionBouton_7() {
        actionBoutonRel(7);
    }

    @FXML
    private void actionBouton_8() {
        actionBoutonRel(8);
    }

    @FXML
    private void actionBouton_9() {
        actionBoutonRel(9);
    }

    @FXML
    private void erase() {
        if (annotating) {
            game.getCase(currentRow, currentCol).setValue(0);
            game.getCase(currentRow, currentCol).clearNotes();
        } else
            game.getCase(currentRow, currentCol).setValue(0);
        updateAllCases();
        playEraseSound();
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
    private void quitGame(MouseEvent event) {
        Platform.exit();
    }
}
