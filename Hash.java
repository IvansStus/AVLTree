package data_structures;

import java.util.Iterator;

/**
 * The Hash data structure has O(1) time complexity (best case) for add, remove, and find
 * for an object in the data structure. The methods in the Hash data structure are defined
 * by the HashI interface. The Hash consists of an array of Linked Lists,
 * the Linked Lists are defined by the HashListI interface.
 * 
 * @author Ivan Stus
 *
 * @param <K> The key for entries in the hash
 * @param <V> The value for entries in the hash
 */

public class Hash<K, V> implements HashI<K, V> {
	LinkedList<HashElement<K, V>> [] harray; //constructor
	int tableSize;
	int numElements;
	double maxLoadFactor;

	class HashElement<K, V> implements Comparable <HashElement<K, V>> {	//Comparable class constructor
		K key;
		V value;
		public HashElement(K key, V value) {
			this.key = key;
			this.value = value;
		}
		public int compareTo(HashElement<K, V> o) {
			return (((Comparable<K>)this.key).compareTo(o.key));
		}
	}

	public Hash(int tableSize) { //sets harray as a hash with size tableSize
		this.tableSize = tableSize;
		harray = (LinkedList<HashElement<K, V>>[]) new LinkedList[tableSize];
		for (int i = 0; i < tableSize; i++)
			harray[i] = new LinkedList<HashElement<K, V>>();
		maxLoadFactor = 0.75;
		numElements = 0;
	}

	/**  
	 * Adds the given key/value pair to the dictionary.  Returns 
	 * false if the dictionary is full, or if the key is a duplicate. 
	 * Returns true if addition succeeded. 
	 *  
	 * @param key the key to add
	 * @param value the value associated with the key
	 * @return true if the key/value are added to the hash.
	 */	
	@Override
	public boolean add(K key, V value) {
		if (loadFactor() > maxLoadFactor) {
			int newSize = tableSize * 2;
			resize(newSize);
		}
		HashElement<K, V> newhc = new HashElement<K, V>(key, value);
		int hashval = key.hashCode(); //The THING!
		hashval = hashval & 0x7FFFFFFF;
		hashval = hashval % tableSize;
		harray[hashval].add(newhc);
		numElements++;		
		return true;
	}

	/**
	 * Deletes the key/value pair identified by the key parameter. 
	 * Returns true if the key/value pair was found and removed, 
	 * otherwise returns false.
	 *  
	 * @param key
	 * @return true when done removing specified key
	 */
	@Override
	public boolean remove(K key) {
		HashElement<K, V> newhc = new HashElement<K, V>(key, null);
		int hashval = key.hashCode();
		hashval = hashval & 0x7FFFFFFF;
		hashval = hashval % tableSize;
		harray[hashval].remove(newhc);	
		return true;
	}

	/**
	 * Change the value associated with an existing key.
	 *
	 * @param key The key to change
	 * @param value The new value to assign to the key
	 * @return true if value changed, false if not
	 */	
	@Override
	public boolean changeValue(K key, V value) {
		int hashval = key.hashCode();
		hashval = hashval & 0x7FFFFFFF;
		hashval = hashval % tableSize;
		HashElement<K, V> newhash = new HashElement<K, V>(key, null);
		for (HashElement<K, V> newhash2 : harray[hashval]) {
			if (newhash.compareTo(newhash2) == 0) {
				newhash2.value = value;
				return true;
			}
		}
		return false;			
	}

	/**
	 * Test whether the hash has the entry associated with the key.
	 * Returns true if the key was found.
	 *
	 * @param key the key to look for
	 * @return whether it is there.
	 */	
	@Override
	public boolean contains(K key) {
		for (K key1 : this) {
			if (((Comparable<K>)key1).compareTo(key)==0) {
				return true;
			}

		}
		return false;
	}

	/**
	 * Returns the value associated with the parameter key. 
	 * Returns null if the key is not found or the dictionary is empty. 
	 *
	 * @param key the key to find the value for
	 * @return the value
	 */	
	@Override
	public V getValue(K key) {
		int hashval = key.hashCode();
		hashval = hashval & 0x7FFFFFFF;
		hashval = hashval % tableSize;
		for (HashElement<K, V>hc : harray[hashval]) {
			if (((Comparable<K>)hc.key).compareTo(key)==0)		
				return hc.value;
		}
		return null;
	}

	/**
	 * Returns the number of key/value pairs currently stored in the dictionary 
	 * @return tableSize
	 */	
	@Override
	public int size() {		
		return tableSize;
	}

	/**
	 * Returns true if the dictionary is empty 
	 *
	 * @return whether the dictionary is empty
	 */	
	@Override
	public boolean isEmpty() {		
		if (harray == null) {
			return true;
		}
		return false;
	}

	/**
	 * Make the dictionary empty
	 */	
	@Override
	public void makeEmpty() {
		harray = null;
		numElements = 0;
	}

	/**
	 * Returns the current load factor of the dictionary (lambda)
	 *
	 * @return the loadFactor
	 */	
	@Override
	public double loadFactor() {
		return ((double) numElements/harray.length);
	}

	/**
	 * Get the maximum load factor (at which point we need to resize)
	 *
	 * @return the maximum load factor of the hash
	 */	
	@Override
	public double getMaxLoadFactor() {		
		return this.maxLoadFactor;
	}

	/**
	 * Set the maximum load factor (at which point we need to resize)
	 *
	 * @param loadfactor the maximum load factor
	 */	
	@Override
	public void setMaxLoadFActor(double loadfactor) {
		this.maxLoadFactor = loadfactor;		
	}

	/**
	 * Resizes the dictionary
	 *
	 * @param newSize the size of the new dictionary
	 */	
	@Override
	public void resize(int newSize) {
		LinkedList<HashElement<K, V>> [] tmparray; 
		tmparray = (LinkedList<HashElement<K,V>> []) new LinkedList[newSize];
		for (int i = 0; i < newSize; i++) {
			tmparray[i] = new LinkedList<HashElement<K, V>>();
			for (K key : this) {
				V val = getValue(key);
				HashElement<K, V> newhc = new HashElement<K, V>(key, val);
				int hashval = key.hashCode();
				hashval = hashval & 0x7FFFFFFF;
				hashval = hashval % newSize;
				tmparray[hashval].add(newhc);
			}
			harray = tmparray;
			tableSize = newSize;
		}		
	}

	/**
	 * Returns an Iterator of the keys in the dictionary, in ascending 
	 * sorted order 
	 *
	 * @return an instance of an Iterator<K> inner class
	 */	
	@Override
	public Iterator<K> iterator() {
		return new keyIteratorHelper<K>();
	}

	class keyIteratorHelper<T> implements Iterator<T> {
		T[] keys;
		int posn;
		public keyIteratorHelper() {
			keys = (T[]) new Object[numElements];
			int counter = 0;
			for (int i = 0; i < tableSize; i++) {
				for (HashElement<K, V> hc : harray[i])
					keys[counter++] = (T)hc.key;
			}
			int min = 0;
			for (int i = 0; i < numElements - 1; i++) { //implementation of selection sort
				for (int j = i + 1; j < numElements; j++) { //nested for loop to loop through numElements
					if (((Comparable<T>)keys[j]).compareTo(keys[min]) < 0) { //if key is less than min, set as new min
						min = j;
					}
					T temp = keys[min];
					keys[min] = keys[j];
					keys[j] = temp;
				}
				posn = 0;
			}
		}
		@Override
		public boolean hasNext() {			
			return posn<keys.length;
		}

		@Override
		public T next() {
			if (!hasNext())
				return null;
			return keys[posn++];
		}

	}
}
