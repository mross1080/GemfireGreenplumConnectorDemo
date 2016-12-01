export CLASSPATH=$CLASSPATH:/home/gpadmin/gemfire-greenplum-2.1.1-beta.jar

# Issue commands to gfsh to start locator and launch a server
echo "Starting locator and server..."
gfsh <<!
connect

start locator --name=loc1 --include-system-classpath

start server --name=s1 --cache-xml-file=server-cache.xml --include-system-classpath --classpath=/home/gpadmin/gemfire-greenplum-2.1.1-beta.jar


# change the name/ location of this jar file depending on where you launch this script
deploy --jar=/home/gpadmin/gemfire-server-0.0.1-SNAPSHOT.jar
deploy --jar=/home/gpadmin/domain-0.0.1-SNAPSHOT.jar



list members;
list regions;
!
