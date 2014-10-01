package okkapel.kkplglutil.rendering.font;

import celestibytes.lib.almightytext.Advstr;

public class RenderableString extends Advstr {
	
	// GLRenderGroup for textparts
	// private GLRenderGroup textparts;
	
	private RendTextObject[] rtxtobjs;
	
	private int width, height;

	public RenderableString(String text) {
		super(text);
	}
	
	public void textAreaWidthChanged() {
		
	}
	
	@Override
	protected void onTranslationChanged() {
		super.onTranslationChanged();
		
	}
	
	@Override
	protected void preAdvstrDelete() {
		super.preAdvstrDelete();
		// delete vbo
	}
}
