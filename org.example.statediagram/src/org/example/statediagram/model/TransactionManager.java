package org.example.statediagram.model;

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
import org.eclipse.tracecompass.tmf.core.trace.ITmfContext;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;

public class TransactionManager {
	
	private static TransactionManager fInstance = null;
	private Map<ITmfTrace, List<Transaction>> fTraceTransactions = new HashMap<>();  
	private List<Transaction> fTransactions;
	private Map<ITmfTrace, Map<Integer, Long>> fFunctionToEntryIdMap = new HashMap<>();

	private TransactionManager() {
		fTransactions = new ArrayList<>();
        TmfSignalManager.registerVIP(this);
         
	}
	
	public static synchronized void dispose() {  
	    TransactionManager manager = fInstance;  
	    if (manager != null) {  
	        TmfSignalManager.deregister(manager);  
	        manager.fTransactions.clear();  
	    }  
	    fInstance = null;  
	}
	
	public static TransactionManager getInstance() {
		if (fInstance == null) {
			fInstance = new TransactionManager();
		}
		return fInstance;
	}

	public List<Transaction> getTransactions(ITmfTrace trace) {
		return fTraceTransactions.getOrDefault(trace, new ArrayList<Transaction>());
	}
	

	public void addTransaction(ITmfTrace trace, ITmfEventField eventField) {
		System.out.println(eventField);
		long time = Long.parseLong(eventField.getField("ts").getFormattedValue());
		int receiver = Integer.parseInt(eventField.getField("tid").getFormattedValue());
		String amount = eventField.getField("args/amount").getFormattedValue();
		long a;
		if (amount.startsWith("0x") || amount.startsWith("0X")) {
		    amount = amount.substring(2);
		    a = Long.parseLong(amount,16);
		    
		} else {
			 a = Long.parseLong(amount);
		}
		
		String type = eventField.getField("args/token_symbol").getFormattedValue();	
		String tokenName = eventField.getField("args/token_name").getFormattedValue();	
		System.out.println("DEBUG :" + tokenName);
		int sender = receiver;
		if ("ETH".equals(type)) {
			sender--;
		}
		
		
		fTraceTransactions.computeIfAbsent(trace, k -> new ArrayList<>()).add(new Transaction(sender, receiver, time, a, type, tokenName));
	}
	
	
	public void registerFunctionEntryId(ITmfTrace trace, int functionId, long entryId) {  
	    fFunctionToEntryIdMap.computeIfAbsent(trace, k -> new HashMap<>()).put(functionId, entryId);  
	}  
	
	public long getEntryIdForFunction(ITmfTrace trace, int functionId) {  
	    Map<Integer, Long> map = fFunctionToEntryIdMap.get(trace);  
	    return map != null ? map.getOrDefault(functionId, -1L) : -1L;  
	}
	
	@TmfSignalHandler  
    public synchronized void traceOpened(TmfTraceOpenedSignal signal) { 
		System.out.println("Signal received : " + signal);
		ITmfTrace trace = signal.getTrace();
		
		
		ITmfContext ctx = trace.seekEvent(0); 
        ITmfEvent event;

        while ((event = trace.getNext(ctx)) != null) {
            ITmfEventField content = event.getContent();
            ITmfEventField argsField = content.getField("args/amount");
            if (argsField != null) {
            	addTransaction(trace, content);
            }
        }
	}
	
	@TmfSignalHandler  
    public synchronized void traceClosed(TmfTraceClosedSignal signal) {  
		System.out.println("Signal received : " + signal);
        ITmfTrace trace = signal.getTrace();  
        fTraceTransactions.get(trace).clear();
    }  
}
