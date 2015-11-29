package com.fray.evo.util;
import java.io.File;
import java.io.IOException;
public final class EcFileSystem {
  public static String getTempPath() throws IOException {
    String rootPath;
    java.io.File genVar4473;
    genVar4473=createTempDirectory();
    rootPath=genVar4473.getName();
    return rootPath;
  }
  private static File createTempDirectory() throws IOException {
    java.lang.String genVar4474;
    genVar4474="amg";
    java.lang.String genVar4475;
    genVar4475="tmp";
    File createTempFile;
    createTempFile=File.createTempFile(genVar4474,genVar4475);
    createTempFile.delete();
    java.io.File genVar4476;
    genVar4476=createTempFile.getParentFile();
    java.lang.String genVar4477;
    genVar4477="etc";
    createTempFile=new File(genVar4476,genVar4477);
    createTempFile.mkdirs();
    createTempFile.mkdir();
    createTempFile.deleteOnExit();
    return createTempFile;
  }
}
