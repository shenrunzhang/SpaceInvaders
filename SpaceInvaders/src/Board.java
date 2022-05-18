import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Board extends JPanel implements ActionListener {

    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int DELAY = 10;
    private Timer timer;
    private Alien alien;
    private SpaceShip ship;
    private boolean moveR = true;
    private Image background;


    public Board() {

        initBoard();
        ship = new SpaceShip(450, 500);
    }

    private void initBoard() {
        loadImage();

        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

        alien = new Alien(ICRAFT_X, ICRAFT_Y);

        timer = new Timer(DELAY, this);
        timer.start();
    }
    private void loadImage() {

		ImageIcon ii = new ImageIcon("SpaceInvaders/src/resources/bardejov.png");
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
        
        g2d.drawImage(alien.getImage(), alien.getX(),
                alien.getY(), this);

        ArrayList<Alien_Missile> a_missiles = alien.getMissiles();

        for (Alien_Missile missile : a_missiles) {
            
            g2d.drawImage(missile.getImage(), missile.getX(),
                    missile.getY(), this);
        }
        
        g2d.drawImage(ship.getImage(), ship.getX(),ship.getY(),this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	
    	int rand = (int)(Math.random() * 300000);
    	
    	if (rand < 1000)
    		alien.fire();
        
        if(alien.getX() == ICRAFT_X + 100)
            moveR = false;
        if(alien.getX() == ICRAFT_X -1)
            moveR = true;
        if(moveR)
            alien.moveRight();
        else
            alien.moveLeft();
        
        updateMissiles();
        updateAlien();
        updateSpaceship();
        repaint();
        
    }

    private void updateMissiles() {

        List<Alien_Missile> a_missiles = alien.getMissiles();

        for (int i = 0; i < a_missiles.size(); i++) {

        	Alien_Missile missile = a_missiles.get(i);

            if (missile.isVisible()) {

                missile.move();
            } else {

            	a_missiles.remove(i);
            }
        }
    }

    private void updateAlien() {
        alien.move();
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