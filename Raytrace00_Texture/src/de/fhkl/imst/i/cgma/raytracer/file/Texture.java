package de.fhkl.imst.i.cgma.raytracer.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture {
	
	public int[][] r;
	public int[][] g;
	public int[][] b;
	public int sizeX;
	public int sizeY;
	
	public Texture(String filename) throws IOException
	{
		String directory = System.getProperty("user.dir");
		BufferedImage img = ImageIO.read(new File(directory + "/texture/" + filename + ".png"));
		
		this.sizeX = img.getWidth(); //x
		this.sizeY = img.getHeight(); //y
		
		r = new int[this.sizeX][this.sizeY];
		g = new int[this.sizeX][this.sizeY];
		b = new int[this.sizeX][this.sizeY];
		
		for (int y=0; y < img.getHeight(); y++)
		{
			for(int x=0; x < img.getWidth(); x++)
			{
				r[x][y] = (img.getRGB(x, y) >> 16) & 0xff;
				g[x][y] = (img.getRGB(x, y) >> 8) & 0xff;
				b[x][y] = (img.getRGB(x, y))  & 0xff;
			}
		}	
	}
	
	public int getSizeX() {
		return this.sizeX;
	}
	
	public int getSizeY() {
		return this.sizeY;
	}
	
	public int getRed(int x, int y) {
		return this.r[x][y];
	}
	
	public int getGreen(int x, int y) {
		return this.g[x][y];	
	}
	
	public int getBlue(int x, int y) {
		return this.b[x][y];	
	}
	
}