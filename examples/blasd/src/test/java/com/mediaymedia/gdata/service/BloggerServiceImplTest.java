package com.mediaymedia.gdata.service;

import com.google.gdata.client.GoogleService;
import com.google.gdata.data.Feed;
import com.mediaymedia.gdata.GDataBaseTest;
import com.mediaymedia.gdata.errors.GoogleErrorAutentificacion;
import com.mediaymedia.gdata.errors.GoogleErrorBlogURL;
import com.mediaymedia.gdata.errors.GoogleErrorIO;
import com.mediaymedia.gdata.model.BlogCategoria;
import com.mediaymedia.gdata.model.BlogEntry;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 10:34:17
 */
public class BloggerServiceImplTest extends GDataBaseTest {
    BloggerServiceImpl bloggerService = new BloggerServiceImpl();


    @BeforeClass
    public void inicioGDataServiceBeanTest() {
        testLog.debug("inicioGDataServiceBeanTest");


    }


    @Test
    public void testBlogger() throws GoogleErrorAutentificacion, GoogleErrorIO, GoogleErrorBlogURL {
        testLog.debug("\n\n\n\n\ntestBlogger");

        GoogleService blogService = bloggerService.createBloggerService(usuarioGmail + extensionMail, clave);
        Feed feed = bloggerService.createBloggerFeed(blogService, blog);

        for (BlogEntry entry : bloggerService.dameEntradas(feed)) {
            testLog.info(entry.getTitulo());
            feed.getCategories().addAll(entry.getEntry().getCategories());
        }


    }

    @Test
    public void testBloggerQueryLabel() throws GoogleErrorAutentificacion, GoogleErrorIO, GoogleErrorBlogURL {
        testLog.debug("\n\n\n\n\ntestBlogger");

        GoogleService blogService = bloggerService.createBloggerService(usuarioGmail + extensionMail, clave);

        List<BlogCategoria> categorias = new ArrayList<BlogCategoria>();
        categorias.add(new BlogCategoria("categoria%201"));
        Feed feed = bloggerService.createBloggerFeed(blogService, blog, categorias);

        for (BlogEntry entry : bloggerService.dameEntradas(feed)) {
            testLog.info(entry.getTitulo());
        }


    }

    @Test
    public void testBloggerURLBlogError() throws GoogleErrorAutentificacion, GoogleErrorIO, GoogleErrorBlogURL {
        testLog.debug("\n\n\n\n\ntestBloggerURLBlogError");

        GoogleService blogService = bloggerService.createBloggerService(usuarioGmail + extensionMail, clave);
        try {
            Feed feed = bloggerService.createBloggerFeed(blogService, "chorra007xxx0001sss");
            assert (false) : " debe lanzar excepcion GoogleErrorBlogURL!";
        } catch (GoogleErrorBlogURL e) {

        }

        bloggerService.createBloggerFeed(blogService, blog);
    }

    @Test
    public void testGoogleAutentificacionError() throws GoogleErrorAutentificacion {
        testLog.debug("\n\n\n\n\ntestGoogleAutentificacionError");
        try {

            bloggerService.createBloggerService(usuarioGmail + extensionMail, clave + "fallo");
            assert (false) : " debe lanzar excepcion GoogleErrorAutentificacion!";
        } catch (GoogleErrorAutentificacion e) {

        }
        bloggerService.createBloggerService(usuarioGmail + extensionMail, clave);


    }


}
