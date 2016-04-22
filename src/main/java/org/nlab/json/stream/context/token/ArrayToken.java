package org.nlab.json.stream.context.token;

/**
 * Created by nlabrot on 19/04/16.
 */
public class ArrayToken implements Token {

    private int currentIndex;
    private Token children;

    public ArrayToken() {
        currentIndex = -1;
        children = null;
    }

    public int getIndex() {
        return currentIndex;
    }

    public Token getChildren() {
        return children;
    }

    public void setChildren(Token elem) {
        ++currentIndex;
        children = elem;
    }

    public String toString() {
        return "Array[idx=" + currentIndex + "]";
    }

    @Override
    public boolean isPreInit() {
        return currentIndex == -1;
    }

    @Override
    public boolean isLiteral() {
        return false;
    }
}
