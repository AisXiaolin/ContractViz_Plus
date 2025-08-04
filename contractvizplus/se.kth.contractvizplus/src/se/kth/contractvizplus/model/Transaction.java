package se.kth.contractvizplus.model;

/**
 * Represents a transaction between two parties.
 * <p>
 * A transaction contains information about the sender, receiver, timestamp,
 * amount, type, and token name. The transaction can be used to track
 * transfers of various types of tokens or cryptocurrencies.
 * </p>
 *
 * @author Alexandre Arezes
 * @version 1.0
 * @since 1.0
 */
public class Transaction {
	
	private int fSender;
	private int fReceiver;
	private long fTime;
	private String fAmount;
	private String fType;
	private String fTokenName;

	/**
	 * Constructs a new Transaction with the specified parameters.
	 * <p>
	 * The time parameter is automatically converted from seconds to milliseconds
	 * by multiplying by 1000.
	 * </p>
	 *
	 * @param sender the identifier of the transaction sender
	 * @param receiver the identifier of the transaction receiver
	 * @param time the timestamp of the transaction in seconds (will be converted to milliseconds)
	 * @param amount the amount being transferred as a string
	 * @param type the type of the transaction (e.g., "ETH", "ERC20", etc.)
	 * @param tokenName the name of the token being transferred
	 */
	public Transaction(int sender, int receiver, long time, String amount, String type, String tokenName) {
		fSender = sender;
		fReceiver = receiver;
		fTime = time * 1000;
		fAmount = amount;
		fType = type;
		fTokenName = tokenName;
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
	 * Returns the identifier of the transaction receiver.
	 *
	 * @return the receiver identifier
	 */
	public int getReceiver() {
		return fReceiver;
	}

	/**
	 * Returns the identifier of the transaction sender.
	 *
	 * @return the sender identifier
	 */
	public int getSender() {
		return fSender;
	}

	/**
	 * Returns the timestamp of the transaction in milliseconds.
	 *
	 * @return the transaction timestamp in milliseconds
	 */
	public long getTime() {
		return fTime;
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
	 * @return the transaction type (e.g., "ETH", "ERC20", etc.)
	 */
	public String getType() {
		return fType;
	}

	/**
	 * Returns the name of the token being transferred.
	 *
	 * @return the token name
	 */
	public String getTokenName() {
		return fTokenName;
	}
	
	/**
	 * Returns a string representation of this transaction.
	 * <p>
	 * The string includes the sender, receiver, time, amount, and type
	 * of the transaction in a readable format.
	 * </p>
	 *
	 * @return a string representation of this transaction
	 */
	@Override
	public String toString() {
		return "Transaction{" +
	            "sender=" + fSender +
	            ", receiver=" + fReceiver +
	            ", time=" + fTime +
	            ", amount=" + fAmount +
	            ", type=" + fType +
	            '}';
	}
}