package org.nlab.json;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.nlab.json.stream.JsonStreams;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by nlabrot on 21/04/16.
 */
public class PartialStream2Test {


    @Test
    public void test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AtomicInteger counter = new AtomicInteger(0);

        JsonStreams.newConsumer("src/test/resources/data.json")
                .matchJsonPath("$[*].friends", c -> {
                    //System.out.println(c);
                    c.partialConsumer()
                            .matchJsonPath("@.id", subC -> {
                                System.out.println(subC);
                            }).consume();


                }).consume();

    }

}
