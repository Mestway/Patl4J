package com.mediaymedia.gdata.service;

import com.google.gdata.client.GoogleService;
import com.google.gdata.data.Feed;
import com.mediaymedia.gdata.errors.GoogleErrorAutentificacion;
import com.mediaymedia.gdata.errors.GoogleErrorBlogURL;
import com.mediaymedia.gdata.errors.GoogleErrorIO;
import com.mediaymedia.gdata.model.BlogCategoria;
import com.mediaymedia.gdata.model.BlogEntry;

import java.util.List;

/**
 * User: juan
 * Date: 13-sep-2007
 * Time: 16:42:47
 * To change this template use File | Settings | File Templates.
 */
public interface BloggerService {

    GoogleService createBloggerService(String userMail, String pass) throws GoogleErrorAutentificacion;

    /**
     *
     * @param myService
     * @param nombreBlog
     * @param categorias en formato URL es decir con sustitucion de caracteres URI para el XML
     * @return
     * @throws GoogleErrorBlogURL
     * @throws GoogleErrorIO
     */
    Feed createBloggerFeed(GoogleService myService, String nombreBlog, List<BlogCategoria> categorias) throws GoogleErrorBlogURL, GoogleErrorIO;

    Feed createBloggerFeed(GoogleService myService, String nombreBlog) throws GoogleErrorBlogURL, GoogleErrorIO;

    /**
     *
     * @param feed esto es solo conversion desde Entry a BlogEntry 
     * @return
     */
    List<BlogEntry> dameEntradas(Feed feed);
}
