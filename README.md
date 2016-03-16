# CodeGraph
Visualisation of jdepend dependency analysis results using d3.js visualisation library

INSTALLATION:

- build the jar using maven

- create a property file containing:
sourceLocations=/home/rene/.m2/repository/org/springframework/spring-core/4.2.5.RELEASE/spring-core-4.2.5.RELEASE.jar
domainPackages=org.springframework

- $ java -jar codegraph-1.0-SNAPSHOT.jar --spring.config.location=file:/dir/your.properties

- open in browser: http://localhost:8080/view
