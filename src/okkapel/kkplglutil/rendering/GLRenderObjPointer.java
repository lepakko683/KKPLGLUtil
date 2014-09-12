package okkapel.kkplglutil.rendering;

import java.nio.FloatBuffer;

import okkapel.kkplglutil.util.Texture;


public class GLRenderObjPointer {
	private int arrIndex = -1;
	private int vertCount = 0;
	private int offset;
	private Texture tex;
	
	public GLRenderObjPointer(int offset, Texture tex, int arrIndex, int vertCount) {
		this.offset = offset;
		this.tex = tex;
		this.arrIndex = arrIndex;
		this.vertCount = vertCount;
	}
	
	public int getArrIndex() {
		return this.arrIndex;
	}
	
	public int getVertCount() {
		return this.vertCount;
	}
	
	public int getOffset() {
		return this.offset;
	}

	public Texture getTexture() {
		return this.tex;
	}
	
	public void bufferSubData(int glTarget, FloatBuffer data, long offset) {
		// TODO: add method body!!!
	}
	
	public void setOffset(int offs) {
		this.offset = offs;
	}
	
	public void setArrIndex(int index) {
		this.arrIndex = index;
	}
	
}
