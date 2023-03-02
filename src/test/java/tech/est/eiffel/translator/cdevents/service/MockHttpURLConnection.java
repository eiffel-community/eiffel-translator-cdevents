/*
    Copyright (C) 2023 Nordix Foundation.
    For a full list of individual contributors, please see the commit history.
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
          http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    SPDX-License-Identifier: Apache-2.0
*/
package tech.est.eiffel.translator.cdevents.service;

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
