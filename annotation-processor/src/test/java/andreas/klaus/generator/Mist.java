package andreas.klaus.generator;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Test;


public class Mist {

	@Test
	public void test() {
		
		System.err.println(ClasspathResourceLoader.class.getName());
	}
}
