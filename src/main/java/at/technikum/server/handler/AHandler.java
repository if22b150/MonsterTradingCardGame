package at.technikum.server.handler;

import at.technikum.server.HttpMethod;

import java.util.ArrayList;

public abstract class AHandler implements IHandler {

    protected ArrayList<HttpMethod> allowedMethods;

    public void setAllowedMethods(ArrayList<HttpMethod> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }
}
