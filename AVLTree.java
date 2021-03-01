/**
 * 
 */
package data_structures;

import java.util.Iterator;

/**
 * @author Ivan Stus
 *
 */
public class AVLTree<K, V> implements AVLTreeI<K, V> {	

	class Node<K, V> implements Comparable <Node<K, V>> {
		K key;	//Initialize variables used in tree
		V value;
		Node<K, V> left;
		Node<K, V> right;
		Node<K, V> parent;
		public Node(K key, V value) {	//Constructor 
			this.key = key;
			this.value = value;
			parent = left = right = null;			
		}
		public int compareTo(Node<K, V> o) {	//CompareTo method for comparable		
			return (((Comparable<K>)this.key).compareTo(o.key));
		}
	}

	private Node<K, V> root; //Initialize node and size of tree
	private int currentSize;

	public AVLTree() {	//Constructor for tree
		root = null;
		currentSize = 0;
	}

	/**
	 * The method to add to the AVLTree.  It will not allow duplicate additions.
	 * @param key the key to add
	 * @param value the value associated with the key
	 */
	@Override
	public void add(K key, V value) {
		Node<K, V> newNode = new Node<K, V>(key, value);
		if (root == null) {
			root = newNode;
		} else {
			add(root, newNode);	//Recursive method
		}					
	}

	private void add(Node<K, V> parent, Node<K, V> newNode) {	//Take in parent and newNode for recursive add	
		if (((Comparable<K>)newNode.key).compareTo(parent.key) > 0) { //Add to right subtree
			if (parent.right == null) {
				parent.right = newNode;
				newNode.parent = parent;
				currentSize++;
			} else {
				add(parent.right, newNode);
			}
		} else {
			if (((Comparable<K>)newNode.key).compareTo(parent.key) < 0) {	//Add to left subtree
				if (parent.left == null) {
					parent.left = newNode;
					newNode.parent = parent;
					currentSize++;
				} else {
					add(parent.left, newNode);
				}
			}
		}
		rotate(newNode);	//After node is added, tree needs to be checked for balance and/or rotated
	}	

	/**
	 * Tests whether the AVLTree contains the key
	 * @param key the key to look for
	 * @return whether the key is found
	 */
	@Override
	public boolean contains(K key) { 
		return getValue(key) != null;	//Contains uses getValue to check if key is in tree
	}

	/**
	 * Get the value associated with a given key
	 * @param key the key to get the value for
	 * @return the current value
	 */
	@Override
	public V getValue(K key) { 
		if (root == null)
			return null;
		Node<K, V> current = root;
		while (((Comparable<K>)current.key).compareTo((K)key) != 0) {
			if (((Comparable<K>)key).compareTo((K)current.key) < 0) 
				current = current.left;
			else 
				current = current.right;
			if (current == null)
				return null;
		}
		return current.value;
	}

	/**
	 * Returns the number of elements in the AVLTree
	 * @return the number of elements in the tree
	 */
	@Override
	public int size() {		
		return currentSize;
	}

	/**
	 * Test whether the AVLTree is empty
	 * @return <code>true</code> if the tree is empty
	 * 		   <code>false</code> if the tree is not empty 
	 */
	@Override
	public boolean isEmpty() {
		if (root == null) {
			return true;
		} else {
			return false;
		}		
	}

	/**
	 * The height of the tree. Recall that a tree with 
	 * only a root node has height 0. You will also need
	 * to have a private method that is not included here
	 * that overloads the height() method.
	 * @return the height of the tree at the root node
	 */
	@Override
	public int height() {		
		return height(root); //Takes height of overall tree with helper method as root being taken in
	}

	private int height(Node<K, V> n) {	//Root is taken in, goes through both subtrees
		if (n == null) 
			return 0;
		if (n.left == null && n.right == null) 		
			return 0;
		int leftHeight = 0;
		int rightHeight = 0;
		if (n.left != null) 
			leftHeight = height(n.left) + 1;
		if (n.right != null)
			rightHeight = height(n.right) + 1;
		if (rightHeight > leftHeight)
			return rightHeight;
		return leftHeight;		
	}

