package com.mediaymedia.gdata;

import com.mediaymedia.test.EinaTest;
import com.mediaymedia.test.TestTimer;
import org.testng.annotations.BeforeClass;

import java.util.Properties;

/**
 * User: juan
 * Date: 14-oct-2007
 * Time: 18:22:29
 */
public class GDataBaseTest extends TestTimer {
    protected String usuarioGmail = "";
    protected String extensionMail = "@gmail.com";
    protected String blog = "";
    protected String clave = "";

    protected enum propiedades {
        usuarioGmail, claveGmail, blog
    }


    @BeforeClass()
    public void inicioBaseTestZanfona() {
        testLog.debug("inicioBaseTestZanfona");

        Properties properties = EinaTest.loadPropertiesFile("componentsTest_mediaymedia.properties");

        clave = EinaTest.loadProperty(properties, propiedades.claveGmail.toString());
        usuarioGmail = EinaTest.loadProperty(properties, propiedades.usuarioGmail.toString());
        blog = EinaTest.loadProperty(properties, propiedades.blog.toString());


    }


}
