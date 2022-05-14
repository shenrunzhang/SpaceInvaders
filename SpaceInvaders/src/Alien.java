import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Alien extends Sprite{
	private int dx;
    private int dy = 0;
    private ArrayList<Alien_Missile> missiles;
    
    public Alien(int x, int y) {
    	super(x,y);
    	
    	missiles = new ArrayList<>();
    	
    	loadImage("SpaceInvaders/src/resources/alien1.png");
    	getImageDimensions();
    }
    
    public void move() {
    	x += dx;
    	y += dy;
    }
    public void moveLeft() {
    	dx = -1;
    }
    public void moveRight() {
    	dx = 1;
    }
    
    public ArrayList<Alien_Missile> getMissiles(){
    	return missiles;
    }
    
    
    public void fire() {
    	missiles.add(new Alien_Missile(x + width/2, y + height));
    }
    
    
}
