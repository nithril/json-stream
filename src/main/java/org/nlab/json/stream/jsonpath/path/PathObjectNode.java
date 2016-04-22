package org.nlab.json.stream.jsonpath.path;

/**
 * Created by nlabrot on 13/04/16.
 */
public class PathObjectNode extends PathNode {
    private String name;
    public PathObjectNode(String name , PathNode child) {
        super(child);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}