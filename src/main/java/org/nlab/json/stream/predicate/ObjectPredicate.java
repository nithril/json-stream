package org.nlab.json.stream.predicate;

import java.util.Arrays;


import org.nlab.json.stream.context.StreamContext;
import org.nlab.json.stream.context.token.ObjectToken;

import com.fasterxml.jackson.core.JsonToken;


/**
 * Created by nlabrot on 15/03/15.
 */
public class ObjectPredicate implements JsonPredicate {

    private final String[] keys;


    public ObjectPredicate(String... keys) {
        this.keys = keys;
    }

    @Override
    public boolean test(StreamContext context) {
        if (JsonToken.FIELD_NAME != context.getJsonToken()) {
            return false;
        }

        for (String key : keys) {
            if (key.equals(((ObjectToken)context.getCurrentToken()).getKey())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "ObjectPredicate{" +
                "keys=" + Arrays.toString(keys) +
                '}';
    }
}
