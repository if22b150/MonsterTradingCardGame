package at.technikum.server.requests.user;

import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreUserRequest extends ARequest {
    private static final IUserRepository userRepository = new UserRepository();
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    public StoreUserRequest() {}

    public StoreUserRequest(String username, String password) {
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
        if(     isNull("username", username)
                || isEmpty("username", username)
                || isTooShort("username", username, 3) ) {
            return false;
        }

        if(userRepository.getByUsername(username) != null) {
            addError("username", " already exists.");
            return false;
        }

        return true;
    }

    private boolean validPassword() {
        return !isNull("password", password)
                && !isEmpty("password", password)
                && !isTooShort("password", password, 6);
    }
}
