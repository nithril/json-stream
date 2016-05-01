package org.nlab;

import java.util.Arrays;

import org.junit.runners.Parameterized;

/**
 * Created by nlabrot on 20/03/16.
 */
public class TestParser extends AbstractTest {


    @Parameterized.Parameters(name = "{index} {0} {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {"src/test/resources/foo.json", "$[1]", new String[]{"$[1].UNINIT"}},
                        {"src/test/resources/foo.json", "$[*]", new String[]{"$[0]#0", "$[1].UNINIT", "$[2].UNINIT", "$[3]#3"}},
                        {"src/test/resources/foo.json", "@[1]", new String[]{"$[1].UNINIT", "$[2].foo.foo[1]#2"}},
                        {"src/test/resources/foo.json", "@.foo", new String[]{"$[1].foo#bar", "$[2].foo.UNINIT" , "$[2].foo.foo[-1]"}},

                        {"src/test/resources/object.json", "$.a", new String[]{"$.a#0"}},

                        {"src/test/resources/object.json", "$..a", new String[]{"$.a#0", "$.b.a#true", "$.c[2].a[-1]", "$.nested1[2].nested1.3[3].nested1.3.2.a[-1]"}},
                        {"src/test/resources/object.json", "$.nested1[2]..a", new String[]{"$.nested1[2].nested1.3[3].nested1.3.2.a[-1]"}},

                });
    }


}
