package com.example.api;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.example.errors.DuplicityException;
import com.example.logic.Logic;

@WebServlet("/users")
public class RegisterUserServlet extends HttpServlet {
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

            String name = jsonRequest.getString("name");
            String username = jsonRequest.getString("username");
            String password = jsonRequest.getString("password");

            // Llamar a la lógica de registro
            Logic logic = Logic.get();
            logic.registerUser(name, username, password);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().flush();

        } catch (DuplicityException e) {
            // Usuario ya existe
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", false);
            jsonResponse.put("error", "DuplicityException");
            jsonResponse.put("message", e.getMessage());

            response.setStatus(HttpServletResponse.SC_CONFLICT);
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
