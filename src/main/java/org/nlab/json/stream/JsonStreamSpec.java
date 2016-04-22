package org.nlab.json.stream;

import javax.xml.stream.XMLStreamException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Spliterator;
import java.util.concurrent.Callable;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.Validate;
import org.jooq.lambda.fi.util.function.CheckedSupplier;
import org.nlab.exception.UncheckedExecutionException;
import org.nlab.json.stream.consumer.JsonConsumer;
import org.nlab.json.stream.context.StreamContext;
import org.nlab.json.stream.reader.JsonMatcherStreamReader;
import org.nlab.json.stream.reader.JsonStreamReaderSpliterator;
import org.nlab.json.stream.reader.PartialJsonStreamReaderSpliterator;
import org.nlab.util.IoCloser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

import static org.nlab.util.IoCloser.ioCloser;

/**
 * Created by nlabrot on 14/12/15.
 */
public class JsonStreamSpec {

    private final Path path;
    private final InputStream inputStream;
    private final JsonParser reader;
    private final Callable<InputStream> resource;

    private boolean closeOnFinish = false;
    private boolean partial = false;

    public JsonStreamSpec(Path path) {
        this.path = path;
        this.inputStream = null;
        this.reader = null;
        this.resource = null;
    }

    public JsonStreamSpec(InputStream inputStream) {
        this.path = null;
        this.inputStream = inputStream;
        this.reader = null;
        this.resource = null;
    }

    public JsonStreamSpec(JsonParser reader) {
        this.path = null;
        this.inputStream = null;
        this.reader = reader;
        this.resource = null;
    }

    public JsonStreamSpec(Callable<InputStream> resource) {
        this.path = null;
        this.inputStream = null;
        this.reader = null;
        this.resource = resource;
    }

    public JsonStreamSpec hint() {
        return this;
    }

    public JsonStreamSpec closeOnFinish() {
        this.closeOnFinish = true;
        return this;
    }

    public JsonStreamSpec partial() {
        this.partial = true;
        return this;
    }

    public JsonStream stream() throws Exception {

        if (path != null) {
            return createStream(path);
        } else if (inputStream != null) {
            return createStream(inputStream, closeOnFinish);
        } else if (reader != null) {
            return createStream(reader, closeOnFinish);
        } else if (resource != null) {
            return createStream(resource, closeOnFinish);
        }

        return null;
    }

    public JsonStream uncheckedStream() {
        try {
            return stream();
        } catch (Exception e) {
            throw new UncheckedExecutionException(e);
        }
    }

    public CheckedSupplier<JsonStream> sstream() {
        return () -> stream();
    }


    public JsonConsumer consumer() throws Exception {
        return new JsonConsumer(stream());
    }

    public JsonConsumer uncheckedConsumer() {
        try {
            return new JsonConsumer(stream());
        } catch (Exception e) {
            throw new UncheckedExecutionException(e);
        }
    }


    public static JsonStreamSpec with(String path) {
        return new JsonStreamSpec(Paths.get(path));
    }

    public static JsonStreamSpec with(Path path) {
        return new JsonStreamSpec(path);
    }

    public static JsonStreamSpec with(InputStream inputStream) {
        return new JsonStreamSpec(inputStream);
    }

    public static JsonStreamSpec with(JsonParser reader) {
        return new JsonStreamSpec(reader).partial();
    }

    public static JsonStreamSpec with(StreamContext streamContext) {
        return new JsonStreamSpec(streamContext.getStreamReader()).partial();
    }

    public static JsonStreamSpec with(Callable<InputStream> resource) {
        return new JsonStreamSpec(resource);
    }


    private JsonStream createStream(Path file) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(Files.newInputStream(file));
            return createStream(inputStream, true);
        } catch (Exception e) {
            ioCloser().close(inputStream);
            throw e;
        }
    }

    private JsonStream createStream(JsonParser reader, boolean close) {
        try {
            JsonMatcherStreamReader jsonMatcherStreamReader = createOrCastMatcherStreamReader(reader);
            Stream<StreamContext> stream = StreamSupport.stream(createXmlStreamReaderSpliterator(jsonMatcherStreamReader), false);

            if (close) {
                stream = stream.onClose(() -> ioCloser().close(reader));
            }

            return new JsonStream(stream, jsonMatcherStreamReader);

        } catch (Exception e) {
            if (close) {
                ioCloser().close(reader);
            }
            throw e;
        }
    }

    private JsonStream createStream(Callable<InputStream> cis, boolean close) throws Exception {
        Validate.notNull(cis);

        InputStream inputStream = null;
        try {
            inputStream = cis.call();
            return createStream(inputStream,close);
        } catch (Exception e) {
            if (close) {
                ioCloser().close(inputStream);
            }
            throw e;
        }
    }


    private JsonStream createStream(InputStream is, boolean close) throws Exception {
        Validate.notNull(is);



        JsonParser streamReader = null;
        try {
            JsonFactory factory = new JsonFactory();


            streamReader = factory.createParser(is);

            JsonMatcherStreamReader jsonMatcherStreamReader = createOrCastMatcherStreamReader(streamReader);
            Stream<StreamContext> stream = StreamSupport.stream(createXmlStreamReaderSpliterator(jsonMatcherStreamReader), false);

            if (close) {
                stream.onClose(IoCloser.promiseIoCloser(streamReader, is));
            } else {
                stream.onClose(IoCloser.promiseIoCloser(streamReader));
            }

            return new JsonStream(stream, jsonMatcherStreamReader);

        } catch (Exception e) {
            IoCloser ioCloser = ioCloser().close(streamReader);
            if (close) {
                ioCloser.close(is);
            }
            throw e;
        }
    }

    private JsonMatcherStreamReader createOrCastMatcherStreamReader(JsonParser reader) {
        if (reader instanceof JsonMatcherStreamReader) {
            return (JsonMatcherStreamReader) reader;
        } else {
            return new JsonMatcherStreamReader(reader);
        }
    }

    private Spliterator<StreamContext> createXmlStreamReaderSpliterator(JsonMatcherStreamReader reader) {
        if (partial) {
            return new PartialJsonStreamReaderSpliterator(reader);
        } else {
            return new JsonStreamReaderSpliterator(reader);
        }
    }


}
