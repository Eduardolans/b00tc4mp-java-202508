import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import main.java.errors.ClientError;
import main.java.errors.ConnectionError;
import main.java.errors.ContentError;

public String getDailyQuote() throws ConnectionError, ServerError, ClientError, ContentError {
    HttpURLConnection conn = null;
    try {
        URL url = new URL("https://zenquotes.io/api/today");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int responseCode = conn.getResponseCode();
        if (responseCode >= 500) {
            throw new ServerError("Server error occurred with code: " + responseCode);
        } else if (responseCode >= 400) {
            throw new ClientError("Client error occurred with code: " + responseCode);
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        }

        JSONArray arr = new JSONArray(content.toString());
        if (arr.length() == 0) {
            throw new ContentError("No quote data received");
        }
        JSONObject obj = arr.getJSONObject(0);
        String quote = obj.getString("q");
        String author = obj.getString("a");
        return "\"" + quote + "\" — " + author;

    } catch (java.net.UnknownHostException | java.net.ConnectException | MalformedURLException e) {
        throw new ConnectionError("Failed to connect to quote service: " + e.getMessage());
    } catch (JSONException e) {
        throw new ContentError("Error parsing quote data: " + e.getMessage());
    } catch (IOException e) {
        throw new ConnectionError("Network error: " + e.getMessage());
    } catch (ConnectionError | ServerError | ClientError | ContentError e) {
        throw e;
    } catch (Exception e) {
        throw new ConnectionError("Unexpected error while fetching quote: " + e.getMessage());
    } finally {
        if (conn != null) {
            conn.disconnect();
        }
    }
}

/////

/*
 * public String getDailyQuote() throws ConnectionError, ServerError,
 * ClientError, ContentError {
 * HttpURLConnection conn = null;
 * try {
 * URL url = new URL("https://zenquotes.io/api/today");
 * conn = (HttpURLConnection) url.openConnection();
 * conn.setRequestMethod("GET");
 * conn.setConnectTimeout(5000);
 * conn.setReadTimeout(5000);
 * 
 * int responseCode = conn.getResponseCode();
 * if (responseCode >= 500) {
 * throw new ServerError("Server error occurred with code: " + responseCode);
 * } else if (responseCode >= 400) {
 * throw new ClientError("Client error occurred with code: " + responseCode);
 * }
 * 
 * StringBuilder content = new StringBuilder();
 * try (BufferedReader in = new BufferedReader(
 * new InputStreamReader(conn.getInputStream()))) {
 * String inputLine;
 * while ((inputLine = in.readLine()) != null) {
 * content.append(inputLine);
 * }
 * }
 * 
 * JSONArray arr = new JSONArray(content.toString());
 * if (arr.length() == 0) {
 * throw new ContentError("No quote data received");
 * }
 * JSONObject obj = arr.getJSONObject(0);
 * String quote = obj.getString("q");
 * String author = obj.getString("a");
 * return "\"" + quote + "\" — " + author;
 * 
 * } catch (java.net.UnknownHostException | java.net.ConnectException |
 * MalformedURLException e) {
 * throw new ConnectionError("Failed to connect to quote service: " +
 * e.getMessage());
 * } catch (Exception e) {
 * if (e instanceof ConnectionError || e instanceof ServerError ||
 * e instanceof ClientError || e instanceof ContentError) {
 * throw (RuntimeException) e;
 * }
 * throw new ConnectionError("Unexpected error while fetching quote: " +
 * e.getMessage());
 * } finally {
 * if (conn != null) {
 * conn.disconnect();
 * }
 * }
 * }
 */