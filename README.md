Json Streaming JsonPath Matcher
==================

Streaming Json is fast but at the cost of lack of context (previous node / attribute...) and matcher. 
Matching a json with a path barely more complex than `parent/child` is cumbersome and imply to implement context saving and matching.
 
Thanks to [JsonPath](http://goessner.net/articles/JsonPath/), a string expression will help to parse the json. 


Field matcher
------------------

The following code:

```java

try(InputStream is = new FileInputStream("foo.json")){
    JsonFactory factory = new JsonFactory();
    JsonParser parser = factory.createParser(is);
    while(parser.nextToken() != null){
        if (JsonToken.FIELD_NAME == parser.getCurrentToken() && "foo".equals(parser.getText())){
            //Do something
        }
    }
}        

```

Can be replaced with a more friendly lambda stream based approach:

```java
JsonStreams.stream("src/test/resources/foo.json")
        .field("foo")
        .forEach(c -> {
            //Do something                
        });
```


JsonPath matcher
------------------

Things can get tough when the path is complex

Pseudo java code:

```java


try(InputStream is = new FileInputStream("foo.json")){
    JsonFactory factory = new JsonFactory();
    JsonParser parser = factory.createParser(is);
    JsonPath jsonPath = new JsonPath();
    while(parser.nextToken() != null){
        if ("$.foo.bar".equals(jsonPath.toString())) {
            //Do something
        }


    }
}   

```

The JsonPath matcher allows to keep the code clean and focused:

```java
JsonStreams.stream("foo.xml")
        .jsonPath("$.foo.bar")
        .forEach(c -> {
            //Do something
        });
```


Predicates
------------------

All matchers are Java 8 [Predicate](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html) that can be combined.


```java
JsonStreams.consumer("foo.xml"))
        .match(jsonPath("@.book").or(jsonPath("@.movie")), c -> {})
        .consume();
```

See the [Predicates helper](org/nlab/json/stream/predicate/Predicates.java) for the list of supported predicates.


Nested Consumer and Streamer
------------------

Consumer and Streamer can be nested.

In the following example, a first stream match all the wikipedia page tag. Starting from this tag, 
a nester consumer extract the title, id, timestamp and contributor name:

```java
    try (InputStream fis = new FileInputStream("src/test/resources/wikipedia.json");
        JsonStreams.stream("wikipedia.json")
            .jsonPath("$.page")
            .map(context -> {
                Page page = new Page();
                context.partialConsumer()
                        .matchJsonPath("@.title", c -> page.title = c.getText())
                        .matchJsonPath("@.id", c -> page.id = c.getText())
                        .matchJsonPath("@.revision.timestamp", c -> page.lastRevision = c.getText())
                        .matchJsonPath("@.revision.contributor.username", c -> page.lastContributor = c.getText())
                        .consume();
                return page;
            })
            .forEach(p -> p.toString())
         
```

Supported JsonPath 
=========================
