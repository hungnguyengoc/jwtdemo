package example.springjwt.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtils {
    private static final ThreadLocal<Session> session = new ThreadLocal();
    private static SessionFactory sessionFactory = null;

    static {
        try {
            if (sessionFactory == null) {
                Configuration configuration = new Configuration().configure();
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration
                        .getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session currentSession() throws HibernateException {
        Session session = (Session) HibernateUtils.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            HibernateUtils.session.set(session);
        }
        return session;
    }

    public static void closeSession() throws HibernateException {
        Session session = (Session) HibernateUtils.session.get();
        if (session != null) {
            session.close();
        }
        HibernateUtils.session.set(null);
    }
}
