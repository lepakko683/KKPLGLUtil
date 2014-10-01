package okkapel.kkplglutil.rendering;

import java.nio.ByteBuffer;
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
	
	public void bufferSubData(ByteBuffer data, long offset) {
		GLHandler.renderPtrSubData(this, data, offset);
	}
	
	public void translateVerts(float dx, float dy, int offset, int vertCount) {
		GLRenderObj robj = GLHandler.getROBJForRPTR(this);
		robj.startBufferModif();
		robj.translateVerticies(dx, dy, offset, vertCount);
		robj.finishBufferModif();
	}
	
	public void setVertPositions(float x, float y, int offset, int vertCount) {
		GLRenderObj robj = GLHandler.getROBJForRPTR(this);
		robj.startBufferModif();
		robj.setVertPos(x, y, offset, vertCount);
		robj.finishBufferModif();
	}
	
	public void setVertPositionsSquare(float x, float y, float width, float height, int offset, int vertCount) {
		GLRenderObj robj = GLHandler.getROBJForRPTR(this);
		robj.startBufferModif();
		robj.setVertPos(x, y, offset, 1);
		robj.setVertPos(x, y+height, offset+1, 1);
		robj.setVertPos(x+width, y+height, offset+2, 1);
		
		robj.setVertPos(x+width, y+height, offset+3, 1);
		robj.setVertPos(x+width, y, offset+4, 1);
		robj.setVertPos(x, y, offset+5, 1);
		robj.finishBufferModif();
	}
	
	public void setOffset(int offs) {
		this.offset = offs;
	}
	
	public void setArrIndex(int index) {
		this.arrIndex = index;
	}
	
}
