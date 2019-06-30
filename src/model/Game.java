package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.util.*;

public class Game {
    private final int playerCount;
    private Map<Card, ObservableSet<Integer>> possibleCardLocations = new HashMap<>();
    private Map<Integer, ObservableSet<Card>> possibleHandContents = new HashMap<>();
    private Map<Integer, ObservableSet<Card>> hands = new HashMap<>();
    private Map<Integer, Integer> handSizes;
    private Map<Card, CardStat> cardStats = new TreeMap<>();
    private Set<Guess> usableGuesses = new HashSet<>();

    public Game(int playerCount, Map<Integer, Integer> handSizes) {
        this.playerCount = playerCount;
        this.handSizes = handSizes;
        handSizes.put(0, 3);
        Set<Integer> allPlayers = new HashSet<>();
        for(int i=0; i<= playerCount; i++) {
            allPlayers.add(i);
            possibleHandContents.put(i, FXCollections.observableSet(new TreeSet<>(Card.getCards())));
        }
        for (Card card : Card.getCards()) {
            possibleCardLocations.put(card, FXCollections.observableSet(new TreeSet<>(allPlayers)));
            cardStats.put(card, new CardStat(card, possibleCardLocations.get(card), playerCount));
        }
    }

    public void setLocation(Card card, int location) {
        possibleCardLocations.get(card).removeIf((item)-> item != location);
        ObservableSet<Card> hand = hands.computeIfAbsent(location, (key) -> FXCollections.observableSet());
        hand.add(card);
        for(int i=0; i <= playerCount; i++) {
            if(i == location) continue;
            possibleHandContents.get(i).remove(card);
        }
        if(hand.size() >= handSizes.get(location)) {
            for (Card c: Card.getCards()) {
                if(!hand.contains(c)) {
                     possibleCardLocations.get(c).remove(location);
                    possibleHandContents.get(location).remove(c);
                }
            }

        }
    }

    private int checkDeduction() {
        int changes = 0;
        for (Map.Entry<Card, ObservableSet<Integer>> entry : possibleCardLocations.entrySet()) {
            if(entry.getValue().size() == 1) {
                for (Integer location : entry.getValue()) {
                    ObservableSet<Card> hand = hands.computeIfAbsent(location, (key) -> FXCollections.observableSet());
                    hand.add(entry.getKey());
                }
                changes++;
            }
        }
        Iterator<Guess> iterator = usableGuesses.iterator();
        while (iterator.hasNext()) {
            Guess guess = iterator.next();
            int answerer = guess.getAnsweringPlayer();
            boolean person = false, weapon = false, room = false;
            if(possibleHandContents.get(answerer).contains(guess.getPerson())) person = true;
            if(possibleHandContents.get(answerer).contains(guess.getWeapon())) weapon = true;
            if(possibleHandContents.get(answerer).contains(guess.getRoom())) room = true;
            if(person && !weapon && !room){
                setLocation(guess.getPerson(), answerer);
                iterator.remove();
                changes++;
            }
            if(!person && weapon && !room){
                setLocation(guess.getWeapon(), answerer);
                iterator.remove();
                changes++;
            }
            if(!person && !weapon && room){
                setLocation(guess.getRoom(), answerer);
                iterator.remove();
                changes++;
            }
        }
        return changes;
    }

    public void addGuess(Guess guess) {
        int answerer = guess.getAnsweringPlayer();
        if(guess.getResult() != Card.None) {
            setLocation(guess.getResult(), answerer);
        }else if (!getHands(answerer).contains(guess.getPerson())
                && !getHands(answerer).contains(guess.getWeapon())
                && !getHands(answerer).contains(guess.getRoom())) {
                    usableGuesses.add(guess);
                }  //We can't glean anything more from this card

        for(int i = (guess.getAskingPlayer()% playerCount) +1; i != answerer; i=(i%playerCount)+1){
            couldNotAnswerGuess(guess, i);
        }
        int deductions = 1;
        int bound= 0;
        while (deductions > bound){
            deductions = checkDeduction();
            bound += deductions;
        }
    }

    private ObservableSet<Card> getHands(int location) {
        return hands.computeIfAbsent(location, (key) -> FXCollections.observableSet());
    }

    private void couldNotAnswerGuess(Guess guess, int player) {
        possibleCardLocations.get(guess.getPerson()).remove(player);
        possibleCardLocations.get(guess.getWeapon()).remove(player);
        possibleCardLocations.get(guess.getRoom()).remove(player);
        possibleHandContents.get(player).remove(guess.getPerson());
        possibleHandContents.get(player).remove(guess.getWeapon());
        possibleHandContents.get(player).remove(guess.getRoom());
    }

    public Map<Card, CardStat> getCardStats() {
        return Collections.unmodifiableMap(cardStats);
    }
}
