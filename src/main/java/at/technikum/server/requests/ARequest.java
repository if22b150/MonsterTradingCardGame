package at.technikum.server.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ARequest {
    private HashMap<String, ArrayList<String>> errors;

    protected String generalMsg;

    public ARequest() {
        errors = new HashMap<>();
    }

    public String getErrorMessages() {
        if(generalMsg != null)
            return "{\"message\": \"" + generalMsg + "\"}";

        StringBuilder s = new StringBuilder().append("{\"message\": \"The given data was invalid.\",\"errors\": {");
        int cnt = 0;
        for(String key : errors.keySet()) {
            s.append("\"").append(key).append("\": [");
            int cnt2 = 1;
            for(String msg : errors.get(key)) {
                s.append("\"").append(msg).append("\"");
                if(cnt2 < errors.get(key).size())
                    s.append(",");
                cnt2++;
            }
            s.append("]");
            if(cnt < errors.keySet().size()-1)
                s.append(",");
            cnt++;
        }
        s.append("}}");
        return String.valueOf(s);
    }

    protected void addError(String fieldName, String msg) {
        if(errors.get(fieldName) != null)
            errors.get(fieldName).add(fieldName + msg);
        else
            errors.put(fieldName, new ArrayList<String>(){{add(fieldName + msg);}});
    }

    protected boolean isNull(String fieldName, Object value) {
        if(value != null)
            return false;

        addError(fieldName, " is required");
        return true;
    }

    protected boolean isEmpty(String fieldName, String value) {
        if(!value.isEmpty())
            return false;

        addError(fieldName, " cannot be empty");
        return true;
    }

    protected boolean isTooShort(String fieldName, String value, int minLength) {
        if(value.length() >= minLength)
            return false;

        addError(fieldName, " must consist of at least " + minLength + " characters");
        return true;
    }
}
