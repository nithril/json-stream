package org.nlab.json.stream.jsonpath.path;

/**
 * Created by nlabrot on 13/04/16.
 */
public class PathArrayIndex extends PathNode {
    private int index;
    public PathArrayIndex(int index) {
        super(null);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}