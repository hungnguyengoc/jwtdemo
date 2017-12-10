package example.springjwt.services.commons;

import example.springjwt.models.entities.commons.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    T getById(Long id);

    T getUniqueByField(String fieldName, Object value);

    List<T> getAll();

    List<T> getByField(String fieldName, Object fieldValue);

    T create(T t);

    T update(T t);

    T delete(T t);

    List<T> delete(List<Long> id);

    default T delete(Long id) {
        T t = this.getById(id);
        if (t != null) {
            this.delete(t);
        }
        return t;
    }
}
