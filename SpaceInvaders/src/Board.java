import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel {
	private SpaceShip ship;
	private ArrayList<Alien> aliens;
	private Image background;

	public Board() {
		initBoard();
		ship = new SpaceShip(background.getWidth(this) / 2, 10);
	}

	private void initBoard() {

		loadImage();

		int w = background.getWidth(this);
		int h = background.getHeight(this);
		setPreferredSize(new Dimension(w, h));
	}

	private void loadImage() {

		ImageIcon ii = new ImageIcon("src/resources/bardejov.png");
		background = ii.getImage();
	}

	@Override
	public void paintComponent(Graphics g) {

		g.drawImage(background, 0, 0, null);
	}
}