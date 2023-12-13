package model.listeners;


import DB.ConnectionPool;
import model.DAO.*;

import javax.servlet.ServletContextListener;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.sql.Connection;

//@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool pool = new ConnectionPool();

        UserDAO userDAO = new UserDAO(pool);
        QuizDAO quizDAO = new QuizDAO(pool);
        QuestionDAO questionDAO = new QuestionDAO(pool);
        QuizPerformanceDAO performanceDAO = new QuizPerformanceDAO(pool);
        MessageDAO messageDAO = new MessageDAO(pool);
        AnnouncementDAO announcementDAO = new AnnouncementDAO(pool);
        QuizCategoryDAO quizCategoryDAO = new QuizCategoryDAO(pool);

        servletContextEvent.getServletContext().setAttribute("userDAO", userDAO);
        servletContextEvent.getServletContext().setAttribute("quizDAO", quizDAO);
        servletContextEvent.getServletContext().setAttribute("questionDAO", questionDAO);
        servletContextEvent.getServletContext().setAttribute("quizPerformanceDAO", performanceDAO);
        servletContextEvent.getServletContext().setAttribute("messageDAO", messageDAO);
        servletContextEvent.getServletContext().setAttribute("announcementDAO",announcementDAO);
        servletContextEvent.getServletContext().setAttribute("categoryDAO",quizCategoryDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}