package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Pair;
import javafx.util.StringConverter;
import model.Card;
import model.CardStat;
import model.Game;
import model.Guess;


public class Root {

    @FXML
    private TableView<CardStat> data;
    @FXML
    private ComboBox<Card> person;
    @FXML
    private ComboBox<Card> weapon;
    @FXML
    private ComboBox<Card> room;
    @FXML
    private ComboBox<Card> result;
    @FXML
    private ComboBox<Pair<Integer, String>> asker;
    @FXML
    private ComboBox<Pair<Integer, String>> answerer;
    @FXML
    private Button submit;

    @FXML
    private void initialize() {
        TableColumn<CardStat, String> cardName = new TableColumn<>("Card");
        cardName.setCellValueFactory(param -> param.getValue().nameProperty());
        data.getColumns().add(cardName);

        StringConverter<Pair<Integer, String>> converter = new StringConverter<Pair<Integer, String>>() {
            @Override
            public String toString(Pair<Integer, String> object) {
                return object.getValue();
            }

            @Override
            public Pair<Integer, String> fromString(String string) {
                return new Pair<>(Main.playerNames.indexOf(string), string);
            }
        };

        asker.setConverter(converter);
        answerer.setConverter(converter);


        for(int i = 0; i<=ui.Main.playerCount; i++) {
            TableColumn<CardStat, String> playerCol;
            if(i == 0)
                playerCol = new TableColumn<>("Envelope");
            else
                playerCol = new TableColumn<>(Main.playerNames.get(i));
            int finalI = i;
            playerCol.setCellValueFactory(param -> param.getValue().playerProperty(finalI));
            playerCol.setStyle("-fx-alignment:CENTER");
            data.getColumns().add(playerCol);
            if(i != 0){
                asker.getItems().add(new Pair<>(i, Main.playerNames.get(i)));
                answerer.getItems().add(new Pair<>(i, Main.playerNames.get(i)));
            }
        }
        person.getItems().addAll(Card.getPeople());
        weapon.getItems().addAll(Card.getWeapons());
        room.getItems().addAll(Card.getRooms());
        result.getItems().addAll(Card.getCards());
        result.setValue(Card.None);

        Game game = new Game(Main.playerCount, Main.handSizes);
        for (Card card : Main.myHand) {
            game.setLocation(card, 1);
        }


        data.getItems().addAll(game.getCardStats().values());
        //Card person, Card weapon, Card room, int askingPlayer, int answeringPlayer, Card result
        submit.setOnAction(event -> {
           game.addGuess(new Guess(person.getValue(), weapon.getValue(), room.getValue(), asker.getValue().getKey(), answerer.getValue().getKey(), result.getValue()));
           asker.setValue(asker.getItems().get(asker.getValue().getKey()));
           result.setValue(Card.None);
        });
    }
}
