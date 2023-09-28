package at.technikum.server;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private HttpMethod method;
    private String urlContent;
    private String pathname;
    private List<String> pathParts;
    private String params;
    private HeaderMap headerMap = new HeaderMap();
    private String body;

    public Request(HttpMethod method, String urlContent) {
        this.method = method;
        this.urlContent = urlContent;
        parseUrlContent();
    }

    private void parseUrlContent() {
        boolean hasParams = urlContent.contains("?");
        String[] urlParts = urlContent.split("\\?");
        this.pathname = urlParts[0];
        this.pathParts = new ArrayList<>();
        String[] stringParts = pathname.split("/");
        for (String part : stringParts) {
            if (!part.isEmpty()) {
                this.pathParts.add(part);
            }
        }
        this.params = hasParams && urlParts.length > 1 ? urlParts[1] : null;
    }

    public String getServiceRoute() {
        return pathParts.isEmpty() ? null : "/" + pathParts.get(0);
    }

    public String getPathPart(int index) {
        return (pathParts.isEmpty() || pathParts.size() <= index) ? null : "/" + pathParts.get(index);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPathname() {
        return pathname;
    }

    public String getParams() {
        return params;
    }

    public HeaderMap getHeaderMap() {
        return headerMap;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
