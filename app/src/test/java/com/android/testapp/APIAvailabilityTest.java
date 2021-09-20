package com.android.testapp;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class APIAvailabilityTest {
    @Test
    public void testAvailability() throws Exception {
        URLConnection connection = new URL("https://jsonplaceholder.typicode.com/albums").openConnection();
        InputStream response = connection.getInputStream();

        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, Charset.defaultCharset()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                buffer.append(line);
            }
        }

        assert buffer.length() > 0;
    }
}
