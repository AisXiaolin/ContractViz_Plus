package org.example.statediagram.views;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class ContractVizPlusPerspectiveFactory implements IPerspectiveFactory {
	
    /** Perspective ID */
    public static final String ID = "org.example.statediagram.views.perspective"; //$NON-NLS-1$

    // Open tracing views
    private static final String FLAME_CHART_VIEW_ID = "org.eclipse.tracecompass.analysis.profiling.ui.flamechart:org.eclipse.tracecompass.incubator.traceevent.analysis.callstack"; //$NON-NLS-1$
    private static final String STATISTICS_VIEW_ID = "org.eclipse.linuxtools.tmf.ui.views.statistics"; //$NON-NLS-1$
    private static final String STATE_CHANGES_VIEW_ID = "org.example.statediagram.views.statediagramview"; //$NON-NLS-1$
    private static final String GAS_USED_VIEW_ID = "org.eclipse.linuxtools.internal.tmf.analysis.xml.ui.views.xyview:Ethereum Fee Per Function"; //$NON-NLS-1$

//    // Standard Eclipse views
    private static final String PROJECT_VIEW_ID = IPageLayout.ID_PROJECT_EXPLORER;

	@Override
    public void createInitialLayout(IPageLayout layout) {

        // Editor area
        layout.setEditorAreaVisible(true);        
        
        IFolderLayout topLeftFolder = layout.createFolder(
                "topLeftFolder", IPageLayout.LEFT, 0.10f, IPageLayout.ID_EDITOR_AREA); //$NON-NLS-1$
        topLeftFolder.addView(PROJECT_VIEW_ID);


        IFolderLayout right = layout.createFolder(
                "right", IPageLayout.RIGHT, 0.75f, IPageLayout.ID_EDITOR_AREA); //$NON-NLS-1$
        right.addView(STATISTICS_VIEW_ID); //$NON-NLS-1$

        IFolderLayout bright = layout.createFolder(
                "bright", IPageLayout.BOTTOM, 0.60f, "right"); //$NON-NLS-1$ //$NON-NLS-2$
        bright.addView(STATE_CHANGES_VIEW_ID); //$NON-NLS-1$


        IFolderLayout topRightFolder = layout.createFolder(
                "topRightFolder", IPageLayout.TOP, 0.95f, IPageLayout.ID_EDITOR_AREA); //$NON-NLS-1$
        topRightFolder.addView(FLAME_CHART_VIEW_ID); //$NON-NLS-1$


        IFolderLayout bottomRightFolder = layout.createFolder(
                "bottomRightFolder", IPageLayout.BOTTOM,0.60f , "topRightFolder"); //$NON-NLS-1$ //$NON-NLS-2$

        bottomRightFolder.addPlaceholder(GAS_USED_VIEW_ID); //$NON-NLS-1$


        
        
        

        layout.addPerspectiveShortcut(ID);
    }

}
