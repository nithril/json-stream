package org.nlab.json.stream.jsonpath;

import org.nlab.json.stream.jsonpath.parser.JsonPathParser;
import org.nlab.json.stream.jsonpath.path.PathNode;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;


/**
 * Created by nlabrot on 20/03/16.
 */
public class JsonPath {

    private static class Singleton {
        private static final JsonPathParser PARSER = Parboiled.createParser(JsonPathParser.class);
    }


    public static PathNode parse(String path) {
        JsonPathParser parser = Singleton.PARSER.newInstance();
        ParsingResult<?> result = new RecoveringParseRunner(parser.InputLine()).run(path);
        return (PathNode) result.resultValue;
    }

}