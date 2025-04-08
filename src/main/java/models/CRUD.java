package models;

import java.util.List;

public interface CRUD<T> {
    public abstract void save(T entity) throws Exception;
    public abstract List<T> findAll() throws Exception;
    public abstract T findById(int id) throws Exception;
    public abstract void update(T entity) throws Exception;
    public abstract void delete(T entity) throws Exception;
    public abstract List<T> paginated(int limit, int offset)throws Exception;
}
