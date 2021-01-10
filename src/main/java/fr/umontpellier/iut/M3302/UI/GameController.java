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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Random;

import static java.lang.Math.min;
import static java.lang.Math.sqrt;

public class GameController {
    private static Double screenSize;
    private final Game game;
    private boolean annotating = false;
    private int currentRow = 0, currentCol = 0;
    @FXML
    private StackPane gameBoard;
    @FXML
    private Button note;
    @FXML
    private Button button0;
    @FXML
    private Button buttonErase;
    @FXML
    private Button mainMenu;
    @FXML
    private Button quitGame;

    public GameController(Game game) {
        this.game = game;
    }

    public void initialize() {
        if (game.getSize() > 10) {
            button0.setVisible(true);
            buttonErase.setTranslateY(-15);
            mainMenu.setTranslateY(-15);
            quitGame.setTranslateY(-15);
        }

        if (screenSize == null) {
            screenSize = 700 * 0.9;
        }

        double size = (screenSize / game.getSize()) * game.getSize();
        this.gameBoard.setPrefSize(size, size);
        this.gameBoard.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        gameBoard.getChildren().add(gridPane);

        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                StackPane stackPane = new StackPane();
                stackPane.setPrefSize(screenSize / game.getSize(), screenSize / game.getSize());
                stackPane.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-font-size:" +
                        (screenSize / game.getSize() / 3) + ";");
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

        caseClicked(0, 0);
        printBlockSeparator(screenSize);
        updateAllCases(size);
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

