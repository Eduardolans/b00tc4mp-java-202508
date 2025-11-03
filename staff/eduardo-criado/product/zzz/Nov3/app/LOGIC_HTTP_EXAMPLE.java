package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private String name; // Para guardar el nombre del usuario

    // URL base de tu API
    private static final String API_URL = "http://localhost:8080/api";

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

    /**
     * NUEVO: registerUser usando la API
     * Hace una petición POST a /api/register con JSON
     */
    public void registerUser(String name, String username, String password) throws DuplicityException, ConnectionException, ServerException, ClientException, ContentException {
        try {
            // Crear URL del endpoint
            URL url = new URL(API_URL + "/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Crear JSON con los datos
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("name", name);
            jsonRequest.put("username", username);
            jsonRequest.put("password", password);

            // Enviar el JSON
            OutputStream os = conn.getOutputStream();
            os.write(jsonRequest.toString().getBytes("UTF-8"));
            os.close();

            // Leer respuesta
            int responseCode = conn.getResponseCode();

            BufferedReader in;
            if (responseCode >= 400) {
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }

            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            // Parsear respuesta JSON
            JSONObject jsonResponse = new JSONObject(content.toString());

            // Manejar errores según el código de respuesta
            if (responseCode == 409) { // Conflict - usuario duplicado
                throw new DuplicityException(jsonResponse.getString("message"));
            } else if (responseCode >= 500) {
                throw new ServerException("Server error: " + jsonResponse.optString("message", "Unknown error"));
            } else if (responseCode >= 400) {
                throw new ClientException("Client error: " + jsonResponse.optString("message", "Unknown error"));
            }

            // Si llegamos aquí, el registro fue exitoso (201 Created)

        } catch (IOException e) {
            throw new ConnectionException("Failed to connect to API: " + e.getMessage());
        } catch (JSONException e) {
            throw new ContentException("Error parsing API response: " + e.getMessage());
        }
    }

    /**
     * NUEVO: loginUser usando la API
     * Hace una petición POST a /api/login con JSON
     * NOTA: Necesitas crear el servlet LoginServlet en la API
     */
    public void loginUser(String username, String password) throws NotFoundException, CredentialsException, ConnectionException, ServerException, ClientException, ContentException {
        try {
            // Crear URL del endpoint
            URL url = new URL(API_URL + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Crear JSON con las credenciales
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("username", username);
            jsonRequest.put("password", password);

            // Enviar el JSON
            OutputStream os = conn.getOutputStream();
            os.write(jsonRequest.toString().getBytes("UTF-8"));
            os.close();

            // Leer respuesta
            int responseCode = conn.getResponseCode();

            BufferedReader in;
            if (responseCode >= 400) {
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }

            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            // Parsear respuesta JSON
            JSONObject jsonResponse = new JSONObject(content.toString());

            // Manejar errores
            if (responseCode == 401 || responseCode == 404) { // Unauthorized o Not Found
                throw new CredentialsException(jsonResponse.getString("message"));
            } else if (responseCode >= 500) {
                throw new ServerException("Server error: " + jsonResponse.optString("message", "Unknown error"));
            } else if (responseCode >= 400) {
                throw new ClientException("Client error: " + jsonResponse.optString("message", "Unknown error"));
            }

            // Si el login fue exitoso, guardar los datos del usuario
            this.username = username;
            this.name = jsonResponse.getString("name"); // La API devuelve el nombre

        } catch (IOException e) {
            throw new ConnectionException("Failed to connect to API: " + e.getMessage());
        } catch (JSONException e) {
            throw new ContentException("Error parsing API response: " + e.getMessage());
        }
    }

    /**
     * MODIFICADO: getUsername ahora devuelve el nombre guardado en memoria
     * Ya no necesita hacer petición a la API porque guardamos el nombre al hacer login
     */
    public String getUsername() throws NotFoundException {
        if (this.name == null) {
            throw new NotFoundException("user not logged in");
        }
        return this.name;
    }

}
