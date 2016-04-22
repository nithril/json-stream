package org.nlab.json.stream.predicate;


import org.nlab.json.stream.context.StreamContext;

/**
 * Created by nlabrot on 15/03/15.
 */
public class AllPredicate implements JsonPredicate {

    public static final AllPredicate INSTANCE = new AllPredicate();

    protected AllPredicate() {
    }

    @Override
    public boolean test(StreamContext staxContext) {
        return true;
    }

    @Override
    public String toString() {
        return "AllPredicate{}";
    }
}
