package org.nlab.json.stream.reader;

import java.util.Spliterator;
import java.util.function.Consumer;

import org.nlab.exception.UncheckedExecutionException;
import org.nlab.json.stream.context.StreamContext;

import com.fasterxml.jackson.core.JsonToken;

/**
 * Created by nlabrot on 08/12/15.
 */
public class PartialJsonStreamReaderSpliterator implements Spliterator<StreamContext> {

    private final JsonMatcherStreamReader jsonMatcherReader;
    private final int depth;

    private boolean eoi = false;

    public PartialJsonStreamReaderSpliterator(JsonMatcherStreamReader jsonMatcherReader) {
        this.jsonMatcherReader = jsonMatcherReader;
        this.depth = jsonMatcherReader.getStreamContext().getElements().size();
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
