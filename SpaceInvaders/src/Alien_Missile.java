public class Alien_Missile extends Sprite{
	
	private final int BOARD_WIDTH = 600;
    private final int MISSILE_SPEED = 3;
    
    public Alien_Missile(int x, int y){
        super(x, y);
        initMissile();
    }
    private void initMissile(){
        loadImage("src/resources/alien_missile.png");
        getImageDimensions();
    }
    public void move(){
    
        y += MISSILE_SPEED;
        
        if (y > BOARD_WIDTH){
            visible = false;
        }
    }
}
