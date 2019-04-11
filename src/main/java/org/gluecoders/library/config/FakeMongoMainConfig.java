package org.gluecoders.library.config;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("fongo")
public class FakeMongoMainConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "mockDB";
    }

    @Bean
    public MongoClient mongo() {
        Fongo fongo = new Fongo("mockDB");
        return fongo.getMongo();
    }

    @Bean
    /*@Profile("fongo-repositorypopulator")*/
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
        /*Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[] { new ClassPathResource("books.json")});*/
        Jackson2RepositoryPopulatorFactoryBean factory = new CustomRepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[] { new ClassPathResource("books.json")});
        return factory;
    }
}

