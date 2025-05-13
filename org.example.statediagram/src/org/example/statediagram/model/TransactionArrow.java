package org.example.statediagram.model;

import org.eclipse.tracecompass.tmf.core.model.OutputElementStyle;
import org.eclipse.tracecompass.tmf.core.model.timegraph.ITimeGraphArrow;
import org.eclipse.tracecompass.tmf.core.model.timegraph.TimeGraphArrow;

public class TransactionArrow extends TimeGraphArrow implements ITimeGraphArrow{

	private String fType;
	private String fTokenName;
	

	
	public TransactionArrow(long sourceId, long destinationId, long time, long duration, long amount) {
		super(sourceId, destinationId, time, duration, amount);
	}
	public TransactionArrow(long sourceId, long destinationId, long time, long duration, long amount,  String type, String tokenName) {
		super(sourceId, destinationId, time, duration, amount);
		fType = type;
		fTokenName = tokenName;
	}
	public TransactionArrow(long sourceId, long destinationId, long time, long duration, long amount, OutputElementStyle style)  {
		super(sourceId, destinationId, time, duration, amount, style);
	}
	
	public TransactionArrow(long sourceId, long destinationId, long time, long duration, long amount, OutputElementStyle style , String type, String tokenName)  {
		super(sourceId, destinationId, time, duration, amount, style);
		fType = type;
		fTokenName = tokenName;
	}

	public boolean isSelfTransaction() {
		return !"ETH".equals(fType);
	}
	public String getType() {
		return fType;
	}
	public String getTokenName() {
		return fTokenName;
	}
	
	@Override
	public String toString() {
		return super.toString() + getTokenName();
	}
	
}
