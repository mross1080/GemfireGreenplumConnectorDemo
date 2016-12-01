package io.pivotal;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.execute.FunctionService;
import org.apache.geode.cache.execute.ResultCollector;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static io.pivotal.gemfire.gpdb.util.sql.GpdbDataType.float4;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GemfireApplicationTests {


//
//
//
//
//
//
//
//
//
//
//
//	private  static ClientCache cache;
//
//	static {
////		ClientCacheFactory ccf = new ClientCacheFactory();
////    	ccf.set("cache-xml-file", "clientCache.xml");
////		System.out.println(new File(".").getAbsolutePath());
////		cache = ccf.create();
//
//		 cache = new ClientCacheFactory().create();
//	}
//
//
//
//
//	@Test
//	public void testRunImport() {
//		String sql = "drop table if exists customer;create table customer ( "
//				+ 	" id	bigint,"
//				+	" name text,"
//				+ " income	bigint"
//				+	");";
//
//		executeSQL(sql); // CREATE TABLE
//		sql = "INSERT INTO test_customer VALUES (199, 'Alice',  123456.0);";
//		executeSQL(sql);
//
//		Region<String,Customer> region = cache.getRegion("Customer");
//		assertTrue(!region.containsKey(199));
//
//		executeImportFromGPDBToGemfire();
//		assertTrue(region.containsKey(199));
//
//
//
//
//	}
//
//	private void executeImportFromGPDBToGemfire() {
//
//
//		Region<?,?> customers = cache.getRegion("Customer");
//
//		ResultCollector<?, ?> rc = FunctionService.onRegion(customers)
//				.execute("ImportGPDBToGemfireFunction");
//		Object result = rc.getResult();
//		if (!(result instanceof List<?>)) {
//			fail("Something other than a List was returned");
//		}
//
//	}
//
//
//	private void executeSQL(String sql) {
//			Connection c = null;
//			Statement stmt = null;
//
//			try {
//				Class.forName("org.postgresql.Driver");
//				c = DriverManager
//						.getConnection("jdbc:postgresql://localhost:5432/testdb",
//								"postgres", "123");
//				stmt = c.createStatement();
//
//				stmt.executeUpdate(sql);
//				stmt.close();
//				c.close();
//			} catch ( Exception e ) {
//				System.err.println( e.getClass().getName()+": "+ e.getMessage() );
//				System.exit(0);
//			}
//			System.out.println("Table created successfully");
//		}
//

}
