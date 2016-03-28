package com.mediaymedia.seam.google;

import com.google.gdata.client.GoogleService;
import com.google.gdata.data.Feed;
import com.mediaymedia.faces.ErrorFaces;
import com.mediaymedia.gdata.errors.GoogleErrorAutentificacion;
import com.mediaymedia.gdata.errors.GoogleErrorBlogURL;
import com.mediaymedia.gdata.errors.GoogleErrorIO;
import com.mediaymedia.gdata.errors.GoogleProblema;
import com.mediaymedia.gdata.service.BloggerService;
import com.mediaymedia.gdata.service.GoogleProperties;
import com.mediaymedia.test.Logger;
import static org.easymock.classextension.EasyMock.*;
import org.testng.annotations.*;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 10:44:16
 */
public class BloggerServiceMinimalImplTest extends GoogleSeviceMinimalTest {


    BloggerServiceMinimalImpl bloggerServiceMinimal;
    BloggerService bloggerService;
    GoogleService googleService;
    Feed feed;

    protected GDataSeam getProbando(){
        return bloggerServiceMinimal;
    }

    public Object[] getMocks() {
        return new Object[]{bloggerService,  googleService,  feed, googleProperties, facesMessages};
    }

     @BeforeClass
     public void iniciaClase(){
         bloggerServiceMinimal = new BloggerServiceMinimalImpl();
         
     }

     @BeforeMethod
    public void iniciar() {

        bloggerService = createMock(BloggerService.class);
        googleService = createMock(GoogleService.class);
        feed = createMock(Feed.class);

        bloggerServiceMinimal.bloggerService = bloggerService;
         bloggerServiceMinimal.googleProperties=googleProperties;

        reset(getMocks());


    }


    @AfterMethod
    public void finaliza() {
        verify(getMocks());
    }


    @Test
    public void testDameEntradas() throws GoogleProblema {
        String gmailCuenta = getUsuarioGmail() + GoogleProperties.gmailExtension;

        expectConsultaProperties(gmailCuenta);
        expect(bloggerService.createBloggerService(gmailCuenta, getClaveGmail())).andReturn(googleService);
        expect(bloggerService.createBloggerFeed(googleService, getBlog(), null)).andReturn(feed);
        expect(bloggerService.dameEntradas(feed)).andReturn(null);
        replay(getMocks());
        bloggerServiceMinimal.dameEntradas();
    }

    @DataProvider(name = "erroresServicio")
    public Object[][] createData1() {
        return new Object[][]{
                {ErrorBlogger.errorAutentificacionGoogle, new GoogleErrorAutentificacion()},
        };
    }

    @DataProvider(name = "erroresFeed")
    public Object[][] createData2() {
        return new Object[][]{
                {ErrorBlogger.errorURLBlog, new GoogleErrorBlogURL()},
                {ErrorBlogger.errorIO, new GoogleErrorIO()},
        };
    }

    @Test(dataProvider = "erroresFeed")
    public void testErroresFeed(ErrorBlogger errorBlogger, Exception aExceptionClass) throws GoogleProblema {
        String gmailCuenta = getUsuarioGmail() + GoogleProperties.gmailExtension;

        expectConsultaProperties(gmailCuenta);
        expect(bloggerService.createBloggerService(gmailCuenta, getClaveGmail())).andReturn(googleService);
        expect(bloggerService.createBloggerFeed(googleService, getBlog(),null)).andThrow(aExceptionClass);
        facesMessages.add(ErrorFaces.dameError(errorBlogger));
        replay(getMocks());
        try {
            bloggerServiceMinimal.dameEntradas();
        } catch (GoogleProblema e) {
            assert (e.getClass().equals(aExceptionClass.getClass()));

        }
    }


    @Test(dataProvider = "erroresServicio")
    public void testErroresServicio(ErrorBlogger errorBlogger, Exception aExceptionClass) throws GoogleProblema {
        String gmailCuenta = getUsuarioGmail() + GoogleProperties.gmailExtension;

        expectConsultaProperties(gmailCuenta);
        expect(bloggerService.createBloggerService(gmailCuenta, getClaveGmail())).andThrow(aExceptionClass);
        facesMessages.add(ErrorFaces.dameError(errorBlogger));
        replay(getMocks());
        try {
            bloggerServiceMinimal.dameEntradas();
        } catch (GoogleErrorAutentificacion e) {
            assert (e.getClass().equals(aExceptionClass.getClass()));

        }
    }

}
