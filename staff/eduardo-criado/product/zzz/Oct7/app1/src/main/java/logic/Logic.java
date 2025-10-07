package logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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

    // public static void main(String[] args) {

    // Logic logic = Logic.get();
    // Data data = Data.get();

    // try {
    // logic.registerUser("Eduardo", "edulo", "1234");
    // logic.registerUser("Eduardo", "edulo", "1234");
    // } catch (DuplicityException exception) {
    // // TODO Auto-generated catch block
    // exception.printStackTrace();
    // }

    // User user = data.findUserByUsername("edulo");
    // if (user != null) {
    // System.out.println("User found: " + user.getName());
    // } else {
    // System.out.println("User not found");
    // }
    // }

    private String username;

    private Logic() {
    }

    public String getDailyQuote() {
        try {
            URL url = new URL("https://zenquotes.io/api/today");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONArray arr = new JSONArray(content.toString());
            JSONObject obj = arr.getJSONObject(0);
            String quote = obj.getString("q");
            String author = obj.getString("a");
            return "\"" + quote + "\" — " + author;
        } catch (Exception e) {
            return "Could not fetch quote.";
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