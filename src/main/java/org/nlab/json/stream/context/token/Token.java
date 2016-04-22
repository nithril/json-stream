package org.nlab.json.stream.context.token;

/**
 * Created by nlabrot on 19/04/16.
 */
public interface Token {

    Token getChildren();

    void setChildren(Token value);

    boolean isLiteral();

    default boolean isPreInit() {
        return false;
    }
}
