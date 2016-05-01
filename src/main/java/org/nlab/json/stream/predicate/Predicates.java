package org.nlab.json.stream.predicate;


import java.util.function.Predicate;

import org.nlab.json.stream.context.StreamContext;
import org.nlab.json.stream.jsonpath.JsonPath;

/**
 * Created by nlabrot on 15/03/15.
 */
public final class Predicates {

    private Predicates() {
    }

    public static Predicate<StreamContext> objects(String... elements) {
        return new ObjectPredicate(elements);
    }

    public static Predicate<StreamContext> jsonPath(String query) {
        return new JsonPathPredicate(JsonPath.parse(query));
    }

    public static Predicate<StreamContext> all() {
        return AllPredicate.INSTANCE;
    }
}
