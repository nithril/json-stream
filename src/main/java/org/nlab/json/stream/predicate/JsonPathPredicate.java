package org.nlab.json.stream.predicate;

import java.util.concurrent.atomic.AtomicBoolean;

import org.nlab.json.stream.context.StreamContext;
import org.nlab.json.stream.jsonpath.evaluation.EvaluatePath;
import org.nlab.json.stream.jsonpath.path.PathNode;

/**
 * Created by nlabrot on 15/03/15.
 */
public class JsonPathPredicate implements JsonPredicate {


    private PathNode pathNode;

    public JsonPathPredicate(PathNode pathNode) {
        this.pathNode = pathNode;
    }

	@Override
	public boolean test(StreamContext streamContext) {
		AtomicBoolean found = new AtomicBoolean(false);

		EvaluatePath nodeSelector = new EvaluatePath(c -> {
			found.set(true);
		});
		nodeSelector.evaluateNext(streamContext , 0 , pathNode);
        return found.get();
	}

}
