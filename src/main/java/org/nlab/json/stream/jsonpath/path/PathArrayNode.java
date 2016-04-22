package org.nlab.json.stream.jsonpath.path;

/**
 * Created by nlabrot on 13/04/16.
 */
public class PathArrayNode extends PathNode {
    private PathArrayIndex index;
    public PathArrayNode(PathNode index, PathNode child) {
        super(child);
        this.index = (PathArrayIndex) index;
    }

    public PathArrayIndex getIndex() {
        return index;
    }
}