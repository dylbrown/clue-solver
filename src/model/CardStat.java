package model;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

import java.util.ArrayList;
import java.util.List;

public class CardStat {
    private Card card;
    private ReadOnlyStringWrapper name;
    private List<ReadOnlyStringWrapper> labels = new ArrayList<>();

    CardStat(Card card, ObservableSet<Integer> players, int playerCount) {
        name = new ReadOnlyStringWrapper(card.toString());
        this.card = card;
        for(int i=0; i<= playerCount; i++) {
            labels.add(new ReadOnlyStringWrapper("?"));
        }

        players.addListener((SetChangeListener<Integer>) change -> {
            for (int i=0; i<= playerCount; i++) {
                if(players.contains(i)) {
                    if(players.size() == 1) {
                        labels.get(i).set("✓");
                    }else{
                        labels.get(i).set("?");
                    }
                }else{
                    labels.get(i).set("");
                }
            }

        });
    }

    public Card getCard() {
        return card;
    }

    public ObservableValue<String> nameProperty() {
        return name.getReadOnlyProperty();
    }

    public ObservableValue<String> playerProperty(int player) {
        return labels.get(player).getReadOnlyProperty();
    }
}
