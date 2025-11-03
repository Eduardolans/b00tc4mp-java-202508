package com.example;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import data.User;
import errors.CredentialsException;
import errors.NotFoundException;
import logic.Logic;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Leer el cuerpo de la petición JSON
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // Parsear el JSON
            JSONObject jsonRequest = new JSONObject(sb.toString());

            String username = jsonRequest.getString("username");
            String password = jsonRequest.getString("password");

            // Llamar a la lógica de login
            Logic logic = Logic.get();
            User user = logic.loginUser(username, password);

            // Crear sesión y guardar el username
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username);

            // Respuesta exitosa con el nombre del usuario
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", true);
            jsonResponse.put("message", "Login successful");
            jsonResponse.put("name", user.getName());
            jsonResponse.put("username", user.getUsername());

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(jsonResponse.toString());

        } catch (CredentialsException | NotFoundException e) {
            // Credenciales inválidas
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", false);
            jsonResponse.put("error", "CredentialsException");
            jsonResponse.put("message", e.getMessage());

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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
