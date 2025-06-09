package org.eclipse.tracecompass.tmf.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.tracecompass.segmentstore.core.ISegment;
import org.eclipse.tracecompass.tmf.core.event.ITmfEvent;
import org.eclipse.tracecompass.tmf.core.event.ITmfEventField;
import org.eclipse.tracecompass.tmf.core.model.OutputElementStyle;
import org.eclipse.tracecompass.tmf.core.model.StyleProperties;
import org.eclipse.tracecompass.tmf.core.presentation.IPaletteProvider;
import org.eclipse.tracecompass.tmf.core.presentation.RGBAColor;
import org.eclipse.tracecompass.tmf.core.trace.ITmfContext;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;
import org.eclipse.tracecompass.tmf.core.trace.TmfTraceManager;


/**
 *
 */
public class CustomColorPaletteProvider implements IPaletteProvider{

    /**
     * Class to manage the colors of the ContractViz+ views
     *
     * @author Alexandre Arezes
     */
    @NonNull
    public static final RGBAColor CYAN = new RGBAColor(102, 197, 204);

    /**
     * Gets an instance of {@link RGBAColor} that represents orange color
     */

    @NonNull
    public static final RGBAColor ORANGE = new RGBAColor(246, 207, 113);

    /**
     * Gets an instance of {@link RGBAColor} that represents red color
     */
    @NonNull
    public static final RGBAColor RED = new RGBAColor(248, 156, 116);

    /**
     * Gets an instance of {@link RGBAColor} that represents light purple color
     */
    @NonNull
    public static final RGBAColor LIGHT_PURPLE = new RGBAColor(220,176,242);

    /**
     * Gets an instance of {@link RGBAColor} that represents green color
     */
    @NonNull
    public static final RGBAColor GREEN = new RGBAColor(135,197,95);

    /**
     * Gets an instance of {@link RGBAColor} that represents blue color
     */
    @NonNull
    public static final RGBAColor BLUE = new RGBAColor(158, 185, 243);

    /**
     * Gets an instance of {@link RGBAColor} that represents pink color
     */
    @NonNull
    public static final RGBAColor PINK = new RGBAColor(254, 136, 177);

    /**
     * Gets an instance of {@link RGBAColor} that represents yellow color
     */
    @NonNull
    public static final RGBAColor YELLOW = new RGBAColor(201, 219, 116);

    /**
     * Gets an instance of {@link RGBAColor} that represents light green color
     */
    @NonNull
    public static final RGBAColor LIGHT_GREEN = new RGBAColor(139, 224, 164);

    /**
     * Gets an instance of {@link RGBAColor} that represents purple color
     */
    @NonNull
    public static final RGBAColor PURPLE = new RGBAColor(180, 151, 231);

    /**
     * Gets an instance of {@link RGBAColor} that represents brown color
     */
    @NonNull
    public static final RGBAColor BROWN = new RGBAColor(211, 180, 132);

    /**
     * Gets an instance of {@link RGBAColor} that represents gray color
     */
    @NonNull
    public static final RGBAColor GRAY = new RGBAColor(179, 179, 179);

    private static final List<@NonNull RGBAColor> PALETTE = Arrays.asList(
            CYAN, ORANGE, RED, LIGHT_PURPLE,
            GREEN, BLUE, PINK, YELLOW,
            LIGHT_GREEN, PURPLE, BROWN, GRAY);

    /**
     * Get the default default color palette provider
     */
    public static CustomColorPaletteProvider fInstance = new CustomColorPaletteProvider();

    private final Map<Long, RGBAColor> assignedColors = new HashMap<>();
    private int currentIndex = 0;

    private CustomColorPaletteProvider() {
        // do nothing
    }

    public static CustomColorPaletteProvider getInstance() {
        if (fInstance == null) {
            fInstance = new CustomColorPaletteProvider();
        }
        return fInstance;
    }

    @Override
    public List<@NonNull RGBAColor> get() {
        return PALETTE;
    }



    /**
     * @param name
     * @return
     */
    public RGBAColor getColor(Long name) {
        return assignedColors.computeIfAbsent(name, key -> {
            RGBAColor color = PALETTE.get(currentIndex % PALETTE.size());
            currentIndex++;
            return color;
        });
    }

    /**
     * @param name
     * @param state
     * @return
     */
    public OutputElementStyle getStyleFor(String name, ISegment state) {
        HashMap<String, Object> style = new HashMap<>();
        RGBAColor color = getColor((long)name.hashCode());
        String hexColor = toHexColor(color);
        state.getClass();
        style.put(StyleProperties.BACKGROUND_COLOR, hexColor);






        ITmfTrace trace = TmfTraceManager.getInstance().getActiveTrace();
        if (trace !=null) {

            ITmfContext ctx = trace.seekEvent(0);
            ITmfEvent event;

            while ((event = trace.getNext(ctx)) != null) {
                ITmfEventField content = event.getContent();
                if (name.equals(content.getField("name").getFormattedValue()) && String.valueOf(state.getStart()/1000).equals(content.getField("ts").getFormattedValue())) {

                    ITmfEventField argsField = content.getField("args/success");
                    if (argsField != null) {
                        if ("false".equals(argsField.getFormattedValue())) {
                            style.put(StyleProperties.BORDER_COLOR, "#ff0000");
                            style.put(StyleProperties.BORDER_WIDTH, 2);
                            style.put(StyleProperties.BORDER_STYLE, "");
                        }

                    }
                }
            }
        }

        return new OutputElementStyle("1", style);
    }

    private static String toHexColor(RGBAColor color) {
        final String HEX_COLOR_FORMAT = "#%02x%02x%02x"; //$NON-NLS-1$

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        return String.format(HEX_COLOR_FORMAT, r, g, b);
    }

}