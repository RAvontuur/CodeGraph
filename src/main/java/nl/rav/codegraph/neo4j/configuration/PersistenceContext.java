package nl.rav.codegraph.neo4j.configuration;

import org.neo4j.ogm.MetaData;
import org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver;
import org.neo4j.ogm.session.Neo4jSession;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("nl.rav.codegraph.neo4j")
@EnableNeo4jRepositories("nl.rav.codegraph.neo4j.repository")
public class PersistenceContext extends Neo4jConfiguration {

    public org.neo4j.ogm.config.Configuration getConfiguration() {
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config
                .driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver")
                .setURI("file:///home/rene/CodeGraph/target/jqassistant/store");
        return config;
    }

    /*
      for unknown reason getSessionFactory ignores the Embedded Driver on our classpath
      in this way we force creating a session using the embedded driver
     */
    @Bean
    public Session getSession() throws Exception {
        return new Neo4jSession(new MetaData("nl.rav.codegraph.neo4j.domain"), new EmbeddedDriver(getConfiguration().driverConfiguration()));
    }

    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "nl.rav.codegraph.neo4j.domain");
    }
}