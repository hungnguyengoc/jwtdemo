package example.springjwt.repositories.userRepository;

import example.springjwt.models.entities.User;
import example.springjwt.utils.HibernateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public User getById(Long id) {
        /*try {
            Session session = HibernateUtils.currentSession();
            String sql = "FROM User u WHERE u.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            User user = (User) query.uniqueResult();
            HibernateUtils.closeSession();
            return user;
        } catch (Exception e) {
            return null;
        }*/

        return null;
    }

    @Override
    public User getUniqueByField(String fieldName, Object value) {
        return null;
    }

    @Override
    public List<User> getAll() {
        try {
            List<User> list = entityManager.createQuery("FROM User").getResultList();
            return list;
        } catch (Exception e) {
            logger.error("Exception getALll", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<User> getByField(String fieldName, Object fieldValue) {
        return null;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User delete(User user) {
        return null;
    }

    @Override
    public List<User> delete(List<Long> id) {
        return null;
    }
}
