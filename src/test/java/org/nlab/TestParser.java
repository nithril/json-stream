package org.nlab;

import java.util.Arrays;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Created by nlabrot on 20/03/16.
 */
@RunWith(Parameterized.class)
public class TestParser extends AbstractTest {


    @Parameterized.Parameters(name = "{index} {0} {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {"src/test/resources/foo.json", "$[1]", new String[]{"$[1].UNINIT"}},
                        {"src/test/resources/foo.json", "@[1]", new String[]{"$[1].UNINIT", "$[2].foo.foo[1]#2"}},
                        {"src/test/resources/foo.json", "@.foo", new String[]{"$[1].foo", "$[2].foo" , "$[2].foo.foo"}},

                        {"src/test/resources/object.json", "$.a", new String[]{"$.a"}},

                        {"src/test/resources/object.json", "$..a", new String[]{"$.a", "$.b.a", "$.c[2].a", "$.nested1[2].nested1.3[3].nested1.3.2.a"}},
                        {"src/test/resources/object.json", "$.nested1[2]..a", new String[]{"$.nested1[2].nested1.3[3].nested1.3.2.a"}},

                });
    }


}
