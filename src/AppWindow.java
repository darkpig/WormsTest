

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;

public class AppWindow extends JPanel{
	

	private static final long serialVersionUID = 1L; //pffkp
	private int windowwidth;
	private int windowheight;
	private Point tmppos;
	private Point tmploc;
	private boolean tmphold;
	private long tmptime;
	private Map Ground = new Map();
	private Player[] Wurm;
	private int locX = 0;
	private int locY = 0;
	private long firstFrame;
	private int frames;
	private long currentFrame;
	private int fps;
	private double zoom = 1.0;
	
	
    public AppWindow() {
    	
    	tmphold = false;
    	tmppos = new Point();
    	tmploc = new Point();
    	Wurm = new Player[1];
    	for(int i = 0; i<1; i++){ 
    		Wurm[i] = new Player((int)(Math.random()*Ground.getDimX()/4-35),31,31,34,"C:/Users/Jan/Desktop/Worms/Worm.png");
    		Ground.setPlayer(Wurm[i], i);
    	}
    	
    	Ground.generate();
    	Ground.start();
    	
    	setFocusable(true);
		this.addKeyListener(new KeyListener() {
			
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyChar()){
					case 'w': if(locY>0)locY-=25; break;
					case 'a': if(locX>0)locX-=25; break;
					case 's': if(locY<Ground.getDimY()-windowheight)locY+=25; break;
					case 'd': if(locX<Ground.getDimX()-windowwidth)locX+=25; break;
					case 'q': for(int i = 0; i<1; i++){ Ground.getPlayer(i).setSpeedY(-2.5); Ground.getPlayer(i).setSpeedX(-1.8); } break;
					case 'e': for(int i = 0; i<1; i++){ Ground.getPlayer(i).setSpeedY(-2.5); Ground.getPlayer(i).setSpeedX(1.8); } break;
					case '+': zoom+=0.1; break;
					case '-': zoom-=0.1; break;
					case ' ': Ground.generate();
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}		

		});

		this.addMouseListener(new MouseListener() {

		    public void mousePressed(MouseEvent e) {
		    	tmppos = e.getPoint();
		    	tmploc.setLocation(locX, locY);
		    	tmptime = System.currentTimeMillis();
		    	tmphold = true;
		    }
		    public void mouseReleased(MouseEvent e) {
		    	tmphold = false;
		    }
		    public void mouseEntered(MouseEvent e) {}
		    public void mouseExited(MouseEvent e) {}
		    public void mouseClicked(MouseEvent e) {
		    	Ground.boom((int)e.getPoint().getX()+locX, (int)e.getPoint().getY()+locY, (int)(System.currentTimeMillis()-tmptime)/20);
		    }

		});
		
		this.addMouseMotionListener(new MouseMotionListener() {

		    public void mouseMoved(MouseEvent e) {}

		    public void mouseDragged(MouseEvent e) {
		    	tmphold = false;
		    	locX = (int)tmploc.getX()+((int)tmppos.getX()-(int)e.getPoint().getX());
		    	locY = (int)tmploc.getY()+((int)tmppos.getY()-(int)e.getPoint().getY());
		    }

		});
    	
    }
	
    public void paint(Graphics g){
    	super.paint(g);
    	
    	frames++;
    	currentFrame = System.currentTimeMillis();
    	if(currentFrame > firstFrame + 1000){
    	   firstFrame = currentFrame;
    	   fps = frames;
    	   frames = 0;
    	}
	   
	    g.drawImage((Image)Ground.getImg(), 0-locX, 0-locY, (int)(Ground.getImg().getWidth()*zoom), (int)(Ground.getImg().getHeight()*zoom), this);
	    
	    for(int i = 0; i<1; i++){ 
	    	g.drawImage((Image)Wurm[i].getImg(), Wurm[i].getPosX()-locX, Wurm[i].getPosY()-locY, this);
	    }
	    
	    if(tmphold) g.drawOval((int)(tmppos.getX()-(((System.currentTimeMillis()-tmptime)/10)/2)), (int)(tmppos.getY()-(((System.currentTimeMillis()-tmptime)/10)/2)), (int)(System.currentTimeMillis()-tmptime)/10, (int)(System.currentTimeMillis()-tmptime)/10);
	    
	    g.setColor(Color.red);
	    g.drawString("FPS: ", 0, 0);


	    
	    
	    windowwidth = this.getWidth();
	    windowheight = this.getHeight();
	    
	    
	    repaint();
    }

}






//hi



















