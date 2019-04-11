package org.gluecoders.library;

import org.gluecoders.library.config.DataExtractor;
import org.gluecoders.library.config.DataSet;

import java.util.ArrayList;
import java.util.Collection;

public class DataSetExtractor implements DataExtractor {
    public Object getData(Object obj) {
        DataSet set = (DataSet) obj;
        Collection<Object> data = new ArrayList<Object>();

        for (Object o : set.getBooks()) {
            data.add(o);
        }
        return data;
    }
}