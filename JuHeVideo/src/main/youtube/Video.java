package main.youtube;

public class Video {
	public String extension = "";
	public String type = "";
	public String url = "";
	public String name = "";

	public Video() {
	}

	public Video(String extension, String type, String url) {
		super();
		this.extension = extension;
		this.type = type;
		this.url = url;
	}

	@Override
	public String toString() {
		return "type: " + type + "___url: " + url;
	}
}
