package com.example;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import data.User;
import logic.Logic;

@WebServlet("/users")
public class GetAllUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Obtener todos los usuarios
            Logic logic = Logic.get();
            User[] users = logic.getAllUsers();

            // Crear array JSON con los usuarios
            JSONArray usersArray = new JSONArray();

            for (User user : users) {
                if (user != null) {  // Filtrar usuarios null
                    JSONObject userJson = new JSONObject();
                    userJson.put("name", user.getName());
                    userJson.put("username", user.getUsername());
                    // No incluimos la password por seguridad
                    usersArray.put(userJson);
                }
            }

            // Respuesta exitosa
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", true);
            jsonResponse.put("count", usersArray.length());
            jsonResponse.put("users", usersArray);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(jsonResponse.toString());

        } catch (Exception e) {
            // Error general
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", false);
            jsonResponse.put("error", "InternalServerError");
            jsonResponse.put("message", e.getMessage());

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(jsonResponse.toString());
        }
    }
}
