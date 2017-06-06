shopizer-erp-angularjs
# shopizer-erp-angularjs


Requires:
-------------------

- MongoDB (community edition)

- Java 8

- NodeJS (version 7)

- @angular/cli

Install nodes modules:
-------------------

At the root of shopizer-admin-ui type the following commands

npm install

bg build


Run services:
-------------------

Start mongodb : mongod --dbpath <yourpath>/mongodb/data/db --port 27010

shopizer-erp is a spring boot app and can de run from the command line or from eclipse IDE

- From the command line 

mvn clean install

java -Xmx512m -XX:MaxPermSize=128M -jar target/shopizer-erp-0.0.1-SNAPSHOT.jar

- From eclipse

Right click on ErpApplication -> Run As -> Java Application
