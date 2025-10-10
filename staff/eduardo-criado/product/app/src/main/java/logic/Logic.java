package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import data.Data;
import data.User;
import errors.ClientException;
import errors.ConnectionException;
import errors.ContentException;
import errors.CredentialsException;
import errors.DuplicityException;
import errors.NotFoundException;
import errors.ServerException;

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

    public String getDailyQuote() throws ConnectionException, ServerException, ClientException, ContentException {
        try {
            URL url = new URL("https://zenquotes.io/api/today");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode >= 500) {
                throw new ServerException("Server error occurred with code: " + responseCode);
            } else if (responseCode >= 400) {
                throw new ClientException("Client error occurred with code: " + responseCode);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONArray arr = new JSONArray(content.toString());
            if (arr.length() == 0) {
                throw new ContentException("No quote data received");
            }
            JSONObject obj = arr.getJSONObject(0);
            String quote = obj.getString("q");
            String author = obj.getString("a");
            return "\"" + quote + "\" — " + author;

        } catch (IOException e) {
            throw new ConnectionException("Failed to connect to quote service: " + e.getMessage());

        } catch (JSONException e) {
            throw new ContentException("Error parsing quote data: " + e.getMessage());

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