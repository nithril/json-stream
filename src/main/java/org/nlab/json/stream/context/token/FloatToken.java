package org.nlab.json.stream.context.token;

/**
 * Created by nlabrot on 19/04/16.
 */
public class FloatToken extends LiteralToken<Float> {

    private final float value;

    public FloatToken(float f) {
        value = f;
    }

    @Override
    public Float getLiteral() {
        return value;
    }
}
