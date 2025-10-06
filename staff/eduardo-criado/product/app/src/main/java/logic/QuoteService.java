package logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class QuoteService {
    public static String getDailyQuote() {
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
}
