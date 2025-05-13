package org.example.statediagram.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StateMachineManager {
    private static StateMachineManager fInstance;
    private final Map<String, StateGraph> fGraphs = new HashMap<>();

    private StateMachineManager() {
    }

    public static StateMachineManager getInstance() {
        if (fInstance == null) {
            fInstance = new StateMachineManager();
        }
        return fInstance;
    }

    public void addGraph(StateGraph graph) {
        fGraphs.put(graph.getName(), graph);
    }

    public StateGraph getGraph(String name) {
        return fGraphs.get(name);
    }

    public Collection<StateGraph> getAllGraphs() {
        return fGraphs.values();
    }

    public void removeGraph(String name) {
        fGraphs.remove(name);
    }

    public void clear() {
        fGraphs.clear();
    }
}