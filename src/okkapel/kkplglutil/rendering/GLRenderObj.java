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
	
	public GLRenderObj(GLRenderMethod renderMethod, ByteBuffer data, int GL_usage) {
		if(renderMethod == GLRenderMethod.VERTEX_ARRAY || renderMethod == GLRenderMethod.VERTEX_BUFFER_OBJECT) {
			this.renderMethod = renderMethod;
			this.data = data;
		} else {
			System.err.println("[KKPLGLUtil] [ERROR]: Invalid GLRenderMethod \"" + (renderMethod == null ? "null" : renderMethod.name()) + "\", Use either VERTEX_ARRAY or VERTEX_BUFFER_OBJECT");
			return;
		}
		
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
		}
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
