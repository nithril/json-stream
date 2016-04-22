package org.nlab.json.stream.context.token;

/**
 * Created by nlabrot on 19/04/16.
 */
public class ObjectToken implements Token {
    public static final String KEY_UNINIT = "UNINIT";
    public static final Token VALUE_UNINIT = new StringToken("UNINIT");

    private String key;
    private Token value;

    public ObjectToken() {
        key = KEY_UNINIT;
        value = VALUE_UNINIT;
    }

    public String getKey() {
        return key;
    }

    public Token getChildren() {
        return value;
    }

    public void setChildren(Token elem) {
        value = elem;
    }


    public void reset(String key){
        this.key = key;
        this.value = VALUE_UNINIT;
    }

    @Override
    public boolean isPreInit() {
        return !isKeyInit() && !isValueInit();
    }

    @Override
    public boolean isLiteral() {
        return false;
    }

    public boolean isKeyInit(){
        return key != KEY_UNINIT;
    }

    public boolean isValueInit(){
        return value != VALUE_UNINIT;
    }

    public String toString() {
        return "Object[key=" + key + "]";
    }

}


