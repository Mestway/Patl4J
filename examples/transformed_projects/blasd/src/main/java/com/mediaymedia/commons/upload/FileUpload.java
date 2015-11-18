package com.mediaymedia.commons.upload;

/**
 * User: juan
 * Date: 11-sep-2007
 * Time: 13:03:54
 * To change this template use File | Settings | File Templates.
 */
public interface FileUpload {


    byte[] getFileData();

    void setFileData(byte[] fileData);

    String getFileContentType();

    void setFileContentType(String contentType);

    /**
     *
     * @return  path + nombre de archivo upload
     */
    String getFileName();

    void setFileName(String fileName);

    public String getFileUploadMsg();


    /**
     *
     * @return  nombre simple sin info de directorio
     */
    String getFileNameSimple();

    /**
     *
     * @param fileNameSimple simple sin info de directorio
     */
    void setFileNameSimple(String fileNameSimple);
}
