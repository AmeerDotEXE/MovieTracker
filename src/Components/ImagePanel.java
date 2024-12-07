package Components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	int imagePos = 50;

	BufferedImage image = null;
	
	public ImagePanel(String imagePath) {
		if (imagePath == null) return;
		try {
			File imageFile = new File(imagePath);
			image = ImageIO.read(imageFile);
		} catch (IOException ex) {
			// handle exception...
			System.out.print("Image Not Found: "+imagePath);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image == null) return;

		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		float imageAspectRation = ((float)imageWidth) / ((float)imageHeight);

		// fill container
		if (getWidth() < imageWidth) {
			imageWidth = getWidth();
			imageHeight = (int) (imageWidth / imageAspectRation);
		}
		// fit container
//		if (getHeight() < imageHeight) {
//			imageHeight = getHeight();
//			imageWidth = (int) (imageHeight * imageAspectRation);
//		}

		// Centering calculations
		int x = (getWidth() - imageWidth) / 2;
		int y = (int) ((getHeight() - imageHeight) * (((float)imagePos)/100.0));
		g.drawImage(image, x, y, imageWidth, imageHeight, getBackground(), this);
	}
	
	

	public int getImagePosition() {
		return imagePos;
	}

	public void setImagePosition(int imagePos) {
		this.imagePos = imagePos;
	}

}