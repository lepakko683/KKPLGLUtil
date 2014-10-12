package okkapel.kkplglutil.rendering;

import java.util.List;

import okkapel.kkplglutil.rendering.util.RRect;
import okkapel.kkplglutil.util.Texture;

public class RenderSection {
	/** Untextured */
	private int offset_ut, vcount_ut;
	private int offset_t, vcount_t;

	private Texture sectionTexture;
	
	private List<RRect> renderData;
	private List<RRect> renderData_ut;
	
	
	public RenderSection(Texture tex, int offset, int vcount) {
		this.sectionTexture = tex;
		this.offset_t = offset;
		this.vcount_t = vcount;
	}
	
	public void renderSection(GLRenderObjPointer rptr) {
		GLHandler.renderRPTRPartially(rptr, offset_t, vcount_t, sectionTexture);
		GLHandler.renderRPTRPartiallyWOTex(rptr, offset_ut, vcount_ut);
	}
	
	public void updateRSect() {
		
	}
	
	public Texture getSectionTexture() {
		return this.sectionTexture;
	}
	
	public int getOffset() {
		return this.offset_t;
	}
	
	public int getVCount() {
		return this.vcount_t;
	}
	
	public int getOffset_ut() {
		return this.offset_ut;
	}
	
	public int getVCount_ut() {
		return this.vcount_ut;
	}
}
