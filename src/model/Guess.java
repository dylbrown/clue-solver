package model;

public class Guess {
    private Card person;
    private Card weapon;
    private Card room;
    private int askingPlayer;
    private int answeringPlayer;
    private Card result = Card.None;

    public Guess(Card person, Card weapon, Card room, int askingPlayer, int answeringPlayer) {
        this.person = person;
        this.weapon = weapon;
        this.room = room;
        this.askingPlayer = askingPlayer;
        this.answeringPlayer = answeringPlayer;
    }

    public Guess(Card person, Card weapon, Card room, int askingPlayer, int answeringPlayer, Card result) {
        this.person = person;
        this.weapon = weapon;
        this.room = room;
        this.askingPlayer = askingPlayer;
        this.answeringPlayer = answeringPlayer;
        this.result = result;
    }

    Card getPerson() {
        return person;
    }

    Card getWeapon() {
        return weapon;
    }

    Card getRoom() {
        return room;
    }

    int getAskingPlayer() {
        return askingPlayer;
    }

    int getAnsweringPlayer() {
        return answeringPlayer;
    }

    Card getResult() {
        return result;
    }
}
