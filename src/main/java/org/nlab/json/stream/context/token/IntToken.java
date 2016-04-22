package org.nlab.json.stream.context.token;

/**
 * Created by nlabrot on 19/04/16.
 */
public class IntToken extends LiteralToken<Integer> {

    private final int value;

    public IntToken(int f) {
        value = f;
    }

    public Token getChildren() {
        return null;
    }

    public void setChildren(Token elem) {
        throw new RuntimeException();
    }

    @Override
    public Integer getLiteral() {
        return value;
    }
}
