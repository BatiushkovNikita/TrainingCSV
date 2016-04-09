package by.expert.soft.service.impl;

import by.expert.soft.dao.exception.DaoException;
import by.expert.soft.dao.impl.AbstractDao;
import by.expert.soft.dao.impl.FieldSort;
import by.expert.soft.dao.impl.PersonDaoImpl;
import by.expert.soft.service.comapators.PersonComparator;
import by.expert.soft.service.exception.ServiceException;
import by.expert.soft.service.interf.PersonService;
import by.expert.soft.vo.PersonVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PersonServiceImpl implements PersonService {
    private Logger Log = LogManager.getLogger(PersonServiceImpl.class.getName());
    private static volatile PersonService personService;

    private AbstractDao<PersonVO, Integer> personDao;

    {
        setPersonDao(PersonDaoImpl.getInstance());
    }

    private PersonServiceImpl() {

    }

    @Override
    public synchronized List<int[]> saveOrUpdate(InputStream inputStream) throws ServiceException {
        try {
            List<PersonVO> personVOs = CSVParser.parse(inputStream);
            return personDao.createOrUpdateAll(personVOs);
        } catch (DaoException e) {
            Log.error("Cannot save or update persons", e);
            throw new ServiceException("Cannot save or update persons", e);
        }
    }

    @Override
    public List<PersonVO> getAll() throws ServiceException {
        List<PersonVO> persons;
        try {
            persons = personDao.getAll();
            return persons;
        } catch (DaoException e) {
            Log.error("Cannot get all persons", e);
            throw new ServiceException("Cannot get all persons", e);
        }
    }

    @Override
    public int getNoOfRecords() throws ServiceException {
        try {
            return personDao.getNumberOfRecords();
        } catch (DaoException e) {
            Log.error("Cannot get number of records", e);
            throw new ServiceException("Cannot get number of records", e);
        }
    }

    @Override
    public List<PersonVO> getSortedAndPaginated(int pageNumber, int recordsPerPage, FieldSort fieldSort) throws ServiceException {
        String fieldName = fieldSort.toString();
        List<PersonVO> persons;
        try {
            persons = personDao.getSortedPages(pageNumber, recordsPerPage, fieldName);
            return persons;
        } catch (DaoException e) {
            Log.error("Cannot get all persons", e);
            throw new ServiceException("Cannot get all persons", e);
        }
    }

    @Override
    public List<PersonVO> getSortedPersons(List<PersonVO> persons, String columnName) {
        Comparator comparator = PersonComparator.loadComparator(columnName);
        Collections.sort(persons, comparator);
        return persons;
    }

    @Override
    public AbstractDao<PersonVO, Integer> getPersonDao() throws ServiceException {
        if (personDao == null) {
            Log.error("Cannot configured PersonService. PersonDao is not submitted.");
            throw new ServiceException("Cannot configured PersonService. PersonDao is not submitted.");
        }
        return personDao;
    }

    @Override
    public void setPersonDao(AbstractDao<PersonVO, Integer> personDao) {
        this.personDao = personDao;
    }

    public static PersonService getInstance() {
        if (personService == null) {
            synchronized (PersonService.class) {
                if (personService == null) {
                    personService = new PersonServiceImpl();
                }
            }
        }
        return personService;
    }
}
