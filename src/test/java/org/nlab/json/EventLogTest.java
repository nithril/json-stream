package org.nlab.json;

import org.junit.Test;
import org.nlab.json.stream.JsonStreams;
import org.nlab.json.stream.context.token.StringToken;
import org.nlab.json.stream.predicate.Predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 13/07/2016
 *
 * @author labrot
 */
public class EventLogTest {

  @Test
  public void test() throws Exception {
    AtomicInteger counter = new AtomicInteger(0);
    JsonStreams.stream("src/test/resources/eventlog.json") // Got 76 Tokens
//                .field("foo")
               .jsonPath("$[*]") // Reduce to 2 Tokens with JsonToken: START_OBJECT
               .map(
                   c -> {
                     List<Object> objectList = new ArrayList<>();

                     //System.out.println(c);

                     c.partialStream()
                      .forEach(c1 -> {
                                 System.out.println(c1.toPath() + " " + c1.getJsonToken());
                                 objectList.add(c1.toPath());
                               }
                              );
                     return objectList;
                   })
               .forEach(c -> {
                 System.out.println(String.join("\n"
                     , "============"
                     , "Field: " + counter.getAndIncrement()
                     , c.toString()
//                            , "CurrentToken: " + c.getCurrentToken()
//                            , "JsonToken: " + c.getJsonToken()
//                            , "Elements: " + c.getElements()
                                               ));
                 //Do something
               });
  }


  @Test
  public void name2() throws Exception {

    JsonStreams.stream("src/test/resources/eventlog.json") // Got 76 Tokens
               .jsonPath("$[*]") // Reduce to 2 Tokens with JsonToken: START_OBJECT
               .map(
                   context -> {

                     context.partialConsumer()
                            .matchJsonPath(
                                "@.event_type",
                                ctx -> {
                                  String eventType = ((StringToken) ctx.getCurrentToken()).getLiteral();
                                  if (!eventType.equals("bounce") && !eventType.equals("success")) {
                                    throw new RuntimeException("Error Json Log not conform");
                                  }
                                }
                                          )
                            .matchJsonPath(
                                "@.bytes",
                                ctx -> {}
                                          )
                            .matchJsonPath(
                                "@.code",
                                ctx -> {}
                                          )
                            .matchJsonPath(
                                "@.del_time",
                                ctx -> {
                                  String date = ((StringToken) ctx.getCurrentToken()).getLiteral();
                                }
                                          )
                            .matchJsonPath(
                                "@.inj_time",
                                ctx -> {
                                  String date = ((StringToken) ctx.getCurrentToken()).getLiteral();
                                }
                                          )
                            .matchJsonPath(
                                "@.env_from",
                                ctx -> {}
                                          )
                            .matchJsonPath(
                                "@.reply",
                                ctx -> {}
                                          )
                            .matchJsonPath(
                                "@.error",
                                ctx -> {}
                                          )
                            .matchJsonPath(
                                "@.reason",
                                ctx -> {}
                                          )
                            .matchJsonPath(
                                "@.ip",
                                ctx -> {}
                                          )
                            .matchJsonPath(
                                "@.source_ip",
                                ctx -> {}
                                          )
                            .matchJsonPath(
                                "@.mid",
                                ctx -> {}
                                          )
                            .matchJsonPath(
                                "@.rcpt_data[0].attempts",
                                ctx -> {
                                  System.out.println(ctx.toPath());
                                })

                            .matchJsonPath(
                                "@.rcpt_data[0]",
                                ctx -> {
                                  System.out.println(ctx.toPath());
                                  return false;
                                })


                            .match(Predicates.all(), c1 -> {
                              System.out.println(c1);
                            })
                            .consume();
                     return "";
                   }
                   )
               .forEach(c -> {
                 System.out.println(String.join("\n"
                     , "============"
                     , "Field: "
                     , c.toString()
//                            , "CurrentToken: " + c.getCurrentToken()
//                            , "JsonToken: " + c.getJsonToken()
//                            , "Elements: " + c.getElements()
                                               ));


               });
  }

}
