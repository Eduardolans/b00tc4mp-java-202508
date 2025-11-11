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

    private static final String API_URL = "http://localhost:8080/api";

    public static Logic get() {
        if (instance == null) {
            instance = new Logic();
        }
        return instance;
    }

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

    public void registerUser(String name, String username, String password)
            throws DuplicityException, ConnectionException, ServerException, ClientException {
        try {
            URL url = new URL(API_URL + "/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create JSON body
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", name);
            jsonBody.put("username", username);
            jsonBody.put("password", password);

            // Send request
            conn.getOutputStream().write(jsonBody.toString().getBytes("UTF-8"));

            int responseCode = conn.getResponseCode();

            if (responseCode == 409) {
                throw new DuplicityException("user already exists");
            } else if (responseCode >= 500) {
                throw new ServerException("Server error occurred with code: " + responseCode);
            } else if (responseCode >= 400) {
                throw new ClientException("Client error occurred with code: " + responseCode);
            }

            conn.disconnect();

        } catch (IOException e) {
            throw new ConnectionException("Failed to connect to API: " + e.getMessage());
        }
    }

    public void loginUser(String username, String password)
            throws CredentialsException, ConnectionException, ServerException, ClientException, ContentException {
        try {
            URL url = new URL(API_URL + "/users/auth");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create JSON body
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", username);
            jsonBody.put("password", password);

            // Send request
            conn.getOutputStream().write(jsonBody.toString().getBytes("UTF-8"));

            int responseCode = conn.getResponseCode();

            if (responseCode == 401) {
                throw new CredentialsException("invalid credentials");
            } else if (responseCode >= 500) {
                throw new ServerException("Server error occurred with code: " + responseCode);
            } else if (responseCode >= 400) {
                throw new ClientException("Client error occurred with code: " + responseCode);
            }

            // Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            // Parse JSON and extract token
            JSONObject jsonResponse = new JSONObject(content.toString());
            Data data = Data.get();
            String token = jsonResponse.getString("token");
            data.setToken(token);

        } catch (IOException e) {
            throw new ConnectionException("Failed to connect to API: " + e.getMessage());
        } catch (JSONException e) {
            throw new ContentException("Error parsing response: " + e.getMessage());
        }
    }

    public String getUsername()
            throws NotFoundException, ConnectionException, ServerException, ClientException, ContentException {
        try {
            Data data = Data.get();
            String token = data.getToken();

            URL url = new URL(API_URL + "/users/info");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token); // Token already includes "Bearer"

            int responseCode = conn.getResponseCode();

            if (responseCode == 401) {
                throw new NotFoundException("user not found or token expired");
            } else if (responseCode >= 500) {
                throw new ServerException("Server error occurred with code: " + responseCode);
            } else if (responseCode >= 400) {
                throw new ClientException("Client error occurred with code: " + responseCode);
            }

            // Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            // Parse JSON and extract name
            JSONObject jsonResponse = new JSONObject(content.toString());
            return jsonResponse.getString("name");

        } catch (IOException e) {
            throw new ConnectionException("Failed to connect to API: " + e.getMessage());
        } catch (JSONException e) {
            throw new ContentException("Error parsing response: " + e.getMessage());
        }
    }

    public User[] getAllUsers()
            throws NotFoundException, ConnectionException, ServerException, ClientException, ContentException {

        // Obtiene todos los usuarios de la API
        // Hace GET a /api/users/all con token JWT
        // Devuelve array de User[]

        try {
            Data data = Data.get();
            String token = data.getToken();

            URL url = new URL(API_URL + "/users/all");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();

            if (responseCode == 401) {
                throw new NotFoundException("user not found or token expired");
            } else if (responseCode == 404) {
                throw new NotFoundException("user not found");
            } else if (responseCode >= 500) {
                throw new ServerException("Server error occurred with code: " + responseCode);
            } else if (responseCode >= 400) {
                throw new ClientException("Client error occurred with code: " + responseCode);
            }

            // Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            // Parse JSON array directly (no wrapper object)
            JSONArray jsonArray = new JSONArray(content.toString());
            User[] users = new User[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userJson = jsonArray.getJSONObject(i);
                String name = userJson.getString("name");
                String username = userJson.getString("username");
                // Password is not included in API response for security
                users[i] = new User(name, username, null);
            }

            return users;

        } catch (IOException e) {
            throw new ConnectionException("Failed to connect to API: " + e.getMessage());
        } catch (JSONException e) {
            throw new ContentException("Error parsing response: " + e.getMessage());
        }
    }

}