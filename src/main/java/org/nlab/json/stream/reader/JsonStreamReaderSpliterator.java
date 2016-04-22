package org.nlab.json.stream.reader;

import java.io.IOException;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.nlab.exception.UncheckedExecutionException;
import org.nlab.json.stream.context.StreamContext;


/**
 * Created by nlabrot on 21/04/16.
 */
public class JsonStreamReaderSpliterator implements Spliterator<StreamContext> {

    private final JsonMatcherStreamReader jsonMatcherReader;

    public JsonStreamReaderSpliterator(JsonMatcherStreamReader jsonMatcherReader) {
        this.jsonMatcherReader = jsonMatcherReader;
    }


    @Override
    public boolean tryAdvance(Consumer<? super StreamContext> action) {
        try {
            if (jsonMatcherReader.isClosed()) {
                return false;
            }

            if (jsonMatcherReader.nextToken() == null) {
                return false;
            }

            action.accept(jsonMatcherReader.getStreamContext());

            return true;

        } catch (IOException e) {
            throw new UncheckedExecutionException(e);
        }
    }

    @Override
    public Spliterator<StreamContext> trySplit() {
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
