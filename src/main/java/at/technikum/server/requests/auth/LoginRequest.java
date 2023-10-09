package at.technikum.server.requests.auth;

import at.technikum.models.User;
import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import at.technikum.server.requests.ARequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class LoginRequest extends ARequest {
    private static final IUserRepository userRepository = new UserRepository();
    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;

    private User user;

    public User getUser() {
        return user;
    }

    public LoginRequest() {}

    public LoginRequest(String username, String password) {
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

        if(!valid)
            return false;

        User loginUser = userRepository.getByUsername(username);
        if(loginUser == null || !Objects.equals(loginUser.getPassword(), password)) {
            generalMsg = "Credentials incorrect.";
            return false;
        } else
            user = loginUser;

        return true;
    }

    private boolean validUsername() {
        return !isNull("Username", username)
                && !isEmpty("Username", username);
    }

    private boolean validPassword() {
        return !isNull("Password", password)
                && !isEmpty("Password", password)
                && !isTooShort("Password", password, 6);
    }
}

