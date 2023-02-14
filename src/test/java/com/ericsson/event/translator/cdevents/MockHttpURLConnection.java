package com.ericsson.event.translator.cdevents;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MockHttpURLConnection extends HttpURLConnection {
    private int responseCode;
    private URL url;
    private InputStream inputStream;
    private OutputStream outputStream;

    /**
     * @param url
     */
    public MockHttpURLConnection(URL url) {
        super(null);
        this.url = url;
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode
     */
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public URL getURL() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param inputStream
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * @param outputStream
     */
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {

    }
}
