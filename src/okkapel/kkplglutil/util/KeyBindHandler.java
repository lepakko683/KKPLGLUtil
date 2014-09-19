package okkapel.kkplglutil.util;

import java.util.ArrayList;
import java.util.List;

public class KeyBindHandler {
	
	private static List<KeyBind> keybinds = new ArrayList<KeyBind>();
	
	public static void addKeyBind(KeyBind kb) {
		keybinds.add(kb);
	}
	
	public static void updateKBs() {
		for(int i=0;i<keybinds.size();i++) {
			keybinds.get(i).update();
		}
	}
	
	public static void deleteKeyBinds() {
		keybinds.clear();
	}
}
