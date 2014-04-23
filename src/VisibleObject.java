import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class VisibleObject {
	
	private BufferedImage model;
	
	public BufferedImage getImg(){ return model; }
	public void setImg(String s){ model = null;	try { model = ImageIO.read(new File(s)); } catch (IOException e) {} }
	
	public VisibleObject(String s){
		model = null;
		try { model = ImageIO.read(new File(s));
		} catch (IOException e) {}
	}
	
	
	
}
