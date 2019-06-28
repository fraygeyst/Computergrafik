package de.fhkl.imst.i.texture;

import java.awt.*;
import java.awt.image.*;

public class ImageTexture  {
	int[] imagePixels;
	int imageWidth, imageHeight;

	public ImageTexture(Image image, int width, int height) throws InterruptedException {
		PixelGrabber grabber = new PixelGrabber (image, 0, 0, width, height, true);
		if (!grabber.grabPixels ())
			throw new IllegalArgumentException ("Invalid image; pixel grab failed.");
		imagePixels = (int[]) grabber.getPixels ();
		imageWidth = grabber.getWidth ();
		imageHeight = grabber.getHeight ();
		
	//	System.out.println(imagePixels[0]);			
		//System.out.println(imageWidth);
		//System.out.println(imageHeight);
		
	}

	public RGB getTexel (double i, double j) {
		
		int pos = (int) (i * imageWidth % imageWidth) + imageWidth * (int) (j * imageHeight % imageHeight);
		
		if (pos <0 ) {
			pos *= -1;
		}
		
		RGB rgb = new  RGB (imagePixels[pos]);
		
		return rgb;
	}
}
