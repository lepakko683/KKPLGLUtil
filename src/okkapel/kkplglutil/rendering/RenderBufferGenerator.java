package okkapel.kkplglutil.rendering;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import okkapel.kkplglutil.util.Colour;

import org.lwjgl.BufferUtils;

public class RenderBufferGenerator {
	
	public static RenderBufferGenerator INSTANCE = new RenderBufferGenerator();
	
	public static final int DEFAULT_GL_STRIDE = 4*5+4;
	
	private boolean errored = false;
	private byte[] dataArray;
	private int arrayPos = 0;
	private int origin = 0;
//	private int bufferDataTypes = -1;
//	private int subBufferDataTypes = -1;
	
	
	public RenderBufferGenerator() {
		dataArray = new byte[1<<22]; // this should be large enough buffer
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
	
	public void addVertexWColor(float x, float y, float z, float r, float g, float a, float b) {
		addVertexWColorWUV(x, y, z, r, g, b, a, 0f, 0f);
	}
	
	public void addVertexWColor(float x, float y, float z, Colour c) {
		addVertexWColorWUV(x, y, z, c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha(), 0f, 0f);
	}
	
	public void addVertexWUV(float x, float y, float z, float u, float v) {
		addVertexWColorWUV(x, y, z, 1f, 1f, 1f, 1f, u, v);
	}
	
	private byte[] buffer = new byte[4];
	public void addVertexWColorWUV(float x, float y, float z, float r, float g, float b, float a, float u, float v) {
		if(!canAdd(5*4+4)) {
			return;
		}
		floatToBytes(u, buffer); System.arraycopy(buffer, 0, dataArray, arrayPos, 4); arrayPos+=4;
		floatToBytes(v, buffer); System.arraycopy(buffer, 0, dataArray, arrayPos, 4); arrayPos+=4;
		dataArray[arrayPos++] = (byte)(r*255);
		dataArray[arrayPos++] = (byte)(g*255);
		dataArray[arrayPos++] = (byte)(b*255);
		dataArray[arrayPos++] = (byte)(a*255);
		floatToBytes(x, buffer); System.arraycopy(buffer, 0, dataArray, arrayPos, 4); arrayPos+=4;
		floatToBytes(y, buffer); System.arraycopy(buffer, 0, dataArray, arrayPos, 4); arrayPos+=4;
		floatToBytes(z, buffer); System.arraycopy(buffer, 0, dataArray, arrayPos, 4); arrayPos+=4;
	}
	
	public void addVertexWColorWUV(float x, float y, float z, Colour c, float u, float v) {
		addVertexWColorWUV(x, y, z, c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha(), u, v);
	}
	
	public ByteBuffer createBuffer() {
		ByteBuffer ret = BufferUtils.createByteBuffer(arrayPos-origin);
		ret.put(dataArray, origin, arrayPos);
		ret.flip();
		reset();
		return ret;
	}
	
	public boolean canAdd(int floatCount) {
		return !errored && arrayPos+floatCount < dataArray.length;
	}
	
	private void floatToBytes(float f, byte[] arr) {
		if(arr == null || arr.length != 4) {
			return;
		}
		int flt = Float.floatToIntBits(f); // Not sure if needed
		
		if(ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
			arr[0] = (byte) (flt & 255);
			arr[1] = (byte) ((flt >> 8) & 255);
			arr[2] = (byte) ((flt >> 16) & 255);
			arr[3] = (byte) ((flt >> 24) & 255);
		} else {
			arr[3] = (byte) (flt & 255);
			arr[2] = (byte) ((flt >> 8) & 255);
			arr[1] = (byte) ((flt >> 16) & 255);
			arr[0] = (byte) ((flt >> 24) & 255);
		}
	}
}
