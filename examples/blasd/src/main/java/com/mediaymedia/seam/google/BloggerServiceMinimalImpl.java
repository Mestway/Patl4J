package com.mediaymedia.seam.google;

import com.google.gdata.client.GoogleService;
import com.google.gdata.data.Feed;
import com.google.gdata.data.Entry;
import com.google.gdata.util.ServiceException;
import com.mediaymedia.gdata.errors.GoogleErrorAutentificacion;
import com.mediaymedia.gdata.errors.GoogleErrorBlogURL;
import com.mediaymedia.gdata.errors.GoogleErrorIO;
import com.mediaymedia.gdata.errors.GoogleProblema;
import com.mediaymedia.gdata.model.BlogEntry;
import com.mediaymedia.gdata.model.BlogCategoria;
import com.mediaymedia.gdata.service.BloggerService;
import com.mediaymedia.gdata.service.BloggerServiceImpl;
import com.mediaymedia.gdata.service.GoogleProperties;
import com.mediaymedia.faces.ErrorFaces;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;

import javax.ejb.Stateless;
import java.util.List;
import java.net.URL;
import java.io.IOException;

/**
 * User: juan
 * Date: 14-oct-2007
 * Time: 18:57:54
 */
@Name(BloggerServiceMinimal.seamName)
@Stateless
public class BloggerServiceMinimalImpl extends GDataSeam implements BloggerServiceMinimal {
    @In()
    GoogleProperties googleProperties;


    BloggerService bloggerService = new BloggerServiceImpl();

    public List<BlogEntry> dameEntradas() throws GoogleProblema {
        return dameEntradas(null);
    }

    public List<BlogEntry> dameEntradas(List<BlogCategoria> categorias) throws GoogleProblema {
        log.debug("dameEntradas"+googleProperties.logea());
        GoogleService blogService = null;
        try {
            blogService = bloggerService.createBloggerService(googleProperties.getGmailCuenta(), googleProperties.getClave());
        } catch (GoogleErrorAutentificacion googleErrorAutentificacion) {
             facesMessages.add(ErrorFaces.dameError(ErrorBlogger.errorAutentificacionGoogle));
            throw googleErrorAutentificacion;
        }
        Feed feed = null;
        try {
            feed = bloggerService.createBloggerFeed(blogService, googleProperties.getBlog(), categorias);
        } catch (GoogleErrorBlogURL googleErrorBlogURL) {
             facesMessages.add(ErrorFaces.dameError(ErrorBlogger.errorURLBlog));
            throw googleErrorBlogURL;
        } catch (GoogleErrorIO googleErrorIO) {
            facesMessages.add(ErrorFaces.dameError(ErrorBlogger.errorIO));
            throw googleErrorIO;
        }
        return bloggerService.dameEntradas(feed);
    }

    public void updateEntry(Entry entry) throws GoogleProblema {
        try {
            GoogleService service = bloggerService.createBloggerService(googleProperties.getGmailCuenta(), googleProperties.getClave());
            service.update(new URL(entry.getEditLink().getHref()), entry);
        } catch (IOException e) {
            facesMessages.add(ErrorFaces.dameError(ErrorBlogger.errorGoogle));
             throw new GoogleProblema(e.getMessage());
        } catch (ServiceException e) {
            facesMessages.add(ErrorFaces.dameError(ErrorBlogger.errorGoogle));
            throw new GoogleProblema(e.getMessage());
        } catch (GoogleErrorAutentificacion googleErrorAutentificacion) {
            facesMessages.add(ErrorFaces.dameError(ErrorBlogger.errorAutentificacionGoogle));
            throw googleErrorAutentificacion;
        }


    }
}
  enum ErrorBlogger {
         errorAutentificacionGoogle,errorGoogle, errorURLBlog, errorIO
    }
