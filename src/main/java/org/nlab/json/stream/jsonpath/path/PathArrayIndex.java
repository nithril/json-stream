package org.nlab.json.stream.jsonpath.path;

/**
 * Created by nlabrot on 13/04/16.
 */
public class PathArrayIndex extends PathNode {

    private int index;

    public PathArrayIndex(String index) {
        super(null);

        if ("*".equals(index)){
            this.index = -1;
        }else{
            this.index = Integer.parseInt(index);

        }



    }

    public int getIndex() {
        return index;
    }


    public boolean matchIndex(int toMatchIndex){
        return index == -1 || toMatchIndex == index;
    }
}