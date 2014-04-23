import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map extends Thread {
	private final int dimX = 3000;
	private final int dimY = 1200;
	private boolean[][] board;
	private BufferedImage FinImg;
	private Player players[];
	private MapModify modifier;
	
	public int getWidth(){ return board.length; }
	public int getHeight(){ return board[0].length; }
	public int getDimX(){ return dimX; }
	public int getDimY(){ return dimY; }
	public boolean getPStatus(int x, int y){ return board[x][y]; }
	public BufferedImage getImg(){ return FinImg; }
	public void setPlayer(Player p, int x){ players[x] = p; }
	public Player getPlayer(int x){ return players[x]; }
	
	public Map(){
		super();
		board = new boolean [dimX][dimY];
		FinImg = new BufferedImage(board.length, board[0].length, BufferedImage.TYPE_INT_RGB);
		players = new Player[1];
		modifier = new MapModify();
		modifier.start();
	}
	
	public void generate(){
		BufferedImage img = null;
		try { img = ImageIO.read(new File("C:/Users/Jan/Desktop/Worms/terr.png"));
		} catch (IOException e) {}
		BufferedImage img3 = null;
		try { img3 = ImageIO.read(new File("C:/Users/Jan/Desktop/Worms/grass.png"));
		} catch (IOException e) {}
		
		double i = Math.random();
		double ix = 0;
		for(int x = 0; x < board.length; x++){
			boolean first = false;
			int firsty = 0;
			if (x%60 == 0) ix = (Math.random()-0.5)*0.02;
			if (x%4 == 0) ix += (Math.random()-0.5)*0.005;
			i += ix;
			for(int y = 0; y < board[0].length; y++){
				if(y > i*100+(0.6*dimY)){
					board[x][y] = true;
					if (!first){
						first = true;
						firsty = y;
					}
					if (y-firsty<img3.getHeight()){ 
						if(((img3.getRGB(x%img3.getWidth(), y-firsty) >> 24) & 0xff)==0) FinImg.setRGB(x, y, new Color(166,229,255,255).getRGB());
						else FinImg.setRGB(x, y, new Color(img3.getRGB(x%img3.getWidth(), y-firsty)).getRGB());
					} else {
						FinImg.setRGB(x, y, new Color(img.getRGB(x%img.getWidth(), y%img.getHeight())).getRGB());
					}
					
					
				} else {
					board[x][y] = false;
					FinImg.setRGB(x, y, new Color(166,229,255,255).getRGB());
				}
			}
		}

	}
	
	public void delete(int x, int y){
		board[x][y] = false;
		FinImg.setRGB(x, y, new Color(166,229,255,255).getRGB());
	}
	
	public void boom(int xp, int yp, int r){
		modifier.addTask(this, xp, yp, r);
		for(int i = 0; i<players.length; i++){
			if((Math.sqrt(Math.pow(players[i].getPosX()-xp, 2)+Math.pow(players[i].getPosY()-yp, 2)))<r){
				if(xp-players[i].getPosX()<0) players[i].setSpeedX((Math.sqrt(Math.pow(xp-players[i].getPosX(), 2)+Math.pow(yp-players[i].getPosY(), 2)))/15);
				else players[i].setSpeedX(-(Math.sqrt(Math.pow(xp-players[i].getPosX(), 2)+Math.pow(yp-players[i].getPosY(), 2)))/15);
				if(yp-players[i].getPosY()<0) players[i].setSpeedY((Math.sqrt(Math.pow(xp-players[i].getPosX(), 2)+Math.pow(yp-players[i].getPosY(), 2)))/15);
				else players[i].setSpeedY(-(Math.sqrt(Math.pow(xp-players[i].getPosX(), 2)+Math.pow(yp-players[i].getPosY(), 2)))/15);
			}
		}
	}
	



	public void run() {
		boolean ground = false;
		while(true){
			for(int i = 0; i<players.length; i++){
				ground = false;
				//Down
				boolean hit = false;
				if(players[i].getSpeedY()>=0){
					for(int x = players[i].getPosX(); x<players[i].getPosX()+players[i].getWidth(); x++){
						if(board[x][players[i].getPosY()+players[i].getHeight()]){
							hit = true;
							boolean found = false;
							int y = 0;
							while(!found){ y--; if(!board[x][players[i].getPosY()+y]){ found=true; players[i].setPosY(players[i].getPosY()+y+1); } }
						}
					}
				}
				if(hit){
					if(players[i].getSpeedY()==0) ground = true;
					if(players[i].getSpeedX()!=0) {
						players[i].setSpeedX(0);
					}
					if(!ground){
						players[i].setSpeedY(players[i].getSpeedY()*-0.05);
						if(players[i].getSpeedY()<0.02 && players[i].getSpeedY()>-0.2) players[i].setSpeedY(0);
					}
				}
				//Up
				if(!hit){
					for(int x = players[i].getPosX()+1; x<players[i].getPosX()+players[i].getWidth()-1; x++){
						if(board[x][players[i].getPosY()]){
							hit = true;
							players[i].setSpeedY(players[i].getSpeedY()*-0.5);
							boolean found = false;
							int y = 0;
							while(!found){ y++; if(!board[x][players[i].getPosY()+y]){ found=true; players[i].setPosY(players[i].getPosY()+y); } }
						}
					}
				}
				//Left
				if(!hit){
					for(int x = players[i].getPosY(); x<players[i].getPosY()+players[i].getHeight()-1; x++){
						if(board[players[i].getPosX()][x]){
							hit = true;
							players[i].setSpeedX(0);
							boolean found = false;
							int y = 0;
							while(!found){ y++; if(!board[players[i].getPosX()+y][x]){ found=true; players[i].setPosX(players[i].getPosX()+y); } }
						}
					}
				}
				//Right
				if(!hit){
					for(int x = players[i].getPosY(); x<players[i].getPosY()+players[i].getHeight()-1; x++){
						if(board[players[i].getPosX()+players[i].getWidth()][x]){
							hit = true;
							players[i].setSpeedX(0);
							boolean found = false;
							int y = 0;
							try {
								while(!found){ y--; if(!board[players[i].getPosX()+y][x]){ found=true; players[i].setPosX(players[i].getPosX()+y); } }
							} catch (ArrayIndexOutOfBoundsException e) {
								
							}
						}
					}
				}	
				
				/*Constant Gravity Speed Apply*/
				if(!ground) players[i].setSpeedY(players[i].getSpeedY()+(9.81/100));
				
				if(((int)(players[i].getPosX()+players[i].getWidth()+players[i].getSpeedX()))>dimX-1 || (int)(players[i].getPosX()+players[i].getSpeedX())<0) { players[i].setSpeedX(players[i].getSpeedX()*(-1)); }
				players[i].setPosX((int)(players[i].getPosX()+players[i].getSpeedX()));
				if((int)(players[i].getPosY()+players[i].getSpeedY())<0) { players[i].setSpeedY(players[i].getSpeedY()*(-1)); }
				players[i].setPosY((int)(players[i].getPosY()+players[i].getSpeedY()));

				
			}
			try{Thread.sleep(10);}
			catch(InterruptedException e){}
		}
		
	}
}
