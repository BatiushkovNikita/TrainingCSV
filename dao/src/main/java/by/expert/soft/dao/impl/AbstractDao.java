package by.expert.soft.dao.impl;

import by.expert.soft.dao.exception.DaoException;
import by.expert.soft.dao.interf.GenericDao;
import by.expert.soft.dao.interf.PagingAndSorting;
import by.expert.soft.dao.pool.ConnectionManager;
import by.expert.soft.dao.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public abstract class AbstractDao<T, PK> implements GenericDao<T, PK>, PagingAndSorting<T> {
    public final ResourceBundle QUERIES = ResourceBundle.getBundle("queries");

    private Logger Log = LogManager.getLogger(AbstractDao.class.getName());

    @Override
    public List<int[]> createOrUpdateAll(List<T> objects) throws DaoException {
        Log.debug("Create/update objects");
        Connection connection = null;
        List<int[]> createdAndUpdated;
        List<T> objectsForCreate = new ArrayList<>();
        List<T> objectsForUpdate = new ArrayList<>();
        try {
            connection = getConnection();
            for (T object : objects) {
                if (isUnique(connection, object)) {
                    objectsForCreate.add(object);
                } else {
                    objectsForUpdate.add(object);
                }
            }
            int[] created = createAll(objectsForCreate);
            int[] updated = updateAll(objectsForUpdate);
            createdAndUpdated = new ArrayList<>(Arrays.asList(created, updated));
            return createdAndUpdated;
        }
        catch (DaoException e) {
            Log.error("Cannot create or update objects", e);
            throw new DaoException("Cannot create or update objects", e);
        } finally {
            closeResources(connection);
        }
    }

    @Override
    public int[] createAll(List<T> objects) throws DaoException {
        Log.debug("Create objects");
        String query = getInsertQuery();
        PreparedStatement pStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            pStatement = connection.prepareStatement(query);
            for (T object : objects) {
                pStatementForInsert(pStatement, object);
                pStatement.addBatch();
            }
            int[] recordsInsert = pStatement.executeBatch();
            connection.commit();
            return recordsInsert;
        } catch (SQLException e) {
            rollback();
            Log.error("Cannot create objects", e);
            throw new DaoException("Cannot create objects", e);
        } finally {
            closeResources(connection, pStatement);
        }
    }

    @Override
    public int[] updateAll(List<T> objects) throws DaoException {
        Log.debug("Update objects");
        String query = getUpdateQuery();
        PreparedStatement pStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            pStatement = getConnection().prepareStatement(query);
            for (T object : objects) {
                pStatementForUpdate(pStatement, object);
                pStatement.addBatch();
            }
            int[] recordsUpdate = pStatement.executeBatch();
            connection.commit();
            return recordsUpdate;
        } catch (SQLException e) {
            rollback();
            Log.error("Cannot update objects", e);
            throw new DaoException("Cannot update objects", e);
        } finally {
            closeResources(connection, pStatement);
        }
    }

    @Override
    public PK create(T object) throws DaoException {
        Log.debug("Creating new " + object);
        PK key = null;
        String query = getInsertQuery();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            pStatement = connection.prepareStatement(query);
            pStatementForInsert(pStatement, object);
            pStatement.executeUpdate();
            resultSet = pStatement.executeQuery("SELECT LAST_INSERT_ID()");
            key = parseResultSetKey(resultSet);
            Log.debug(object + " was created");
            return key;
        } catch (SQLException e) {
            Log.error("Cannot create " + object, e);
            throw new DaoException("Cannot create " + object, e);
        } finally {
            closeResources(connection, pStatement, resultSet);
        }
    }

    @Override
    public void update(T object) throws DaoException {
        Log.debug("Updating " + object);
        String query = getUpdateQuery();
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getConnection();
            pStatement = connection.prepareStatement(query);
            pStatementForUpdate(pStatement, object);
            int count = pStatement.executeUpdate();
            if (count == 1) {
                Log.debug("Object: " + object + " was updated");
            } else if (count > 1) {
                Log.debug("On update modify more then 1 record: " + count);
            } else {
                Log.debug("Object: " + object + " not found");
            }
        } catch (SQLException e) {
            Log.error("Cannot update " + object);
            throw new DaoException("Cannot update " + object, e);
        } finally {
            closeResources(connection, pStatement);
        }
    }

    @Override
    public List<T> getSortedPages(int pageNumber, int recordsPerPage, String fieldName) throws DaoException {
        Log.debug("Getting sorted and paginated list of objects");
        List<T> entities;
        String query = getSelectAllSortedAndPaginatedQuery();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            pStatement = connection.prepareStatement(query);
            pStatementForSortedAndPaginated(pStatement, pageNumber, recordsPerPage, fieldName);
            resultSet = pStatement.executeQuery();
            entities = parseResultSet(resultSet);
            if (entities.size() == 0) {
                Log.debug("Object not found");
            } else {
                Log.debug("Returning list of objects");
            }
            return entities;
        } catch (SQLException e) {
            Log.error("Cannot get sorted and paginated list of objects", e);
            throw new DaoException("Cannot get sorted and paginated list of objects", e);
        } finally {
            closeResources(connection, pStatement, resultSet);
        }
    }

    @Override
    public int getNumberOfRecords() throws DaoException {
        Log.debug("Getting number of records");
        String query = getCountQuery();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int numberOfRecords = 0;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                numberOfRecords = resultSet.getInt(1);
            }
            return numberOfRecords;
        } catch (SQLException e) {
            Log.error("Cannot get number of records", e);
            throw new DaoException("Cannot get number of records", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }

    @Override
    public List<T> getAll() throws DaoException {
        Log.debug("Getting list of objects");
        List<T> entities;
        String query = getSelectAllQuery();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            entities = parseResultSet(resultSet);
            if (entities.size() == 0) {
                Log.debug("Object not found");
            } else {
                Log.debug("Returning list of objects");
            }
            return entities;
        } catch (SQLException e) {
            Log.error("Cannot get list of objects", e);
            throw new DaoException("Cannot get list of objects", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }

    protected abstract String getSelectAllSortedAndPaginatedQuery() throws DaoException;

    protected abstract String getInsertQuery() throws DaoException;

    protected abstract String getUpdateQuery() throws DaoException;

    protected abstract String getSelectAllQuery() throws DaoException;

    protected abstract String getCountQuery() throws DaoException;

    protected abstract void pStatementForInsert(PreparedStatement pStatement, T object) throws DaoException;

    protected abstract void pStatementForUpdate(PreparedStatement pStatement, T object) throws DaoException;

    protected abstract void pStatementForSortedAndPaginated(PreparedStatement pStatement, int pageNumber, int recordsPerPage, String fieldName) throws DaoException;

    protected abstract List<T> parseResultSet(ResultSet resultSet) throws DaoException;

    protected abstract PK parseResultSetKey(ResultSet resultSet) throws DaoException;

    protected abstract boolean isUnique(Connection connection, T object) throws DaoException;

    public Connection getConnection() throws DaoException {
        return ConnectionPool.POOL.getConnection();
    }

/*    public Connection getConnection() throws DaoException {
        return ConnectionManager.POOL.getConnection();
    }*/

    public void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
                Log.debug("ResultSet closed");
            } catch (SQLException e) {
                Log.error("Cannot close ResultSet", e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
                Log.debug("Statement closed");
            } catch (SQLException e) {
                Log.error("Cannot close Statement", e);
            }
        }
/*        if (connection != null) {
            ConnectionManager.POOL.closeConnection(connection);
        }*/
        if (connection != null) {
            try {
                connection.close();
                Log.debug("Connection closed");
            } catch (SQLException e) {
                Log.error("Cannot close Connection", e);
            }
        }
    }

    public void closeResources(Statement statement, ResultSet resultSet) {
        closeResources(null, statement, resultSet);
    }

    public void closeResources(Connection connection, Statement statement) {
        closeResources(connection, statement, null);
    }

    public void closeResources(Connection connection) {
        closeResources(connection, null, null);
    }

    private void rollback() throws DaoException {
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            Log.error("Cannot rollback transaction", e);
            throw new DaoException("Cannot rollback transaction", e);
        }
    }
}
