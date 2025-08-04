package se.kth.contractvizplus.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.tracecompass.tmf.core.CustomColorPaletteProvider;
import org.eclipse.tracecompass.tmf.core.presentation.RGBAColor;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;
import org.json.*;

/**
 * TransactionParser parses transaction state changes from a JSON file and updates state graphs.
 * <p>
 * This utility reads a JSON file containing sequential state changes, creates or updates
 * state graphs for each contract address, and adds new states and transitions. It also
 * generates tooltips for each state based on the JSON node data.
 * </p>
 *
 * <ul>
 *   <li>Integrates with StateMachineManager and TransactionManager singletons.</li>
 *   <li>Uses a color palette to assign colors to states based on step index.</li>
 *   <li>Shortens addresses and keys for display.</li>
 * </ul>
 *
 * @author Alexandre Arezes
 * @since 1.0
 */
public class TransactionParser {
	
	

	/**
	 * Parses a JSON file containing sequential state changes and updates the state graphs.
	 *
	 * @param trace the trace context for which to update graphs
	 * @param p the path to the JSON file with transaction state changes
	 */
	public static void parseTransactions(ITmfTrace trace, String p) {
		Path path = Paths.get(p);
		try {
			String string = Files.readString(path);
			JSONObject s = new JSONObject(string);
			JSONArray changesArray = s.getJSONArray("sequential_changes");
		
			StateMachineManager stateMachineManager = StateMachineManager.getInstance();	
			List<Function> functions = TransactionManager.getInstance().getFunctions();
			
			
			
			
			for (int i = 0; i < changesArray.length(); i++) {
				JSONObject node = changesArray.getJSONObject(i);
				StateGraph graph = stateMachineManager.getOrCreateGraph(trace, shorten(node.getString("address")));
								
				Function function = functions.get(node.getInt("node_idx"));
				CustomColorPaletteProvider colorPaletteProvider = CustomColorPaletteProvider.getInstance();
				RGBAColor color = colorPaletteProvider.getColor((long)node.getInt("step_idx"));
				State state = new State(shorten( node.getString("key")), color, function.getStartTime(), function.getEndTime());
				int transitionIndex = graph.getStates().lastIndexOf(state);
				if (transitionIndex != -1) {
					graph.getState(transitionIndex).addTransition(null, state);
				}
				
				StringBuilder tooltipBuilder = new StringBuilder();
				for (String key : node.keySet()) {
					tooltipBuilder.append(key + "\t" + node.get(key) + "\n");
				}
				state.setTooltip(tooltipBuilder.toString());
				
				graph.addState(state);	
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Shortens a string for display, keeping the first 4 and last 3 characters.
	 *
	 * @param str the string to shorten
	 * @return the shortened string, or the original if too short
	 */
	private static String shorten(String str) {
        if (str == null || str.length() <= 7) {
            return str;
        }

        String start = str.substring(0, 4);
        String end = str.substring(str.length() - 3);

        return start + "..." + end;
    }

}
