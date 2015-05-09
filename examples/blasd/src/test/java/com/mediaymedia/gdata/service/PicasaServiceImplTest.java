package com.mediaymedia.gdata.service;

import com.mediaymedia.gdata.GDataBaseTest;
import com.mediaymedia.gdata.errors.*;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 12:30:30
 */
public class PicasaServiceImplTest  extends GDataBaseTest {
       PicasaServiceImpl picasaService = new PicasaServiceImpl();


    @BeforeClass
        public void inicioGDataServiceBeanTest() {
          testLog.debug("inicio picasaServiceTest");



      }



    @Test
        public void testPicasaAutentificacionError() throws GoogleErrorAutentificacion {
           testLog.debug("\n\n\n\n\ntestPicasaAutentificacionError");
           try {

               picasaService.createPicasaService(usuarioGmail + extensionMail, clave + "peo");
               assert (false) : " debe lanzar excepcion GoogleErrorAutentificacion!";
           } catch (GoogleErrorAutentificacion e) {

           }
           picasaService.createPicasaService(usuarioGmail + extensionMail, clave);

       }

       @Test
       public void testPicasaURLError() throws GoogleErrorAutentificacion, GoogleErrorIO, GoogleErrorPicasaURL {
           testLog.debug("\n\n\n\n\ntestPicasaURLError");
           PicasawebService service = picasaService.createPicasaService(usuarioGmail + extensionMail, clave);
           try {
               picasaService.createPicasaFeedUser(service, usuarioGmail + "xxzzzyy");
               assert (false) : " debe lanzar excepcion GoogleErrorPicasaURL!";
           } catch (GoogleErrorPicasaURL e) {

           }
           picasaService.createPicasaFeedUser(service, usuarioGmail);

       }

       @Test
       public void testPicasaAlbumError() throws GoogleErrorAutentificacion, GoogleErrorIO, GoogleErrorPicasaURL, GoogleErrorPicasaAlbumNoExiste {
           testLog.debug("\n\n\n\n\ntestPicasaAlbumError");
           PicasawebService service = picasaService.createPicasaService(usuarioGmail + extensionMail, clave);
           UserFeed feedUser = picasaService.createPicasaFeedUser(service, usuarioGmail);
           try {

               picasaService.createAlbumEntry(feedUser, "noExsite");
               assert (false) : " debe lanzar excepcion GoogleErrorPicasaAlbumNoExiste!";
           } catch (GoogleErrorPicasaAlbumNoExiste e) {

           }
           AlbumEntry albumEntry = picasaService.createAlbumEntry(feedUser, "Primero");

       }

       @Test
       public void testPicasaFotoError() throws GoogleProblema {
           testLog.debug("\n\n\n\n\ntestPicasaFotoError");
           PicasawebService service = picasaService.createPicasaService(usuarioGmail + extensionMail, clave);
           UserFeed feedUser = picasaService.createPicasaFeedUser(service, usuarioGmail);
           AlbumFeed albumFeed = picasaService.createPicasaFeedAlbum(service, usuarioGmail, "Primero");
           for (PhotoEntry foto : albumFeed.getPhotoEntries()) {
               testLog.info(foto.getTitle());
               testLog.info(picasaService.dameMiniatura(foto));
               PhotoFeed feedPhoto = picasaService.createPicasaFeedPhoto(service, usuarioGmail, foto);
               testLog.info((picasaService.dameZoom(feedPhoto)));
           }
       }


}
