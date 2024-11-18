package GraphTraversalComponent.src;

import java.util.List;

public interface GraphTraversalSecondary<T> extends GraphTraversalKernel<T> {
    List<T> findPath(T start, T end);
    void resetGraph();
}
