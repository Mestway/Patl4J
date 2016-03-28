package com.mediaymedia.gdata.service;

import com.google.gdata.client.GoogleService;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.google.gdata.data.Feed;
import com.google.gdata.data.Entry;
import com.mediaymedia.gdata.errors.GoogleErrorAutentificacion;
import com.mediaymedia.gdata.errors.GoogleErrorBlogURL;
import com.mediaymedia.gdata.errors.GoogleErrorIO;
import com.mediaymedia.gdata.model.BlogEntry;
import com.mediaymedia.gdata.model.BlogCategoria;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * User: juan
 * Date: 13-sep-2007
 * Time: 16:50:40
 * To change this template use File | Settings | File Templates.
 */
public class BloggerServiceImpl extends GData implements BloggerService {

    public GoogleService createBloggerService(String userMail, String pass) throws GoogleErrorAutentificacion {
        GoogleService gservice = new GoogleService("blogger", "exampleCo-exampleApp-1");
        try {
            gservice.setUserCredentials(userMail, pass);
        } catch (AuthenticationException e) {
            throw new GoogleErrorAutentificacion(e.getMessage(), e);
        }
        log.debug("creando blogger service: " + userMail);
        return gservice;

    }


    public Feed createBloggerFeed(GoogleService myService, String nombreBlog, List<BlogCategoria> categorias) throws GoogleErrorBlogURL, GoogleErrorIO {
        Feed feed;
        try {
            URL url ;
            if(categorias==null)
                 url = dameFeedURLBlog(myService, nombreBlog);
            else
                url = dameFeedURLBlog(myService, nombreBlog, categorias);

            feed = myService.getFeed(url, Feed.class);

        } catch (ServiceException e) {
            throw new  GoogleErrorBlogURL(e.getMessage());
        } catch (IOException e) {
            throw new GoogleErrorIO(e.getMessage());
        }
        log.debug("devolviendo feed blog: " + nombreBlog);
        return feed;
    }
    public Feed createBloggerFeed(GoogleService myService, String nombreBlog) throws GoogleErrorBlogURL, GoogleErrorIO {
          return createBloggerFeed(myService, nombreBlog, null);
    }


    public List<BlogEntry> dameEntradas(Feed feed) {
        List<BlogEntry> entradas = new ArrayList<BlogEntry>();
        for (Entry entry : feed.getEntries()) {
            BlogEntry o = new BlogEntry(entry);
            entradas.add(o);
        }
        return entradas;
    }


    /**
     * esta funcion realiza un query al feed del blog de la siguiente manera:
     * introduce entre el url normal y la query "/-/"
     * y después continua anyadiendo etiquetas entre barras
     * ejemplo para buscar en etiqueta linux: /-/linux/
     * ejemplo para buscar en etiqueta linux y mac: /-/linux/mac/
     * @param myService
     * @param blogId
     * @param categorias
     * @return
     * @throws GoogleErrorIO
     */
    protected URL dameFeedURLBlog(GoogleService myService, String blogId, List<BlogCategoria> categorias) throws GoogleErrorIO {
        String urlblog = dameBaseURL(blogId);
        if(categorias.size()>0)urlblog+="/-";
        for(BlogCategoria blogCategoria:categorias)
            urlblog+="/"+blogCategoria.getTerm()+"/";
        return dameFeedURL( urlblog);

    }

    private String dameBaseURL(String blogId) {
        return "http://" + blogId + ".blogspot.com/feeds/posts/default";
    }

    protected URL dameFeedURLBlog(GoogleService myService, String blogId) throws GoogleErrorIO {
        String urlblog = dameBaseURL(blogId);
            return dameFeedURL( urlblog);
    }

}
