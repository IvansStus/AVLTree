/**
 * 
 */
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import data_structures.LinkedList.Iteratorhelper;
import data_structures.LinkedList.Node;

/**
 * The linked list for our hash will only implement the
 * methods in the HashListI interface, a reduced set of
 * methods compared to the linked list from Assignment 1.
 * 
 * @author Ivan Stus
 *
 */
public class LinkedList<E> implements HashListI<E> {

	public class Node<E> { //constructor
		Node<E> next;
		E data;
		public Node(E obj) {
			data = obj;
			next = null;
		}
	}

	private Node<E> head; //initialize basic parts of linkedlist no tail pointer	
	private int currentSize;	

	/**
	 * Add an object to the list at this position
	 * @param obj - the thing to be added
	 */
	@Override
	public void add(E obj) {
		Node<E> newNode = new Node<E>(obj);
		newNode.next = head;
		head = newNode;
		currentSize++;		
	}

	/**
	 * Remove an object from the list
	 * @param obj The object to remove
	 * @return The object removed
	 */
	@Override
	public E remove(E obj) {
		Node<E> current = head;
		Node<E> previous = null;
		while (current != null) {
			if (((Comparable<E>)current.data).compareTo(obj) == 0) {
				if (current == head) 
					return null;				
				currentSize--;
				previous.next = current.next;
				return current.data;
			}
			previous = current;
			current = current.next;
		}
		return null;
	}

	/**
	 * Make the list empty
	 */
	@Override
	public void makeEmpty() {
		head = null;
		currentSize = 0;		
	}

	/**
	 * Is the list empty?
	 * @return true if the list is empty
	 */
	@Override
	public boolean isEmpty() {
		return (head == null);
	}

	/**
	 * The current number of elements in the list
	 * @return the size of the llist
	 */
	@Override
	public int size() {
		return currentSize;
	}

	/**
	 * Does the list contain this object
	 * @param obj The object to look for
	 * @return True if the list contains it.
	 */
	@Override
	public boolean contains(E obj) {
		Node <E> tmp = head;
		while (tmp != null) {
			if (((Comparable<E>)tmp.data).compareTo(obj)==0) 
				return true;
			tmp = tmp.next;			
		}
		return false;
	}

	/**
	 * An iterator for the list
	 */
	@Override
	public Iterator<E> iterator() {
		return new Iteratorhelper();		
	}

	class Iteratorhelper implements Iterator<E> {
		Node<E> index;	
		Node<E> tmp;
		public Iteratorhelper() {
			index = head;
		}

		public boolean hasNext() {
			return index != null;
		}

		public E next() {
			if (!hasNext()) 
				throw new NoSuchElementException();
			E tmp = index.data;		
			index = index.next;						
			return tmp;
		}		
	}

}
