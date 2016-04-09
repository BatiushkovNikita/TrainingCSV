package by.expert.soft.web.controller;


import by.expert.soft.web.command.impl.CommandFactory;
import by.expert.soft.web.command.interf.Command;
import by.expert.soft.web.exception.WebException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
public class Controller extends HttpServlet {
    public Logger Log = LogManager.getLogger(Controller.class.getName());

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        performAction(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        performAction(request, response);
    }

    private void performAction(HttpServletRequest request, HttpServletResponse response) {
        String paramCommand = request.getParameter("command");
        try {
            Command command = CommandFactory.getCommand(paramCommand);
            String nextPage = command.execute(request, response);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException | WebException e) {
            Log.error("Cannot perform action by parameter: " + paramCommand, e);
        }
    }
}
