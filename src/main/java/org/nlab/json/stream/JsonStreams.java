package org.nlab.json.stream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.jooq.lambda.fi.util.function.CheckedSupplier;
import org.nlab.json.stream.consumer.JsonConsumer;

import com.fasterxml.jackson.core.JsonParser;

/**
 * Created by nlabrot on 08/12/15.
 */
public final class JsonStreams {

    private JsonStreams() {
    }

    /**
     * Create a Stream from a path
     * As the stream parses an InputStream, the stream must be embed in a try-with-resource statement
     *
     * @param path path
     * @return JsonStream
     * @throws XMLStreamException
     * @throws IOException
     */
    public static JsonStream stream(Path path) throws Exception {
        return JsonStreamSpec.with(path).stream();
    }


    /**
     * Create a Stream from a path
     * As the stream parses an InputStream, the stream must be used in a try-with-resource statement
     *
     * @param path path
     * @return JsonStream
     * @throws XMLStreamException
     * @throws IOException
     */
    public static JsonStream stream(String path) throws Exception {
        return JsonStreamSpec.with(path).stream();
    }

    public static CheckedSupplier<JsonStream> sstream(String path) {
        return () -> JsonStreamSpec.with(path).stream();
    }


    /**
     * Create a Stream from an InputStream.
     * The InputStream will be closed if the stream is used in a try-with-resource statement
     *
     * @param is InputStream
     * @return JsonStream
     * @throws XMLStreamException
     */
    public static JsonStream streamAndClose(InputStream is) throws Exception {
        return JsonStreamSpec.with(is).closeOnFinish().stream();
    }

    public static CheckedSupplier<JsonStream> sstreamAndClose(InputStream is) throws IOException, XMLStreamException {
        return JsonStreamSpec.with(is).closeOnFinish().sstream();
    }

    /**
     * Create a Stream from an InputStream.
     * The InputStream will be closed if the stream is used in a try-with-resource statement
     *
     * @param is InputStream
     * @return JsonStream
     * @throws XMLStreamException
     */
    public static JsonStream stream(InputStream is) throws Exception {
        return JsonStreamSpec.with(is).stream();
    }

    public static CheckedSupplier<JsonStream> sstream(InputStream is) throws IOException, XMLStreamException {
        return JsonStreamSpec.with(is).sstream();
    }

    /**
     * Create a Stream from an XMLStreamReader.
     * The XMLStreamReader will be closed if the stream is used in a try-with-resource statement
     *
     * @param reader XMLStreamReader
     * @return JsonStream
     * @throws XMLStreamException
     */
    public static JsonStream streamAndClose(JsonParser reader) throws Exception {
        return JsonStreamSpec.with(reader).closeOnFinish().stream();
    }

    public static CheckedSupplier<JsonStream> sstreamAndClose(JsonParser reader) throws IOException, XMLStreamException {
        return JsonStreamSpec.with(reader).closeOnFinish().sstream();
    }

    /**
     * Create a Stream from an XMLStreamReader.
     *
     * @param reader XMLStreamReader
     * @return JsonStream
     * @throws XMLStreamException
     */
    public static JsonStream stream(JsonParser reader) throws Exception {
        return JsonStreamSpec.with(reader).stream();
    }

    public static CheckedSupplier<JsonStream> sstream(JsonParser reader) throws IOException, XMLStreamException {
        return JsonStreamSpec.with(reader).sstream();
    }




    /**
     * Create an JsonConsumer from a path
     *
     * @param path path
     * @return JsonStream
     * @throws IOException
     * @throws XMLStreamException
     */
    public static JsonConsumer newConsumer(String path) throws Exception {
        return JsonStreamSpec.with(path).consumer();
    }

    /**
     * Create an JsonConsumer from a path
     *
     * @param path path
     * @return JsonStream
     * @throws XMLStreamException
     * @throws IOException
     */
    public static JsonConsumer newConsumer(Path path) throws Exception {
        return JsonStreamSpec.with(path).consumer();
    }

    /**
     * Create an JsonConsumer from an XMLStreamReader
     * The XMLStreamReader is closed once the consumer finished succesfully or with error
     *
     * @param xmlStreamReader
     * @return JsonStream
     * @throws XMLStreamException
     */
    public static JsonConsumer newConsumerAndClose(JsonParser xmlStreamReader) throws Exception {
        return JsonStreamSpec.with(xmlStreamReader).closeOnFinish().consumer();
    }

    /**
     * Create an JsonConsumer from an InputStream
     * The InputStream is closed once the consumer finished succesfully or with error
     *
     * @param inputStream
     * @return JsonStream
     * @throws XMLStreamException
     */
    public static JsonConsumer newConsumerAndClose(InputStream inputStream) throws Exception {
        return JsonStreamSpec.with(inputStream).closeOnFinish().consumer();
    }


}
