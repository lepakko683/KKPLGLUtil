package okkapel.kkplglutil.rendering;

import java.nio.FloatBuffer;


public class GLRenderObjPointer {
	private int arrIndex = -1;
	private int offset;
	private int texId = -1;
	private boolean temp = false; 
	
	public GLRenderObjPointer(int offset, int texId, boolean temp, int arrIndex) {
		this.offset = offset;
		this.texId = texId;
		this.temp = temp;
		this.arrIndex = arrIndex;
	}
	
	public int getArrIndex() {
		return this.arrIndex;
	}
	
	public int getOffset() {
		return this.offset;
	}

	public int getTexture() {
		return this.texId;
	}
	
	public boolean isTemporary() {
		return this.temp;
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
