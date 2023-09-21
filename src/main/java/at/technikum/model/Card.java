package at.technikum.model;

enum ECardType {
    SPELL,
    MONSTER
}

enum EElementType {
    FIRE,
    WATER,
    NORMAL
}
public class Card extends AModel {
    private String name;
    private final int damage;
    private ECardType cardType;

    private EElementType elementType;

    public Card(String name, int damage, ECardType cardType, EElementType elementType) {
        this.name = name;
        this.damage = damage;
        this.cardType = cardType;
        this.elementType = elementType;
    }
}
