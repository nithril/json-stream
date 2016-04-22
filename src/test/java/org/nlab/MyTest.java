package org.nlab;

import org.junit.Test;
import org.nlab.json.stream.JsonStreams;

/**
 * Created by nlabrot on 21/04/16.
 */
public class MyTest {

    @Test
    public void name() throws Exception {

        JsonStreams.newConsumer("src/test/resources/test.json")
                .matchObject("book" , c -> {
                    System.out.println(c.toPath());
                })
                .consume();




    }

    @Test
    public void name2() throws Exception {

        JsonStreams.newConsumer("src/test/resources/foo.json")
                .matchJsonPath("@.foo" , c -> {
                    System.out.println(c.toPath());
                })
                .consume();




    }


    @Test
    public void tags() throws Exception {

        JsonStreams.newConsumer("src/test/resources/data.json")
                .matchJsonPath("@.tags" , c -> {
                    System.out.println("@.tags " + c.toPath()+ " " +c.getJsonToken());
                    c.partialStream().forEach( subc -> {
                            System.out.println(subc.toPath()+ " " +c.getJsonToken());
                    });

                })
                .consume();




    }
}
