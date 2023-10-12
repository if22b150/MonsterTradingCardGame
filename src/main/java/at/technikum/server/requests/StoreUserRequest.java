package at.technikum.server.requests;

import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import at.technikum.server.requests.ARequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreUserRequest extends ARequest {
    private static final IUserRepository userRepository = new UserRepository();
    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
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
        if(     isNull("Username", username)
                || isEmpty("Username", username)
                || isTooShort("Username", username, 3) ) {
            return false;
        }

        if(userRepository.getByUsername(username) != null) {
            addError("Username", " already exists.");
            return false;
        }

        return true;
    }

    private boolean validPassword() {
        return !isNull("Password", password)
                && !isEmpty("Password", password)
                && !isTooShort("Password", password, 6);
    }
}
