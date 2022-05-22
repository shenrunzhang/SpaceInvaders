import java.util.ArrayList;
public class Alien extends Sprite {
	private int dx;
	private int dy = 0;
	private ArrayList<Alien_Missile> missiles;
	private int init_x;

	public Alien(int x, int y) {
		super(x, y);
		init_x = x;
		missiles = new ArrayList<>();

		loadImage("src/resources/alien1.png");
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
	public void moveDown() {
		dy = 2;
	}
	public void stopMove() {
		dy = 0;
		dx = 0;
	}
	public int getInitX() {
		return init_x;
	}

	public ArrayList<Alien_Missile> getMissiles() {
		return missiles;
	}

	public void fire() {
		if (super.isVisible()) {
			missiles.add(new Alien_Missile(x + width / 5, y + height * 3 / 5));
		}
	}

}
