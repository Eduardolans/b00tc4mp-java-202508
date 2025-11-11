package com.example.api;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.example.data.Data;

/**
 * Servlet for resetting all data in memory.
 * FOR TESTING PURPOSES ONLY - Should only be used in test environments
 */
@WebServlet("/test/reset")
public class ResetDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Reset all data in memory
            Data data = Data.get();
            data.reset();

            // Success response
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", "Data reset successfully");

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(jsonResponse.toString());

        } catch (Exception e) {
            // Error response
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", false);
            jsonResponse.put("error", "InternalServerError");
            jsonResponse.put("message", e.getMessage());

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(jsonResponse.toString());
        }
    }
}
