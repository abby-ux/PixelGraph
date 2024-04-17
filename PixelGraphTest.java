package project;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Stack;

import org.junit.jupiter.api.Test;

class PixelGraphTest {

	@Test
	void testPixelGraph() {
		PixelGraph pg = new PixelGraph("cool.png");
		assertEquals(
		"E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0",
		pg.toString(true)
				);
		
		assertEquals(
		"B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 153 | B: 153",
		pg.toString(false)
				);
	}
	
	@Test
	void testSetNeighbors() {
		PixelGraph pg = new PixelGraph("cool.png");
		assertEquals(
		"D: 351.0 U: 351.0 R: 183.0 L: 351.0\r\n",
		pg.neighborsTest(true)
				);
	}
	
	@Test
	void testGetBrightness() {
		PixelGraph pg = new PixelGraph("cool.png");
		Pixel pix = pg.getPix(0, 0);
		assertEquals(pix.getBrightness(),
				201);
		Pixel pix2 = pg.getPix(1, 3);
		assertEquals(pix2.getBrightness(), 140);
		
	}

	@Test
	void testCalcEnergyValue() {
		PixelGraph pg = new PixelGraph("cool.png");
		assertEquals(
		"E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 183.0 | E: 9.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0",
		pg.toString(true)
				);
	}

	@Test
	void testFindLowestSeam() {
		PixelGraph pg = new PixelGraph("cool.png");
		Stack<Pixel> blueSeam = pg.findLowestSeam(false);
		Stack<Pixel> energySeam = pg.findLowestSeam(true);

		assertEquals(
			"153\r\n"
			+ "153\r\n"
			+ "153\r\n"
			+ "153\r\n"
			+ "153\r\n"
			+ "153\r\n"
			+ "153\r\n"
			+ "153\r\n",
			pg.seamToString(blueSeam, false)
				);
		assertEquals(
		"9.0\r\n"
		+ "9.0\r\n"
		+ "9.0\r\n"
		+ "9.0\r\n"
		+ "9.0\r\n"
		+ "9.0\r\n"
		+ "9.0\r\n"
		+ "9.0\r\n",
		pg.seamToString(energySeam, true)
				);
	}
	

	@Test
	void testColorSeam() {
		PixelGraph pg = new PixelGraph("cool.png");
		pg.colorSeam(Color.GREEN, pg.findLowestSeam(false));
		assertEquals(
		"B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 0 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 0 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 0 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 0 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 0 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 0 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 0 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 239 | B: 243 | B: 243 | B: 0 | B: 153",
		pg.toString(false)
				);
		
		PixelGraph pg2 = new PixelGraph("cool.png");
		pg2.colorSeam(Color.RED, pg.findLowestSeam(true));
		assertEquals(
		"B: 234 | B: 234 | B: 239 | B: 0 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 0 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 0 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 0 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 0 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 0 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 0 | B: 243 | B: 243 | B: 153 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 0 | B: 243 | B: 243 | B: 153 | B: 153",
		pg2.toString(false)
				);
	}

	@Test
	void testRemoveSeam() {
		PixelGraph pg = new PixelGraph("cool.png");
		pg.removeSeam(true);
		assertEquals(
		"E: 351.0 | E: 183.0 | E: 174.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 174.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 174.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 174.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 174.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 174.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 174.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0\r\n"
		+ "E: 351.0 | E: 183.0 | E: 174.0 | E: 9.0 | E: 177.0 | E: 177.0 | E: 351.0",
		pg.toString(true)
				);
		
		pg.removeSeam(false);
		assertEquals(
		"B: 234 | B: 234 | B: 239 | B: 243 | B: 243 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 243 | B: 243 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 243 | B: 243 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 243 | B: 243 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 243 | B: 243 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 243 | B: 243 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 243 | B: 243 | B: 153\r\n"
		+ "B: 234 | B: 234 | B: 239 | B: 243 | B: 243 | B: 153",
		pg.toString(false)
				);
		
		pg.removeSeam(true);
		assertEquals(
				pg.getWidth(), 5);
	}

	@Test
	void testUndo() {
		PixelGraph pg = new PixelGraph("cool.png");
		pg.colorSeam(Color.RED, pg.findLowestSeam(true));
		pg.removeSeam(false); // 7 cols left
		pg.colorSeam(Color.RED, pg.findLowestSeam(true));
		pg.removeSeam(false); // 6 cols left
		pg.colorSeam(Color.RED, pg.findLowestSeam(true));
		pg.removeSeam(false);// 5 cols left
		
		assertEquals(pg.getWidth(), 5);
		pg.undo();
		assertEquals(pg.getWidth(), 6);
		pg.undo();
		assertEquals(pg.getWidth(), 7);
		pg.undo();
		assertEquals(pg.getWidth(), 8);
	}

	@Test
	void testGetWidth() {
		PixelGraph pg = new PixelGraph("cool.png");
		assertEquals(pg.getWidth(), 8);
	}

}
