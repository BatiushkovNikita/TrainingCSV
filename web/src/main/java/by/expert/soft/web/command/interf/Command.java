package by.expert.soft.web.command.interf;

import by.expert.soft.web.exception.WebException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response) throws WebException;
}
