package io.pivotal.gemfire.client;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;


public class GemfireClientApplication {
	private static ClientCache cache;
	static {
		ClientCacheFactory ccf = new ClientCacheFactory();
		ccf.set("cache-xml-file", "clientCache.xml");
//    ccf.set("cache-xml-file", "/home/centos/clientCache.xml");
		System.out.println(new File(".").getAbsolutePath());
		cache = ccf.create();
	}
	public static void main(String[] args) {

		Region<String,Parent> region =  cache.getRegion("Parent");
		Parent p =  new Parent();

		p.setId(BigInteger.valueOf( 114455));
		p.setIncome(BigDecimal.valueOf( 1144355));
		p.setName("Bob Odenkirk");

		region.put("a",p);

		System.out.println("Put object");
		System.out.println("Getting object " + region.get("a"));



	}
}
