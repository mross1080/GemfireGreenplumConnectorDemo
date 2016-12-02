package io.pivotal.gemfire.client;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import io.pivotal.demo.entity.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class GemfireClientApplicationTests {

    private  static ClientCache cache;

    static {
		ClientCacheFactory ccf = new ClientCacheFactory();
    	ccf.set("cache-xml-file", "clientCache.xml");
		System.out.println(new File(".").getAbsolutePath());
		cache = ccf.create();

    }


    @Before
    public void setup() {
        String sql = "drop table if exists customer;create table customer ( "
                + 	" id	text,"
                +	" name text,"
                + " income	int"
                +	");";


        executeSQL(sql); // CREATE TABLE

    }

    @Test
    public void testRunImportFromGPDBToGemfire() {

       String  sql = "INSERT INTO customer VALUES ('19', 'Alice',  123456.0);";
        executeSQL(sql);

        Region<String,Customer> region = cache.getRegion("Customer");
        assertTrue(!region.containsKey("19"));

        executeImportFromGPDBToGemfire();
        assertNotNull(region.containsKey("19"));




    }


    @Test
    public void testRunImportFromGemfireToGPDB() {

        Customer customer = new Customer();
        customer.setName("Bobby O Hoolihan");
        customer.setId("1245");
        customer.setIncome(1221000);

        Region<String,Customer> region = cache.getRegion("Customer");
        region.put(customer.getId(),customer);


        String sql = "select * from customer where id='1245'";
        assertEquals(0,executeQuerySQL(sql));

        executeImportFromGemfireToGPDB();

        assertEquals(1,executeQuerySQL(sql));


    }


    @After
    public static void tearDown() {

//        Region<?,?> customers = cache.getRegion("Customer");
//        customers.clear();


    }


    private int executeQuerySQL(String sql) {
        Connection c = null;
        Statement stmt = null;
        int resultsCount = 0;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/demo",
                            "postgres", "123");
            stmt = c.createStatement();


            ResultSet rs = stmt.executeQuery(sql );

            while ( rs.next() ) {
                resultsCount++;
                String id = rs.getString("id");
                String  name = rs.getString("name");
                int income = rs.getInt ("income");
                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "income = " + income );

                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
            System.out.println("query executed successfully");

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.out.println("there was a problem");
        }

        return  resultsCount;

    }




    private void executeImportFromGemfireToGPDB() {


        Region<?,?> customers = cache.getRegion("Customer");

        ResultCollector<?, ?> rc = FunctionService.onRegion(customers)
                .execute("ImportFromGemfireToGPDBFunction");
        Object result = rc.getResult();
        if (!(result instanceof List<?>)) {
            fail("Something other than a List was returned");
        }
        System.out.println(result.toString());

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
            System.out.println("update successfully");

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.out.println("there was a problem");
        }
    }

}
