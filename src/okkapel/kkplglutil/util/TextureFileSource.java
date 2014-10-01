package okkapel.kkplglutil.util;

import java.io.File;

import celestibytes.lib.resources.FileSource;

public class TextureFileSource extends FileSource {
	
	protected Texture tex = null;

	public TextureFileSource(File file) {
		super(file);
		loadTexture();
	}
	
	public Texture loadTexture() {
		tex = TextureLoader.loadTexture(this.getFile(), true);
		return this.tex;
	}
	
	public Texture getTexture() {
		return this.tex;
	}
	
}
