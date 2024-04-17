package project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;



public class Action {
		public Stack<Integer> colRemovedAt;
		
		
		public Stack<Pixel> removed;
		public Action (Stack<Pixel> seamRemoved, Stack<Integer> colRemovedAt) {
			this.removed = seamRemoved;
			
			this.colRemovedAt = colRemovedAt;
			
		}
		
		public Stack<Pixel> getRemoved(){
			return this.removed;
		}
		public Stack<Integer> getRemovedCols(){
			return this.colRemovedAt;
		}

}
