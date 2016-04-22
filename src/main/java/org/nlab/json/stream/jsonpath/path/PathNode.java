package org.nlab.json.stream.jsonpath.path;

/**
 * Created by nlabrot on 13/04/16.
 */
public class PathNode {

    private PathNode child;

    public PathNode(PathNode child) {
        this.child = child;
    }

    public PathNode getChild() {
        return child;
    }
}
