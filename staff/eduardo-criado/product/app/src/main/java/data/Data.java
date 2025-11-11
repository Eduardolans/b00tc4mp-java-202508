package data;

/**
 * Data class for managing client-side session state.
 * Only stores the authentication token - all user data is managed by the API.
 */
public class Data {
    private static Data instance;

    public static Data get() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    private String token;

    private Data() {
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}