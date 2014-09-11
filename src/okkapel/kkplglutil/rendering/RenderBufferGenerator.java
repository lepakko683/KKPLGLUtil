package okkapel.kkplglutil.rendering;

import java.nio.FloatBuffer;

import okkapel.kkplglutil.util.Colour;

import org.lwjgl.BufferUtils;

public class RenderBufferGenerator {
	
	public static RenderBufferGenerator INSTANCE = new RenderBufferGenerator();
	
	public static final int DEFAULT_GL_STRIDE = 4*8;
	
	private boolean errored = false;
	private float[] dataArray;
	private int arrayPos = 0;
	private int origin = 0;
//	private int bufferDataTypes = -1;
//	private int subBufferDataTypes = -1;
	
	
	public RenderBufferGenerator() {
		dataArray = new float[8*65536]; // this should be large enough buffer
	}
	
	public boolean isEmpty() {
		return arrayPos == 0;
	}
	
	public void startCreatingBuffer(/*int dataTypes*/) {
//		bufferDataTypes = dataTypes;
	}
	
	public void startCreatingSubBuffer(/*int dataTypes*/) {
		if(origin == 0) {
			origin = arrayPos;
//			subBufferDataTypes = dataTypes;
		} else {
			System.err.println("Already creating sub buffer!");
			errored = true;
		}
	}
	
	private void reset() {
		if(errored) {
			errored = false;
		}
		if(origin != 0) {
			arrayPos = origin;
			origin = 0;
		} else {
			arrayPos = 0;
		}
	}
	
	public void addVertexWColor(float x, float y, float z, float r, float g, float b) {
		addVertexWColorWUV(x, y, z, r, g, b, 0f, 0f);
	}
	
	public void addVertexWColor(float x, float y, float z, Colour c) {
		addVertexWColorWUV(x, y, z, c.getRed(), c.getGreen(), c.getBlue(), 0f, 0f);
	}
	
	public void addVertexWUV(float x, float y, float z, float u, float v) {
		addVertexWColorWUV(x, y, z, 1f, 1f, 1f, u, v);
	}
	
	public void addVertexWColorWUV(float x, float y, float z, float r, float g, float b, float u, float v) {
		if(!canAdd(8)) {
			return;
		}
		
		dataArray[arrayPos++] = u;
		dataArray[arrayPos++] = v;
		dataArray[arrayPos++] = r;
		dataArray[arrayPos++] = g;
		dataArray[arrayPos++] = b;
		dataArray[arrayPos++] = x;
		dataArray[arrayPos++] = y;
		dataArray[arrayPos++] = z;
	}
	
	public void addVertexWColorWUV(float x, float y, float z, Colour c, float u, float v) {
		addVertexWColorWUV(x, y, z, c.getRed(), c.getGreen(), c.getBlue(), u, v);
	}
	
	public FloatBuffer createBuffer() {
		FloatBuffer ret = BufferUtils.createFloatBuffer(arrayPos);
		ret.put(dataArray, origin, arrayPos);
		ret.flip();
		reset();
		return ret;
	}
	
	public boolean canAdd(int floatCount) {
		return !errored && arrayPos+floatCount < dataArray.length;
	}
}
