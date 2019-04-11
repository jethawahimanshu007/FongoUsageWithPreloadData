package org.gluecoders.library.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gluecoders.library.DataSetExtractor;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2ResourceReader;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CustomJackson2ResourceReader extends Jackson2ResourceReader {
    private Class<?> entityClass = DataSet.class;
    private String typeKey = "_class";
    private ObjectMapper mapper;
    private DataExtractor extractor = new DataSetExtractor();

    public CustomJackson2ResourceReader(ObjectMapper mpr) {
        super(mpr);
        mapper = mpr;
    }
    @Override
    public Object readFrom(Resource resource, ClassLoader classLoader)
            throws Exception {

        InputStream stream = resource.getInputStream();
        JsonNode node = mapper.reader(JsonNode.class).readTree(stream);

        if (node.isArray()) {

            Iterator<JsonNode> elements = node.elements();
            List<Object> result = new ArrayList<Object>();

            while (elements.hasNext()) {
                JsonNode element = elements.next();
                result.add(readSingle(element, classLoader));
            }

            return result;
        }

        return readSingle(node, classLoader);
    }
    private Object readSingle(JsonNode node, ClassLoader classLoader)
            throws IOException {

        JsonNode typeNode = node.findValue(typeKey);
        String typeName = typeNode == null ? null : typeNode.asText();

        Class<?> type = ClassUtils.resolveClassName(typeName, classLoader);

        Object obj = mapper.reader(type).readValue(node);

        obj = type.equals(DataSet.class)  ?  extractor.getData(obj)  :  obj;

        return obj;
    }
}