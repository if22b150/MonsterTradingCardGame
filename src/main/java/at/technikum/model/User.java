package at.technikum.model;


import java.util.ArrayList;


public class User extends AModel {
    static int idCounter = 0;
    private String username;
    private String password;
    private int coins;

    private ArrayList<Card> stack;

    private ArrayList<Card> deck;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.coins = 20;
        this.stack = new ArrayList<Card>();
        this.deck = new ArrayList<Card>();
        this.id = idCounter++;
    }
}
