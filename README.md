## Demo Overview

This demo details an environment setup and demonstrates the
GemFire-Greenplum Connector ability to copy data from GPDB to
GemFire and from GemFire to GPDB.

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

1. Verify that GemFire has the correct regions, and functions as defined in the `cache.xml` file.

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

