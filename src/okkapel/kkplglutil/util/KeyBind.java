package okkapel.kkplglutil.util;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public abstract class KeyBind {
	
	private boolean wasButtonDownOnLastTick = false;
	private boolean isMouseButton;
	private boolean isPressed = false;
	private int buttonId;
	
	
	public KeyBind(boolean isMouseButton, int buttonId) {
		this.isMouseButton = isMouseButton;
		this.buttonId = buttonId;
	}
	
	public void update() {
		if(button_down()) {
			onKeyHeldDown();
			if(!wasButtonDownOnLastTick) {
				onKeyPushedDown();
			}
			wasButtonDownOnLastTick = true;
		} else {
			if(wasButtonDownOnLastTick) {
				onKeyUp();
				wasButtonDownOnLastTick = false;
			}
		}
	}
	
	private boolean button_down() {
		return isMouseButton ? Mouse.isButtonDown(buttonId) : Keyboard.isKeyDown(buttonId);
	}
	
	/**Executed on every tick*/
	public abstract void onKeyHeldDown();
	
	/**Executed only once when the key is pressed*/
	public abstract void onKeyPushedDown();
	
	/**Executed only once when the key released*/
	public abstract void onKeyUp();
}
