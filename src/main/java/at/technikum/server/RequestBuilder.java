package at.technikum.server;

import at.technikum.enums.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

public class RequestBuilder {
    public Request buildRequest(BufferedReader bufferedReader) throws IOException {
        String requestLine = bufferedReader.readLine();

        if (requestLine != null) {
            String[] requestLineParts = requestLine.split(" ");

            if (requestLineParts.length != 3) {
                throw new IllegalArgumentException("Invalid request line: " + requestLine);
            }

            HttpMethod method = parseHttpMethod(requestLineParts[0]);
            String uri = requestLineParts[1];
            Request request = new Request(method, uri);

            String headerLine = bufferedReader.readLine();
            while (headerLine != null && !headerLine.isEmpty()) {
                request.getHeaderMap().addHeader(headerLine);
                headerLine = bufferedReader.readLine();
            }

            int contentLength = request.getHeaderMap().getContentLength();
            if (contentLength > 0) {
                char[] bodyBuffer = new char[contentLength];
                bufferedReader.read(bodyBuffer, 0, contentLength);
                request.setBody(new String(bodyBuffer));
            }

            return request;
        }

        return null;
    }

    private HttpMethod parseHttpMethod(String methodString) {
        return HttpMethod.valueOf(methodString.toUpperCase(Locale.ROOT));
    }
}
