package com.mediaymedia.seam.google;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.Entry;
import com.google.gdata.data.photos.*;
import com.mediaymedia.gdata.errors.*;
import com.mediaymedia.gdata.model.AlbumFotoEntry;
import com.mediaymedia.gdata.model.BlogEntry;
import com.mediaymedia.gdata.model.FotoEntry;
import com.mediaymedia.gdata.service.GoogleProperties;
import com.mediaymedia.gdata.service.PicasaService;
import com.mediaymedia.gdata.service.PicasaServiceImpl;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 10:42:12
 */
@Name(PicasaServiceMinimal.seamName)
@Stateless
public class PicasaServiceMinimalImpl extends GDataSeam implements PicasaServiceMinimal {


    @In
    GoogleProperties googleProperties;

    PicasaService picasaService = new PicasaServiceImpl();

    public AlbumFeed getAlbumGrupo(String id) throws GoogleErrorAutentificacion, GoogleErrorIO, GoogleErrorPicasaURL, GoogleErrorPicasaAlbumNoExiste {
        PicasawebService myService = picasaService.createPicasaService(googleProperties.getGmailCuenta(), googleProperties.getClave());

        UserFeed myUserFeed = picasaService.createPicasaFeedUser(myService, googleProperties.getUsuarioGmail());

        for (AlbumEntry al : myUserFeed.getAlbumEntries()) {
            if (al.getName().equals(id)) {
                log.debug("devolviendo galeria de fotos  de grupo " + id);
                return picasaService.createPicasaFeedAlbum(myService, googleProperties.getUsuarioGmail(), al.getName());
            }
        }
        throw new GoogleErrorPicasaAlbumNoExiste();
    }

    public PhotoFeed getFoto(PhotoEntry id) throws GoogleErrorPicasaFotoNoExiste, GoogleErrorIO, GoogleErrorAutentificacion {
        return picasaService.createPicasaFeedPhoto(picasaService.createPicasaService(googleProperties.getGmailCuenta(), googleProperties.getClave()), googleProperties.getUsuarioGmail(), id);
    }

    public List<FotoEntry> dameListaFotos(String idGaleria) throws GoogleProblema, GoogleErrorIO, GoogleErrorPicasaURL, GoogleErrorAutentificacion, GoogleErrorPicasaFotoNoExiste {
        List<FotoEntry> fotos;
        AlbumFeed feed = getAlbumGrupo(idGaleria);
        List<PhotoEntry> list = feed.getPhotoEntries();
        fotos = new ArrayList<FotoEntry>();
        for (PhotoEntry f : list) {
            PhotoFeed feedFoto = getFoto(f);
            FotoEntry foto = new FotoEntry();
            foto.setUrl(dameMiniatura(f));
//            foto.setTitulo(f.getTitle().getPlainText());
            if (fotos.size() == 0)
                foto.setZoom(dameZoom(feedFoto));
            fotos.add(foto);
        }
        return fotos;
    }

    public String dameZoom(PhotoFeed feedFoto) throws GoogleProblema {
        return picasaService.dameZoom(feedFoto);
    }

    public String dameMiniatura(PhotoEntry f) throws GoogleProblema {
        return picasaService.dameMiniatura(f);
    }



    public List<AlbumFotoEntry> getPortadasDiscos() throws GoogleErrorAutentificacion, GoogleErrorIO, GoogleErrorPicasaURL {
        List<AlbumFotoEntry> portadas = new ArrayList<AlbumFotoEntry>();
        PicasawebService myService = picasaService.createPicasaService(googleProperties.getGmailCuenta(), googleProperties.getClave());

        UserFeed myUserFeed = picasaService.createPicasaFeedUser(myService, googleProperties.getUsuarioGmail());

        for (AlbumEntry al : myUserFeed.getAlbumEntries()) {

            AlbumFotoEntry o = new AlbumFotoEntry();
            o.setName(al.getName());
            portadas.add(o);
        }
        return portadas;
    }

    public FotoEntry getPrimeraFoto(String idAlbum) throws GoogleProblema, GoogleErrorAutentificacion {
        FotoEntry foto = new FotoEntry();
        AlbumFeed feed = getAlbumGrupo(idAlbum);
        if (feed.getPhotoEntries().size() > 0) {
            PhotoEntry photoEntry = feed.getPhotoEntries().get(0);
            String mini = dameMiniatura(photoEntry);
            String zoom = dameZoom(getFoto(photoEntry));
            foto.setUrl(mini);
            foto.setZoom(zoom);
//            foto.setTitulo(photoEntry.getTitle().getPlainText());
//            foto.setId(new Long(photoEntry.getId()));
        }

        return foto;
    }

    public PhotoEntry getPrimeraPhotoEntry(String idAlbum) throws GoogleErrorPicasaAlbumNoExiste, GoogleErrorIO, GoogleErrorPicasaURL, GoogleErrorAutentificacion {
        AlbumFeed feed = getAlbumGrupo(idAlbum);
        if (feed.getPhotoEntries().size() > 0) {
            return feed.getPhotoEntries().get(0);

        }

        return null;
    }


}
