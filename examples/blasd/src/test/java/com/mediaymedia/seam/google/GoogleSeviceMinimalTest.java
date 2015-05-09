package com.mediaymedia.seam.google;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import java.util.Properties;

import org.jboss.seam.faces.FacesMessages;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.mediaymedia.gdata.service.GoogleProperties;
import com.mediaymedia.test.EinaTest;
import com.mediaymedia.test.Logger;
import com.mediaymedia.test.TestTimer;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 11:47:36
 */
public abstract class GoogleSeviceMinimalTest extends TestTimer {
    Properties properties;
    GoogleProperties googleProperties;
    FacesMessages facesMessages;

    protected abstract  GDataSeam getProbando();


    @BeforeClass
    public void iniciaPadreBeforeClass(){
        properties = EinaTest.loadPropertiesFile("componentsTest_mediaymedia.properties");
        

    }

    @BeforeMethod
    public void iniciaPadreBeforeMetod(){
        facesMessages = createMock(FacesMessages.class);
        googleProperties = createMock(GoogleProperties.class);
        getProbando().setFacesMessages(facesMessages);
        getProbando().setLog(new Logger());

    }


    protected void expectConsultaProperties(String gmailCuenta) {
        expect(googleProperties.getBlog()).andStubReturn(getBlog());
        expect(googleProperties.getClave()).andStubReturn(getClaveGmail());
        expect(googleProperties.getGmailCuenta()).andStubReturn(gmailCuenta);
        expect(googleProperties.logea()).andStubReturn("Blog: "+getBlog()+ " cuenta: " +gmailCuenta+getClaveGmail());
    }


    protected String getBlog() {
           return EinaTest.loadProperty(properties, "blog");
       }

       protected String getClaveGmail() {
           return EinaTest.loadProperty(properties, "claveGmail");
       }

       protected String getUsuarioGmail() {
           return EinaTest.loadProperty(properties, "usuarioGmail");
       }


}
