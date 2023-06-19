import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Player extends GameObject {
	
	private Random r;
	
	public Player(int x, int y, ID id){
		super(x, y, id);
	}
	
	public void tick(){
		x += velX;
		y += velY;
	}
	
	public void render(Graphics g){
		if(id == ID.Player){
			g.setColor(Color.WHITE);
		}
		else if(id == ID.Player2){
			g.setColor(Color.RED);
		}
		
		g.fillRect(x, y, 32, 32);
	}
}