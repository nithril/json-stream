
package org.nlab.json.stream;

import java.util.logging.Logger;

import org.nlab.json.stream.jsonpath.evaluation.EvaluatePath;
import org.nlab.json.stream.jsonpath.path.PathNode;
import org.nlab.json.stream.reader.JsonMatcherStreamReader;


public class TokenStack {
    protected static Logger log = Logger.getLogger("com.jayway.jsonpath");


    private PathNode pathNode;

    private EvaluatePath evaluation = new EvaluatePath(null);


    /**
     * reads from stream and notifies the callback of matched registered paths
     */
    public void read(JsonMatcherStreamReader parser) throws Exception {


        while (parser.nextToken() != null) {
            switch (parser.getCurrentToken()) {
                case START_ARRAY: {
                    evaluation.evaluateNext(parser.getStreamContext(), 0, pathNode);
                    break;
                }
                case END_ARRAY: {
                    break;
                }
                case VALUE_EMBEDDED_OBJECT:
                case START_OBJECT: {
                    evaluation.evaluateNext(parser.getStreamContext(), 0, pathNode);
                    break;
                }
                case END_OBJECT: {
                    break;
                }
                case FIELD_NAME: {
                    evaluation.evaluateNext(parser.getStreamContext(), 0, pathNode);
                    break;
                }
                case VALUE_TRUE:
                case VALUE_FALSE: {
                    evaluation.evaluateNext(parser.getStreamContext(), 0, pathNode);
                    break;
                }
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT: {
                    evaluation.evaluateNext(parser.getStreamContext(), 0, pathNode);
                    break;
                }
                case VALUE_STRING: {
                    evaluation.evaluateNext(parser.getStreamContext(), 0, pathNode);
                    break;
                }
                case VALUE_NULL: {
                    evaluation.evaluateNext(parser.getStreamContext(), 0, pathNode);
                    break;
                }
                default:
                    assert false;
            }

        }
    }

    public EvaluatePath getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(EvaluatePath evaluation) {
        this.evaluation = evaluation;
    }


    public PathNode getPathNode() {
        return pathNode;
    }

    public void setPathNode(PathNode pathNode) {
        this.pathNode = pathNode;
    }
}

