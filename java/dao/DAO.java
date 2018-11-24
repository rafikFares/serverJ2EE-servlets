package dao;


import java.util.List;

public abstract class DAO<T, S, D> {

    public DAO() {
    }

    public abstract T insert(T obj);

    public abstract Boolean delete(D id);

    public abstract Boolean update(T obj, S idObject);

    public abstract T findOne(S id);

    public abstract List<T> find(S id);

    public abstract List<T> findByField(S field, S value);

}