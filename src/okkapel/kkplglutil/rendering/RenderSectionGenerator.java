package okkapel.kkplglutil.rendering;

import java.util.ArrayList;
import java.util.List;

import celestibytes.lib.Tuple;
import okkapel.kkplglutil.rendering.util.RRect;
import okkapel.kkplglutil.util.Colour;
import okkapel.kkplglutil.util.TexIcon;

public class RenderSectionGenerator {
	public static final RenderSectionGenerator INSTANCE = new RenderSectionGenerator();
	
	private List<RRect> renderData;
	private RenderBufferGenerator rbg;
	
	public RenderSectionGenerator() {
		renderData = new ArrayList<RRect>();
	}
	
	public void addRect(int x, int y, int width, int height, Colour color) {
		
	}
	
	public void addRectWUV(int x, int y, int width, int height, TexIcon tic, Colour color) {
		
	}
	
	public Tuple<List<RRect>, RenderSection> createRenderSection() {
		
	}
}
