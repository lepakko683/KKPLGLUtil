package okkapel.kkplglutil.rendering.util;

import okkapel.kkplglutil.rendering.GLRenderObj;
import okkapel.kkplglutil.util.TexIcon;

public class RRect {
	
	/** Relative to the GuiElement x and y */
	private int x, y;
	private int width, height;
	private int offset;
	private TexIcon tic;
	
	protected RRect(int x, int y, int width, int height, int offset) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.offset = offset;
	}
	
	/** startBufferModif is expected to have been called before this. And finishBufferModif is expected to be called after all modifications. */
	public void renderDataUpdate(int guiex, int guiey, GLRenderObj robj) {
		robj.setVertPos(guiex+x, guiey+y, offset, 1);
		robj.setVertPos(guiex+x, guiey+y+height, offset+1, 1);
		robj.setVertPos(guiex+x+width, guiey+y+height, offset+2, 1);
		
		robj.setVertPos(guiex+x+width, guiey+y+height, offset+3, 1);
		robj.setVertPos(guiex+x+width, guiey+y, offset+4, 1);
		robj.setVertPos(guiex+x, guiey+y, offset+5, 1);
	}
}
