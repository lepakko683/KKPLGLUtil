package okkapel.kkplglutil.rendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GLRenderGroupHandler {
	private HashMap<String, Integer> groupNames = null;
	private List<GLRenderGroup> groups = null;
	
	public GLRenderGroupHandler() {
		groupNames = new HashMap<String, Integer>();
		groups = new ArrayList<GLRenderGroup>();
	}
	
	public void renderGroup(String name) {
		Integer value = groupNames.get(name);
		renderGroup(value == null ? -1 : value.intValue());
	}
	
	public void renderGroup(int id) {
		if(id < 0 || id >= groups.size()) {
			System.err.println("GLRenderGroup not found. Ignoring render call...");
			return;
		}
		groups.get(id).renderGroup();
	}
}
