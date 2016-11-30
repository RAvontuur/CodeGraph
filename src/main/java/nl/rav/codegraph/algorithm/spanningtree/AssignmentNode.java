package nl.rav.codegraph.algorithm.spanningtree;

import java.util.List;

/**
 * Created by rene on 23-11-16.
 */
public interface AssignmentNode<N> {

    N getFrom();

    List<N> getDownwards();

    //an upward dependency is a cyclic dependency
    List<N> getUpwards() ;

}