    private void printBlockSeparator(double value) {
        double block = value / game.getSize() * sqrt(game.getSize());
        double size = value / game.getSize() * game.getSize();

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

    public void resize(double size) {
//        double width = stage.getWidth();
//        double height = stage.getHeight();
//        double smallestBorder = Math.min(stage.getHeight() * 0.9, stage.getWidth() * 750 / 900 * 0.9);
        screenSize = min(((BorderPane) gameBoard.getParent()).getHeight() * 0.9,
                ((BorderPane) gameBoard.getParent()).getWidth() * 0.9);
        gameBoard.setPrefSize(screenSize, screenSize);
        gameBoard.getChildren().remove(1, gameBoard.getChildren().size());
        printBlockSeparator(screenSize);
        updateAllCases(screenSize);
//        for (int i = 0; i < ((GridPane) gameBoard.getChildren().get(0)).getChildren().size(); i++) {
//            StackPane stackPane = ((StackPane) ((GridPane) (gameBoard.getChildren().get(0))).getChildren().get(i));
//            stackPane.setPrefSize(value / game.getSize(), value / game.getSize());
//            stackPane.setStyle(stackPane.getStyle() + "-fx-font-size: " + ((int) value / game.getSize() / 3) + ";");
//
//        }
//        gameBoard.getChildren().get(0);
//        System.out.println(gameBoard.getChildren());
    }

    public void updateAllCases(double size) {
        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                updateCase(game.getCase(i, j),
                        (StackPane) ((GridPane) (gameBoard.getChildren().get(0))).getChildren()
                                .get(i * game.getSize() + j), size);
            }
        }
    }

    public void updateCase(Case c, StackPane cPane, double size) {
        cPane.setPrefSize(size / game.getSize(), size / game.getSize());
        cPane.getChildren().clear();

        if (c.isError())
            cPane.setStyle(cPane.getStyle() + "-fx-background-color: #fd7575;");
        else
            cPane.setStyle(cPane.getStyle() + "-fx-background-color: white;");
        if (c.isInitial())
            cPane.setStyle(cPane.getStyle() + "-fx-font-weight: bold;");
        else
            cPane.setStyle(cPane.getStyle() + "-fx-font-weight: normal;");

        if (c.getValue() != 0) {
            Text text = new Text(String.valueOf(c.getValue()));
            cPane.getChildren().add(text);

            if (c.isHint())
                ((Text) cPane.getChildren().get(0)).setFill(new Color(0, 0.75, 0, 1));
            if (c.isAlgoSolved())
                ((Text) cPane.getChildren().get(0)).setFill(new Color(0.75, 0, 0, 1));


        } else {
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            for (int i = 0; i < game.getSize(); i++) {
                StackPane stackPane = new StackPane();
                stackPane.setPrefSize(size / Math.pow(game.getSize(), 1.5), size / Math.pow(game.getSize(), 1.5));
                if (c.getNotes()[i]) {
                    Text text = new Text((String.valueOf(i + 1)));
                    text.setStyle("-fx-font-size: " + ((int) size / Math.pow(game.getSize(), 1.75)) + ";");
                    stackPane.getChildren().add(text);
                }
                gridPane.add(stackPane, (int) (i % Math.sqrt(game.getSize())), (int) (i / Math.sqrt(game.getSize())));
            }
            cPane.getChildren().add(gridPane);
        }
    }

    public void shortcuts(KeyEvent keyEvent) throws Exception {
        switch (keyEvent.getText()) {
            case "1" -> nbPressed(1);
            case "2" -> nbPressed(2);
            case "3" -> nbPressed(3);
            case "4" -> nbPressed(4);
            case "5" -> nbPressed(5);
            case "6" -> nbPressed(6);
            case "7" -> nbPressed(7);
            case "8" -> nbPressed(8);
            case "9" -> nbPressed(9);
            case "0" -> nbPressed(0);
        }
        if (game.getCase(currentRow, currentCol) != null) {
            switch (keyEvent.getCode()) {
                case BACK_SPACE -> {
                    if (annotating)
                        game.getCase(currentRow, currentCol).clearNotes();
                    else
                        game.eraseValue(currentRow, currentCol);
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

                case N -> {
                    annotating = !annotating;
                    if (annotating)
                        note.setStyle("-fx-background-color: grey");
                    else
                        note.setStyle("-fx-background-color: white");
                }

                case A -> {
                    if (game.isNotSolved()) {
                        game.solve();
                    }
                }

                case V -> {
                    if (!game.isValidated()) {
                        finished(game.validate());
                    }
                }

                case I -> {
                    try {
                        game.addIndice(currentRow, currentCol);
                    } catch (Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }

                case R -> {
                    if (game.isValidated())
                        gameBoard.getChildren()
                                .remove(gameBoard.getChildren().size() - 2, gameBoard.getChildren().size());
                    game.restart();
                }

                case M -> {
                    FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));

                    Parent gamePane = gameLoader.load();
                    Stage primaryStage = (Stage) ((Node) keyEvent.getTarget()).getScene().getWindow();
                    primaryStage.getScene().setRoot(gamePane);
                }
            }
            updateAllCases(screenSize);
            keyEvent.consume();
        }
    }

    @FXML
    private void restart(MouseEvent event) {
        if (game.isValidated())
            gameBoard.getChildren().remove(gameBoard.getChildren().size() - 2, gameBoard.getChildren().size());
        game.restart();
        updateAllCases(screenSize);
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
        updateAllCases(screenSize);
    }

    @FXML
    private void giveUp(MouseEvent event) {
        if (game.isNotSolved()) {
            game.solve();
            updateAllCases(screenSize);
        }
    }

    @FXML
    private void validate(MouseEvent event) {
        if (!game.isValidated()) {
            finished(game.validate());
        }
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
            img.setFitWidth(screenSize - (screenSize / game.getSize()));
            img.setTranslateY(-100);

            Label labelTimer = new Label();
            labelTimer.setText("39.56 min");
            labelTimer.setStyle(
                    "-fx-background-color:rgba(31,31,31,0.95);-fx-font-size:30px;-fx-padding: 20;-fx-text-fill: white;-fx-border-width: 3px;-fx-border-color: white");
            labelTimer.setTranslateY(screenSize / 12);

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
    }

    @FXML
    private void actionBouton_0() throws Exception {
        nbPressed(0);
    }

    public void nbPressed(int value) throws Exception {
        if (annotating) {
            if (value <= game.getSize())
                game.toggleNote(value, currentRow, currentCol);
            else
                throw new Exception("not a correct value");
        } else
            game.setValue(value, currentRow, currentCol);
        playWriteSound();
        updateAllCases(screenSize);
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

    @FXML
    private void actionBouton_1() throws Exception {
        nbPressed(1);
    }

    @FXML
    private void actionBouton_2() throws Exception {
        nbPressed(2);
    }

    @FXML
    private void actionBouton_3() throws Exception {
        nbPressed(3);
    }

    @FXML
    private void actionBouton_4() throws Exception {
        nbPressed(4);
    }

    @FXML
    private void actionBouton_5() throws Exception {
        nbPressed(5);
    }

    @FXML
    private void actionBouton_6() throws Exception {
        nbPressed(6);
    }

    @FXML
    private void actionBouton_7() throws Exception {
        nbPressed(7);
    }

    @FXML
    private void actionBouton_8() throws Exception {
        nbPressed(8);
    }

    @FXML
    private void actionBouton_9() throws Exception {
        nbPressed(9);
    }

    @FXML
    private void erase() {
        if (annotating)
            game.getCase(currentRow, currentCol).clearNotes();
        else
            game.eraseValue(currentRow, currentCol);
        updateAllCases(screenSize);
        playEraseSound();
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

    @FXML
    private void mainMenu(MouseEvent event) throws IOException {
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));

        Parent gamePane = gameLoader.load();
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        double width = primaryStage.getWidth();
        double height = primaryStage.getHeight();
        primaryStage.getScene().setRoot(gamePane);
    }

    @FXML
    private void quitGame(MouseEvent event) {
        Platform.exit();
    }
}
