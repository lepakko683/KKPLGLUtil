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
	
	public GLRenderObj getRenderObj() {
		return GLHandler.getROBJForRPTR(this);
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
	
	/** Rectangle to be exact */
	public void setVertPositionsSquare(float x, float y, float width, float height, int offset, int rectCount) {
		GLRenderObj robj = GLHandler.getROBJForRPTR(this);
		robj.startBufferModif();
		for(int i=0;i<rectCount;i++) {
			robj.setVertPos(x, y, offset+i*6, 1);
			robj.setVertPos(x, y+height, offset+i*6+1, 1);
			robj.setVertPos(x+width, y+height, offset+i*6+2, 1);
			
			robj.setVertPos(x+width, y+height, offset+i*6+3, 1);
			robj.setVertPos(x+width, y, offset+i*6+4, 1);
			robj.setVertPos(x, y, offset+i*6+5, 1);
		}
		robj.finishBufferModif();
	}
	
	public void setVertPositionsTriangle(float x, float y, float width, float height, int offset, int triCount) {
		GLRenderObj robj = GLHandler.getROBJForRPTR(this);
		robj.startBufferModif();
		for(int i=0;i<triCount;i++) {
			robj.setVertPos(x, y, offset+i*3, 1);
			robj.setVertPos(x, y+height, offset+i*3, 1);
			robj.setVertPos(x+width, y+height, offset+i*3, 1);
		}
		robj.finishBufferModif();
	}
	
	public void setOffset(int offs) {
		this.offset = offs;
	}
	
	public void setArrIndex(int index) {
		this.arrIndex = index;
	}
	
}
