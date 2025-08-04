package se.kth.contractvizplus.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a graph of states for a state diagram.
 * <p>
 * The graph holds a list of {@link State} objects and provides methods
 * to add, retrieve, and clear states. Each state is automatically assigned
 * an index based on its position in the list.
 * </p>
 *
 * @author Alexandre Arezes
 * @version 1.0
 * @since 1.0
 */
public class StateGraph {
    private final List<State> states = new ArrayList<>();
    private final String name;

    /**
     * Constructs a new StateGraph with the specified name.
     *
     * @param name the name of the state graph.
     */
    public StateGraph(String name) {
        this.name = name;
    }

    /**
     * Adds a state to the graph.
     * <p>
     * The state is assigned an index corresponding to its position in the list.
     * </p>
     *
     * @param state the {@link State} to add to the graph.
     */
    public void addState(State state) {
        state.setIndex(states.size());
        states.add(state);
    }

    /**
     * Returns the list of states in the graph.
     *
     * @return the list of {@link State} objects.
     */
    public List<State> getStates() {
        return states;
    }

    /**
     * Returns the state at the specified index.
     *
     * @param idx the index of the state to retrieve.
     * @return the {@link State} at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public State getState(int idx) {
        return states.get(idx);
    }

    /**
     * Returns the name of this state graph.
     *
     * @return the name of the graph.
     */
    public String getName() {
        return name;
    }

    /**
     * Removes all states from the graph.
     */
    public void clear() {
        states.clear();
    }
}