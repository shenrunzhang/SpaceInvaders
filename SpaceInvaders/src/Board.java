import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.TimerTask;

import java.awt.Font;
import java.awt.FontMetrics;

import java.awt.Image;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class Board extends JPanel implements ActionListener {
	private int health = 100;
	
	private final int ICRAFT_X = 40;
	private final int ICRAFT_Y = 60;
	private final int DELAY = 60;
	private Timer timer;
	private ArrayList<Alien> aliens;
	private SpaceShip ship;
	private boolean ingame;
	private boolean win;
	private Image background;
	private java.util.Timer timer1;
	private java.util.Timer timer2;
	private java.util.Timer timer3;
	private int movement;
	//private Shield[] shields;
	private ArrayList<Shield> shields = new ArrayList<>();
	
	private int[][] pos = {
			{230, 0} , {402, 0}, {575, 0}, {747, 0},
			{230, 75}, {402,75}, {575,75},{747,75},
			{230, 150},{402,150},{575,150},{747,150},
			{230, 225},{402,225},{575,225},{747,225}
			
	};
	
	public Board() {

		initBoard();
		ship = new SpaceShip(450, 500);
//		shields = new Shield[4];
//		for (int i = 0; i < shields.length; i++ ) {
//			shields[i] = new Shield(200 + i * 200,400);
//		}
		for (int i = 0; i < 4; i++) {
			shields.add(new Shield(200 + i * 200,400));
		}
		
	}

	private void initBoard() {
		ingame = true;	
		
		aliens = new ArrayList<>();

        for (int[] p : pos) {
            aliens.add(new Alien(p[0], p[1]));
        }

		
		loadImage();

		addKeyListener(new TAdapter());
		setBackground(Color.BLACK);
		setFocusable(true);

		timer = new Timer(DELAY, this);
		timer.start();
		
		timer1 = new java.util.Timer();
        timer1.scheduleAtFixedRate(new MoveRight(), 
                0, 9000 * (DELAY/60));
        timer2 = new java.util.Timer();
        timer2.scheduleAtFixedRate(new MoveLeft(), 
                3000 * (DELAY/60), 9000 * (DELAY/60));
        timer3 = new java.util.Timer();
        timer3.scheduleAtFixedRate(new MoveDown(), 
                6000 * (DELAY/60), 9000 * (DELAY/60));  
	}
	
	private class MoveRight extends TimerTask {

        @Override
        public void run() { 
            movement = 0;
        }
    }
	private class MoveLeft extends TimerTask {

        @Override
        public void run() { 
            movement = 1;
        }
    }
	private class MoveDown extends TimerTask {

        @Override
        public void run() { 
            movement = 2;
        }
    }

	private void loadImage() {

		ImageIcon ii = new ImageIcon("src/resources/background.jpg");
		background = ii.getImage();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		g.drawImage(background, 0, 0, null);
		
		
		if(ingame) {
			doDrawing(g);
			drawHealth(g);
		}
		else if (win)
			drawWon(g);
		else
			drawGameOver(g);

		Toolkit.getDefaultToolkit().sync();
	}
	private void drawGameOver(Graphics g) {
		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (1000 - fm.stringWidth(msg)) / 2,
                650 / 2);
	}
	private void drawWon(Graphics g) {
		String msg = "You Won";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (1000 - fm.stringWidth(msg)) / 2,
                650 / 2);
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < aliens.size(); i++) {
			g2d.drawImage(aliens.get(i).getImage(), aliens.get(i).getX(), aliens.get(i).getY(), this);
			ArrayList<Alien_Missile> a_missiles = aliens.get(i).getMissiles();
			for(Alien_Missile missile: a_missiles) {
				g2d.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
			}
		}

		ArrayList<Missile> missiles = ship.getMissiles();
		if (ship.isVisible())
			g2d.drawImage(ship.getImage(), ship.getX(), ship.getY(), this);
		for (Missile missile : missiles) {
			g2d.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
		}
		
		for (Shield s: shields) {
			g2d.drawImage(s.getImage(), s.getX(), s.getY(), this);
		}

	}
	private void drawHealth(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
        g2d.fillRect(50,50,health, 15);
        g2d.setColor(Color.WHITE);
        g2d.drawRect(48, 48, 104, 19);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for (int i = 0; i < aliens.size(); i++) {
			randomAlienFire(aliens.get(i));
		}
		inGame();
		
		updateMissiles();
		updateAlien();
		updateSpaceship();
		updateShield();
		
		checkCollisions();
		
		repaint();

	}
	
	private void checkCollisions() {
		Rectangle r3 = ship.getBounds();
		for(int i = 0; i< aliens.size();i++) {    
            Rectangle r2 = aliens.get(i).getBounds();
            
            if (r3.intersects(r2)) {
                ship.setVisible(false);
                aliens.get(i).setVisible(false);
                ingame = false;
            }
            
            ArrayList<Alien_Missile> a_missiles = aliens.get(i).getMissiles();
            
            for(int j = 0; j < a_missiles.size(); j ++) {
            	Rectangle r4 = a_missiles.get(j).getBounds();
            	if(r3.intersects(r4)) {
            		a_missiles.remove(j);
            		health -= 20;
            	}
            	
            	for(int k = 0; k < shields.size(); k++) {
            		Rectangle r5 = shields.get(k).getBounds();
            		if(r4.intersects(r5)) {
            			a_missiles.get(j).setVisible(false);
            			shields.get(k).resize();
            			
            		}
            	}
            }
            
            
        }
		ArrayList<Missile> ms = ship.getMissiles();
		for(Missile m : ms) {
			Rectangle r1 = m.getBounds();
			for (Alien alien: aliens) {
				Rectangle r2 = alien.getBounds();
				
				if (r1.intersects(r2)) {
					m.setVisible(false);
					alien.setVisible(false);
				}
			}
		}
		
		
	}
	private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

	private void updateMissiles() {
		for (int i = 0; i < aliens.size(); i++) {
			ArrayList<Alien_Missile> a_missiles = aliens.get(i).getMissiles();
			for (int k = 0; k < a_missiles.size(); k++) {
				Alien_Missile missile = a_missiles.get(k);

				if (missile.isVisible()) {
					missile.move();
				} else {
					a_missiles.remove(k);
				}
			}
			
		}


		ArrayList<Missile> missiles = ship.getMissiles();

		for (int i = 0; i < missiles.size(); i++) {

			Missile missile = missiles.get(i);

			//alienMissileCollision(missiles, i);

			if (missile.isVisible()) {

				missile.move();
			} else {

				missiles.remove(i);
			}
		}

	}
	private void updateShield() {
		for(int i = 0; i<shields.size(); i ++) {
			if (shields.get(i).getHealth() < 20) {
				shields.remove(i);
			}
			
		}
	}

	private void randomAlienFire(Alien alien) {
		int rand = (int) (Math.random() * 300000);

		if (rand < 1500)
			alien.fire();
		if (movement == 0) {
			alien.stopMove();
			alien.moveRight();
		}	
		if (movement == 1) {
			alien.stopMove();
			alien.moveLeft();
		}
		if (movement == 2) {
			alien.stopMove();
			alien.moveDown();
		}


	}

	private void updateAlien() {
		if (aliens.isEmpty()) {
			ingame = false;
			win = true;
			return;
		}
		for (int i = 0; i < aliens.size(); i++) {
			
			if (aliens.get(i).isVisible())
				aliens.get(i).move();
			else
				aliens.remove(i);
		}
	}

	private void updateSpaceship() {
		ship.move();
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