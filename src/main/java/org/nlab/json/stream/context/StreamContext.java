package org.nlab.json.stream.context;

import java.util.Stack;

import org.nlab.json.stream.JsonStream;
import org.nlab.json.stream.JsonStreamSpec;
import org.nlab.json.stream.consumer.JsonConsumer;
import org.nlab.json.stream.context.token.ArrayToken;
import org.nlab.json.stream.context.token.LiteralToken;
import org.nlab.json.stream.context.token.ObjectToken;
import org.nlab.json.stream.context.token.RootToken;
import org.nlab.json.stream.context.token.Token;
import org.nlab.json.stream.reader.JsonMatcherStreamReader;

import com.fasterxml.jackson.core.JsonToken;

/**
 * Created by nlabrot on 17/04/16.
 */
public class StreamContext {

    protected JsonToken jsonToken;
    protected Token currentToken;
    protected final Stack<Token> elements;
    protected final JsonMatcherStreamReader streamReader;

    public StreamContext(JsonMatcherStreamReader streamReader) {
        this.streamReader = streamReader;
        this.elements = new Stack<>();
        this.currentToken = new RootToken();
        this.elements.push(currentToken);
    }

    public Token getCurrentToken() {
        return currentToken;
    }


    public Stack<Token> getElements() {
        return elements;
    }

    public void pop() {
        elements.pop();
        currentToken = elements.peek();
    }

    public void start(Token newElem) {
        currentToken.setChildren(newElem);
        currentToken = newElem;
        elements.push(newElem);
    }

    public void setLiteralValue(Token token) {
        popLiteral();
        start(token);
    }

    public void popLiteral() {
        if (getElements().peek().isLiteral()) {
            pop();
        }
    }

    public String toPath() {

        StringBuilder builder = new StringBuilder();

        for (Token element : elements) {

            if (element instanceof RootToken) {
                builder.append("$");
            } else if (element instanceof ArrayToken) {
                builder.append('[').append(((ArrayToken) element).getIndex()).append("]");
            } else if (element instanceof ObjectToken) {
                builder.append(".").append(((ObjectToken) element).getKey());
            } else if (element instanceof LiteralToken) {
                builder.append("#").append(((LiteralToken) element).getLiteral());
            } else {
                builder.append("$").append(element.getChildren());
            }

        }
        return builder.toString();

    }

    public JsonMatcherStreamReader getStreamReader() {
        return streamReader;
    }

    public JsonToken getJsonToken() {
        return jsonToken;
    }

    public void setJsonToken(JsonToken jsonToken) {
        this.jsonToken = jsonToken;
    }

    public JsonStream partialStream() {
        return new JsonStreamSpec(streamReader).partial().uncheckedStream();
    }

    public JsonConsumer partialConsumer() {
        return new JsonStreamSpec(streamReader).partial().uncheckedConsumer();
    }

    @Override
    public String toString() {
        return "StreamContext{" +
                "currentToken=" + currentToken +
                ", elements=" + elements +
                '}';
    }

}
