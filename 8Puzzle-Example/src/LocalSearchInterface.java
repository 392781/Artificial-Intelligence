import java.util.Collection;
import java.util.function.Function;

public interface LocalSearchInterface {
    Collection<byte[]> search(Problem problem, Function<State, Integer> heuristic) throws UnsolvableProblemException;
}
