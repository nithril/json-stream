package org.nlab.json.stream.reader;

import java.io.IOException;

import org.nlab.json.stream.context.StreamContext;
import org.nlab.json.stream.context.token.ArrayToken;
import org.nlab.json.stream.context.token.BooleanToken;
import org.nlab.json.stream.context.token.FloatToken;
import org.nlab.json.stream.context.token.IntToken;
import org.nlab.json.stream.context.token.NullToken;
import org.nlab.json.stream.context.token.ObjectToken;
import org.nlab.json.stream.context.token.StringToken;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserDelegate;

/**
 * Created by nlabrot on 16/04/16.
 */
public class JsonMatcherStreamReader extends JsonParserDelegate {

    private StreamContext context;

    public JsonMatcherStreamReader(JsonParser parser) {
        super(parser);

        context = new StreamContext(this);
    }


    @Override
    public JsonToken nextToken() throws IOException {

        JsonToken nextToken = super.nextToken();

        if (nextToken == null){
            return null;
        }

        context.setJsonToken(nextToken);

        switch (nextToken) {
            case START_ARRAY: {
                context.popLiteral();
                context.start(new ArrayToken());
                break;
            }
            case END_ARRAY: {
                context.popLiteral();
                context.pop();
                break;
            }
            case VALUE_EMBEDDED_OBJECT:
            case START_OBJECT: {
                context.popLiteral();
                context.start(new ObjectToken());
                break;
            }
            case END_OBJECT: {
                context.popLiteral();
                context.pop();
                break;
            }
            case FIELD_NAME: {
                context.popLiteral();
                ((ObjectToken)context.getCurrentToken()).reset(this.getText());
                break;
            }
            case VALUE_TRUE:
            case VALUE_FALSE: {
                context.setLiteralValue(new BooleanToken(getBooleanValue()));

                break;
            }
            case VALUE_NUMBER_INT:{
                context.setLiteralValue(new IntToken(getIntValue()));
                break;
            }
            case VALUE_NUMBER_FLOAT: {
                context.setLiteralValue(new FloatToken(getFloatValue()));
                break;
            }
            case VALUE_STRING: {
                context.setLiteralValue(new StringToken(getText()));
                break;
            }
            case VALUE_NULL: {
                context.setLiteralValue(new NullToken());
                break;
            }
            default:
                assert false;
        }

        return nextToken;
    }

    public StreamContext getStreamContext() {
        return context;
    }






}
