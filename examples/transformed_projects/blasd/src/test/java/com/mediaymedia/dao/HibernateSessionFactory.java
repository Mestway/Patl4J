package com.mediaymedia.dao;


import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.cfg.Configuration;


public class HibernateSessionFactory {

    /**
     * Location of hibernate.cfg.xml file.
     * NOTICE: Location should be on the classpath as Hibernate uses
     * #resourceAsStream style lookup for its configuration file. That
     * is place the config file in a Java package - the default location
     * is the default Java package.<br><br>
     * Examples: <br>
     * <code>CONFIG_FILE_LOCATION = "/hibernate.conf.xml".
     * CONFIG_FILE_LOCATION = "/com/foo/bar/myhiberstuff.conf.xml".</code>
     */
    private static String CONFIG_FILE_LOCATION = "hibernate.test.cfg.xml";


    /**
     * The single instance of hibernate configuration
     */
    private static final Configuration cfg = new Configuration();

    /**
     * The single instance of hibernate SessionFactory
     */
    private static org.hibernate.SessionFactory sessionFactory;


    /**
     * Metodo encargado de crear un session factory en caso de no existir uno actual.
     */
    private static void creaSessionFactory() {
        if (sessionFactory == null) {
            try {

                cfg.configure(CONFIG_FILE_LOCATION);
                sessionFactory = cfg.buildSessionFactory();
            }
            catch (Exception e) {
                System.err.println("%%%% Error Creating SessionFactory %%%%");
                e.printStackTrace();
            }
        }
    }


    /**
     * Metodo encargado de crear una nueva sesion de hibernate
     */
    public static Session crearSesion() {
        creaSessionFactory();
//                                        new DAOInterceptor()
        return sessionFactory.openSession();
    }

    /**
     * Default constructor.
     */
    private HibernateSessionFactory() {
    }

}
