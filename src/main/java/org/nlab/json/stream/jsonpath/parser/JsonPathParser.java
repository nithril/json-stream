package org.nlab.json.stream.jsonpath.parser;

import org.nlab.json.stream.jsonpath.path.PathArrayNode;
import org.nlab.json.stream.jsonpath.path.PathArrayIndex;
import org.nlab.json.stream.jsonpath.path.PathCurrentNode;
import org.nlab.json.stream.jsonpath.path.PathDescendantNode;
import org.nlab.json.stream.jsonpath.path.PathNode;
import org.nlab.json.stream.jsonpath.path.PathObjectNode;
import org.nlab.json.stream.jsonpath.path.PathRootNode;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.support.Var;


/**
 * Created by nlabrot on 20/03/16.
 */
public class JsonPathParser extends BaseParser<PathNode> {

    public Rule InputLine() {
        return Start();
    }

    public Rule Start() {
        return FirstOf(Root(), CurrentNode());
    }

    public Rule Root() {
        return Sequence(Ch('$'), Next(), push(new PathRootNode(pop())));
    }

    public Rule CurrentNode() {
        return Sequence(Ch('@'), Next(), push(new PathCurrentNode(pop())));
    }

    public Rule Next() {
        return FirstOf(Child(), Descendant(), Eoi());
    }

    public Rule Child() {
        return FirstOf(DotObject(), Array());
    }

    public Rule Descendant() {
        return Sequence(String(".."), FirstOf(Object(), Array(), Eoi()) , push(new PathDescendantNode(pop())));
    }

    public Rule Array() {
        return Sequence(Ch('['), Number(), Ch(']'), Next(), swap(), push(new PathArrayNode(pop() , pop())));
    }

    public Rule DotObject() {
        return Sequence(Ch('.'), Object());
    }

    public Rule Object() {
        Var<String> op = new Var<>();
        return Sequence(
                Sequence(FirstOf(Ch('*'), OneOrMore(NoneOf("[].*"))), op.set(match())),
                Next() , push(new PathObjectNode(op.get() , pop())));
    }

    public Rule Number() {
        return Sequence(FirstOf(Digits() , '*') , push(new PathArrayIndex(matchOrDefault("0"))));
    }

    @SuppressSubnodes
    public Rule Digits() {
        return OneOrMore(Digit());
    }

    public Rule Digit() {
        return CharRange('0', '9');
    }

    public Rule Eoi(){
        return Sequence(EOI , push(null));
    }
}