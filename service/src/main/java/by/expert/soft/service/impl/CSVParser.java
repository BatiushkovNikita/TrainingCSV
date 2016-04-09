package by.expert.soft.service.impl;

import by.expert.soft.service.exception.ParseFormatException;
import by.expert.soft.service.exception.ServiceException;
import by.expert.soft.vo.PersonVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class CSVParser {

    private static Logger Log = LogManager.getLogger(CSVParser.class.getName());
    private static String regexp = "^(\\s)*[A-ZА-Я][a-zа-я]+(\\s)*,(\\s)*[A-ZА-Я][a-zа-я]+(\\s)*,(\\s)*[\\w]+(\\s)*,(\\s)*[\\w@.]+(\\s)*,(\\s)*[\\d]+(\\s)*$";

    private CSVParser() {
    }

    public static List<PersonVO> parse(InputStream inputStream) throws ServiceException {
        Log.debug("Start parsing data from stream");
        List<PersonVO> personVOs = new ArrayList<>();
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null) {
                if (line.matches(regexp)) {
                    String[] person = line.split(",");
                    PersonVO personVO = new PersonVO();
                    personVO.setName(person[0].trim());
                    personVO.setSurname(person[1].trim());
                    personVO.setLogin(person[2].trim());
                    personVO.setEmail(person[3].trim());
                    personVO.setPhoneNumber(person[4].trim());
                    personVOs.add(personVO);
                } else {
                    throw new ParseFormatException();
                }
            }
            return personVOs;
        } catch (IOException e) {
            Log.error("Cannot parse data from stream", e);
            throw new ServiceException("Cannot parse data from stream", e);
        }
    }
}
