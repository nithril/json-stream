package org.nlab.json.stream.context.token;

/**
 * Created by nlabrot on 19/04/16.
 */
public abstract class LiteralToken<T> implements Token {

    @Override
    public boolean isLiteral() {
        return true;
    }

    @Override
    public boolean isPreInit() {
        return false;
    }

    @Override
    public void setChildren(Token elem) {
        throw new RuntimeException();
    }

    @Override
    public Token getChildren() {
        return null;
    }


    public abstract T getLiteral();
}
