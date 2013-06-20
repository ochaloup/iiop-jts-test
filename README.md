
There is no parent project. Each subdirectory has to be build on its own.    
Client project does not share interfaces with server project (it's just quick&dirty).

Server
=======
- mvn clean package
- cp target/iiop-jts-server-1.0-SNAPSHOT.jar $JBOSS_HOME/standalone/deployments/
- check server to run with JTS transactions
  - under jacorb subsystem set transactions parameter from spec to on
  - under transactions subsystem add tag &lt;jts/&gt;
- run the server (./bin/standalone.sh -c standalone-full-jts.xml)

Client
=======
- mvn clean compile
- mvn exec:java 
  - for more logging add command line arg -Djava.util.logging.config.file=target/classes/logging.properties
  - for another class would be run -Dmain.class=org.jboss.qa.IIOPEJB2Testing


Just running with AS libraries:
-------------------------------
(running this does not work correctly probably)

     export JBOSS_HOME=...
     export MODULE_PATH=$JBOSS_HOME/modules/system/layers/base
     java -cp $MODULE_PATH/org/slf4j/main/slf4j-api-1.7.2-redhat-1.jar:$MODULE_PATH/org/jacorb/main/jacorb-2.3.2-redhat-4.jar:$MODULE_PATH/org/jboss/jts/main/jbossjts-jacorb-4.17.4.Final-redhat-2.jar:$MODULE_PATH/javax/ejb/api/main/jboss-ejb-api_3.1_spec-1.0.2.Final-redhat-2.jar:$MODULE_PATH/org/jboss/logging/main/jboss-logging-3.1.2.GA-redhat-1.jar:target/classes:. -Djava.util.logging.config.file=target/classes/logging.properties org.jboss.qa.IIOPTesting
