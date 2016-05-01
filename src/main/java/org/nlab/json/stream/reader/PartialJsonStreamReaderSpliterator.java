package org.nlab.json.stream.reader;

import java.util.Spliterator;
import java.util.function.Consumer;

import org.nlab.exception.UncheckedExecutionException;
import org.nlab.json.stream.context.StreamContext;
import org.nlab.json.stream.context.token.ArrayToken;
import org.nlab.json.stream.context.token.ObjectToken;
import org.nlab.json.stream.context.token.Token;

import com.fasterxml.jackson.core.JsonToken;

/**
 * Created by nlabrot on 08/12/15.
 */
public class PartialJsonStreamReaderSpliterator implements Spliterator<StreamContext> {

    private final JsonMatcherStreamReader jsonMatcherReader;
    private final int depth;
    private final Token token;

    private boolean eoi = false;

    private String key;
    private int index;


    public PartialJsonStreamReaderSpliterator(JsonMatcherStreamReader jsonMatcherReader) {
        this.jsonMatcherReader = jsonMatcherReader;
        this.depth = jsonMatcherReader.getStreamContext().getElements().size();
        this.token = jsonMatcherReader.getStreamContext().getElements().peek();

        if (this.token instanceof ObjectToken){
            key = ((ObjectToken) this.token).getKey();
        }else if (this.token instanceof ArrayToken){
            index = ((ArrayToken) this.token).getIndex();
        }

    }

    @Override
    public boolean tryAdvance(Consumer<? super StreamContext> action) {
        try {
            if (eoi) {
                return false;
            }

            if (jsonMatcherReader.isClosed()) {
                return false;
            }

            if (jsonMatcherReader.nextToken() == null) {
                return false;
            }

            action.accept(jsonMatcherReader.getStreamContext());

            if (jsonMatcherReader.getStreamContext().getElements().size() == depth){

                if (token instanceof ObjectToken){
                    if (((ObjectToken) token).getKey().equals(key)){
                        return true;
                    }
                }else if (token instanceof ArrayToken){
                    if (((ArrayToken) token).getIndex() == index){
                        return true;
                    }
                }

                eoi = true;
                return false;
            }


            return true;

        } catch (Exception e) {
            throw new UncheckedExecutionException(e);
        }
    }

    @Override
    public Spliterator trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return 0;
    }

    @Override
    public int characteristics() {
        return Spliterator.NONNULL | Spliterator.DISTINCT | Spliterator.IMMUTABLE;
    }
}
