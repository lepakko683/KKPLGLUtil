package okkapel.kkplglutil.util;

public class TexIcon {
	private int texId = -1;
	private float u1, v1, u2, v2;
	
	public TexIcon(int texId, float u1, float v1, float u2, float v2) {
		this.texId = texId; this.u1 = u1; this.v1 = v1; this.u2 = u2; this.v2 = v2;
	}
	
	public int getTexId() {
		return this.texId;
	}
	
	public float getU1() {
		return this.u1;
	}
	
	public float getU2() {
		return this.u2;
	}
	
	public float getV1() {
		return this.v1;
	}
	
	public float getV2() {
		return this.v2;
	}
	
}
