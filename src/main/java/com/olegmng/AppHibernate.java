package com.olegmng;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class AppHibernate {
    public static void main(String[] args) {

        final SessionFactory sessionFactory = new Configuration().
                configure("hibernate.cfg.xml").buildSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<User> users = LongStream.rangeClosed(1, 10)
                    .mapToObj(it -> new User(it))
                    .peek(it -> it.setName("User #" + it.getId()))
                    .peek(it -> session.persist(it))
                    .collect(Collectors.toList());


//            User user = new User();
//            user.setName("Oleg");
//            System.out.println("User before persist: " + user.getId());
//
//            session.persist(user);
//            System.out.println("User after persist: " + user.getId());
            session.getTransaction().commit();

        }

        //операция update
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User loadedUser = session.get(User.class, 1);
            loadedUser.setName("Updated");

            session.merge(loadedUser);
            session.getTransaction().commit();

        }

        try (Session session = sessionFactory.openSession()) {

            //JQL - java query language
            List<User> users = session.createQuery("select u from User u where id >= 1", User.class)
                    .getResultList();

            System.out.println(users);

            User loadedUser = session.get(User.class, 1);
            System.out.println("User = " + loadedUser);
        }

        sessionFactory.close();


    }

}