	/**
	 * An iterator for all the keys in the AVLTree. This will
	 * iterate over the keys using <b>InOrder Traversal</b>
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<K> iterator() {
		return new keyIteratorHelper<K>();
	}

	class keyIteratorHelper<T> implements Iterator<T> {
		T[] keys;
		int posn = 0;
		int counter = 0;
		public keyIteratorHelper() {
			keys = (T[]) new Object[currentSize];
			counter = 0;
			inOrder(root);
		}

		private void inOrder(Node<K, V> current) {	//Iterator gets tree values stored inorder inside array
			if (current != null) {
				inOrder(current.left);
				keys[counter++] = (T) current.key;
				inOrder(current.right);
			}
		}

		public boolean hasNext() {
			return posn<keys.length;
		}

		public T next() {
			if (!hasNext())				
				return null;
			return keys[posn++];
		}		
	}

	/**
	 * Recursively print the tree. This method should print the
	 * entire tree using <em>Inorder Traversal</em> to the standard
	 * output (i.e. using System.out.println or System.out.print).
	 * You can print the tree one node per line, and use periods to
	 * note the hierarchy of the tree.
	 */
	@Override
	public void print() {
		print(root);	//print method is recursive and takes in root as parameter
	}	

	private void print(Node<K, V> n) {	
		if (n == null)
			return;
		if (n.left != null) 			
			print(n.left);	//Recursive left case			
		if (n == root)
			System.out.println("Key: " + n.key + " Value: " + n.value + " (Root)");	//Add "root" for root
		else
			System.out.println("Key: " + n.key + " Value: " + n.value);	//Regular printing for nodes of tree with values 
		if (n.right != null)
			print(n.right);	//Recursive right case			
	}

	private void rotate(Node<K, V> n) {	//Rotate, also known as checkBalance, Checks to see if tree is unbalanced, calls reBalance if it is
		if ((height(n.left) - height(n.right) > 1) || (height(n.left) - height(n.right) < -1)) {
			rebalance(n);
		}
		if (n.parent == null)
			return;
		rotate(n.parent);
	}

	private void rebalance(Node<K, V> n) {	//Checks cases for what rotations to be used	
		if (height(n.left) - height(n.right) > 1) {
			if (height(n.left.left) > height(n.left.right))
				n = rightRotation(n);
			else
				n = leftRightRotation(n);
		}
		else {
			if (height(n.right) - height(n.left) > 1) { 
				if (height(n.right.right) > height(n.right.left))
					n = leftRotation(n);
				else
					n = rightLeftRotation(n);
			}
		}
		if (n.parent == null)
			root = n;		
	}

	private Node<K, V> leftRotation(Node<K, V> n) {
		Node<K, V> newTop = n.right;
		n.right = newTop.left;
		newTop.left = n;
		if (n.parent != null) {	//I was stuck on this for a while
			if (n.parent.left == n)	//Parent pointers had to be stored and updated appropriately
				n.parent.left = newTop;
			else 
				n.parent.right = newTop;		
		}
		newTop.parent = n.parent;
		n.parent = newTop;
		return newTop;
	}

	private Node<K, V> rightRotation(Node<K, V> n) {
		Node<K, V> newTop = n.left;
		n.left = newTop.right;
		newTop.right = n;	
		if (n.parent != null) {
			if (n.parent.left == n)
				n.parent.left = newTop;
			else 
				n.parent.right = newTop;
		}
		newTop.parent = n.parent;
		n.parent = newTop;
		return newTop;
	}

	private Node<K, V> leftRightRotation(Node<K, V> n) {		
		n.left = leftRotation(n.left);
		return rightRotation(n);		
	}

	private Node<K, V> rightLeftRotation(Node<K, V> n) {		
		n.right = rightRotation(n.right);		
		return leftRotation(n);
	}
}
