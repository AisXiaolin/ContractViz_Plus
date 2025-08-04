package se.kth.contractvizplus.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.tracecompass.tmf.core.event.ITmfEvent;
import org.eclipse.tracecompass.tmf.core.event.ITmfEventField;
import org.eclipse.tracecompass.tmf.core.signal.TmfSignalHandler;
import org.eclipse.tracecompass.tmf.core.signal.TmfSignalManager;
import org.eclipse.tracecompass.tmf.core.signal.TmfTraceClosedSignal;
import org.eclipse.tracecompass.tmf.core.signal.TmfTraceOpenedSignal;
import org.eclipse.tracecompass.tmf.core.signal.TmfTraceSelectedSignal;
import org.eclipse.tracecompass.tmf.core.signal.TmfWindowRangeUpdatedSignal;
import org.eclipse.tracecompass.tmf.core.timestamp.TmfTimeRange;
import org.eclipse.tracecompass.tmf.core.trace.ITmfContext;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;
import org.eclipse.tracecompass.tmf.core.trace.TmfTraceManager;

/**
 * Singleton manager for handling transactions and functions across different TMF traces.
 * <p>
 * This manager processes trace events to extract transaction and function execution information,
 * maintains mappings between functions and their entry IDs, and responds to various TMF signals
 * for trace lifecycle management. It also handles UI view updates when traces are selected.
 * </p>
 *
 * @author Alexandre Arezes
 * @version 1.0
 * @since 2025-06-17
 */
public class TransactionManager {
	
	private static TransactionManager fInstance = null;
	private Map<ITmfTrace, List<Transaction>> fTraceTransactions = new HashMap<>();  
	private Map<ITmfTrace, List<Function>> fTraceFunctions = new HashMap<>();
	private Map<ITmfTrace, Map<Integer, Long>> fFunctionToEntryIdMap = new HashMap<>();
	private ITmfTrace fCurrentTrace;
	private long fSizeArrow;
	private Map<ITmfTrace, Integer> fDepth = new HashMap<>();

	/**
	 * Private constructor for singleton pattern.
	 * Initializes the current trace and registers for TMF signal notifications.
	 */
	private TransactionManager() {
		fCurrentTrace = TmfTraceManager.getInstance().getActiveTrace();
        TmfSignalManager.registerVIP(this);         
	}
	
	/**
	 * Disposes the singleton instance and unregisters from signal management.
	 * This method is thread-safe and should be called when the manager is no longer needed.
	 */
	public static synchronized void dispose() {  
	    TransactionManager manager = fInstance;  
	    if (manager != null) {  
	        TmfSignalManager.deregister(manager);  
	    }  
	    fInstance = null;  
	}
	
	/**
	 * Returns the singleton instance of {@link TransactionManager}.
	 *
	 * @return the singleton instance
	 */
	public static TransactionManager getInstance() {
		if (fInstance == null) {
			fInstance = new TransactionManager();
		}
		return fInstance;
	}

	/**
	 * Returns the list of transactions for the specified trace.
	 *
	 * @param trace the {@link ITmfTrace} to get transactions for
	 * @return a list of {@link Transaction} objects, or an empty list if none exist
	 */
	public List<Transaction> getTransactions(ITmfTrace trace) {
		return fTraceTransactions.getOrDefault(trace, new ArrayList<Transaction>());
	}
	
	/**
	 * Returns the list of functions for the current trace.
	 *
	 * @return a list of {@link Function} objects for the current trace, or an empty list if none exist
	 */
	public List<Function> getFunctions() {
		return fTraceFunctions.getOrDefault(fCurrentTrace, new ArrayList<Function>());
	}
	
	/**
	 * Returns the current arrow size used for visualization.
	 * The arrow size is calculated based on the time range and is used for scaling purposes.
	 *
	 * @return the current arrow size
	 */
	public long getSizeArrow() {
		return fSizeArrow;
	}

	/**
	 * Returns the depth multiplied by a scaling factor for the current trace.
	 * The depth represents the number of concurrent function executions.
	 *
	 * @return the scaled depth (depth * 1.7) for the current trace
	 */
	public int getDepth() {
		return (int) ((int) fDepth.getOrDefault(fCurrentTrace, 0)*1.7);
	}
	
	/**
	 * Adds a transaction extracted from a trace event field.
	 * <p>
	 * This method parses various fields from the event to construct a {@link Transaction}
	 * object and adds it to the transaction list for the specified trace.
	 * </p>
	 *
	 * @param trace the {@link ITmfTrace} the transaction belongs to
	 * @param eventField the {@link ITmfEventField} containing transaction data
	 */
	public void addTransaction(ITmfTrace trace, ITmfEventField eventField) {
	    ITmfEventField tsField = eventField.getField("ts");  
	    long time = (tsField != null) ? Long.parseLong(tsField.getFormattedValue()) : 0;  
	      
	    ITmfEventField tidField = eventField.getField("tid");  
	    int receiver = (tidField != null) ? Integer.parseInt(tidField.getFormattedValue()) : 0;  
	      
	    ITmfEventField amountField = eventField.getField("args/amount");  
	    String amount = (amountField != null) ? amountField.getFormattedValue() : "	";  
	      
	    ITmfEventField tokenSymbolField = eventField.getField("args/token_symbol");  
	    String type = (tokenSymbolField != null) ? tokenSymbolField.getFormattedValue() : "";  
	      
	    ITmfEventField tokenNameField = eventField.getField("args/token_name");  
	    String tokenName = (tokenNameField != null) ? tokenNameField.getFormattedValue() : "";  
	      	
		int sender = receiver;
		if ("ETH".equals(type)) {
			sender--;
		}
		fTraceTransactions.computeIfAbsent(trace, k -> new ArrayList<>()).add(new Transaction(sender, receiver, time, amount, type, tokenName));
	}
	
