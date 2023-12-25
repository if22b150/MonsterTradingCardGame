package at.technikum.server.requests;

import at.technikum.models.User;
import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class StoreSessionRequest extends ARequest {
    private static final IUserRepository userRepository = new UserRepository();
    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;

    private User user;

    public User getUser() {
        return user;
    }


    public StoreSessionRequest() {}

    public StoreSessionRequest(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isValid() {
        boolean valid = true;
        if(!validUsername())
            valid = false;
        if(!validPassword())
            valid = false;
        return valid;
    }

    private boolean validUsername() {
        if(     isNull("Username", username)
                || isEmpty("Username", username)) {
            return false;
        }

        User user1 = userRepository.getByUsername(username);
        if(user1 == null) {
            addError("Username", " not found.");
            return false;
        }

        this.user = user1;

        return true;
    }

    private boolean validPassword() {
        if(     isNull("Password", password)
                || isEmpty("Password", password)) {
            return false;
        }

        if(this.user != null) {
            System.out.println(this.user.getPassword());
            System.out.println(password);
            if(Objects.equals(this.user.getPassword(), password))
                return true;

            addError("Password", " is incorrect.");
            return false;
        }

        return false;
    }
}
