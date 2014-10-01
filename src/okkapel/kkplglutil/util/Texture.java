package okkapel.kkplglutil.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class Texture {
	
	private static Texture missingTextureTex = null;
	private static boolean failedToCreateMissingTex = false;
	
	private int glTexId = -1, imgWidth = 0, imgHeight = 0;
	
	public Texture(int texId) {
		this.glTexId = texId;
	}
	
	public Texture(int texId, int imgWidth, int imgHeight) {
		this.glTexId = texId;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
	}
	
	public int getImgWidth() {
		return this.imgWidth;
	}
	
	public int getImgHeight() {
		return this.imgHeight;
	}
	
	public void bind() {
		if(this.glTexId == -1) {
			return;
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.glTexId);
	}
	
	public void bind(int gl_target) {
		if(this.glTexId == -1) {
			return;
		}
		GL11.glBindTexture(gl_target, this.glTexId);
	}
	
	public int getTextureId() {
		return this.glTexId;
	}
	
	public static Texture getMissingTexture() {
		if(missingTextureTex == null && failedToCreateMissingTex) {
			int tid = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tid);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, 1, 1, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, BufferUtils.createByteBuffer(4).put((byte)0xFF).put((byte)0x00).put((byte)0xFF).put((byte)0xFF));
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			
			if(GL11.glGetError() == GL11.GL_NO_ERROR) {
				missingTextureTex = new Texture(tid);
			} else {
				failedToCreateMissingTex = true;
			}
		}
		return missingTextureTex;
	}
	
}
