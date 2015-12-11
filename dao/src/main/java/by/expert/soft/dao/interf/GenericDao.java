package by.expert.soft.dao.interf;

import by.expert.soft.dao.exception.DaoException;

import java.util.List;

public interface GenericDao<T, PK> {

    PK create(T object) throws DaoException;

    void update(T object) throws DaoException;

    List<T> getAll() throws DaoException;

    List<int[]> createOrUpdateAll(List<T> objects) throws DaoException;

    int[] createAll(List<T> objects) throws DaoException;

    int[] updateAll(List<T> objects) throws DaoException;
}
