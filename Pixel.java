	package project;
	
	import java.awt.Color;
	
	public class Pixel {
		
	    private int blue;
	    private int red;
	    private int green;
	    private int brightness;
	    private double energy;
	
	    private Pixel left;
	    private Pixel right;
	    private Pixel up;
	    private Pixel down;
	    private Color color;
	    private int row;
	    private int col;
	
	    // Constructor
	    public Pixel(Pixel up, Pixel right, Pixel down, Pixel left, Color color) {
	    	this.up = up;
	    	this.right = right;
	    	this.down = down;
	    	this.left = left;
	        this.color = color;
	        this.energy = 0; // Energy initially set to 0
	        this.blue = color.getBlue();
	        this.red = color.getRed();
	        this.green = color.getGreen();
	        this.brightness = (red+blue+green)/3;
	        
	        if (left!=null) {
				left.right = this;
			}
			if(up!=null) {
				up.down = this;
			}
			if (right!=null) {
				right.left = this;
			}
			if(down!=null) {
				down.up = this;
			}
	
	    }
	

	    public int getBlue() {
	        return blue;
	    }
	    public int getRed() {
	    	return red;
	    }
	    public int getGreen() {
	    	return green;
	    }
	    public Color getColor() {
	    	return color;
	    }
	    public int getBrightness() {
	    	return brightness;
	    }
	
	    public double getEnergy() {
	        return energy;
	    }
	    
	    public void setEnergy(double energy) {
	        this.energy = energy;
	    }
	
	    public Pixel getUp() {
	        return up;
	    }
	    public void setUp(Pixel up) {
	        this.up = up;
	    }
	    public Pixel getRight() {
	        return right;
	    }
	    public void setRight(Pixel right) {
	        this.right = right;
	    }
	    public Pixel getDown() {
	        return down;
	    }
	    public void setDown(Pixel down) {
	        this.down = down;
	    }
	    public Pixel getLeft() {
	        return left;
	    }
	    public void setLeft(Pixel left) {
	        this.left = left;
	    }

	    public int getCol() {
	    	return this.col;
	    }
	    public void setCol(int col) {
	    	this.col = col;
	    }
	    public int getRow() {
	    	return this.row;
	    }
	    public void setRow(int row) {
	    	this.row = row;
	    }
	
		public int getRGB() {
			return color.getRGB();
		}

		public void setColor(Color inputColor) {
			// Extract RGB components from the input color
		    int red = inputColor.getRed();
		    int green = inputColor.getGreen();
		    int blue = inputColor.getBlue();
		    
		    // Set RGB values of the pixel
		    this.setRed(red);
		    this.setGreen(green);
		    this.setBlue(blue);
		}
		public void setColor(int r, int g, int b) {
			// Extract RGB components from the input color
		    int red = r;
		    int green = g;
		    int blue = b;
		    
		    // Set RGB values of the pixel
		    this.setRed(red);
		    this.setGreen(green);
		    this.setBlue(blue);
		}
		public void setRed(int red) {
		    if (red < 0) {
		        red = 0;
		    } else if (red > 255) {
		        red = 255;
		    }

		    this.red = red;
		}
		public void setGreen(int g) {
		    if (g < 0) {
		        g = 0;
		    } else if (g > 255) {
		        g = 255;
		    }

		    this.green = g;
		}
		public void setBlue(int b) {
		    if (b < 0) {
		        b = 0;
		    } else if (b > 255) {
		        b = 255;
		    }

		    this.blue = b;
		}

	}
