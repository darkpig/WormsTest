public class Player extends VisibleObject{
	private int posX;
	private int posY;
	private int width;
	private int height;
	private double speedX;
	private double speedY;
	
	
	public Player(int posX, int posY, int width, int height, String img){
		super(img);
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;

		
	}
	public int getPosX(){ return posX; }
	public int getPosY(){ return posY; }
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public double getSpeedX(){ return speedX; }
	public double getSpeedY(){ return speedY; }
	
	public void setPosX(int posX){ this.posX = posX; }
	public void setPosY(int posY){ this.posY = posY; }
	public void setSpeedX(double speedX){ this.speedX = speedX; }
	public void setSpeedY(double speedY){ this.speedY = speedY; }
}
