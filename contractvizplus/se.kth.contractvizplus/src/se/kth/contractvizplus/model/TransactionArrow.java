package se.kth.contractvizplus.model;

import org.eclipse.tracecompass.tmf.core.model.OutputElementStyle;
import org.eclipse.tracecompass.tmf.core.model.timegraph.ITimeGraphArrow;
import org.eclipse.tracecompass.tmf.core.model.timegraph.TimeGraphArrow;

/**
 * Represents a transaction arrow in a time graph visualization.
 * <p>
 * This class extends {@link TimeGraphArrow} to represent transactions between
 * entities in a time graph. It includes additional information such as the
 * transaction amount, type, and token name. The arrow can be used to visualize
 * cryptocurrency or token transfers in a timeline view.
 * </p>
 *
 * @author Alexandre Arezes
 * @version 1.0
 * @since 1.0
 */
public class TransactionArrow extends TimeGraphArrow implements ITimeGraphArrow {

    private String fType;
    private String fTokenName;
    private String fAmount;

    /**
     * Constructs a new TransactionArrow with basic transaction information.
     *
     * @param sourceId the identifier of the source entity
     * @param destinationId the identifier of the destination entity
     * @param time the timestamp of the transaction
     * @param duration the duration of the transaction
     * @param amount the amount being transferred
     */
    public TransactionArrow(long sourceId, long destinationId, long time, long duration, String amount) {
        super(sourceId, destinationId, time, duration);
        fAmount = amount;
    }

    /**
     * Constructs a new TransactionArrow with complete transaction information.
     *
     * @param sourceId the identifier of the source entity
     * @param destinationId the identifier of the destination entity
     * @param time the timestamp of the transaction
     * @param duration the duration of the transaction
     * @param amount the amount being transferred
     * @param type the type of the transaction (e.g., "ETH", "ERC20")
     * @param tokenName the name of the token being transferred
     */
    public TransactionArrow(long sourceId, long destinationId, long time, long duration, String amount, String type, String tokenName) {
        super(sourceId, destinationId, time, duration);
        fType = type;
        fTokenName = tokenName;
        fAmount = amount;
    }

    /**
     * Constructs a new TransactionArrow with styling information.
     *
     * @param sourceId the identifier of the source entity
     * @param destinationId the identifier of the destination entity
     * @param time the timestamp of the transaction
     * @param duration the duration of the transaction
     * @param amount the amount being transferred
     * @param style the visual style for the arrow
     */
    public TransactionArrow(long sourceId, long destinationId, long time, long duration, String amount, OutputElementStyle style) {
        super(sourceId, destinationId, time, duration, style);
        fAmount = amount;
    }

    /**
     * Constructs a new TransactionArrow with complete transaction information and styling.
     *
     * @param sourceId the identifier of the source entity
     * @param destinationId the identifier of the destination entity
     * @param time the timestamp of the transaction
     * @param duration the duration of the transaction
     * @param amount the amount being transferred
     * @param style the visual style for the arrow
     * @param type the type of the transaction (e.g., "ETH", "ERC20")
     * @param tokenName the name of the token being transferred
     */
    public TransactionArrow(long sourceId, long destinationId, long time, long duration, String amount, OutputElementStyle style, String type, String tokenName) {
        super(sourceId, destinationId, time, duration, style);
        fType = type;
        fTokenName = tokenName;
        fAmount = amount;
    }

    /**
     * Determines if this is a self-transaction (non-ETH transaction).
     * <p>
     * A transaction is considered a self-transaction if its type is not "ETH".
     * </p>
     *
     * @return {@code true} if this is not an ETH transaction, {@code false} otherwise
     */
    public boolean isSelfTransaction() {
        return !"ETH".equals(fType);
    }

    /**
     * Returns the type of this transaction.
     *
     * @return the transaction type (e.g., "ETH", "ERC20"), or null if not set
     */
    public String getType() {
        return fType;
    }

    /**
     * Returns the name of the token being transferred.
     *
     * @return the token name, or null if not set
     */
    public String getTokenName() {
        return fTokenName;
    }

    /**
     * Returns the amount being transferred in this transaction.
     *
     * @return the transaction amount as a string
     */
    public String getAmount() {
        return fAmount;
    }

    /**
     * Returns a string representation of this transaction arrow.
     * <p>
     * The string includes the parent class's string representation
     * followed by the token name.
     * </p>
     *
     * @return a string representation of this transaction arrow
     */
    @Override
    public String toString() {
        return super.toString() + getTokenName();
    }
}