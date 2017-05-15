package com.xyh.video.download;

import com.videoplayer.bd.R;

import android.text.TextUtils;


public class FileTypeUtils {
	public static String movies = ".avi.rmvb.rm.asf.divx.mpg.mpeg.mpe.wmv.mp4.mkv.vob.flv.webm.3gp";
	public static String musics = ".mp3.wma.ogg.aac.wav.flac.ape";
	public static String images = ".bmp.jpg.jpeg.png.gif";
	public static String docs = ".doc.docx";
	public static String ppts = ".ppt.pptx";
	public static String zips = ".zip.rar.gz.7z.tar.gzip.iso";

	public static int getFileTypeIconByMimeType(String mimeType) {
		return R.drawable.filesystem_icon_default;
	}

	public static int getFileTypeIconByName(String fileName) {
		if (TextUtils.isEmpty(fileName)) {
			return R.drawable.filesystem_icon_default;
		}

		if (fileName.contains(".")) {
			String suffixName = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
			if (suffixName.equals(".apk")) {
				return R.drawable.filesystem_icon_apk;
			}

			if (suffixName.equals(".torrent") || suffixName.equals(".bt")) {
				return R.drawable.filesystem_icon_bt;
			}

			if (suffixName.equals(".chm")) {
				return R.drawable.filesystem_icon_chm;
			}

			if (suffixName.equals(".epub")) {
				return R.drawable.filesystem_icon_epub;
			}

			if (suffixName.equals(".xls") || suffixName.equals(".xlsx")) {
				return R.drawable.filesystem_icon_excel;
			}

			if (suffixName.equals(".link")) {
				return R.drawable.filesystem_icon_link;
			}

			if (movies.contains(suffixName)) {
				return R.drawable.filesystem_icon_movie;
			}

			if (musics.contains(suffixName)) {
				return R.drawable.filesystem_icon_music;
			}
			if (images.contains(suffixName)) {
				return R.drawable.filesystem_icon_photo;
			}

			if (suffixName.equals(".pdf")) {
				return R.drawable.filesystem_icon_pdf;
			}
			if (ppts.contains(suffixName)) {
				return R.drawable.filesystem_icon_ppt;
			}

			if (suffixName.equals(".txt")) {
				return R.drawable.filesystem_icon_txt;
			}
			if (suffixName.equals(".html")) {
				return R.drawable.filesystem_icon_web;
			}
			if (docs.contains(suffixName)) {
				return R.drawable.filesystem_icon_word;
			}
			if (zips.contains(suffixName)) {
				return R.drawable.filesystem_icon_zip;
			}
		}
		return R.drawable.filesystem_icon_default;
	}
}
