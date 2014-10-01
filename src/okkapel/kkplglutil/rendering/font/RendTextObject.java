package okkapel.kkplglutil.rendering.font;

public class RendTextObject {
	// The base part of rendered text, usually a single word, TODO: (sometimes a colored part of a word)
	
	private int[] arrInds = null;
	
	public RendTextObject(int[] arrInds) {
		this.arrInds = arrInds;
	}
	
}
