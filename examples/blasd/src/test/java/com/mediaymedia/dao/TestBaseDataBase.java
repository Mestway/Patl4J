package com.mediaymedia.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.dbunit.DatabaseTestCase;



/**
 * Created by IntelliJ IDEA.
 * User: juanitu
 * Date: 28-dic-2006
 * Time: 18:39:05
 * To change this template use File | Settings | File Templates.
 */
public abstract class TestBaseDataBase extends DatabaseTestCase implements TestDataBase {
    static SessionFactory sessionFactory;

    protected void setUp() throws Exception {
        super.setUp();
    }

    
    protected void tearDown() throws Exception {
        super.tearDown();    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected IDatabaseConnection getConnection() throws Exception {
        Class driverClass = Class.forName(getDriverClass());
        Connection jdbcConnection = DriverManager.getConnection(
                getDb(), getUsuario(), getPassword());
        return new DatabaseConnection(jdbcConnection);
    }


    String datosPrueba;

    protected IDataSet getDataSet() throws Exception {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(getDatosPrueba());
            ReplacementDataSet dataSet = new ReplacementDataSet(new FlatXmlDataSet(fileInputStream));
            dataSet.addReplacementObject("[NULL]", null);

            return dataSet;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
/*
            IDataSet fullDataSet = getConnection().createDataSet();
            FlatXmlDataSet.write(fullDataSet, new FileOutputStream(getDatosPrueba()));
            FlatDtdDataSet.write(getConnection().createDataSet(),
                    new FileOutputStream("test.dtd"));
            return fullDataSet;
*/

        }
    }


    protected DatabaseOperation getSetUpOperation() throws Exception {
/*
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
*/
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }
}
