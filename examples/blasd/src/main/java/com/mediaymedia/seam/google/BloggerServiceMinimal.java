package com.mediaymedia.seam.google;

import com.mediaymedia.gdata.service.GoogleProperties;
import com.mediaymedia.gdata.model.BlogEntry;
import com.mediaymedia.gdata.model.BlogCategoria;
import com.mediaymedia.gdata.errors.GoogleErrorAutentificacion;
import com.mediaymedia.gdata.errors.GoogleErrorIO;
import com.mediaymedia.gdata.errors.GoogleErrorBlogURL;
import com.mediaymedia.gdata.errors.GoogleProblema;
import com.google.gdata.data.Entry;

import java.util.List;

/**
 * User: juan
 * Date: 14-oct-2007
 * Time: 18:57:49
 */
public interface BloggerServiceMinimal {
    String seamName="bloggerServiceMinimal";

    List<BlogEntry> dameEntradas() throws  GoogleProblema;
    
    List<BlogEntry> dameEntradas(List<BlogCategoria> categorias) throws  GoogleProblema;

    void updateEntry(Entry entry) throws GoogleProblema;
}
