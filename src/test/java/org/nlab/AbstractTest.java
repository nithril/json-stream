package org.nlab;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.nlab.json.stream.JsonStreams;

/**
 * Created by nlabrot on 20/03/16.
 */
@RunWith(Parameterized.class)
public class AbstractTest {

    @Parameterized.Parameter(value = 0)
    public String file;

    @Parameterized.Parameter(value = 1)
    public String jsonPath;

    @Parameterized.Parameter(value = 2)
    public String[] expectedPaths;

    @Test
    public void name() throws Exception {
        AtomicInteger counter = new AtomicInteger(0);

        JsonStreams.newConsumer(file)
                .matchJsonPath(jsonPath, c -> {
                    System.out.println(c.toPath());
                    Assert.assertEquals(expectedPaths[counter.getAndIncrement()], c.toPath());
                }).consume();

        Assert.assertEquals(expectedPaths.length , counter.get());
    }



}
