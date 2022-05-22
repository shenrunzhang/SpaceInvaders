import java.awt.Image;

public class Shield extends Sprite{
	private double damage = 15;
	
	public Shield(int x, int y){
        super(x, y);
        initShield();
    }
	public void initShield() {
		loadImage("src/resources/shield.png");
        getImageDimensions();
	}
	public void resize() {
		
		image = image.getScaledInstance((int)(width - damage), (int)(height - height * damage / width), Image.SCALE_DEFAULT);
		damage += 15;
	}
	public int getHealth(){
		return (int) (width - damage);
	}
	
}
