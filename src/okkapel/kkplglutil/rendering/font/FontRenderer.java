package okkapel.kkplglutil.rendering.font;

import java.io.File;
import java.io.FileOutputStream;

import org.lwjgl.opengl.GL11;

public class FontRenderer {
	
	private static Font font;
	private static FontFileSource fontFSource;
	
	public static void init(File fontTexture, File fontData) {
		fontFSource = new FontFileSource(fontTexture);
		font = fontFSource.loadCharWidths(null);
		try {
			FileOutputStream fos = new FileOutputStream(fontData, true);
//			byte[] out = new byte[] {(byte)0x41,(byte)0x42,(byte)0x42,(byte)0x41,(byte)0xd};
			fos.write(font.getCharWidthArray());
			fos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static float getChrU(int indx) {
		return (indx % font.chrCountHoriz)*font.singleChrW;
	}
	
	private static float getChrV(int indx) {
		return ((int) (indx/font.chrCountHoriz))*font.singleChrH;
	}
	
	public static void renderStr(char[] chrs, float x, float y) {
		
		float scale = 32f;
		float xoffs = x;
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		fontFSource.getTexture().bind();
		GL11.glBegin(GL11.GL_QUADS);
		
		for(int i=0;i<chrs.length;i++) {
			renderChr((int)chrs[i], xoffs, y, scale);
			xoffs += (font.getChrWidth(chrs[i])/(float)font.singleTexWidth)*scale + 4f; // 4f will most like need to change!
		}
		
		GL11.glEnd();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
	}
	
	private static void renderChr(int indx, float x, float y, float scale) {
		
		GL11.glTexCoord2f(getChrU(indx), getChrV(indx));
		GL11.glVertex2f(x, y);
//		GL11.glTexCoord2f(0f, 1f);
		
		
		GL11.glTexCoord2f(getChrU(indx), getChrV(indx)+font.singleChrH);
		GL11.glVertex2f(x, y+scale);
//		GL11.glTexCoord2f(1f, 1f);
		
		
		GL11.glTexCoord2f(getChrU(indx)+font.singleChrW, getChrV(indx)+font.singleChrH);
		GL11.glVertex2f(x+scale, y+scale);
//		GL11.glTexCoord2f(1f, 0f);
		
		
		GL11.glTexCoord2f(getChrU(indx)+font.singleChrW, getChrV(indx));
		GL11.glVertex2f(x+scale, y);
//		GL11.glTexCoord2f(0f, 0f);
	}
	
	
	public static int getStringWidthInPixels() {
		return 0;
	}
	
	protected static void setFontRendererFont(Font nfont) {
		font = nfont;
	}
	
	public static FontFileSource getCurrentFontFileSource() {
		return fontFSource;
	}
	
	public static Font getFontRendererFont() {
		return font;
	}

	public static void deleteTextures() {
		GL11.glDeleteTextures(fontFSource.getTexture().getTextureId());
	}
}