	/**
	 * Registers a mapping between a function ID and its corresponding entry ID for a trace.
	 *
	 * @param trace the {@link ITmfTrace} the function belongs to
	 * @param functionId the function identifier
	 * @param entryId the entry identifier to associate with the function
	 */
	public void registerFunctionEntryId(ITmfTrace trace, int functionId, long entryId) {  
	    fFunctionToEntryIdMap.computeIfAbsent(trace, k -> new HashMap<>()).put(functionId, entryId);  
	}  
	
	/**
	 * Returns the entry ID for a given function ID in the specified trace.
	 *
	 * @param trace the {@link ITmfTrace} to search in
	 * @param functionId the function identifier
	 * @return the corresponding entry ID, or -1 if not found
	 */
	public long getEntryIdForFunction(ITmfTrace trace, int functionId) {  
	    Map<Integer, Long> map = fFunctionToEntryIdMap.get(trace);  
	    return map != null ? map.getOrDefault(functionId, -1L) : -1L;  
	}
	
	/**
	 * Signal handler for trace opened events.
	 * <p>
	 * This method processes all events in the opened trace to extract function execution
	 * information and transactions. It identifies function start ('B') and end ('E') events
	 * to build a complete picture of function executions, and processes transaction events.
	 * </p>
	 *
	 * @param signal the {@link TmfTraceOpenedSignal} event
	 */
	@TmfSignalHandler  
    public synchronized void traceOpened(TmfTraceOpenedSignal signal) { 
		System.out.println("Signal received : " + signal);
		ITmfTrace trace = signal.getTrace();
		fCurrentTrace = trace;
		ITmfContext ctx = trace.seekEvent(0); 
		

        ITmfEvent event;
        
        Map<Integer, List<Function>> functions = new HashMap<>();
        int index = 0;
        
        
        while ((event = trace.getNext(ctx)) != null) {
            ITmfEventField content = event.getContent();
            
            if("B".equals( content.getField("ph").getFormattedValue())) {
            	functions
	                .computeIfAbsent(Integer.parseInt(content.getField("tid").getFormattedValue()), k -> new ArrayList<>())
	                .add(new Function(index++, Long.parseLong(content.getField("ts").getFormattedValue())));
            } else if("E".equals( content.getField("ph").getFormattedValue())) {
            	List<Function> fs = functions
            		.computeIfAbsent(Integer.parseInt(content.getField("tid").getFormattedValue()), k -> new ArrayList<>());
            	fs.get(fs.size()-1).setEndTime(Long.parseLong(content.getField("ts").getFormattedValue()));
            }
            
            ITmfEventField argsField = content.getField("args/amount");
            if (argsField != null) {
            	addTransaction(trace, content);
            }
        }
        
        Map<Integer, Function> tmp = new HashMap<>();
        for (List<Function> functionList :functions.values()) {
        	for (Function function : functionList) {
        		tmp.put(function.getIndex(), function);
        	}
        }
        
        fDepth.put(fCurrentTrace, functions.size());
        
        List<Function> finalFunctions = new ArrayList<>();
        for (int i = 0; i < tmp.size(); i++) {
        	finalFunctions.add(tmp.get(i));
        }
        fTraceFunctions.put(trace, finalFunctions);
        
        StringBuilder pathBuilder = new StringBuilder();
		pathBuilder.append("./addFiles/");
		pathBuilder.append(trace.getName().substring(0, trace.getName().length()-5));
		pathBuilder.append("_storage_report.json");
		
		
        TransactionParser.parseTransactions(fCurrentTrace, pathBuilder.toString());
        fSizeArrow = fCurrentTrace.getEndTime().getValue() - fCurrentTrace.getStartTime().getValue();
		fSizeArrow /= 50;
        
        
	}
	
	/**
	 * Signal handler for trace closed events.
	 * <p>
	 * Cleans up all data associated with the closed trace, including transactions,
	 * functions, and state machine data.
	 * </p>
	 *
	 * @param signal the {@link TmfTraceClosedSignal} event
	 */
	@TmfSignalHandler  
    public synchronized void traceClosed(TmfTraceClosedSignal signal) {  
		System.out.println("Signal received : " + signal);
        ITmfTrace trace = signal.getTrace();  
        fTraceTransactions.get(trace).clear();
        fTraceFunctions.get(trace).clear();
        StateMachineManager.getInstance().clear(trace);
    }  
	
	/**
	 * Signal handler for trace selection events.
	 * <p>
	 * Updates the current trace and recalculates the arrow size. Also triggers
	 * the opening of relevant UI views in the Eclipse workbench for trace analysis.
	 * </p>
	 *
	 * @param signal the {@link TmfTraceSelectedSignal} event
	 */
	@TmfSignalHandler  
	public void traceSelected(TmfTraceSelectedSignal signal) {		
		ITmfTrace trace = signal.getTrace();
		
		if (trace == fCurrentTrace) {
			return;
		}
		
		
		fCurrentTrace = trace;
		fSizeArrow = fCurrentTrace.getEndTime().getValue() - fCurrentTrace.getStartTime().getValue();
		fSizeArrow /= 50;
	}
	
	/**
	 * Signal handler for window range update events.
	 * <p>
	 * Recalculates the arrow size based on the new time range for proper visualization scaling.
	 * </p>
	 *
	 * @param signal the {@link TmfWindowRangeUpdatedSignal} event
	 */
	@TmfSignalHandler  
	public void intervalChanged(TmfWindowRangeUpdatedSignal signal) {		
		TmfTimeRange range = signal.getCurrentRange();
		fSizeArrow = range.getEndTime().getValue() - range.getStartTime().getValue();
		fSizeArrow /= 50;
	}
		
}