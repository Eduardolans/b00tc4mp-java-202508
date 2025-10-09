package logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import data.Data;
import data.User;

import errors.*;

public class Logic {

    private static Logic instance;

    public static Logic get() {
        if (instance == null) {
            instance = new Logic();
        }
        return instance;
    }

    private String username;

    private Logic() {
    }

    public String getDailyQuote() throws ConnectionError, ServerError, ClientError, ContentError {
        try {
            URL url = new URL("https://zenquotes.io/api/today");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode >= 500) {
                throw new ServerError("Server error occurred with code: " + responseCode);
            } else if (responseCode >= 400) {
                throw new ClientError("Client error occurred with code: " + responseCode);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            try {
                JSONArray arr = new JSONArray(content.toString());
                if (arr.length() == 0) {
                    throw new ContentError("No quote data received");
                }
                JSONObject obj = arr.getJSONObject(0);
                String quote = obj.getString("q");
                String author = obj.getString("a");
                return "\"" + quote + "\" — " + author;
            } catch (Exception e) {
                throw new ContentError("Error parsing quote data: " + e.getMessage());
            }
        } catch (java.net.UnknownHostException | java.net.ConnectException | MalformedURLException e) {
            throw new ConnectionError("Failed to connect to quote service: " + e.getMessage());
        } catch (Exception e) {
            if (e instanceof ConnectionError || e instanceof ServerError ||
                    e instanceof ClientError || e instanceof ContentError) {

                throw (RuntimeException) e;
            }
            throw new ConnectionError("Unexpected error while fetching quote: " + e.getMessage());
        }

    }

    public void registerUser(String name, String username, String password) throws DuplicityException {

        // this.username = username;

        Data data = Data.get();

        User user = data.findUserByUsername(username);

        if (user != null) {
            throw new DuplicityException("user already exists");
        }

        user = new User(name, username, password);

        data.addUser(user);

    }

    public void loginUser(String username, String password) throws NotFoundException, CredentialsException {
        Data data = Data.get();

        User user = data.findUserByUsername(username);

        if (user == null) {
            throw new CredentialsException("user not found");
        }

        if (!user.getPassword().equals(password)) {
            throw new CredentialsException("invalid password");
        }

        this.username = username;

    }

    public String getUsername() throws NotFoundException {
        Data data = Data.get();
        User user = data.findUserByUsername(this.username);

        if (user == null) {
            throw new NotFoundException("user not found");
        }

        return user.getName();
    }

}