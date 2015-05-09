package com.mediaymedia.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeMethod;

/**
 *
 * User: juan
 * Date: 13-sep-2007
 * Time: 16:10:12
 * To change this template use File | Settings | File Templates.
 */
public class TestTimer {

    protected Log testLog = LogFactory.getLog(TestTimer.class);

    long start;
    long finish;

    @BeforeMethod
    public void startPrueba() {
        start = System.currentTimeMillis();

    }

    @AfterMethod
    public void finishPrueba() {
        finish = System.currentTimeMillis();


        long resultado = finish - start;
        testLog.info("Tiempo empleado en realizar la prueba:" + (resultado / 1000) + " segundos");


    }

}
