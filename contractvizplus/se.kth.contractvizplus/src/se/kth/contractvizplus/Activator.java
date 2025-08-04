package se.kth.contractvizplus;

import org.eclipse.tracecompass.common.core.TraceCompassActivator;
import se.kth.contractvizplus.model.TransactionManager;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends TraceCompassActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "se.kth.contractvizplus"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
		super(PLUGIN_ID);
		TransactionManager.getInstance();
	}


	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	@Override
	protected void startActions() {
		plugin = this;
		
	}

	@Override
	protected void stopActions() {
		plugin = null;
		TransactionManager.dispose();
	}

}
