package okkapel.kkplglutil.rendering;

public interface IGLRenderable {
	
	public GLRenderMethod getRenderMethod();
	
	public int getOffset();
	
	/**@return -1 if doesn't have texture.*/
	public int getTexture(); 
}
