package by.expert.soft.service.interf;

import by.expert.soft.dao.impl.AbstractDao;
import by.expert.soft.dao.impl.FieldSort;
import by.expert.soft.service.exception.ServiceException;
import by.expert.soft.vo.PersonVO;

import java.io.InputStream;
import java.util.List;

public interface PersonService {

    List<int[]> saveOrUpdate(InputStream inputStream) throws ServiceException;

    List<PersonVO> getAll() throws ServiceException;

    int getNoOfRecords() throws ServiceException;

    List<PersonVO> getSortedAndPaginated(int pageNumber, int recordsPerPage, FieldSort fieldSort) throws ServiceException;

    List<PersonVO> getSortedPersons(List<PersonVO> persons, String columnName);

    AbstractDao<PersonVO, Integer> getPersonDao() throws ServiceException;

    void setPersonDao(AbstractDao<PersonVO, Integer> personDao);
}
