package com.example.api;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.example.api.util.JwtUtil;
import com.example.errors.CredentialsException;
import com.example.errors.NotFoundException;
import com.example.logic.Logic;

@WebServlet("/users/auth")
public class AuthenticateUserServlet extends HttpServlet {
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
            String userId = logic.authenticateUser(username, password);

            String token = JwtUtil.generateToken(userId);

            // Respuesta exitosa con el nombre del usuario
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("token", "Bearer " + token);

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
