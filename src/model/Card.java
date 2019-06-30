package model;

import java.util.*;

import static model.CardType.*;

public enum Card {
    //People
    Green(Person), Plum(Person), Mustard(Person), Peacock(Person), Scarlet(Person), White(Person),
    //Weapons
    Candlestick(Weapon), Knife(Weapon), Pipe(Weapon), Revolver(Weapon), Rope(Weapon), Wrench(Weapon),
    //Rooms
    Ballroom(Room), BilliardRoom(Room), Conservatory(Room), DiningRoom(Room), Hall(Room), Kitchen(Room), Library(Room), Lounge(Room), Study(Room),

    None(model.CardType.None);


    private final CardType type;
    Card(CardType type) {
        this.type = type;
    }

    private static final Set<Card> people = new TreeSet<>(Arrays.asList(Green, Mustard, Peacock, Plum, Scarlet, White));
    private static final Set<Card> weapons = new TreeSet<>(Arrays.asList(Candlestick, Knife, Pipe, Revolver, Rope, Wrench));
    private static final Set<Card> rooms = new TreeSet<>(Arrays.asList(Ballroom, BilliardRoom, Conservatory, DiningRoom, Hall, Kitchen, Library, Lounge, Study));
    private static final Set<Card> allCards = new TreeSet<>();
    static{
        allCards.addAll(people);
        allCards.addAll(weapons);
        allCards.addAll(rooms);
    }

    public CardType getType() {
        return type;
    }

    public static Set<Card> getPeople() {
        return Collections.unmodifiableSet(people);
    }

    public static Set<Card> getWeapons() {
        return Collections.unmodifiableSet(weapons);
    }

    public static Set<Card> getRooms() {
        return Collections.unmodifiableSet(rooms);
    }

    public static Set<Card> getCards() {
        return Collections.unmodifiableSet(allCards);
    }
}
