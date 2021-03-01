package dns_resolver;

/**
 * A URL Object is a representation of the URL that we have been giving. 
 * It knows how to compare URLs!
 *
 * Note: The templates for some methods have been provided, but you should consider which additional
 * methods to add to this class.
 * 
 * @author CS310 Ivan Stus
 *
 */
public class URL implements Comparable<URL> {
	
	String url;
	
	public URL(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		return url.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return url.equals(obj);
	}

	@Override
	public String toString() {
		return url;
	}	

	@Override
	public int compareTo(URL o) {
		return (url.toString()).compareTo(this.url);
	}
}
