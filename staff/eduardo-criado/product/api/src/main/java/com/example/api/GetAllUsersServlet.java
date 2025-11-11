package com.example.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.api.helper.JwtHelper;
import com.example.data.User;
import com.example.errors.NotFoundException;
import com.example.logic.Logic;

@WebServlet("/users/all")
public class GetAllUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Validar el token de la cabecera Authorization
            String userId = JwtHelper.validateTokenAndGetUserId(request.getHeader("Authorization"));

            if (userId == null) {
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("error", "Unauthorized");
                jsonResponse.put("message", "Invalid or expired token");

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            // Obtener todos los usuarios
            Logic logic = Logic.get();
            List<User> users = logic.getAllUsers(userId);

            // Crear array JSON con los usuarios
            JSONArray usersArray = new JSONArray();

            for (User user : users) {
                if (user != null) { // Filtrar usuarios null
                    JSONObject userJson = new JSONObject();
                    userJson.put("name", user.getName());
                    userJson.put("username", user.getUsername());
                    // No incluimos la password por seguridad
                    usersArray.put(userJson);
                }
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(usersArray.toString());

        } catch (NotFoundException e) {
            // Usuario no encontrado
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("error", "NotFoundException");
            jsonResponse.put("message", e.getMessage());

            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
