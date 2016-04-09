package by.expert.soft.web.command.impl.commands;


import by.expert.soft.dao.impl.FieldSort;
import by.expert.soft.service.exception.ServiceException;
import by.expert.soft.service.impl.PersonServiceImpl;
import by.expert.soft.service.interf.PersonService;
import by.expert.soft.vo.PersonVO;
import by.expert.soft.web.command.interf.Command;
import by.expert.soft.web.exception.WebException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewDataCommand implements Command {
    private Logger Log = LogManager.getLogger(ImportCommand.class.getName());
    public final int RECORDS_PER_PAGE = 10;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws WebException {
        try {
            String page = request.getParameter("page");
            int currentPage = 1;
            if (page != null) {
                currentPage = Integer.valueOf(page);
            }
            PersonService personService = PersonServiceImpl.getInstance();
            int noOfRecords = personService.getNoOfRecords();
            List<PersonVO> persons = personService.getSortedAndPaginated(currentPage, RECORDS_PER_PAGE, FieldSort.DEFAULT);
            String columnName = request.getParameter("column");
            if (columnName != null) {
                persons = personService.getSortedPersons(persons, columnName);
            }
            request.setAttribute("persons", persons);
            request.setAttribute("currentPage", currentPage);
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
            request.setAttribute("noOfPages", noOfPages);
            return "/jsp/data.jsp";
        } catch (ServiceException e) {
            Log.error("Cannot view data", e);
            throw new WebException("Cannot view data", e);
        }
    }
}
