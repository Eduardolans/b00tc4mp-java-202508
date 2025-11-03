package com.example;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import data.User;
import errors.NotFoundException;
import logic.Logic;

@WebServlet("/getUserName")
public class GetUserName extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String username;

            // Si se proporciona un parámetro username, usarlo
            String requestedUsername = request.getParameter("username");

            if (requestedUsername != null && !requestedUsername.isEmpty()) {
                // Consultar usuario específico
                username = requestedUsername;
            } else {
                // Si no hay parámetro, verificar sesión (comportamiento actual)
                HttpSession session = request.getSession(false);

                if (session == null || session.getAttribute("username") == null) {
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("success", false);
                    jsonResponse.put("error", "Unauthorized");
                    jsonResponse.put("message", "No active session and no username provided");

                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(jsonResponse.toString());
                    return;
                }
                username = (String) session.getAttribute("username");
            }

            // Obtener los datos del usuario
            Logic logic = Logic.get();
            User user = logic.getUserByUsername(username);

            // Respuesta exitosa
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", true);
            jsonResponse.put("name", user.getName());
            jsonResponse.put("username", user.getUsername());

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(jsonResponse.toString());

        } catch (NotFoundException e) {

            // Error de usuario no encontrado
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", false);
            jsonResponse.put("error", "NotFound");
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