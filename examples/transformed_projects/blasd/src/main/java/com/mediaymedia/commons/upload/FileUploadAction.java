package com.mediaymedia.commons.upload;


/**
 * User: juan
 * Date: 11-sep-2007
 * Time: 13:04:08
 * To change this template use File | Settings | File Templates.
 */
public abstract class FileUploadAction implements FileUpload {

    private byte[] fileData;
    private String fileContentType;
    private String fileName;
    private String fileNameSimple;

    private String fileUploadMsg = "";

    public String getFileUploadMsg() {
        return fileUploadMsg;
    }

    public void setFileUploadMsg(String fileUploadMsg) {
        this.fileUploadMsg = fileUploadMsg;
    }


    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getFileNameSimple() {
        return fileNameSimple;
    }

    public void setFileNameSimple(String fileNameSimple) {
        this.fileNameSimple = fileNameSimple;
    }


}
