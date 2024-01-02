package at.technikum.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private HttpMethod method;
    private String urlContent;
    private String pathname;
    private List<String> pathParts;
    private Map<String, String> params;
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

        this.params = (hasParams && urlParts.length > 1) ? getParametersMap(urlParts[1]) : new HashMap<>();
    }

    public String getServiceRoute() {
        return pathParts.isEmpty() ? null : "/" + pathParts.get(0);
    }

    public String getPathPart(int index) {
        return (pathParts.isEmpty() || pathParts.size() <= index) ? null : "/" + pathParts.get(index);
    }
    public String getPathPart(int index, boolean withSlash) {
        return (pathParts.isEmpty() || pathParts.size() <= index) ? null : (withSlash ? "/" : "") + pathParts.get(index);
    }
    private Map<String, String> getParametersMap(String queryString) {
        Map<String, String> parameters = new HashMap<>();

        if (queryString != null && !queryString.isEmpty()) {
            String[] keyValuePairs = queryString.split("&");

            for (String pair : keyValuePairs) {
                String[] entry = pair.split("=");
                String key = entry[0];
                String value = entry.length > 1 ? entry[1] : null;

                parameters.put(key, value);
            }
        }

        return parameters;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPathname() {
        return pathname;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getParam(String key) {
        return params.get(key);
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
