package okkapel.kkplglutil.rendering.font;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import okkapel.kkplglutil.util.TextureFileSource;

public class FontFileSource extends TextureFileSource {
	
	private static final String fileEndingTexture = ".font.png";
	private static final String fileEndingData = ".font.data.dat"; // Will probably be a binary file

//	private int singleTexWidth = 8;
//	private int cwHalfPoint = -1;
//	private byte[] charWidths;
	
	public FontFileSource(File file) {
		super(file);
//		loadTexture();
//		if(this.tex.getImgWidth() % singleTexWidth != 0 || this.tex.getImgHeight() % singleTexWidth != 0) {
//		if(256 % singleTexWidth != 0 || 256 % singleTexWidth != 0) {
//			System.err.println("[KGLU - FontFileSource] [Error]: invalid image size, width or height not divisible by " + this.singleTexWidth);
//			return;
//		}
//		charWidths = new byte[((256/singleTexWidth)*(256/singleTexWidth))*2];
//		cwHalfPoint = charWidths.length/2;
//		System.out.println("charWidthsLength: " + charWidths.length);
//		charWidths = new byte[(this.tex.getImgWidth()*this.tex.getImgHeight() / singleTexWidth*singleTexWidth)*2];
//		loadCharWidths();
	}
	
	/** Warning: really expensive operation */
	public Font loadCharWidths(Font reload) {
		BufferedImage img = null;
		Font ret = reload != null ? reload : new Font();
		try {
			img = ImageIO.read(this.getFile());
			
			if(img.getWidth() % ret.singleTexWidth != 0 || img.getHeight() % ret.singleTexWidth != 0) {
				System.err.println("[KGLU - FontFileSource] [Error]: invalid image size, width or height not divisible by " + ret.singleTexWidth);
				return null;
			}
			
			ret.charWidths = new byte[((img.getWidth()/ret.singleTexWidth)*(img.getHeight()/ret.singleTexWidth))*2];
			ret.cwHalfPoint = ret.charWidths.length/2;
			System.out.println("charWidthsLength: " + ret.charWidths.length);
			
			System.out.println("Reading file: " + this.getFile().getAbsolutePath());
			
			int[] pixls = new int[ret.singleTexWidth*ret.singleTexWidth];
			int texture_count_h = img.getWidth()/ret.singleTexWidth;
			int texture_count_v = img.getHeight()/ret.singleTexWidth;
			ret.chrCountHoriz = texture_count_h;
			ret.chrCountVert = texture_count_v;
			boolean findingStartPos = true;
			System.out.println("pixel buffer size: " + pixls.length);
			System.out.println("image contains: " + texture_count_h + "x" + texture_count_v + " = " + (texture_count_h*texture_count_v) + " chars");
			int dir = 1;
			for(int y = 0;y<texture_count_v;y++) {
				for(int x = 0;x<texture_count_h;x++) {
					img.getRGB(x * ret.singleTexWidth, y * ret.singleTexWidth, ret.singleTexWidth, ret.singleTexWidth, pixls, 0, ret.singleTexWidth);
					findingStartPos = true;
					dir = 1;
					for(int x2 = 0;findingStartPos ? x2 < ret.singleTexWidth : x2 > -1;x2+=dir) {
						for(int y2 = 0;y2<ret.singleTexWidth;y2++) {
							if(pixls[y2*ret.singleTexWidth+x2] >> 24 != 0x00) {
								if(findingStartPos) {
									dir = -1;
									ret.charWidths[y*texture_count_h+x] = (byte)x2;
									x2 = ret.singleTexWidth;
									findingStartPos = false;
									break;
								} else {
									ret.charWidths[ret.cwHalfPoint+(y*texture_count_h+x)] = (byte)((x2+1)-ret.charWidths[y*texture_count_h+x]);
									x2 = -1;
									y2 = ret.singleTexWidth;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
