package project;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;



public class UserMenu {
	public void reDraw(JFrame frame, BufferedImage oldImg, BufferedImage newImg) {
		frame.getContentPane().removeAll();
		frame.repaint();
		frame.getContentPane().setLayout(new FlowLayout());
	    frame.getContentPane().add(new JLabel(new ImageIcon(oldImg)));
	    frame.getContentPane().add(new JLabel(new ImageIcon(newImg)));
	    frame.pack();
	    frame.setVisible(true);
	   
	
	}
	public void reDraw(JFrame frame, PixelGraph image, PixelGraph ogImg) {
		BufferedImage img = image.convertToBufferedImage();
		BufferedImage oldImg = ogImg.convertToBufferedImage();
    	frame.getContentPane().removeAll();
		frame.repaint();
		frame.getContentPane().setLayout(new FlowLayout());
	    frame.getContentPane().add(new JLabel(new ImageIcon(oldImg)));
	    frame.getContentPane().add(new JLabel(new ImageIcon(img)));
	    frame.pack();
	    frame.setVisible(true);
		
	}
	
	public BufferedImage scale(BufferedImage org, int factor) {
		
		int oldHeight = org.getHeight();
		int oldWidth = org.getWidth();
		
		int newHeight = oldHeight*factor;
		int newWidth = oldWidth*factor;
		
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);		
		for(int row = 0; row < newHeight; row++) {
			for(int col = 0; col < newWidth; col++) {
				newImg.setRGB(row, col, org.getRGB(row/factor,col/factor));			
			}
		}
		return newImg;		
	}
	
	
	
	
	public void choice(PixelGraph ogImg, PixelGraph img, String input) {
	    Stack<Pixel> energySeam = null;
	    Stack<Pixel> blueSeam = null;
	    boolean shouldQuit = false;
	    Scanner scan = new Scanner(System.in);
	    
	    
	    JFrame f = new JFrame();
	    switch (input) {
	        case "e": // Lowest energy
	            energySeam = img.findLowestSeam(true);
	            img.colorSeam(Color.RED, img.findLowestSeam(true));
	    		reDraw(f, img, ogImg);
	            	            
	            String dInput = "";

	                System.out.println("Press 'd' to delete the highlighted seam:");
	                dInput = scan.next();
	                if (dInput.equals("d")) {
	                    img.removeSeam(true);
	                    reDraw(f, img, ogImg);
	                    break;
	                } else {
	                    System.out.println("Invalid input. Please try again.");
	                }
	            
	            break;
	        
	        case "b": // Bluest seam
	            blueSeam = img.findLowestSeam(false);
	            img.colorSeam(Color.GREEN, img.findLowestSeam(false));
	            reDraw(f, img, ogImg);	            
	            dInput = "";
	                System.out.println("Press 'd to delete the highlighted seam:");
	                dInput = scan.next();
	                if (dInput.equals("d")) {
	                    img.removeSeam(false);
	                    reDraw(f, img, ogImg);
	                    break;
	                } else {
	                    System.out.println("Invalid input. Please try again.");
	                }
	            
	            break;
	            
	        case "u": // Undo
	            img.undo();
	            reDraw(f, img, ogImg);
	            
	            break;
	        

	        case "q": // Quit
	            System.out.println("Thanks for playing");
	            shouldQuit = true;
	            reDraw(f, img, ogImg);
	            break;
	        
	        default:
	            System.out.println("That is not a valid option.");
	            reDraw(f, img, ogImg);
	            break;
	    }
	}

	
	
	public static void printMenu() {
		System.out.println("Please make a selection");
		System.out.println("e: Highlight lowest energy seam.");
		System.out.println("b: Highlight the bluest seam.");
		System.out.println("u: Undo a deletion.");
		System.out.println("q: Quit.");
	}
	
	
	
   public static void main(String args[]) throws Exception{
	   
 
	  //read images
	  File originalFile1= new File("anteater1.png");
      BufferedImage img1 = ImageIO.read(originalFile1);
      
      File originalFile2= new File("anteater1.png");
      BufferedImage img2 = ImageIO.read(originalFile2);
     
      //create an instance of the class
      UserMenu test = new UserMenu();
      PixelGraph im1 = new PixelGraph("anteater1.png");
      PixelGraph im2 = new PixelGraph("anteater1.png");
      
      //Create a frame
      JFrame f = new JFrame();
      f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      
      test.reDraw(f, img1, img2);
      System.out.println("Welcome. The two images have been loaded");
      printMenu();
      boolean shouldQuit = false;
  	
		Scanner scan = new Scanner(System.in);
		while(!shouldQuit) {
			printMenu();
			
			try {
				String input = scan.next();
				if(input.equals("q")) {
					shouldQuit = true;
				}
				test.choice(im1, im2, input);
			} 
			
			catch (InputMismatchException e) {
				System.out.println("Input should be a letter: b, r, u, or q");
				shouldQuit = true;
			}
		}
		scan.close();
		}

   }





