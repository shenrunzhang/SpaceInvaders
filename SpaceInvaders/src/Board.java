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

public class Board extends JPanel implements ActionListener {

    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int DELAY = 10;
    private Timer timer;
    private Alien alien;
    private boolean moveR = true;


    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

        alien = new Alien(ICRAFT_X, ICRAFT_Y);

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

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

    private class TAdapter extends KeyAdapter {

       /* @Override
        public void keyReleased(KeyEvent e) {
            alien.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            alien.keyPressed(e);
        */
    }
}