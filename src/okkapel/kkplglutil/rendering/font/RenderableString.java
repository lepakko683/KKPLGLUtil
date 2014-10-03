package okkapel.kkplglutil.rendering.font;

import okkapel.kkplglutil.rendering.GLRenderObjPointer;
import celestibytes.lib.almightytext.Advstr;

public class RenderableString extends Advstr {
	
	private GLRenderObjPointer wholeStr;
	private RendTextObject[] rtxtobjs;
	
	private int width, height;

	public RenderableString(String text) {
		super(text);
	}
	
	public RenderableString(String text, int width, int height) {
		this(text);
		this.width = width;
		this.height = height;
	}
	
	public void textAreaWidthChanged() {} // TODO
	
	@Override
	protected void onTranslationChanged() {
		super.onTranslationChanged();
		// recreate vbo
	}
	
	@Override
	protected void preAdvstrDelete() {
		super.preAdvstrDelete();
		// delete vbo
	}
	
}
