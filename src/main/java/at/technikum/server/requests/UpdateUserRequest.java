package at.technikum.server.requests;

import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import at.technikum.server.requests.ARequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateUserRequest extends ARequest {
    private static final IUserRepository userRepository = new UserRepository();

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Bio")
    private String bio;

    @JsonProperty("Image")
    private String image;

    public UpdateUserRequest() {}

    public UpdateUserRequest(String name, String bio, String image) {
        super();
        this.name = name;
        this.bio = bio;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getImage() {
        return image;
    }

    public boolean isValid() {
        boolean valid = true;
        if(!validName())
            valid = false;
        if(!validBio())
            valid = false;
        if(!validImage())
            valid = false;
        return valid;
    }

    private boolean validName() {
        if(     !isNull("Name", name)
                 && isEmpty("Name", name) ) {
            return false;
        }

        return true;
    }

    private boolean validBio() {
        if(     !isNull("Bio", bio)
                && isEmpty("Bio", bio) ) {
            return false;
        }

        return true;
    }

    private boolean validImage() {
        if(     !isNull("Image", image)
                && isEmpty("Image", image) ) {
            return false;
        }

        return true;
    }
}
