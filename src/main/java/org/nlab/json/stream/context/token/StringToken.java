package org.nlab.json.stream.context.token;

/**
 * Created by nlabrot on 19/04/16.
 */
public class StringToken extends LiteralToken<String> {

    private final String value;

    public StringToken(String s) {
        value = s;
    }

    @Override
    public String getLiteral() {
        return value;
    }
}
