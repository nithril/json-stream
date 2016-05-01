package org.nlab;

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
@RunWith(Parameterized.class)
public class PartialStreamTest {


    @Parameterized.Parameters(name = "{index} {0} {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {"src/test/resources/data.json", "$[*].name", Map.class, new String[]{
                                "{\"first\":\"Geraldine\",\"last\":\"Ramirez\"}",
                                "{\"first\":\"Ashley\",\"last\":\"Wilcox\"}",
                                "{\"first\":\"Kinney\",\"last\":\"Callahan\"}",
                                "{\"first\":\"Deann\",\"last\":\"Guy\"}",
                                "{\"first\":\"Patricia\",\"last\":\"Hunt\"}"}},

                        {"src/test/resources/data.json", "$[*].tags", List.class, new String[]{
                                "[7,\"exercitation1\"]",
                                "[8,\"exercitation2\"]",
                                "[9,\"exercitation3\"]",
                                "[10,\"exercitation4\"]",
                                "[11,\"exercitation5\"]"}},

                        {"src/test/resources/data.json", "@.age", Integer.class, new String[]{
                                "33",
                                "23",
                                "36",
                                "21",
                                "20"}},
                });
    }

    @Parameterized.Parameter(value = 0)
    public String file;

    @Parameterized.Parameter(value = 1)
    public String jsonPath;

    @Parameterized.Parameter(value = 2)
    public Class expectedType;

    @Parameterized.Parameter(value = 3)
    public String[] expectedJsons;

    @Test
    public void test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AtomicInteger counter = new AtomicInteger(0);

        JsonStreams.newConsumer(file)
                .matchJsonPath(jsonPath, c -> {
                    Object v = mapper.readValue(c.getStreamReader(), expectedType);
                    String actualJson = mapper.writeValueAsString(v);
                    System.out.println(actualJson);

                    Assert.assertEquals(expectedJsons[counter.getAndIncrement()], actualJson);
                }).consume();

        Assert.assertEquals(expectedJsons.length, counter.get());
    }

}
