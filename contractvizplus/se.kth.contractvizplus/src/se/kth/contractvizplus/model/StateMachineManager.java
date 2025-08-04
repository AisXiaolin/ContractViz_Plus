package se.kth.contractvizplus.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.tracecompass.tmf.core.signal.TmfSignalHandler;
import org.eclipse.tracecompass.tmf.core.signal.TmfSignalManager;
import org.eclipse.tracecompass.tmf.core.signal.TmfTraceSelectedSignal;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;
import org.eclipse.tracecompass.tmf.core.trace.TmfTraceManager;

/**
 * Singleton manager for handling collections of state graphs associated with different TMF traces.
 * <p>
 * This manager allows adding, retrieving, and removing {@link StateGraph} instances,
 * as well as responding to selected trace events. Each trace can have multiple state graphs,
 * identified by a name.
 * </p>
 *
 * @author Alexandre Arezes
 * @version 1.0
 * @since 1.0
 */
public class StateMachineManager {
    private static StateMachineManager fInstance;
    private final Map<ITmfTrace, Map<String, StateGraph>> fTraceGraph = new HashMap<>();
    private ITmfTrace fCurrentTrace;

    /**
     * Private constructor for singleton pattern.
     * Initializes the current trace and registers for TMF signal notifications.
     */
    private StateMachineManager() {
        fCurrentTrace = TmfTraceManager.getInstance().getActiveTrace();
        TmfSignalManager.registerVIP(this);
    }

    /**
     * Returns the singleton instance of {@link StateMachineManager}.
     *
     * @return the singleton instance
     */
    public static StateMachineManager getInstance() {
        if (fInstance == null) {
            fInstance = new StateMachineManager();
        }
        return fInstance;
    }

    /**
     * Adds a state graph to the current trace.
     * The graph is identified by its name within the current trace.
     *
     * @param graph the {@link StateGraph} to add
     */
    public void addGraph(StateGraph graph) {
        fTraceGraph.computeIfAbsent(fCurrentTrace, key -> new HashMap<String, StateGraph>()).put(graph.getName(), graph);
    }

    /**
     * Retrieves a state graph by name for the current trace.
     *
     * @param name the name of the graph
     * @return the {@link StateGraph} with the specified name, or null if not found
     */
    public StateGraph getGraph(String name) {
        return fTraceGraph.get(fCurrentTrace).get(name);
    }

    /**
     * Retrieves an existing state graph for a specified trace and name, or creates it if it does not exist.
     *
     * @param trace the {@link ITmfTrace} to associate with the graph
     * @param name  the name of the graph
     * @return the existing or newly created {@link StateGraph}
     */
    public StateGraph getOrCreateGraph(ITmfTrace trace, String name) {
        return fTraceGraph.computeIfAbsent(trace, key -> new HashMap<String, StateGraph>())
                .computeIfAbsent(name, key -> new StateGraph(key));
    }

    /**
     * Returns all state graphs associated with the current trace.
     *
     * @return a collection of {@link StateGraph} instances for the current trace
     */
    public Collection<StateGraph> getAllGraphs() {
        return fTraceGraph.getOrDefault(fCurrentTrace, new HashMap<String, StateGraph>()).values();
    }

    /**
     * Removes a state graph by name from the current trace.
     *
     * @param name the name of the graph to remove
     */
    public void removeGraph(String name) {
        fTraceGraph.get(fCurrentTrace).remove(name);
    }

    /**
     * Clears all state graphs associated with the specified trace.
     *
     * @param trace the {@link ITmfTrace} whose graphs should be cleared
     */
    public void clear(ITmfTrace trace) {
        fTraceGraph.get(trace).clear();
    }

    /**
     * Signal handler for trace selection changes.
     * Updates the current trace when a new trace is selected in the environment.
     *
     * @param signal the {@link TmfTraceSelectedSignal} event
     */
    @TmfSignalHandler
    public void traceSelected(TmfTraceSelectedSignal signal) {
        fCurrentTrace = signal.getTrace();
    }

    /**
     * Disposes this manager and unregisters it from the TMF signal manager.
     * Should be called when the manager is no longer needed.
     */
    public void dispose() {
        TmfSignalManager.deregister(this);
    }
}