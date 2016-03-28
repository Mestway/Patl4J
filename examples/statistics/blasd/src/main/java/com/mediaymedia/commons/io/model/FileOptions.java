package com.mediaymedia.commons.io.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

/**
 *
 * User: juan
 * Date: 30-may-2007
 * Time: 16:54:53
 * To change this template use File | Settings | File Templates.
 */
public abstract class FileOptions {
    private static Log log = LogFactory.getLog(FileOptions.class);

    public static void mueveArchivo(File inFile, File outFile) throws IOException {

        if (inFile.getCanonicalPath().equals(outFile.getCanonicalPath())) {
//check whether the file in the target directory exists or not
//if exists then don't do anything and simply return back.
            return;
        }

        FileInputStream fin = new FileInputStream(inFile);
        FileOutputStream fout = new FileOutputStream(outFile);
        copy(fin, fout);
        fin.close();
        fout.close();
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {

// do not allow other threads to read from the
// input or write to the output while copying is
// taking place

        synchronized (in) {
            synchronized (out) {
                byte[] buffer = new byte[256];
                while (true) {
                    int bytesRead = in.read(buffer);
                    if (bytesRead == -1) break;
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    public static void borrarArchivo(String archivoEnPath) {
        File imageFile = new File( archivoEnPath);
        imageFile.delete();
    }
    public static void moverArchivo(String archivoEnPAthIni, String archivoEnPAthFin) {

        File fileInit = new File(archivoEnPAthIni);
        File fileFin = new File(archivoEnPAthFin);

        try {
            FileOptions.mueveArchivo(fileInit, fileFin);
            FileOptions.borrarArchivo(archivoEnPAthIni);

        } catch (IOException e) {
            log.info( "error de lectura escritura " + e.toString());
        }
    }

}
