package at.technikum.models;


public class User extends AModel {
    static int idCounter = 1;
    private String username;
    private String bio;
    private String image;

    public String getPassword() {
        return password;
    }

    private String password;
    private int coins;
//    private ArrayList<Card> stack;
//    private ArrayList<Card> deck;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.coins = 20;
//        this.stack = new ArrayList<Card>();
//        this.deck = new ArrayList<Card>();
        this.id = idCounter++;
    }
}
