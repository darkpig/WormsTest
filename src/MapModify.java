public class MapModify extends Thread{

	Map subject;
	private int id = 0;
	private int[] index = new int[100];
	private int x;
	private int y;

	private int r;
	
	public MapModify(){
		super();
	}
	
	public void addTask(Map subject, int x, int y, int r){
		this.subject = subject;
		index[id++] = 0;
		if(id>=100) id = 0;
		this.x = x;
		this.y = y;
		this.r = r;
	}

	
	private void radial(int id){
		for(int i = x-r; i<x+r; i++){
			for(int i2 = y-r; i2<y+r; i2++){
				try{
					if(subject.getPStatus(i, i2)){
						if((Math.sqrt(Math.pow(i-x, 2)+Math.pow(i2-y, 2)))<r){
							subject.delete(i, i2);
						}
					}
				} catch (ArrayIndexOutOfBoundsException e){}
			}
		}
		index[id] = -1;
	}

	
	public void run() {
		int i = 0;
		while(true){
			if (index[i] == 0) radial(i);
			i++;
			if(i>=100) i = 0;
			try{Thread.sleep(0,1);}
			catch(InterruptedException e){}
		}

	}
	
}
