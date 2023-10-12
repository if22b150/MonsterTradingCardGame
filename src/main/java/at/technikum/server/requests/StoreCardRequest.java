package at.technikum.server.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreCardRequest extends ARequest {
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Damage")
    private int damage;

    public StoreCardRequest() {}

    public StoreCardRequest(String id, String name, int damage) {
        super();
        this.id = id;
        this.name = name;
        this.damage = damage;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isValid() {
        boolean valid = true;
        if(!validId())
            valid = false;
        if(!validName())
            valid = false;
        if(!validDamage())
            valid = false;
        return valid;
    }

    private boolean validId() {
        // TODO: exists may also be implemented
        return !isNull("Id", id) && !isEmpty("Id", id);
    }

    private boolean validName() {
        return !isNull("Name", name) && !isEmpty("Name", name);
    }

    private boolean validDamage() {
        return !isNull("Damage", damage) && isMax("Damage", damage, 100) && isMin("Damage", damage, 1);
    }
}
