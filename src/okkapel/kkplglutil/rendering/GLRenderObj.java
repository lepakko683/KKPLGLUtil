package okkapel.kkplglutil.rendering;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL15;

public class GLRenderObj {
	private int vboid = -1;
	private int dataSize = 0;
	private ByteBuffer data = null;
	private GLRenderMethod renderMethod;
	private boolean mergeable = true;
	private boolean isVBO = false;
	private int vertexCount;
	
	private int minModifVert = 0;
	private int maxModifVert = 0;
	
	/* KKPL - Notes to self: 
	 * Use buffer position and limit to limit the size of the buffer that will be copied to the VBO.
	 * Keep track of minimum and maximum vertex of the ROBJ modifications and set the limit and position to those with finishBufferModif()
	 */
	
	public GLRenderObj(GLRenderMethod renderMethod, ByteBuffer data, int GL_usage, int vertexCount) {
		if(renderMethod == GLRenderMethod.VERTEX_ARRAY || renderMethod == GLRenderMethod.VERTEX_BUFFER_OBJECT) {
			this.renderMethod = renderMethod;
			this.data = data;
		} else {
			System.err.println("[KKPLGLUtil] [ERROR]: Invalid GLRenderMethod \"" + (renderMethod == null ? "null" : renderMethod.name()) + "\", Use either VERTEX_ARRAY or VERTEX_BUFFER_OBJECT");
			return;
		}
		
		this.vertexCount = vertexCount;
		
		if(renderMethod == GLRenderMethod.VERTEX_BUFFER_OBJECT) {
			this.vboid = GL15.glGenBuffers();
			if(this.vboid == -1) {
				System.err.println("[KKPLGLUtil] [WARNING]: Couldn't create vbo, using as vertex array instead!");
				this.renderMethod = GLRenderMethod.VERTEX_ARRAY;
				return;
			}
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vboid);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, this.data, GL15.GL_DYNAMIC_DRAW/*GL_usage*/);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			this.isVBO = true;
		}
	}
	
	public void modifBufferData(int offset, ByteBuffer data) {
		this.data.position(offset);
		this.data.put(data);
		this.data.position(0);
	}
	
	private void updateBufferBounds(int startVert, int vertCount) {
		if(this.minModifVert > startVert) {
			this.minModifVert = startVert;
		}
		
		if(this.maxModifVert < startVert+vertCount) {
			this.maxModifVert = startVert+vertCount;
		}
	}
	
	/**if vertCount is set to -1, all vertecies will be translated */
	public void translateVerticies(float x, float y, int startVert, int vertCount) {
		updateBufferBounds(startVert, vertCount);
		for(int i=startVert;i<(vertCount == -1 ? this.vertexCount : vertCount);i++) {
			data.putFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 12, data.getFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 12)+x);
			data.putFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 16, data.getFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 16)+y);
		}
	}
	
	/**if vertCount is set to -1, all vertecies will be translated */
	public void translateVerticies(float x, float y, float z, int startVert, int vertCount) {
		updateBufferBounds(startVert, vertCount);
		for(int i=startVert;i<vertexCount;i++) {
			data.putFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 12, data.getFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 12)+x);
			data.putFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 16, data.getFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 16)+y);
			data.putFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 20, data.getFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 20)+z); 
		}
	}
	
	public void setVertPos(float x, float y, int startVert, int vertCount) {
		updateBufferBounds(startVert, vertCount);
		for(int i=startVert;i<vertexCount;i++) {
			data.putFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 12, x);
			data.putFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 16, y);
		}
	}
	
	public void setVertPos(float x, float y, float z, int startVert, int vertCount) {
		updateBufferBounds(startVert, vertCount);
		for(int i=startVert;i<vertexCount;i++) {
			data.putFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 12, x);
			data.putFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 16, y);
			data.putFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 20, z); 
		}
	}
	
	public void setVertColors(byte r, byte g, byte b, byte a, int startVert, int vertCount) {
		updateBufferBounds(startVert, vertCount);
		for(int i=startVert;i<vertexCount;i++) {
			data.put(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 8, r);
			data.put(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 9, g);
			data.put(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 10, b);
			data.put(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 11, a);
		}
	}
	
	public void setVertUVs(float u, float v, int startVert, int vertCount) {
		updateBufferBounds(startVert, vertCount);
		for(int i=startVert;i<vertexCount;i++) {
			data.putFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 0, u);
			data.putFloat(i*RenderBufferGenerator.DEFAULT_GL_STRIDE + 4, v);
		}
	}
	
	public void startBufferModif() {
		this.data.limit(this.data.capacity());
		this.data.position(0);
		this.maxModifVert=0;
		this.minModifVert=0;
	}
	
	public void finishBufferModif() {
		if(this.isVBO) {
			this.data.position(minModifVert*RenderBufferGenerator.DEFAULT_GL_STRIDE);
			this.data.limit(maxModifVert*RenderBufferGenerator.DEFAULT_GL_STRIDE);
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vboid);
			GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, (long)(minModifVert*RenderBufferGenerator.DEFAULT_GL_STRIDE), data);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			return;
		}
		this.data.position(0);
		this.data.limit(data.capacity());
	}
	
	public GLRenderMethod getRenderMethod() {
		return this.renderMethod;
	}
	
	public int getVBOId() {
		return this.vboid;
	}
	
	public ByteBuffer getData() {
		return this.data;
	}
	
	public boolean isMergeable() {
		return this.mergeable;
	}
	
	public int getDataSize() {
		return this.dataSize;
	}
	
	public void deleteVbo() {
		if(this.vboid != -1) {
			GL15.glDeleteBuffers(this.vboid);
		}
	}
}
