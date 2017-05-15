/**
 * 
 */
package com.shipin.player.gui;

/**
 * @author wjy
 *
 */
public class FileItem {
	
	private int id;
	private String filepraPath;
	private String filesonPath;
	public boolean isDirectory;
	public int getFolder_count() {
		return folder_count;
	}
	public void setFolder_count(int folder_count) {
		this.folder_count = folder_count;
	}
	public String filePath;
	public String fileName;
	public int folder_count;
	
	//添加属性 check属性 1105
	public boolean  ischeck;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFilepraPath() {
		return filepraPath;
	}
	public void setFilepraPath(String filepraPath) {
		this.filepraPath = filepraPath;
	}
	public String getFilesonPath() {
		return filesonPath;
	}
	public void setFilesonPath(String filesonPath) {
		this.filesonPath = filesonPath;
	}
	public boolean isDirectory() {
		return isDirectory;
	}
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean isIscheck() {
		return ischeck;
	}
	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}
	@Override
	public String toString() {
		return "FileItem [id=" + id + ", filepraPath=" + filepraPath
				+ ", filesonPath=" + filesonPath + ", isDirectory="
				+ isDirectory + ", filePath=" + filePath + ", fileName="
				+ fileName + ", ischeck=" + ischeck + "]";
	}
	
}
