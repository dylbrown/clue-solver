package model;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private Map<Card, ReadOnlyStringWrapper> cardProperties = new HashMap<>();

    public Player(ObservableSet<Card> hand, ObservableSet<Card> maybe) {
        hand.addListener((SetChangeListener<Card>) change -> {
            while(change.wasAdded()) {
                cardProperties.computeIfAbsent(change.getElementAdded(), (key)->new ReadOnlyStringWrapper("?")).set("✓");
            }
        });
        maybe.addListener((SetChangeListener<Card>) change -> {
            while(change.wasRemoved()) {
                cardProperties.computeIfAbsent(change.getElementRemoved(), (key)->new ReadOnlyStringWrapper("?")).set("");
            }
        });
    }

    public ObservableValue<String> getCardProperty(Card card) {
        return cardProperties.computeIfAbsent(card, (key)->new ReadOnlyStringWrapper("?")).getReadOnlyProperty();
    }
}
