package org.example.statediagram.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Color;

public class State {
    private final String fName;
    private final Color fColor;
    private final long fStart;
    private final long fEnd;
    private String fTooltip;

    private final Map<String, List<State>> transitions = new HashMap<>();

    public State(String name, Color color, long start, long end) {
        fName = name;
        fColor = color;
        fStart = start;
        fEnd = end;
    }

    public String getName() {
        return fName;
    }
    public Color getColor() {
		return fColor;
	}
    public long getStart() {
    	return fStart;
    }
    public long getEnd() {
		return fEnd;
	}
    
    public void setTooltip(String tooltip) {
		fTooltip = tooltip;
	}
    public String getTooltip() {
		return fTooltip;
	}

    public void addTransition(String evenement, State etatCible) {
        transitions.computeIfAbsent(evenement, k -> new ArrayList<>()).add(etatCible);
    }

    public Map<String, List<State>> getTransitions() {
        return transitions;
    }
}