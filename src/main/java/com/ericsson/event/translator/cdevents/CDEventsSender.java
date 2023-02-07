package com.ericsson.event.translator.cdevents;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.message.MessageWriter;
import io.cloudevents.http.HttpMessageFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
public class CDEventsSender {

    public HttpURLConnection sendCDEvent(CloudEvent ceToSend, URL url) throws IOException {
        HttpURLConnection httpUrlConnection = createConnection(url);
        MessageWriter messageWriter = createMessageWriter(httpUrlConnection);
        messageWriter.writeBinary(ceToSend);

        return httpUrlConnection;
    }

    private HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setRequestMethod("POST");
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setDoInput(true);
        return httpUrlConnection;
    }

    private MessageWriter createMessageWriter(HttpURLConnection httpUrlConnection) {
        return HttpMessageFactory.createWriter(
                httpUrlConnection::setRequestProperty,
                body -> {
                    try {
                        if (body != null) {
                            httpUrlConnection.setRequestProperty("content-length", String.valueOf(body.length));
                            try (OutputStream outputStream = httpUrlConnection.getOutputStream()) {
                                outputStream.write(body);
                            }
                        } else {
                            httpUrlConnection.setRequestProperty("content-length", "0");
                        }
                    } catch (IOException t) {
                        throw new UncheckedIOException(t);
                    }
                });
    }
}
