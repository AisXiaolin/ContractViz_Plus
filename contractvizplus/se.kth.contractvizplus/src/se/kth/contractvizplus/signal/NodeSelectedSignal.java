package se.kth.contractvizplus.signal;

import org.eclipse.tracecompass.tmf.core.signal.TmfSignal;
import se.kth.contractvizplus.model.State;

/**
 * Signal indicating that a state node has been selected.
 * <p>
 * This signal is typically used to notify listeners that a particular {@link State}
 * has been selected within the state diagram.
 * </p>
 *
 * @author Alexandre Arezes
 */
public class NodeSelectedSignal extends TmfSignal{
	
    private final State fSelectedState;
	
	/**
	 * Constructs a new NodeSelectedSignal with the specified source and selected state.
	 * 
	 * @param source the source object that triggered this signal
	 * @param state the state that was selected, representing the node in the state diagram
	 */
	public NodeSelectedSignal(Object source, State state) {
		super(source);
		fSelectedState = state;
	}

	/**
	 * Returns the currently selected state.
	 * 
	 * @return the selected State object, or null if no state is selected
	 */
	public State getState() {
		return fSelectedState;
	}
	
	
}
