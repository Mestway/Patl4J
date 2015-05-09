package com.mediaymedia.gdata.service;

import com.google.gdata.client.GoogleService;
import com.mediaymedia.gdata.errors.GoogleProblema;
import com.mediaymedia.gdata.errors.GoogleErrorIO;

import java.net.URL;
import java.net.MalformedURLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * User: juan
 * Date: 13-sep-2007
 * Time: 17:35:04
 * To change this template use File | Settings | File Templates.
 */
public class GData {
    protected Log log = LogFactory.getLog(GData.class);


    protected URL dameFeedURL( String url) throws GoogleErrorIO {
        URL  feedUrl = null;
        try {
            feedUrl = creaURL(url);
        } catch (MalformedURLException e) {
            throw new GoogleErrorIO(e.getMessage(),e);
        }
        log.debug("return feedurl:" + url);
        return feedUrl;
    }

    protected URL creaURL(String url) throws MalformedURLException {
        return new URL(url);
    }

}
