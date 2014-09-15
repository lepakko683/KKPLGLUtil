package okkapel.kkplglutil.rendering;

import java.util.ArrayList;
import java.util.List;

public class GLRenderBufferHandler {
	private List<GLRenderObj> renderObjs = null;
	
	// Merging
	private boolean cannotMerge = true;
	private int smallestObjId = -1;
	private int smallestObjSize = -1;
	private GLRenderObj buffer = null;
	
	public static GLRenderBufferHandler INSTANCE = new GLRenderBufferHandler();
	
	public GLRenderBufferHandler() {
		renderObjs = new ArrayList<GLRenderObj>();
	}
	
	public GLRenderObj getRenderObj(int id) {
		if(id < 0 || id >= renderObjs.size()) {
			return null;
		}
		
		return renderObjs.get(id);
	}
	
	public void attemptMerge() {
		if(cannotMerge) {
			return;
		}
		
		if(smallestObjId != -1) {
			int canditateA = -1;
			int sizeWCandA = 1024;
			
			for(int i=0;i<renderObjs.size();i++) {
				buffer = renderObjs.get(i); 
				if(buffer.isMergeable() && buffer.getDataSize() + smallestObjSize < sizeWCandA) {
					sizeWCandA = buffer.getDataSize() + smallestObjSize;
				}
			}
		}
	}
}
