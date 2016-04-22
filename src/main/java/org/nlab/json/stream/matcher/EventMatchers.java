package org.nlab.json.stream.matcher;

import org.apache.commons.lang3.Validate;
import org.jooq.lambda.fi.util.function.CheckedFunction;
import org.nlab.json.stream.context.StreamContext;
import org.nlab.json.stream.predicate.Predicates;


public final class EventMatchers {

    private EventMatchers() {
    }

    public static EventMatcher jsonPath(String query, CheckedFunction<StreamContext,Boolean> consumer){
        Validate.notBlank(query);
        return new EventMatcher(Predicates.jsonPath(query), consumer);
    }


    public static EventMatcher objects(String[] keys, CheckedFunction<StreamContext,Boolean> consumer){
        Validate.notEmpty(keys);
        return new EventMatcher(Predicates.elements(keys), consumer);
    }

    public static EventMatcher object(String key, CheckedFunction<StreamContext,Boolean> consumer){
        Validate.notBlank(key);
        return new EventMatcher(Predicates.elements(key), consumer);
    }


}
