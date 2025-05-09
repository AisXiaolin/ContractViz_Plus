package org.example.statediagram.views;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles; 

public class StateDiagramView extends ViewPart {

    public static final String ID = "org.example.statediagram.views.statediagramview";

    private Graph graph;

    @Override
    public void createPartControl(Composite parent) {
        graph = new Graph(parent, SWT.NONE);

        // Créer des nœuds (états)
        GraphNode unprivileged = new GraphNode(graph, SWT.NONE);
        unprivileged.setText("Unprivileged");
        GraphNode privileged = new GraphNode(graph, SWT.NONE);
        privileged.setText("Privileged");
        GraphNode exploited = new GraphNode(graph, SWT.NONE);
        exploited.setText("Exploited");

        graph.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Object selection = e.item;

                if (selection instanceof GraphNode) {
                    GraphNode node = (GraphNode) selection;
                    String label = node.getText();

                    // Ton action ici :
                    System.out.println("Noeud cliqué : " + label);

                    // Par exemple : ouvrir un message
                    MessageDialog.openInformation(Display.getCurrent().getActiveShell(),
                        "Nœud sélectionné", "Vous avez cliqué sur : " + label);
                }
            }
        });
        

        // Créer des flèches (transitions)
        GraphConnection arrow1 = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, unprivileged, privileged);
        arrow1.setCurveDepth(5);
        GraphConnection arrow2 = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, privileged, unprivileged);
        arrow2.setCurveDepth(5);
        new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, privileged, exploited);

        GraphConnection loop = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, unprivileged, unprivileged);
        loop.setCurveDepth(50);
        
        unprivileged.setLocation(100, 100);
        privileged.setLocation(250, 100);
        exploited.setLocation(400, 100);
        
        // Layout automatique
    }

    @Override
    public void setFocus() {
        graph.setFocus();
    }
}
