package okkapel.kkplglutil.rendering;

import java.util.ArrayList;
import java.util.List;

public class GLRenderGroup {
	private List<GLRenderObjPointer> renderObjs = null;
	
	public GLRenderGroup() {
		renderObjs = new ArrayList<GLRenderObjPointer>();
	}
	
	public void renderGroup() {
		for(int i=0;i<this.renderObjs.size();i++) {
			GLHandler.renderRendPtr(this.renderObjs.get(i));
		}
	}
}
