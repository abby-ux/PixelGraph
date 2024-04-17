package project;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class PixelGraph {
	private ArrayList<ArrayList<Pixel>> image;
	private Stack<Action> commands;
	Stack<Stack<Pixel>> beforeColored;
	private JFrame frame = new JFrame();
	
	// Constructor that takes in a string meant to be a file name
	// and creates an arraylist of arraylist of pixels, a PixelGraph, where the pixels in
	// the PixelGraph are the pixels from the file input.
	public PixelGraph(String fileName) {
		commands = new Stack<Action>();
		image = new ArrayList<ArrayList<Pixel>>();
		beforeColored = new Stack<Stack<Pixel>>();
		
		BufferedImage inputImg;
		File inputFile = null;
		
		try {
			inputFile = new File(fileName);
			inputImg = ImageIO.read(inputFile);
			for (int row = 0; row < inputImg.getHeight(); row++) {
				ArrayList<Pixel> pixRow = new ArrayList<Pixel>();
				for (int col = 0; col < inputImg.getWidth(); col++) {
					//Retrieving contents of a pixel
					int pixel = inputImg.getRGB(col, row);
					//Creating a Color object from pixel value
		            Color originalColor = new Color(pixel);
		            //Retrieving the R G B values
		            int red = originalColor.getRed();
		            int green = originalColor.getGreen();
		            int blue = originalColor.getBlue();
		            Color newColor = new Color(red, green, blue);
		            //setting the new image to the original color pixels 
		            
		            Pixel newPix = new Pixel(null, null, null, null, newColor);

		            pixRow.add(newPix);
		            
				}
				image.add(pixRow);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		JFrame f = new JFrame();
	    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		reDraw(f, convertToBufferedImage());

	}
	
	//reDraws a single BufferedImage by taking in a JFrame and BufferedImage
	// and returns nothing
	public void reDraw(JFrame frame, BufferedImage image) {
    	frame.getContentPane().removeAll();
		frame.repaint();
		frame.getContentPane().setLayout(new FlowLayout());
//	    frame.getContentPane().add(new JLabel(new ImageIcon(oldImg)));
	    frame.getContentPane().add(new JLabel(new ImageIcon(image)));
	    frame.pack();
	    frame.setVisible(true);
	}
	// converts an array list of array list of pixels into 
	// a BufferedImage and returns that BufferedImage
	public BufferedImage convertToBufferedImage() {
        int height = image.size();
        int width = image.get(0).size();
         
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                Pixel pixel = image.get(r).get(c);
                int rgb = new Color(pixel.getRed(), pixel.getGreen(), 
                		pixel.getBlue()).getRGB();
                img.setRGB(c, r, rgb);
            }
        }
        setAllNeighbors();
        return img;
    }
	
	
	// Loops through every pixel in the arraylist of arraylist
	// of pixels and sets its image.
	// takes in nothing and returns northing.
	private void setAllNeighbors() {
        int height = image.size();
        int width = image.get(0).size();
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                Pixel pixel = image.get(r).get(c);
                // Set neighbors and energy values for the current pixel
                setPixelNeighbors(pixel, r, c);
                calcEnergyValue(pixel, r, c);
            }
        }
    }

	// takes in a pixel, an integer representing a row, and an integer
	// representing a column and returns a double that is the calculated
	// energy value of this pixel. Wraps around the image to calculate the value.
	// sets the row and column values of the current pixel as well.
	public double calcEnergyValue(Pixel pix, int r, int c) {
        int rows = image.size();
        int cols = image.get(0).size();

                Pixel currentPix = image.get(r).get(c);
                currentPix.setRow(r);
                currentPix.setCol(c);
                double upLeft = getPixelBrightness(r - 1, c - 1, rows, cols);
                double up = getPixelBrightness(r - 1, c, rows, cols);
                double upRight = getPixelBrightness(r - 1, c + 1, rows, cols);
                double left = getPixelBrightness(r, c - 1, rows, cols);
                double right = getPixelBrightness(r, c + 1, rows, cols);
                double downLeft = getPixelBrightness(r + 1, c - 1, rows, cols);
                double down = getPixelBrightness(r + 1, c, rows, cols);
                double downRight = getPixelBrightness(r + 1, c + 1, rows, cols);

                double horEnergy = (upLeft + left + downLeft) - (upRight + right + downRight);
                double vertEnergy = (upLeft + up + upRight) - (downLeft + down + downRight);
                double energy = Math.sqrt(horEnergy * horEnergy + vertEnergy * vertEnergy);
                
                currentPix.setEnergy(energy);
                return energy;
    }


	// takes in a pixel, and integer r representing the row of the pixel, and 
	// an integer c representing the column of the pixel and returns northing.
	// This method sets the neighbors, using wrap around for edge cases, of the 
	// pixel that was passed into the parameters.
    private void setPixelNeighbors(Pixel pixel, int r, int c) {
        int height = image.size();
        int width = image.get(0).size();
        // Left neighbor
        if (c > 0) {
            pixel.setLeft(image.get(r).get(c - 1));
        } else {
            pixel.setLeft(image.get(r).get(width-1)); // Wrap around to the right edge
        }
        // Right neighbor
        if (c < width - 1) {
            pixel.setRight(image.get(r).get(c+1));
        } else {
            pixel.setRight(image.get(r).get(0)); // Wrap around to the left edge
        }
        // Up neighbor
        if (r > 0) {
            pixel.setUp(image.get(r - 1).get(c));
        } else {
            pixel.setUp(image.get(height - 1).get(c)); // Wrap around to the bottom edge
        }
        // Down neighbor
        if (r < height - 1) {
            pixel.setDown(image.get(r + 1).get(c));
        } else {
            pixel.setDown(image.get(0).get(c)); // Wrap around to the top edge
        }
    }
	
	// This method takes in integers representing a row, and column, and the total 
    // amount of columns in a PixelGraph and returns a double with the brightness of 
    // the pixel at the row and column indices passed in. If an invalid index is passed
    // in, the method will return the brightness of a pixel bordering the edge
    // closest to the invalid index passed in.
    private double getPixelBrightness(int row, int col, int rows, int cols) {
        if (row < 0) 
        	row = rows - 1;  // Wrap around to the bottom row
        if (row >= rows) 
        	row = 0;  // Wrap around to the top row
        if (col < 0) 
        	col = cols - 1;  // Wrap around to the rightmost column
        if (col >= cols) 
        	col = 0;  // Wrap around to the leftmost column
        Pixel pixel = image.get(row).get(col);
        return pixel.getBrightness();
    }
	
	
    // This method takes in a boolean signaling if we are finding the lowest energy seam
    // (true) or the bluest seam (false). It returns this seam as a 
    // stack of pixels.
    public Stack<Pixel> findLowestSeam(boolean isForEnergy){
		
		Stack<Pixel> cheapestSeam = new Stack<>();

		Pixel lowestInRow = findMinValue(0, isForEnergy);
		int c = lowestInRow.getCol();
		//add the lowest value from the first row to the energySeam
		cheapestSeam.add(lowestInRow);
		for (int r = 1; r < image.size(); r++) {
			
			lowestInRow = findMinNeighbor(lowestInRow, isForEnergy);
			cheapestSeam.add(lowestInRow);
		}
		
		Stack<Pixel> beforeColor = new Stack<Pixel>();
		beforeColor.addAll(cheapestSeam);
		
		return cheapestSeam;
	} 
	
    
    // This method takes in an integers representing a pixel row of an image
    // and a boolean asking if we are finding the lowest energy (true) or highest
    // blue values (false). The method returns a Pixel, the lowest or bluest
    // pixel in that row.
	private Pixel findMinValue(int row, boolean isForEnergy) {
		double minValue = Double.MAX_VALUE;
		Pixel minPixel = null;
		int col = -1;
		double val = -1;
			for (int c = 0; c < image.get(0).size(); c++) {	
				if (isForEnergy) {
					val = image.get(row).get(c).getEnergy();
				} else {
					val = image.get(row).get(c).getBlue();
				}

				if (val < minValue) {
					minValue = val;
					col = c;
					minPixel =image.get(row).get(c);
				}
			}    
		return minPixel;
	}
	
	
	
	// This method takes in a pixel which is the pixel that we are trying to find
	// the neighbors of, and a boolean asking if we are finding the lowest energy (true) or highest
    // blue values (false). The method then finds and returns the pixel that is the 
	// bluest or lowest energy neighbor to the pixel passed into the parameters.
	private Pixel findMinNeighbor(Pixel entry, boolean isForEnergy) {
		
		double minValue = Double.MAX_VALUE;
		Pixel minPix = null;

		if (entry == null) {
			System.out.println("null");
			return null;
		} 
		
		if (isForEnergy) {
//			Below
			if (entry.getDown().getEnergy() < minValue) {
				minValue = entry.getDown().getEnergy();
				minPix = entry.getDown();
			}
//			Below right
				if (entry.getDown().getRight().getEnergy() < minValue) {
					minValue = entry.getDown().getRight().getEnergy();
					minPix = entry.getDown().getRight();
				}
//			Below Left
				if (entry.getDown().getLeft().getEnergy() < minValue) {
					minValue = entry.getDown().getLeft().getEnergy();
					minPix = entry.getDown().getLeft(); 
				}
		} else {
		    Pixel down = entry.getDown();
		    if (down.getBlue() < minValue) {
		        minValue = down.getBlue();
		        minPix = down;
		    }
		    Pixel downRight = entry.getDown().getRight();
		    if (downRight.getBlue() < minValue) {
		        minValue = downRight.getBlue();
		        minPix = downRight;
		    }
		    Pixel downLeft = entry.getDown().getLeft();
		    if (downLeft.getBlue() < minValue) {
		        minValue = downLeft.getBlue();
		        minPix = downLeft;
		    }
		}
		return minPix;
	}




	// This method takes in an input color and a stack of pixels representing a seam,
	// and then colors the seam with the input color. It returns nothing.
	public void colorSeam(Color inputColor, Stack<Pixel> seam) {
		Stack<Integer> indicesColored = new Stack<Integer>();
		Stack<Pixel> beforeColor = new Stack<Pixel>();

		for (int r = image.size()-1; r >= 0; r--) {
			    	
			    	Pixel pixelToRemove = seam.pop();
			        int columnToColor = pixelToRemove.getCol();
			        indicesColored.push(columnToColor);

			        Pixel currentPix = image.get(r).get(columnToColor);
			        Pixel pixCopy = new Pixel(currentPix.getUp(), currentPix.getRight(),
			        currentPix.getDown(), currentPix.getLeft(), 
			        currentPix.getColor());
			        
			        beforeColor.push(pixCopy);
			        image.get(r).get(columnToColor).setColor(inputColor);
		
			    }

		commands.push(new Action(beforeColor, indicesColored));
	}


	
	
	
	// This method takes in a boolean asking if we are removing the seam that is
	// the lowest energy (true) or the bluest (false) and them removes that seam
	// from the PixelGraph. The method returns nothing.
	public void removeSeam(boolean isForEnergy) {
		if (image.get(0).size() <= 0) {
			System.out.println("No seam to remove.");
			return;
		}
		Stack<Pixel> seamToRemove = findLowestSeam(isForEnergy);
	    Stack<Integer> indicesRemoved = new Stack<>();

	    // Remove pixels corresponding to the lowest seam
	    for (int r = image.size()-1; r >= 0; r--) {
	    	
	    	Pixel pixelToRemove = seamToRemove.pop();
	        int colToRemove = pixelToRemove.getCol();
	       
	        indicesRemoved.push(colToRemove);
	        image.get(r).remove(colToRemove);

	    }

	    setAllNeighbors();
	}
	
	

	// This method undoes the last deletion of a seam in the PixelGraph.
	// It takes in and returns nothing.
	public void undo() {

		if (commands.size()>0) {
			Action toUndo = commands.pop();
			Stack<Pixel> toAdd = toUndo.getRemoved();
			Stack<Integer> colAdd = toUndo.getRemovedCols();

			int colSize = colAdd.size();
			int toAddSize = toAdd.size();
			ArrayList<ArrayList<Pixel>> newImg = new ArrayList<ArrayList<Pixel>>();
	        if (colSize > 0) {
	        	ArrayList<Pixel> pixRow = new ArrayList<Pixel>();
	            for (ArrayList<Pixel> row : image) {

	                int colIndex = colAdd.pop();

	                    row.add(colIndex, toAdd.pop());
	                    pixRow = row;

	            }
	            colSize--;
	            toAddSize--;
	            newImg.add(pixRow);
	        }
		} else {
			System.out.println("No actions to undo.");
		}
		
		setAllNeighbors();
	}

	// This method takes in nothing and returns a 
	// PixelGraph width as an integer.
	public int getWidth() {
		return image.get(0).size();
	}
	// This method takes in nothing and returns a 
		// PixelGraph height as an integer.
	private int getHeight() {
		return image.size();
	}
	
	//returns a pixel at a specified row and column passed in
	//used for testing.
	public Pixel getPix(int r, int c) {
		return image.get(r).get(c);
	}
	
	
	// This method is a toString override that prints out the energy or 
	// bluest RBG values of each pixel in a PixelGraph. It will print out the 
	// blue values if the parameter is false, and energy values if true.
	public String toString(boolean isForEnergy) {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("#.##"); // Formats to two decimal places
		for (int r = 0; r < image.size(); r++) {
			for (int c = 0; c < image.get(0).size(); c++) {
				Pixel current = image.get(r).get(c);
				if (isForEnergy) {
					sb.append("E: " + current.getEnergy());
				} else {
					sb.append("B: " + current.getBlue());
				}
				if (c < image.get(0).size()-1) {
					sb.append(" | ");
				}
			}
			if (r < image.size()-1) {
				sb.append(System.lineSeparator());
			}
		}
		return sb.toString();
	}
	
	// takes in a stack of pixels that is a pixel seam and 
	// a boolean asking if this seam is of energy values (true) or of
	// blue values (false) and returns a string of those values
	public String seamToString(Stack<Pixel> seam, boolean isForEnergy) {
		StringBuilder sb = new StringBuilder();
		while (seam.size() > 0) {
			Pixel poppedRef = seam.pop();
			if (isForEnergy) {
				sb.append(poppedRef.getEnergy());
			} else {
				sb.append(poppedRef.getBlue());
			}
				sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
	
	public String neighborsTest(boolean isForEnergy) {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < 1; r++) {
			for (int c = 0; c < 1; c++) {
				Pixel current = image.get(r).get(c);
				if (isForEnergy) {
					sb.append("D: " + current.getDown().getEnergy());
					sb.append(" U: " + current.getUp().getEnergy());
					sb.append(" R: " + current.getRight().getEnergy());
					sb.append(" L: " + current.getLeft().getEnergy());
					sb.append(System.lineSeparator());
				}
			}
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
	}
}
