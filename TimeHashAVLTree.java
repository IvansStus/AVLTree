package time_data_structures;
import data_structures.AVLTree;
import data_structures.Hash;
import data_structures.LinkedList;
import dns_resolver.IPAddress;
import dns_resolver.URL;
import java.util.HashMap;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TimeHashAVLTree {

	static LinkedList<IPAddress> list;	//Initialize data structures being used
	static Hash<IPAddress, URL> hash; 
	static AVLTree<IPAddress, URL> tree;
	static HashMap<IPAddress, URL> map;
	static TreeMap<IPAddress, URL> treemap;
	public static String filename = ("src/time_data_structures/top-250k.ip"); //250K file

	public TimeHashAVLTree() { //Constructor
		list = new LinkedList<IPAddress>();
		hash = new Hash<IPAddress, URL>(1);
		tree = new AVLTree<IPAddress, URL>();
		map = new HashMap<IPAddress, URL>();
		treemap = new TreeMap<IPAddress, URL>();
	}	

	public static LinkedList<IPAddress> listLoader(String filename) throws IOException {	
		BufferedReader in = new BufferedReader(new FileReader(filename)); //Read file

		String line;		
		String[] values;

		LinkedList<IPAddress> list = new LinkedList<IPAddress>();	//Call list

		try {
			while((line = in.readLine()) != null) {
				values = line.split("\t");	//Split 
				IPAddress ip = new IPAddress(values[1]);	//Store ips
				list.add(ip);	//Add ips		
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public static Hash<IPAddress, URL> loadHash(String filename) throws IOException {

		long start = System.currentTimeMillis(); //Start time

		BufferedReader in = new BufferedReader(new FileReader(filename));	

		String line;
		String[] values;

		Hash<IPAddress, URL> return_hash = new Hash<IPAddress, URL>(1);

		try {
			while ((line = in.readLine()) != null) {
				values = line.split("\t");
				URL url = new URL(values[0]);	//Store urls
				IPAddress ip = new IPAddress(values[1]);
				return_hash.add(ip, url);	//Add urls
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		long stop = System.currentTimeMillis();	//End time
		System.out.println("HashList Add Time: " + (stop - start));
		return return_hash;
	}

	public static void searchHash(LinkedList<IPAddress> list, Hash<IPAddress, URL> hash) {

		long start = System.currentTimeMillis();	//Start time	

		for (IPAddress ip : list) {	//Loop thru ips and search hash for ip addresses
			hash.contains(ip);
		}

		long stop = System.currentTimeMillis();	//End time
		System.out.println("HashList Search Time: " + (stop - start));
	}

	
	public static AVLTree<IPAddress, URL> loadTree(String filename) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(filename));	

		long start = System.currentTimeMillis();		

		String line;
		String values[];

		AVLTree<IPAddress, URL> return_tree = new AVLTree<IPAddress, URL>();

		try {
			while ((line = in.readLine()) != null) {
				values = line.split("\t");
				URL url = new URL(values[0]);
				IPAddress ip = new IPAddress(values[1]);
				return_tree.add(ip, url);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		long stop = System.currentTimeMillis();
		System.out.println("AVLTree Add Time: " + (stop - start));
		return return_tree;
	}

	public static void searchAVLTree(LinkedList<IPAddress> list, AVLTree<IPAddress, URL> tree) {

		long start = System.currentTimeMillis();		

		for (IPAddress ip : list) {
			tree.contains(ip);
		}

		long stop = System.currentTimeMillis();
		System.out.println("AVLTree Search Time: " + (stop - start));
	}


	public static HashMap<IPAddress, URL> loadHashMap(String filename) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(filename));	

		long start = System.currentTimeMillis();		

		String line;
		String values[];

		HashMap<IPAddress, URL> return_map = new HashMap<IPAddress, URL>();

		try {
			while ((line = in.readLine()) != null) {
				values = line.split("\t");
				URL url = new URL(values[0]);
				IPAddress ip = new IPAddress(values[1]);
				return_map.put(ip, url);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		long stop = System.currentTimeMillis();
		System.out.println("HashMap Add Time: " + (stop - start));
		return return_map;
	}

	public static void searchHashMap(LinkedList<IPAddress> list, HashMap<IPAddress, URL> map) {

		long start = System.currentTimeMillis();		

		for (IPAddress ip : list) {
			map.containsValue(ip);
		}

		long stop = System.currentTimeMillis();
		System.out.println("HashMap Search Time: " + (stop - start));
	}


	public static TreeMap<IPAddress, URL> loadTreeMap(String filename) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(filename));	

		long start = System.currentTimeMillis();		

		String line;
		String values[];

		TreeMap<IPAddress, URL> return_treemap = new TreeMap<IPAddress, URL>();

		try {
			while ((line = in.readLine()) != null) {
				values = line.split("\t");
				URL url = new URL(values[0]);
				IPAddress ip = new IPAddress(values[1]);
				return_treemap.put(ip, url);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		long stop = System.currentTimeMillis();
		System.out.println("TreeMap Add Time: " + (stop - start));
		return return_treemap;
	}

	public static void searchTreeMap(LinkedList<IPAddress> list, TreeMap<IPAddress, URL> treemap) {

		long start = System.currentTimeMillis();		

		for (IPAddress ip : list) {
			treemap.containsValue(ip);
		}

		long stop = System.currentTimeMillis();
		System.out.println("TreeMap Search Time: " + (stop - start));
	}


	public static void main(String[] args) throws IOException {	
		//		loadHash(filename);	//Broken Hash :(
				loadTree(filename);	//StackOverflow when adding :(
		loadHashMap(filename);	//Works
		loadTreeMap(filename);	//Works
		//		searchHash(list, hash);	//Broken Hash :(
		//		searchAVLTree(list, tree);	//Broken 
		//searchHashMap(list, map);	//Nullpointer...
		//searchTreeMap(list, treemap);	//Nullpointer
	}	//Sadly didn't have enough time to fully finish these, but I got the correct ideas down I hope, If my hash was functioning correctly and my AVLtree height method then it would have been a different story :(

}
