package university.alisawalter.DAO.postgres;

import university.alisawalter.DAO.DAOException;

import java.util.List;

public interface DAO<T> {
    void add(T objectToadd) throws DAOException;

    List<T> getAll() throws DAOException;

    T getById(int id) throws DAOException;

    void update(T objectToUpdate) throws DAOException;

    void remove(T objectToRemove) throws DAOException;
}
