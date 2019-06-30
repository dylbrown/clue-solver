package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Card;

import java.io.IOException;
import java.util.*;

public class Main extends Application {
    static int playerCount;
    static Map<Integer, Integer> handSizes = new HashMap<>();
    static List<String> playerNames = new ArrayList<>();
    static List<Card> myHand = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        ChoiceDialog<Integer> numberOfPlayers = new ChoiceDialog<>();
        numberOfPlayers.setTitle("Set Players");
        numberOfPlayers.setHeaderText("How Many Players Are There?");
        numberOfPlayers.getDialogPane().getButtonTypes().clear();
        numberOfPlayers.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));
        numberOfPlayers.getItems().addAll(2, 3, 4, 5, 6);
        Optional<Integer> numPlayers = numberOfPlayers.showAndWait();
        if(!numPlayers.isPresent()) return;
        playerCount = numPlayers.get();

        Dialog<String> playerInfo = new Dialog<>();
        playerInfo.setTitle("Player Info");
        playerInfo.setHeaderText("Input each player's information.");
        playerInfo.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        List<Pair<TextField, ComboBox<Integer>>> data = new ArrayList<>();
        for(int i = 0; i< playerCount; i++) {
            grid.add(new Label("Name"), 0, i);
            TextField textField = new TextField();
            grid.add(textField, 1, i);
            grid.add(new Label("Cards"), 2, i);
            ComboBox<Integer> integerComboBox = new ComboBox<>();
            integerComboBox.getItems().addAll(3, 4, 5, 6, 9);
            grid.add(integerComboBox, 3, i);
            data.add(new Pair<>(textField, integerComboBox));
        }

        playerInfo.getDialogPane().setContent(grid);

        playerInfo.showAndWait();
        playerNames.add("Envelope");
        for(int i = 0; i< playerCount; i++) {
            Pair<TextField, ComboBox<Integer>> pair = data.get(i);
            handSizes.put(i+1, pair.getValue().getValue());
            playerNames.add(pair.getKey().getCharacters().toString());
        }

        Dialog<String> hand = new Dialog<>();
        hand.setTitle("Starting Cards");
        hand.setHeaderText("Input your starting hand here.");
        hand.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));

        HBox hBox = new HBox();
        List<ComboBox<Card>> handData = new ArrayList<>();
        for(int i = 0; i< handSizes.get(1); i++) {
            ComboBox<Card> comboBox = new ComboBox<>();
            comboBox.getItems().addAll(Card.getCards());
            handData.add(comboBox);
            hBox.getChildren().add(comboBox);
        }

        hand.getDialogPane().setContent(hBox);

        hand.showAndWait();

        for (ComboBox<Card> datum : handData) {
            myHand.add(datum.getValue());
        }


        Parent root = FXMLLoader.load(getClass().getResource("root.fxml"));

        Scene scene = new Scene(root, 480, 640);
        stage.setTitle("My JavaFX Application");
        stage.setScene(scene);
        stage.show();
    }
}
