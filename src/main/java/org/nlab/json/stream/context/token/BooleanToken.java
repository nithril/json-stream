package org.nlab.json.stream.context.token;

/**
 * Created by nlabrot on 19/04/16.
 */
public class BooleanToken extends LiteralToken<Boolean> {

    private final boolean value;

    public BooleanToken(boolean f) {
        value = f;
    }

    @Override
    public Boolean getLiteral() {
        return value;
    }
}
