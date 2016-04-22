package org.nlab.json.stream.context.token;

/**
 * Created by nlabrot on 19/04/16.
 */
public class NullToken extends LiteralToken<Void> {
    @Override
    public Void getLiteral() {
        return null;
    }
}
