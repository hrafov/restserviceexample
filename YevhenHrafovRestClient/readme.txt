- files in client directory- .\files : 
    -cover.jpg       
    -coverfromclient.jpg
    -selenium-server-standalone-3.12.0.jar
    
- files in service directory - src/main/java/servicfiles : 
    -cover1.jpg
    -selenium-server-standalone-3.12.0.jar 
   
- i try to do in with MTOM but there is bug in jdk1.8_45 :
          - https://bugs.openjdk.java.net/browse/JDK-8166600
            
- to start service you should perform following steps (from Service project):
    mvn clean install, mvn tomcat7:run-war
- to see results (from Client project):    
    mvn clean test 
      
You can see results also in target/surefire-reports/html/index.html             