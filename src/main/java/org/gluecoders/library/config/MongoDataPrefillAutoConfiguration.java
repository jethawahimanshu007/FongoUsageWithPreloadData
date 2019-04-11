package org.gluecoders.library.config;

import com.codesnippets4all.json.parsers.JSONParser;
import com.codesnippets4all.json.parsers.JsonParserFactory;
import com.mongodb.util.JSON;
import org.apache.commons.io.FileUtils;
import org.bson.Document;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Configuration
@AutoConfigureAfter(value = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})

//@ConditionalOnResource(resources = "classpath:seeddata.json")

public class MongoDataPrefillAutoConfiguration {

    @Bean
    @Profile("fongo")
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, BeanFactory beanFactory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        try {
            mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        }
        catch (NoSuchBeanDefinitionException ignore) {}

        // Don't save _class to mongo
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return mappingConverter;
    }

    @Bean
    public Boolean preFill(MongoTemplate mongoTemplate) throws IOException {
        File file = new ClassPathResource("seeddata.json").getFile();

        JSONParser parser = getJsonParser();
        String jsonString = FileUtils.readFileToString(file, Charset.defaultCharset());

        Map<String, Object> jsonMap = parser.parseJson(jsonString);

        jsonMap.forEach((key, value) -> {
            List dbObjects = (List) jsonMap.get(key);

            for (Object dbObject : dbObjects) {
                Document document = new Document(parser.parseJson(JSON.serialize(dbObject)));
                mongoTemplate.insert(document, key);
            }
        });

        return true;
    }

    private JSONParser getJsonParser() {
        JsonParserFactory factory= JsonParserFactory.getInstance();
        return factory.newJsonParser();
    }

}
