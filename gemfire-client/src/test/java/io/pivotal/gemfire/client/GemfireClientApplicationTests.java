package io.pivotal.gemfire.client;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import io.pivotal.demo.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//@ImportResource("classpath:config/sdg-context.xml")
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@SpringBootConfiguration
public class GemfireClientApplicationTests {

    private  static ClientCache cache;

    static {
		ClientCacheFactory ccf = new ClientCacheFactory();
    	ccf.set("cache-xml-file", "clientCache.xml");
		System.out.println(new File(".").getAbsolutePath());
		cache = ccf.create();

    }




    @Test
    public void testRunImport() {
        String sql = "drop table if exists customer;create table customer ( "
                + 	" id	text,"
                +	" name text,"
                + " income	bigint"
                +	");";

        executeSQL(sql); // CREATE TABLE
        sql = "INSERT INTO customer VALUES ('19', 'Alice',  123456.0);";
        executeSQL(sql);

        Region<String,Customer> region = cache.getRegion("Customer");
        assertTrue(!region.containsKey("19"));

        executeImportFromGPDBToGemfire();
        assertNotNull(region.containsKey("19"));


    }

    private void executeImportFromGPDBToGemfire() {


        Region<?,?> customers = cache.getRegion("Customer");

        ResultCollector<?, ?> rc = FunctionService.onRegion(customers)
                .execute("ImportFromGPDBToGemfireFunction");
        Object result = rc.getResult();
        if (!(result instanceof List<?>)) {
            fail("Something other than a List was returned");
        }
        System.out.println(result.toString());

    }


    private void executeSQL(String sql) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/demo",
                            "postgres", "123");
            stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

}
