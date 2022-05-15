import java.util.ArrayList;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class SpaceShip extends Sprite {
	private int dx;
	private int dy;
	private ArrayList<Missile> missiles;

	public SpaceShip(int x, int y) {
		super(x, y);
		missiles = new ArrayList<Missile>();
		loadImage("src/resources/spaceship.png");
	}

	public void shoot() {
		Missile missile = new Missile(x + width / 2, y + height);
		missiles.add(missile);
	}

	public ArrayList<Missile> getMissiles() {
		return missiles;
	}

	public void move() {
		x += dx;
		y += dy;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			shoot();
		}

		if (key == KeyEvent.VK_LEFT) {
			dx = -2;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 2;
		}

		if (key == KeyEvent.VK_UP) {
			dy = -2;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 2;
		}
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