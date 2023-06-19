import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1000000002L;
	
	public static final int WIDTH = 700, HEIGHT = WIDTH / 12 * 9;
	
	private Thread thread;
	private boolean running = false;
	private Random r;
	private Handler handler;
	
	public Game(){
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));
		new Window(WIDTH, HEIGHT, "My Game", this);
		r = new Random();
		handler.addObject(new Player(r.nextInt(WIDTH)/2+32, r.nextInt(HEIGHT)/2+32, ID.Player));
		handler.addObject(new Player(r.nextInt(WIDTH)/2+62, r.nextInt(HEIGHT)/2+32, ID.Player2));
		
	} 
	
	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try{
			thread.join();
			running = false;
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
				while(delta >= 1){
					tick();
					delta--;
				}
			if(running){
				render();
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick(){
		handler.tick();
	}
	
	private void render(){
		BufferStrategy bufStrat = this.getBufferStrategy();
		if(bufStrat == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bufStrat.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		g.dispose();
		bufStrat.show();
	}
	
	public static void main(String args[]){
		new Game();
	}
}