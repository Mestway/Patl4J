package com.mediaymedia.gdata.service;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.*;
import com.mediaymedia.gdata.errors.*;

/**
 * User: juan
 * Date: 13-sep-2007
 * Time: 16:42:47
 * To change this template use File | Settings | File Templates.
 */
public interface PicasaService {

    String dameZoom(PhotoFeed feedFoto) throws GoogleProblema;

    String dameMiniatura(PhotoEntry f) throws GoogleProblema;


    PhotoFeed createPicasaFeedPhoto(PicasawebService myService, String usuarioGmail, PhotoEntry foto) throws GoogleErrorPicasaFotoNoExiste, GoogleErrorIO;

    PicasawebService createPicasaService(String userMail, String pass) throws GoogleErrorAutentificacion;

    UserFeed createPicasaFeedUser(PicasawebService myService, String userGmail) throws GoogleErrorPicasaURL, GoogleErrorIO;

    AlbumFeed createPicasaFeedAlbum(PicasawebService myService, String usuarioGmail, String name) throws GoogleErrorPicasaAlbumNoExiste, GoogleErrorIO;

    AlbumEntry createAlbumEntry(UserFeed feedUser, String albumName) throws GoogleErrorPicasaAlbumNoExiste;

}
