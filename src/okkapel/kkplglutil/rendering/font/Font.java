package okkapel.kkplglutil.rendering.font;

public class Font {
	protected int singleTexWidth = 8;
	protected int cwHalfPoint = -1;
	protected byte[] charWidths;
	protected int chrCountHoriz = 32;
	protected int chrCountVert = 32;
	
	protected int imgWidth = 256;
	protected int imgHeight = 256;
	
	/** for UV */
	protected float singleChrW = 8f/256f;
	
	/** for UV */
	protected float singleChrH = 8f/256f;
	
	public void reload() {
		FontRenderer.getCurrentFontFileSource().loadCharWidths(this);
	}
	
	public byte[] getCharWidthArray() {
		return this.charWidths;
	}
	
	public int getChrWidth(int indx) {
		if(indx == 0x20) {
			return 4;
		}
		return charWidths[cwHalfPoint+indx];
	}
}
