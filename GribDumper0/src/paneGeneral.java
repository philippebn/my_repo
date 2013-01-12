import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;


public class paneGeneral extends JPanel {

	private static final long serialVersionUID = 3571787724834181234L;
	private float[] values;
	private BufferedImage image;
	
	
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters

    }
	

	public paneGeneral() {
	    System.out.println("Hello World");
	    
	    values = new float[160000];
	    double tmp = 0.0;
	    for (int i = 0; i < values.length; i++) {
	    	values[i] = (float) tmp;
	    	tmp = tmp + 0.1;
	    	//System.out.println(values[i]);
		}

	    
	    //InputStream in = new ByteArrayInputStream(bytearray);

	    //BufferedImage image = ImageIO.read(in);
      
        image = (BufferedImage) getImageFromArray(this.values, 400, 400);
	}
	
	
	public static Image getImageFromArray(float[] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
        WritableRaster raster = (WritableRaster) image.getData();
        raster.setPixels(0,0,width,height,pixels);
        return image;
    }
}
