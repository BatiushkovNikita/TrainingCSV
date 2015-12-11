package by.expert.soft.web.command.impl.commands;

import by.expert.soft.web.command.interf.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageImportCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/jsp/import.jsp";
    }
}
