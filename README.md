## Demo Overview

This demo details an environment setup and demonstrates the
GemFire-Greenplum Connector ability to copy data from GPDB to
GemFire and from GemFire to GPDB.  If anything the most important file in this project is `GemfireClientApplicationTests.java`.  This is a file under the `gemfire-client` sub project, and this file will run two separate integration tests that interact with a GPDB instancce via JDBC and a Gemfire instance.  The tests will run pre and post assertions to prove out that running functions on the cluster will provide an expexted output.  

The demo runs `GPDB` and `GemFire 9.0` within a single `Centos 7` VM.  

On the GemFire side, there is 1 region: `Customer`.  Configuration for the regions is defined in the `cache.xml` file.  The `cache.xml` file is really where all the magic happens.  This is where you will define the mappings between the Gemfire Regions and the GPDB Tables.  

The mapping is as follows:

* `Customer` region -> `customer` table


## Prerequisites

1. Maven

2. The Gemfire-Greenplum connector jar
3. Gemfire 9.0
4. Greenplum 4.x


## Setting up the Environment
1. Lets start by getting Greenplum Setup.  Lets start up GPDB and then run the `setup_gpdb.sql` file to setup our tables and seed some data.

   ```
   gpstart
   cd setup_scripts
   psql -f setup_gpdb.sql
   ```
   
2.  The next important step is to start gpfdist in order for Gemfire and GPDB to have a channel to communicate.  (Set a custom port if you want with -p flag!  But default is 8000)
   ```
   gpfdist -p 8002
   ```
3. So now you have GPDB running, seeded with data, with the external protocol setup.  Lets startup Gemfire.  The Specific scripts I provided create one locator, one server, deploy a jar with the domain objects, and a jar with the functions(gemfire-server).  You're going to need to go into the script and change the absolute path to match where the jars are in your system.  
   ```
   . start_gemfire.sh
   ```

4. Verify that GemFire has the correct regions, and functions as defined in the `cache.xml` file.

    ```
    gfsh>list regions
    List of regions
    ---------------
     Customer
    
    gfsh>list functions
    Member | Function
    ------ | -------------------------------
    s1     | ClearRegionRemoveAllFunction
    s1     | ImportFromGPDBToGemfireFunction
    s1     | ImportFromGemfireToGPDBFunction

    ```
   
## Running Operations from GFSH

  1. You should now have a running singlenode Gemfire instance, and now we're ready to import some data from GPDB into Gemfire.  From gfsh
      ```
      gfsh> connect
      gfsh> import gpdb --region=/Customer
       ```
5. You should see a successful import message showing how many objects you imported.  

## Running The Operations Programmatically
As we all know, GFSH is just a client application itself, so what it's actually doing is triggering operations that happen on the server.  In a production enviornment we're probably going to want to know how to trigger the imports and exports via a function.

Lets check out the first function `ImportFromGemfireToGPDBFunction.java` it does one important thing
```
Region<?,?> region = cache.getRegion("Customer");
long numberOfResults = GpdbService.createOperation(region).exportRegion();
String result = "Successfully imported this many records : " + numberOfResults;
```
1. So what we need to is to deploy this function onto the server so you can run it yourself!  Lets start by building the package from the root, this will build the gemfire-server package with the functions and the domain package with the Domain Objects.


```
      mvn clean install
```

2.  This will build the objects you need to run the integration tests of the client, now start gemfire with new newly built jars.
   ```
   . start_gemfire.sh
   ```
3. You'll have everything you need deployed and now you can run the tests in Gemfire Client
   ```
   cd gemfire-client
   mvn test
   ```
4. This command will run the integration tests in `GemfireClientApplicationTests` that will prove the functionality of the gemfire greenplum connector.  