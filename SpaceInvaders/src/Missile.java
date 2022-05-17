
public class Missile extends Sprite {
	private final int dy = 2;
	private final int boardWidth = 390;

	public Missile(int x, int y) {
		super(x, y);
		display();
	}

	private void display() {
		loadImage("resources/missile.png");
		getImageDimensions();
	}

	public void move() {
		y += dy;
		if (y > boardWidth) {
			visible = false;
		}
	}

}
