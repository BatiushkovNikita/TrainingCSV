package by.expert.soft.dao.exception;

import java.sql.SQLException;

public class DaoException extends SQLException {

    public DaoException() {
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
