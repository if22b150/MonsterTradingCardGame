package at.technikum.enums;

public enum ECardName {
    RegularSpell("RegularSpell"),
    Ork("Ork"),
    FireSpell("FireSpell"),
    WaterGoblin("WaterGoblin"),
    Dragon("Dragon"),
    WaterSpell("WaterSpell"),
    Knight("Knight"),
    Wizard("Wizard"),
    Kraken("Kraken"),
    FireElf("FireElf");

    public final String name;

    /**
     * Constructor to initialize the HTTP status code and message.
     *
     * @param name The card type.
     */
    ECardName(String name) {
        this.name = name;
    }
}
