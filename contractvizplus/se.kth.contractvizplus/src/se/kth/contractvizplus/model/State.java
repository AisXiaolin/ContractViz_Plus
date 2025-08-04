package se.kth.contractvizplus.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.tracecompass.tmf.core.presentation.RGBAColor;


/**
 * Represents a state in a state diagram.
 * <p>
 * Each state has a name, a color for display, a start and end time,
 * an optional tooltip, an index, and a map of transitions to other states
 * triggered by events.
 * </p>
 *
 * @author Alexandre Arezes
 * @version 1.0
 * @since 1.0
 */
public class State {
    private final String fName;
    private final RGBAColor fColor;
    private final long fStart;
    private final long fEnd;
    private String fTooltip;
    private int fIndex;

    private final Map<String, List<State>> transitions = new HashMap<>();

    /**
     * Constructs a new State with the specified name, color, start time, and end time.
     *
     * @param name the name of the state. Cannot be null.
     * @param color the display color for the state.
     * @param start the start timestamp of this state's activity.
     * @param end the end timestamp of this state's activity.
     */
    public State(String name, RGBAColor color, long start, long end) {
        fName = name;
        fColor = color;
        fStart = start;
        fEnd = end;
    }

    /**
     * Gets the name of this state.
     *
     * @return the name of the state.
     */
    public String getName() {
        return fName;
    }

    /**
     * Gets the display color of this state.
     *
     * @return the {@link Color} associated with this state.
     */
    public RGBAColor getColor() {
		return fColor;
	}

    /**
     * Gets the start timestamp of this state's activity.
     *
     * @return the start time in milliseconds.
     */
    public long getStart() {
    	return fStart;
    }

    /**
     * Gets the end timestamp of this state's activity.
     *
     * @return the end time in milliseconds.
     */
    public long getEnd() {
		return fEnd;
	}
    
    /**
     * Sets the tooltip text for this state.
     * <p>
     * The tooltip can be used to display additional information about the state
     * in a user interface.
     * </p>
     *
     * @param tooltip the tooltip text.
     */
    public void setTooltip(String tooltip) {
		fTooltip = tooltip;
	}

    /**
     * Gets the tooltip text for this state.
     *
     * @return the tooltip text, or null if not set.
     */
    public String getTooltip() {
		return fTooltip;
	}

    /**
     * Sets the index of this state.
     * <p>
     * The index can be used for ordering or identifying states numerically.
     * </p>
     *
     * @param index the numerical index for this state.
     */
    public void setIndex(int index) {
    	fIndex = index;
    }
    
    /**
     * Gets the index of this state.
     *
     * @return the numerical index of this state.
     */
    public int getIndex() {
    	return fIndex;
    }

    /**
     * Adds a transition from this state to a target state, triggered by a specific event.
     * <p>
     * If the event already has transitions, the new target state is added to the list
     * of existing target states for that event.
     * </p>
     *
     * @param evenement the name of the event triggering the transition.
     * @param etatCible the target {@link State} of the transition.
     */
    public void addTransition(String evenement, State etatCible) {
        transitions.computeIfAbsent(evenement, k -> new ArrayList<>()).add(etatCible);
    }

    /**
     * Gets the map of transitions for this state.
     * <p>
     * The map keys are event names (String), and the values are lists of
     * target {@link State} objects that can be reached from this state
     * upon the occurrence of the respective event.
     * </p>
     *
     * @return a map where keys are event strings and values are lists of target states.
     */
    public Map<String, List<State>> getTransitions() {
        return transitions;
    }
    
    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * Two {@code State} objects are considered equal if their names are equal.
     * Other fields like color, timestamps, tooltip, index, and transitions
     * are not considered in the equality check.
     * </p>
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument;
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
    	if (!(obj instanceof State)) {
    		return false;
    	}
    	return Objects.equals(((State)obj).fName,fName);
    }
    
    /**
     * Returns a string representation of the state.
     * <p>
     * The string includes the state's name, color, start time, end time,
     * tooltip, index, and details of its transitions.
     * For transitions, it lists the event and the names of the target states.
     * </p>
     *
     * @return a string representation of this state.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("State{name='").append(fName)
          .append("', color=").append(fColor)
          .append(", start=").append(fStart)
          .append(", end=").append(fEnd)
          .append(", tooltip='").append(fTooltip)
          .append("', index=").append(fIndex)
          .append(", transitions={");

        for (Map.Entry<String, List<State>> entry : transitions.entrySet()) {
            sb.append(entry.getKey()).append("=[");
            List<State> targets = entry.getValue();
            for (int i = 0; i < targets.size(); i++) {
                sb.append(targets.get(i).getName()); // Assuming State has a getName() method
                if (i < targets.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("], ");
        }

        // Remove trailing comma and space if transitions exist
        if (!transitions.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}}");
        return sb.toString();
    }
}