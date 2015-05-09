package com.mediaymedia.gdata.service;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.media.mediarss.MediaGroup;
import com.google.gdata.data.media.mediarss.MediaThumbnail;
import com.google.gdata.data.photos.*;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.mediaymedia.gdata.errors.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * User: juan
 * Date: 13-sep-2007
 * Time: 18:22:32
 * To change this template use File | Settings | File Templates.
 */
public class PicasaServiceImpl extends GData implements PicasaService {


    public String dameZoom(PhotoFeed feedFoto) throws GoogleProblema {
        try {
            return feedFoto.getMediaGroup().getContents().get(0).getUrl();
        } catch (Exception e) {
            throw new GoogleProblema(e.getMessage(), e);
        }

    }

    public String dameMiniatura(PhotoEntry f) throws GoogleProblema {
        try {
            MediaGroup group = f.getExtension(MediaGroup.class);
            List<MediaThumbnail> thumbnails = group.getThumbnails();
            MediaThumbnail thumbnail = thumbnails.get(0);
            return thumbnail.getUrl();
        } catch (Exception e) {
            throw new GoogleProblema(e.getMessage(),e);

        }
    }


    public PhotoFeed createPicasaFeedPhoto(PicasawebService myService, String usuarioGmail, PhotoEntry foto) throws GoogleErrorPicasaFotoNoExiste, GoogleErrorIO {
        try {
            URL url = dameFeedURLFotoBisPicasa(myService, usuarioGmail, foto);
            return myService.getFeed(url, PhotoFeed.class);
        } catch (IOException e) {
            throw new GoogleErrorIO(e.getMessage(), e);
        } catch (ServiceException e) {
            throw new GoogleErrorPicasaFotoNoExiste(e.getMessage(), e);
        }
    }


    public PicasawebService createPicasaService(String userMail, String pass) throws GoogleErrorAutentificacion {
        PicasawebService myService = new PicasawebService("picasa-exampleApp-1");
        try {
            myService.setUserCredentials(userMail, pass);
        } catch (AuthenticationException e) {
            throw new GoogleErrorAutentificacion(e.getMessage(), e);
        }
        log.debug("creando picasa service con cuenta de correo:" + userMail);
        return myService;
    }

    public UserFeed createPicasaFeedUser(PicasawebService myService, String userGmail) throws GoogleErrorPicasaURL, GoogleErrorIO {
        try {
            UserFeed feed = myService.getFeed(dameFeedURLAlbumPicasa(myService, userGmail), UserFeed.class);
            log.debug("devolviendo feed album picasa para usuario: " + userGmail);
            return feed;
        } catch (IOException e) {
            throw new GoogleErrorIO(e.getMessage(), e);
        } catch (ServiceException e) {
            throw new GoogleErrorPicasaURL(e.getMessage(), e);
        }
    }

    public AlbumFeed createPicasaFeedAlbum(PicasawebService myService, String usuarioGmail, String al) throws GoogleErrorPicasaAlbumNoExiste, GoogleErrorIO {
        try {
            AlbumFeed albumFeed = myService.getFeed(dameFeedURLFotoPicasa(myService, usuarioGmail, al), AlbumFeed.class);
            log.debug("devolviendo album feed: " + al);
            return albumFeed;
        } catch (IOException e) {
            throw new GoogleErrorIO(e.getMessage(), e);
        } catch (ServiceException e) {
            throw new GoogleErrorPicasaAlbumNoExiste(e.getMessage(), e);
        }
    }

    public AlbumEntry createAlbumEntry(UserFeed myUserFeed, String s) throws GoogleErrorPicasaAlbumNoExiste {
        for (AlbumEntry al : myUserFeed.getAlbumEntries()) {
            log.info(al.getId() + "--" + al.getName());
            if (al.getName().equals(s)) return al;
        }
        throw new GoogleErrorPicasaAlbumNoExiste("no existe el album: " + s);
    }


    protected URL dameFeedURLFotoPicasa(PicasawebService myService, String usuarioGmail, String name) throws GoogleErrorIO {
        String urlPicasa = "http://picasaweb.google.com/data/feed/api/user/" + usuarioGmail + "/album/" + name + "?kind=photo";
        return dameFeedURL( urlPicasa);
    }

    protected URL dameFeedURLAlbumPicasa(PicasawebService myService, String usuarioGmail) throws GoogleErrorIO {
        String urlPicasa = "http://picasaweb.google.com/data/feed/api/user/" + usuarioGmail + "?kind=album";
        return dameFeedURL( urlPicasa);
    }

    protected URL dameFeedURLFotoBisPicasa(PicasawebService myService, String usuarioGmail, PhotoEntry photoEntry) throws GoogleErrorIO {
        String url = "http://picasaweb.google.com/data/feed/api/user/" + usuarioGmail + "/albumid/" + photoEntry.getAlbumId() + "/photoid/" + photoEntry.getGphotoId();
        return dameFeedURL( url);
    }


}
