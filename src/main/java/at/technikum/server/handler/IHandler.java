package at.technikum.server.handler;

import at.technikum.server.HttpMethod;
import at.technikum.server.Request;
import at.technikum.server.Response;

import java.util.ArrayList;

public interface IHandler {
    Response handleRequest(Request request);

    void setAllowedMethods(ArrayList<HttpMethod> methods);
}
