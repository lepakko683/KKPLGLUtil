package okkapel.kkplglutil.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;


public class RenderObjRenderer {
	private GLRenderObj buffer;
	
	public void renderRenderObj(GLRenderObj obj, long offset, long number) {
		
	}
	
	public void renderRenderObjPointer(GLRenderObjPointer pointer) {
		this.buffer = GLRenderBufferHandler.INSTANCE.getRenderObj(pointer.getArrIndex());
		if(this.buffer == null) {
			return;
		}
		
		if(this.buffer.getRenderMethod() == GLRenderMethod.VERTEX_BUFFER_OBJECT) {
			renderAsVBO(this.buffer);
			return;
		}
		if(this.buffer.getRenderMethod() == GLRenderMethod.VERTEX_ARRAY) {
			renderAsVA(this.buffer);
		}
	}
	
	/** Vertex Buffer Object */
	private void renderAsVBO(GLRenderObj o) {
		int rid = o.getVBOId();
		if(rid == -1) {
			rid = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, rid);
			glBufferData(GL_ARRAY_BUFFER, (FloatBuffer)null, GL_DYNAMIC_DRAW); // TODO: data!
		}
		glVertexPointer(3, GL_FLOAT, 32/*8*4*/, 5*4);
		glColorPointer(3, GL_FLOAT, 32/*8*4*/, 2*4);
		glTexCoordPointer(2, GL_FLOAT, 32/*8*4*/, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/** Vertex Array */
	private void renderAsVA(GLRenderObj o) {
		//glInterleavedArrays(GL_T2F_C3F_V3F, 4*8, this.renderData); // GL_C3F_V3F
	}
}
