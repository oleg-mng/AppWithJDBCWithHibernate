package com.olegmng;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AppHibernate {
    public static void main(String[] args) {
        final SessionFactory sessionFactory = new Configuration().
                configure("hibernate.cfg.xml").buildSessionFactory();
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            User user = new User();
            user.setName("Oleg");
            System.out.println("User before persist: " + user.getId());

            session.persist(user);
            System.out.println("User after persist: " + user.getId());
            session.getTransaction().commit();

        }

        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            User loadedUser = (User) session.get(User.class, 1);
            System.out.println("User = " + loadedUser);
        }

        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            User loadedUser = (User) session.get(User.class, 1);
            System.out.println("User = " + loadedUser);
        }

        sessionFactory.close();


    }

}
