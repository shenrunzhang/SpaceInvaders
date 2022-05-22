import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class Board extends JPanel implements ActionListener {

	private final int ICRAFT_X = 40;
	private final int ICRAFT_Y = 60;
	private final int DELAY = 10;
	private Timer timer;
	private Alien[][] aliens;
	private Alien alien;
	private SpaceShip ship;
	private boolean moveR = true;
	private Image background;

	public Board() {

		initBoard();
		ship = new SpaceShip(450, 500);
	}

	private void initBoard() {
		int rowAliens = 4;
		int columnAliens = 4;
		aliens = new Alien[columnAliens][rowAliens];
		loadImage();

		addKeyListener(new TAdapter());
		setBackground(Color.BLACK);
		setFocusable(true);
		for (int i = 0; i < columnAliens; i++) {
			for (int j = 0; j < rowAliens; j++) {
				aliens[i][j] = new Alien(230 + i * (690 / rowAliens), j * (250 / columnAliens));
			}
		}
		timer = new Timer(DELAY, this);
		timer.start();
	}

	private void loadImage() {

		ImageIcon ii = new ImageIcon("src/resources/background.jpg");
		background = ii.getImage();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
		doDrawing(g);

		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < aliens.length; i++) {
			for (int j = 0; j < aliens[i].length; j++) {
				if (aliens[i][j].isVisible())
					g2d.drawImage(aliens[i][j].getImage(), aliens[i][j].getX(), aliens[i][j].getY(), this);
				ArrayList<Alien_Missile> a_missiles = aliens[i][j].getMissiles();
				for (Alien_Missile missile : a_missiles) {
					g2d.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
				}
			}
		}

		ArrayList<Missile> missiles = ship.getMissiles();
		g2d.drawImage(ship.getImage(), ship.getX(), ship.getY(), this);
		for (Missile missile : missiles) {
			g2d.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < aliens.length; i++) {
			for (int j = 0; j < aliens[i].length; j++) {
				randomAlienFire(aliens[i][j]);
			}
		}
		updateMissiles();
		updateAlien();
		updateSpaceship();
		repaint();

	}

	private void updateMissiles() {

		for (int i = 0; i < aliens.length; i++) {
			for (int j = 0; j < aliens[i].length; j++) {
				ArrayList<Alien_Missile> a_missiles = aliens[i][j].getMissiles();

				for (int k = 0; k < a_missiles.size(); k++) {

					Alien_Missile missile = a_missiles.get(k);

					if (missile.isVisible()) {

						missile.move();
					} else {

						a_missiles.remove(k);
					}
				}
			}
		}

		ArrayList<Missile> missiles = ship.getMissiles();
		// System.out.println(missiles.size());

		for (int i = 0; i < missiles.size(); i++) {

			Missile missile = missiles.get(i);

			boolean hit = false;
			int cordx = 0;
			int cordy = 0;
			for (int j = 0; j < aliens.length; j++) {
				for (int k = 0; k < aliens[j].length; k++) {
					if (missile.collide(aliens[j][k]) && aliens[j][k].isVisible()) {
						hit = true;
						cordx = j;
						cordy = k;
					}
				}
			}
			if (hit) {
				aliens[cordx][cordy].setVisible(false);
			}
			if (missile.isVisible()) {

				missile.move();
			} else {

				missiles.remove(i);
			}
		}

	}

	private void randomAlienFire(Alien alien) {
		int rand = (int) (Math.random() * 300000);

		if (rand < 1000)
			alien.fire();

		if (alien.getX() == alien.getInitX() + 100)
			moveR = false;
		if (alien.getX() == alien.getInitX() - 1)
			moveR = true;

		if (moveR)
			alien.moveRight();
		else
			alien.moveLeft();

	}

	private void updateAlien() {
		for (int i = 0; i < aliens.length; i++) {
			for (int j = 0; j < aliens[i].length; j++) {
				aliens[i][j].move();
			}
		}
	}

	private void updateSpaceship() {
		ship.move();
		System.out.println("X: " + ship.getX() + " " + "Y: " + ship.getY());

	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			ship.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			ship.keyPressed(e);
		}

	}

}