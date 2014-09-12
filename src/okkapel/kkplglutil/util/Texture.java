package okkapel.kkplglutil.util;

import org.lwjgl.opengl.GL11;

public class Texture {
	private int glTexId = -1;
	
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
	
}
