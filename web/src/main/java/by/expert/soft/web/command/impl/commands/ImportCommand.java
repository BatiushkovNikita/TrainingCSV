package by.expert.soft.web.command.impl.commands;

import by.expert.soft.service.exception.ParseFormatException;
import by.expert.soft.service.exception.ServiceException;
import by.expert.soft.service.impl.PersonServiceImpl;
import by.expert.soft.service.interf.PersonService;
import by.expert.soft.web.command.interf.Command;
import by.expert.soft.web.exception.WebException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ResourceBundle;

public class ImportCommand implements Command {
    private Logger Log = LogManager.getLogger(ImportCommand.class.getName());
    private ResourceBundle bundle = ResourceBundle.getBundle("content");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws WebException {
        try {
            Part part = request.getPart("content");
            if (part.getSize() != 0) {
                InputStream inputStream = part.getInputStream();
                PersonService personService = PersonServiceImpl.getInstance();
                List<int[]> records = personService.saveOrUpdate(inputStream);
                infoHandling(request, records);
            }
        } catch (ParseFormatException exc) {
            Log.error("Cannot import data from file");
            errorHandling(request);
        } catch (IOException | ServletException | ServiceException e) {
            Log.error("Cannot import data from file", e);
            throw new WebException("Cannot import data from file", e);
        }
        return "/jsp/import.jsp";
    }

    private void errorHandling(HttpServletRequest request) {
        request.setAttribute("errorDataFormat", bundle.getString("error.data.format"));
        request.setAttribute("formatExample", bundle.getString("error.format.example"));
    }

    private void infoHandling(HttpServletRequest request, List<int[]> records) {
        int recordsCreated = records.get(0).length;
        int recordsUpdated = records.get(1).length;
        request.setAttribute("infoCreateRecords", bundle.getString("info.create.records") + " " + recordsCreated);
        request.setAttribute("infoUpdateRecords", bundle.getString("info.update.records") + " " + recordsUpdated);
    }
}
