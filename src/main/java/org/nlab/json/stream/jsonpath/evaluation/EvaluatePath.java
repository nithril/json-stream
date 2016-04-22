package org.nlab.json.stream.jsonpath.evaluation;

import java.util.function.Consumer;

import org.nlab.json.stream.context.StreamContext;
import org.nlab.json.stream.jsonpath.path.PathArrayNode;
import org.nlab.json.stream.jsonpath.path.PathCurrentNode;
import org.nlab.json.stream.jsonpath.path.PathDescendantNode;
import org.nlab.json.stream.jsonpath.path.PathNode;
import org.nlab.json.stream.jsonpath.path.PathObjectNode;
import org.nlab.json.stream.jsonpath.path.PathRootNode;
import org.nlab.json.stream.context.token.ArrayToken;
import org.nlab.json.stream.context.token.ObjectToken;
import org.nlab.json.stream.context.token.Token;
import org.nlab.json.stream.context.token.RootToken;

/**
 * Created by nlabrot on 19/04/16.
 */
public class EvaluatePath {

    private Consumer<StreamContext> callback;

    public EvaluatePath(Consumer<StreamContext> callback) {
        this.callback = callback;
    }

    public void evaluateNext(StreamContext context, int index, PathNode node) {

        if (node == null || index >= context.getElements().size()) {
            return;
        }

        Token parserToken = context.getElements().get(index);

        if (parserToken instanceof RootToken) {
            if (node instanceof PathRootNode) {
                evaluateNext(context, index + 1, node.getChild());
            } else if (node instanceof PathCurrentNode) {
                evaluatePathCurrent(context, index, node);
            }
        } else if (parserToken instanceof ArrayToken && node instanceof PathArrayNode) {
            evaluateArray(context, index, (PathArrayNode) node);
        } else if (parserToken instanceof ObjectToken && node instanceof PathObjectNode) {
            evaluateObject(context, index, (PathObjectNode) node);
        } else if (node instanceof PathDescendantNode){
            evaluatePathCurrent(context, index, node);
        }

    }


    public void evaluatePathCurrent(StreamContext context, int index, PathNode node) {
        for (int i = index; i <= context.getElements().size(); i++) {
            evaluateNext(context, i, node.getChild());
        }
    }


    public void evaluateArray(StreamContext context, int index, PathArrayNode pathArrayNode) {
        ArrayToken arrayToken = (ArrayToken) context.getElements().get(index);
        if (arrayToken.getIndex() == pathArrayNode.getIndex().getIndex()) {
            // match if next node is leaf and next node is an array or an object uninitialized or a literal and there is no next path node
            if (index + 2 == context.getElements().size()
                    && (context.getElements().get(index + 1).isPreInit() ||  context.getElements().get(index + 1).isLiteral())
                    && pathArrayNode.getChild() == null) {
                callback.accept(context);
                return;
            } else {
                evaluateNext(context, index + 1, pathArrayNode.getChild());
            }
        }
    }


    private void evaluateObject(StreamContext context, int index, PathObjectNode pathObjectNode) {
        ObjectToken objectToken = (ObjectToken) context.getElements().get(index);
        if (objectToken.isKeyInit() && objectToken.getKey().equals(pathObjectNode.getName())) {

            // match if both node and path reached last and node does not yet has it's value parsed
            if (index + 1 >= context.getElements().size() && !objectToken.isValueInit() && pathObjectNode.getChild() == null) {
                callback.accept(context);
            } else {
                evaluateNext(context, index + 1, pathObjectNode.getChild());
            }


        }
    }


    public Consumer getCallback() {
        return callback;
    }

    public void setCallback(Consumer callback) {
        this.callback = callback;
    }
}
