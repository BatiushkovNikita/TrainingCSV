package by.expert.soft.dao.impl;

import by.expert.soft.dao.exception.DaoException;
import by.expert.soft.dao.model.Person;
import by.expert.soft.dao.model.PersonDetails;
import by.expert.soft.vo.PersonVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl extends AbstractDao<PersonVO, Integer> {
    private Logger Log = LogManager.getLogger(PersonDaoImpl.class.getName());

    private static volatile PersonDaoImpl instance;

    private PersonDaoImpl() {
    }

    public static AbstractDao<PersonVO, Integer> getInstance() {
        if (instance == null) {
            synchronized (PersonDaoImpl.class) {
                if (instance == null) {
                    instance = new PersonDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    protected String getInsertQuery() throws DaoException {
        try {
            return QUERIES.getString("insert.person");
        } catch (Exception e) {
            Log.error("Query cannot be found", e);
            throw new DaoException("Query cannot be found", e);
        }
    }

    @Override
    protected String getUpdateQuery() throws DaoException {
        try {
            return QUERIES.getString("update.person");
        } catch (Exception e) {
            Log.error("Query cannot be found", e);
            throw new DaoException("Query cannot be found", e);
        }
    }

    @Override
    protected String getSelectAllQuery() throws DaoException {
        try {
            return QUERIES.getString("select.all.person");
        } catch (Exception e) {
            Log.error("Query cannot be found", e);
            throw new DaoException("Query cannot be found", e);
        }
    }

    @Override
    protected String getCountQuery() throws DaoException {
        try {
            return QUERIES.getString("select.person.count");
        } catch (Exception e) {
            Log.error("Query cannot be found", e);
            throw new DaoException("Query cannot be found", e);
        }
    }

    @Override
    protected String getSelectAllSortedAndPaginatedQuery() throws DaoException {
        try {
            return QUERIES.getString("select.all.person.sorted.and.paginated");
        } catch (Exception e) {
            Log.error("Query cannot be found", e);
            throw new DaoException("Query cannot be found", e);
        }
    }

    @Override
    protected void pStatementForInsert(PreparedStatement pStatement, PersonVO personVO) throws DaoException {
        Person person = extractPerson(personVO);
        PersonDetails personDetails = extractPersonDetails(personVO);
        Log.debug("Creating prepared statement for PersonVO insert");
        try {
            pStatement.setString(1, personDetails.getEmail());
            pStatement.setString(2, personDetails.getLogin());
            pStatement.setString(3, personDetails.getPhoneNumber());
            pStatement.setString(4, person.getName());
            pStatement.setString(5, person.getSurname());
        } catch (SQLException e) {
            Log.error("Cannot create prepared statement for PersonVO insert", e);
            throw new DaoException("Cannot create prepared statement for PersonVO insert", e);
        }
    }

    @Override
    protected void pStatementForUpdate(PreparedStatement pStatement, PersonVO personVO) throws DaoException {
        Log.trace("Creating prepared statement for PersonVO update");
        try {
            int id = personVO.getId();
            pStatement.setString(1, personVO.getName());
            pStatement.setString(2, personVO.getSurname());
            pStatement.setString(4, personVO.getLogin());
            pStatement.setString(5, personVO.getEmail());
            pStatement.setString(6, personVO.getPhoneNumber());
            pStatement.setInt(3, id);
            pStatement.setInt(7, id);
        } catch (SQLException e) {
            Log.error("Cannot create prepared statement for PersonVO update", e);
            throw new DaoException("Cannot create prepared statement for PersonVO update", e);
        }
    }

    protected boolean isUnique(Connection connection, PersonVO personVO) throws DaoException {
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        int recordId = 0;
        try {
            pStatement = connection.prepareStatement(QUERIES.getString("person.select.login"));
            pStatement.setString(1, "%" + personVO.getLogin());
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                recordId = resultSet.getInt("pd_id");
            }
            if (recordId != 0) {
                personVO.setId(recordId);
                return false;
            }
            return true;
        } catch (SQLException e) {
            Log.error("Cannot check PersonVO for uniqueness", e);
            throw new DaoException("Cannot check PersonVO for uniqueness", e);
        } finally {
            closeResources(pStatement, resultSet);
        }
    }

    @Override
    protected void pStatementForSortedAndPaginated(PreparedStatement pStatement, int pageNumber, int recordsPerPage, String fieldName) throws DaoException {
        int startNumber = recordsPerPage * (pageNumber - 1);
        try {
            pStatement.setInt(2, startNumber);
            pStatement.setInt(3, recordsPerPage);
            pStatement.setString(1, fieldName);
        } catch (SQLException e) {
            Log.error("Cannot create prepared statement for sorted and paginated list", e);
            throw new DaoException("Cannot create prepared statement for sorted and paginated list", e);
        }
    }

    @Override
    protected List<PersonVO> parseResultSet(ResultSet resultSet) throws DaoException {
        List<PersonVO> persons = new ArrayList<>();
        try {
            while (resultSet.next()) {
                PersonVO personVO = new PersonVO();
                personVO.setId(resultSet.getInt("p_id"));
                personVO.setLogin(resultSet.getString("login"));
                personVO.setEmail(resultSet.getString("email"));
                personVO.setPhoneNumber(resultSet.getString("phoneNumber"));
                personVO.setName(resultSet.getString("name"));
                personVO.setSurname(resultSet.getString("surname"));
                persons.add(personVO);
            }
            return persons;
        } catch (SQLException e) {
            Log.error("Cannot parse result set for User", e);
            throw new DaoException("Cannot parse result set for User", e);
        }
    }

    @Override
    protected Integer parseResultSetKey(ResultSet resultSet) throws DaoException {
        int key = 0;
        try {
            while (resultSet.next()) {
                key = resultSet.getInt(1);
            }
            return key;
        } catch (SQLException e) {
            Log.error("Cannot parse result set for Person", e);
            throw new DaoException("Cannot parse result set for User", e);
        }
    }

    private PersonDetails extractPersonDetails(PersonVO personVO) {
        PersonDetails personDetails = new PersonDetails();
        personDetails.setEmail(personVO.getEmail());
        personDetails.setLogin(personVO.getLogin());
        personDetails.setPhoneNumber(personVO.getPhoneNumber());
        return personDetails;
    }

    private Person extractPerson(PersonVO personVO) {
        Person person = new Person();
        person.setName(personVO.getName());
        person.setSurname(personVO.getSurname());
        return person;
    }
}
