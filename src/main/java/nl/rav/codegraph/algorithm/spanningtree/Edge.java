package nl.rav.codegraph.algorithm.spanningtree;

/**
 * a typed directed edge
 *
 * the ids are the ids of the corresponding nodes.
 */
public class Edge {

    private final long fromId;
    private final long toId;
    private EdgeType edgeType = EdgeType.TREE;
    private Long assignedParentId;

    public Edge(long fromId, long toId) {
        this.fromId = fromId;
        this.toId = toId;
    }

    public long getFromId() {
        return fromId;
    }

    public long getToId() {
        return toId;
    }

    public EdgeType getEdgeType() {
        return edgeType;
    }

    public void setEdgeType(EdgeType edgeType) {
        this.edgeType = edgeType;
    }

    public Long getAssignedParentId() {
        return assignedParentId;
    }

    public void setAssignedParentId(Long assignedParentId) {
        this.assignedParentId = assignedParentId;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "fromId=" + fromId +
                ", toId=" + toId +
                ", edgeType=" + edgeType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (fromId != edge.fromId) return false;
        return toId == edge.toId;

    }

    @Override
    public int hashCode() {
        int result = (int) (fromId ^ (fromId >>> 32));
        result = 31 * result + (int) (toId ^ (toId >>> 32));
        return result;
    }
}
