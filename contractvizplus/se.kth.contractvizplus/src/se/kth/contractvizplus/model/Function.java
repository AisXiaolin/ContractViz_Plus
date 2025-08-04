package se.kth.contractvizplus.model;

/**
 * Represents a function execution with timing information.
 * <p>
 * This class tracks the execution of a function by storing its index identifier
 * along with start and end timestamps. It is typically used for performance
 * monitoring, profiling, or state diagram analysis.
 * </p>
 *
 * @author Alexandre Arezes
 * @version 1.0
 * @since 1.0
 */
public class Function {
	private int fIndex;
	private long fStartTime;
	private long fEndTime;
	
	/**
	 * Constructs a new Function with the specified index and start time.
	 * <p>
	 * The end time is not set during construction and should be set later
	 * using {@link #setEndTime(long)} when the function execution completes.
	 * </p><p>
	 *
	 * @param index the unique identifier for this function
	 * @param startTime the timestamp when the function execution started
	 */
	public Function(int index, long startTime) {
		fIndex = index;
		fStartTime = startTime;
	}
	
	/**
	 * Sets the end time for this function execution.
	 * <p>
	 * This method should be called when the function execution completes
	 * to record the completion timestamp.
	 * </p>
	 *
	 * @param endTime the timestamp when the function execution ended
	 */
	public void setEndTime(long endTime) {
		fEndTime = endTime;
	}
	
	/**
	 * Returns the index identifier of this function.
	 *
	 * @return the unique identifier for this function
	 */
	public int getIndex() {
		return fIndex;
	}
	
	/**
	 * Returns the start time of this function execution.
	 *
	 * @return the timestamp when the function execution started
	 */
	public long getStartTime() {
		return fStartTime;
	}
	
	/**
	 * Returns the end time of this function execution.
	 *
	 * @return the timestamp when the function execution ended,
	 *         or 0 if the end time has not been set yet
	 */
	public long getEndTime() {
		return fEndTime;
	}
	
	/**
	 * Returns a string representation of this Function.
	 * <p>
	 * The string contains the function's index, start time, and end time
	 * in a readable format.
	 * </p>
	 *
	 * @return a string representation of this Function
	 */
	@Override
	public String toString() {
	    return "Function{" +
	    	   "index=" + fIndex +
	           ", startTime=" + fStartTime +
	           ", endTime=" + fEndTime +
	           '}';
	}
}