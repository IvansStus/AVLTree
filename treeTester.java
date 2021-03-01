package tests;
import data_structures.AVLTree;
import time_data_structures.TimeHashAVLTree;

public class treeTester {	

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		tree.add(6, 1);		
		tree.add(3, 5);
		tree.add(8,12);
		tree.add(1,8);
		tree.add(5,11);	
		tree.add(7,11);	
		tree.add(9,11);	
		tree.add(10,11);		
		System.out.println(tree.height());
		tree.print();	
	}
}
