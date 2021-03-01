package dns_resolver;

/**
 * The IPAddress is using iIPv4 and has dotted-decimal notation, with the network, two subnets, 
 * and host separated by periods. For example, the IP address 130.191.226.146 has 
 * a network of 130, a subnet of 191, the second subnet is 226, and the host address is 146.
 * 
 * Your IPAddress class should accept a string of dotted-decimal IPAddresses in the constructor
 * and separate them into the components. 
 *
 * Note: The templates for some methods have been provided, but you should consider which additional
 * methods to add to this class.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/IP_address#IPv4_addresses">Wikipedia IPv4 addresses</a>
 * @author CS310 Ivan Stus
 *
 */

public class IPAddress implements Comparable<IPAddress> {

	int network;
	int subnet;
	int subnet2;
	int host;
	String ip;

	/**
	 * The constructor for the IPAddress class
	 * 
	 * @param ip the dotted-decimal IP address
	 */
	public IPAddress(String ip) {
		this.ip = ip;
		String data[] = ip.split("\\.");
		network = Integer.parseInt(data[0]);
		subnet = Integer.parseInt(data[1]);
		subnet2 = Integer.parseInt(data[2]);
		host = Integer.parseInt(data[3]);
	}


	@Override
	public int hashCode() {
		return ip.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return ip.equals(obj);
	}

	@Override
	public String toString() {
		return ip;
	}

	@Override
	public int compareTo(IPAddress ip) {		
		return (ip.toString()).compareTo(this.ip);
	}

}
