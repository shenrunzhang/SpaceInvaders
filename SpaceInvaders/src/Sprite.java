import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Sprite {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean visible;
	protected Image image;

	public Sprite(int x, int y) {

		this.x = x;
		this.y = y;
		visible = true;
	}

<<<<<<< HEAD
        width = image.getWidth(null);
        height = image.getHeight(null);
    }  
    public boolean collide(Sprite s) {
    	if(x >= s.getX() && x + width <= s.getX()) {
    		if(y >= s.getY() && y + height <= s.getY()) {
    			return true;
    		}
    	}
    	return false;
    }
    public Image getImage() {
        return image;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isVisible() {
        return visible;
    }
    public int getWidth() {
    	return width;
    }
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
=======
	protected void loadImage(String imageName) {

		ImageIcon ii = new ImageIcon(imageName);
		image = ii.getImage();
	}

	protected void getImageDimensions() {

		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	// Use only for missile.collide(ship)
	public boolean collide(Sprite s) {
		// System.out.println("X: " + x + " Y: " + y);
		// System.out.println("Width: " + width + " Height: " + height);
		// System.out.println("SX: " + s.getX() + " SY: " + s.getY());
		if (x <= s.getX() + 60 && x + 10 >= s.getX()) {
			if (y <= s.getY() + 70 && y + 20 >= s.getY()) {
				return true;
			}
		}
		return false;
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(Boolean visible1) {
		visible = visible1;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
>>>>>>> a4b742f4e75aec8f4d5744413abf4a0392a8d779
}