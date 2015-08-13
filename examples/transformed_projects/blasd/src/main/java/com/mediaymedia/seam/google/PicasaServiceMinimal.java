package com.mediaymedia.seam.google;

import com.mediaymedia.gdata.model.BlogEntry;
import com.mediaymedia.gdata.model.FotoEntry;
import com.mediaymedia.gdata.model.AlbumFotoEntry;
import com.mediaymedia.gdata.service.GoogleProperties;
import com.mediaymedia.gdata.errors.*;
import com.google.gdata.data.Entry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoFeed;
import com.google.gdata.data.photos.PhotoEntry;

import java.util.List;

/**
 * User: juan
 * Date: 14-oct-2007
 * Time: 18:57:49
 */
public interface PicasaServiceMinimal {
    String seamName="picasaServiceMinimal";


    AlbumFeed getAlbumGrupo(String id) throws GoogleErrorAutentificacion, GoogleErrorIO, GoogleErrorPicasaURL, GoogleErrorPicasaAlbumNoExiste;

    PhotoFeed getFoto(PhotoEntry id) throws GoogleErrorPicasaFotoNoExiste, GoogleErrorIO, GoogleErrorAutentificacion;

    List<FotoEntry> dameListaFotos(String idGaleria) throws GoogleProblema, GoogleErrorIO, GoogleErrorPicasaURL, GoogleErrorAutentificacion, GoogleErrorPicasaFotoNoExiste;

    String dameZoom(PhotoFeed feedFoto) throws GoogleProblema;

    String dameMiniatura(PhotoEntry f) throws GoogleProblema;







    List<AlbumFotoEntry> getPortadasDiscos() throws GoogleErrorAutentificacion, GoogleErrorIO, GoogleErrorPicasaURL;

    FotoEntry getPrimeraFoto(String idAlbum) throws GoogleProblema,  GoogleErrorAutentificacion;
    PhotoEntry getPrimeraPhotoEntry(String idAlbum) throws GoogleErrorPicasaAlbumNoExiste, GoogleErrorIO, GoogleErrorPicasaURL, GoogleErrorAutentificacion;


}
