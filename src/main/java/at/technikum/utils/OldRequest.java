package at.technikum.utils;

import at.technikum.server.HttpMethod;
import com.sun.net.httpserver.HttpExchange;

public class OldRequest {
    public HttpExchange exchange;

    public String route;
    public HttpMethod httpMethod;

    public String path;

    // Extract the part of the path after "/api/users/"
    public String subPath;

    // Split the subPath into segments
    public String[] pathSegments;

    public boolean isIndexRequest = false;

    public boolean isShowRequest = false;

    public boolean isStoreRequest = false;

    public boolean isUpdateRequest = false;

    public int resourceId;

    public int errorCode = 0;


    public OldRequest(String route, HttpExchange exchange) {
        this.route = route;
        this.exchange = exchange;
        this.path = exchange.getRequestURI().getPath();
        this.httpMethod = HttpMethod.valueOf(exchange.getRequestMethod());

        this.initProperties();
    }



    private void initProperties() {
        // Initialize Properties
        this.subPath = this.path.substring(this.route.length());
        this.pathSegments = this.subPath.split("/");

        // Initialize Route Properties and validate route
        if(this.subPath.isEmpty()) {
            switch (this.httpMethod) {
                case GET -> this.isIndexRequest = true;
                case POST -> this.isStoreRequest = true;
                default -> this.errorCode = 405;
            }
            return;
        }

        if (this.pathSegments.length == 1) {
            if(!Helper.stringIsInteger(this.pathSegments[0])) {
                this.errorCode = 422;
                return;
            }

            this.resourceId = Integer.parseInt(this.pathSegments[0]);

            switch (this.httpMethod) {
                case GET -> this.isShowRequest = true;
                case PUT -> this.isUpdateRequest = true;
                default -> this.errorCode = 405;
            }
            return;
        }
    }
}
