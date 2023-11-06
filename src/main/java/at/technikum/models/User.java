package at.technikum.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class User extends AModel {
    private String username;
    private String name;
    private String bio;
    private String image;

    public String getPassword() {
        return password;
    }

    @JsonIgnore
    private String password;
    private int coins;

    public User(String username) {
        this.username = username;
        this.coins = 20;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.coins = 20;
//        this.stack = new ArrayList<Card>();
//        this.deck = new ArrayList<Card>();
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coins = 20;
    }

    public User(int id, String username, String name, String bio, String image, int coins) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.coins = coins;
        this.bio = bio;
        this.image = image;
    }
}
