package by.expert.soft.dao.interf;

import by.expert.soft.dao.exception.DaoException;

import java.util.List;

public interface PagingAndSorting<T> {

    List<T> getSortedPages(int pageNumber, int pageCount, String fieldName) throws DaoException;

    int getNumberOfRecords() throws DaoException;
}
