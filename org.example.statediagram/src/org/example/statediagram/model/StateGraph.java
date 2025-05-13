package org.example.statediagram.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StateGraph {
	private final Map<String, State> states = new HashMap<>();
    private final String name;

    public StateGraph(String name) {
        this.name = name;
    }

    public void addState(State state) {
        states.put(state.getName(), state);
    }

    public Collection<State> getStates() {
        return states.values();
    }

    public State getState(String name) {
        return states.get(name);
    }

    public String getName() {
        return name;
    }

    public void clear() {
        states.clear();
    }
}
