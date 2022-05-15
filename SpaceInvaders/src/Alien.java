import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Alien extends Sprite {
	private int dx;
	private int dy;
	private ArrayList<Alien_Missile> missiles;

	public Alien(int x, int y) {
		super(x, y);

		missiles = new ArrayList<>();

		loadImage("resources/alien1.png");
		getImageDimensions();
	}

	public void move() {
		x += dy;
		y += dy;
	}

	public ArrayList<Alien_Missile> getMissiles() {
		return missiles;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			fire();
		}

		if (key == KeyEvent.VK_LEFT) {
			dx = -1;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 1;
		}

		if (key == KeyEvent.VK_UP) {
			dy = -1;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 1;
		}
	}

	public void fire() {
		missiles.add(new Alien_Missile(x + width / 2, y - height));
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			dy = 0;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
	}
}
