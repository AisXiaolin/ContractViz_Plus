package org.example.statediagram.views;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.tracecompass.tmf.core.signal.TmfSignalManager;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.example.statediagram.model.State;
import org.example.statediagram.model.StateGraph;
import org.example.statediagram.model.StateMachineManager;
import org.example.statediagram.model.TransactionManager;
import org.example.statediagram.signal.NodeSelectedSignal; 

public class StateDiagramView extends ViewPart {

    public static final String ID = "org.example.statediagram.views.statediagramview";

    private CTabFolder fTabFolder;
    private Composite fParentComposite;
    private TransactionManager fTransactionManager;
    
    @Override
    public void createPartControl(Composite parent) {
    	fTransactionManager = TransactionManager.getInstance();
    	fTransactionManager.getClass();
    	TmfSignalManager.register(this);  
    	fParentComposite = parent;    	    	
    	
    	fTabFolder = new CTabFolder(parent, SWT.BORDER);
        fTabFolder.setSimple(false);
        fTabFolder.setTabHeight(30);
        
        
        fTabFolder.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	CTabItem selectedTab = fTabFolder.getSelection();
                if (selectedTab != null) {
                    Control control = selectedTab.getControl();
                    if (control instanceof Composite) {
                        Composite composite = (Composite) control;
                        for (Control child : composite.getChildren()) {
                            if (child instanceof Graph) {
                                ((Graph) child).setSelection(new GraphItem[0]);
                            }
                        }
                    }
                    NodeSelectedSignal nodeSelectedSignal = new NodeSelectedSignal(this,  null);
                    TmfSignalManager.dispatchSignal(nodeSelectedSignal);
                    refresh();
                }
            }
        });
        
        fParentComposite.addControlListener(new ControlAdapter() {
            @Override
            public void controlResized(ControlEvent e) {
                
                refresh();
            }
        });
        
//    	creating pages
		StateGraph graph1 = new StateGraph("Contract 1");
//	state is for nodes [remember to append -> 000L]
		State unprivileged = new State("Unprivileged", new Color(245, 156, 154), 1644207699000L, 1644208199000L);
		State privileged = new State("Privileged", new Color(197, 219, 169), 1644209199000L, 1644209699000L);
		State exploited = new State("Exploited", new Color(158, 204, 240), 1644206199000L, 1644208799000L);
		
		unprivileged.addTransition(null, privileged);
		privileged.addTransition(null, unprivileged);
		privileged.addTransition(null, exploited);
		
		unprivileged.setTooltip("Line1:afwejfioawejoajweffwefaiwejfoaijwefoajweofjaweo\nLine2:faweufahweiufha\nLine3:fwheuifahwe");
		
		graph1.addState(unprivileged);
		graph1.addState(privileged);
		graph1.addState(exploited);
		
		StateMachineManager.getInstance().addGraph(graph1);
		
		StateGraph graph2 = new StateGraph("Contract 2");
		State s1 = new State("S1", new Color(255, 0, 0), 1644207699000L, 1644208199000L);
		State s2 = new State("S2", new Color(0, 255, 0), 1644209199000L, 1644209699000L);
		s1.addTransition(null, s2);
		
		graph2.addState(s1);
		graph2.addState(s2);
		StateMachineManager.getInstance().addGraph(graph2);
		
//		create the 3rd "page"
		
		StateGraph test3 = new StateGraph("Contract 3");
//		state is for nodes [remember to append -> 000L]
			State t1 = new State("t1", new Color(245, 156, 154), 1644207699000L, 1644208199000L);
			State t2 = new State("t2", new Color(197, 219, 169), 1644209199000L, 1644209699000L);
			State t3 = new State("t3", new Color(158, 204, 240), 1644206199000L, 1644208799000L);
			
			t1.addTransition("t1 to t2", t2);
			t2.addTransition("t2 to t3", t3);
			
			
			t1.setTooltip("Line1:afwejfioawejoajweffwefaiwejfoaijwefoajweofjaweo\nLine2:faweufahweiufha\nLine3:fwheuifahwe");
			
			test3.addState(t1);
			test3.addState(t2);
			test3.addState(t3);
			
			StateMachineManager.getInstance().addGraph(test3);
        
        //TODO: changer le nom de la classe transaction une transaction ce n'est pas juste une fleche mais lensemble des fonctions et donc doit contenir lensemble des etats possibles
		
		
		for (StateGraph graph : StateMachineManager.getInstance().getAllGraphs()) {
			createGraph(graph);
		}
    
    }
    
    
    
    public void createGraph(StateGraph stateGraph) {
        
        CTabItem tabItem = new CTabItem(fTabFolder, SWT.NONE);
        tabItem.setText(stateGraph.getName());

        Composite tabComposite = new Composite(fTabFolder, SWT.NONE);
        tabItem.setControl(tabComposite);
        tabComposite.setLayout(new GridLayout(1	, false));

        Graph graph = new Graph(tabComposite, SWT.NONE);
        graph.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Map<String, GraphNode> nodeMap = new HashMap<>();

        for (State state : stateGraph.getStates()) {
            GraphNode node = new GraphNode(graph, SWT.NONE);
            node.setText(state.getName());
            node.setBackgroundColor(new Color(255,255,255));
            node.setForegroundColor(new Color(0,0,0));
            node.setHighlightColor(state.getColor());
            Label tooltipLabel = new Label(state.getTooltip());
            node.setTooltip(tooltipLabel);
            nodeMap.put(state.getName(), node);
        }

        for (State state : stateGraph.getStates()) {
            GraphNode sourceNode = nodeMap.get(state.getName());
            for (Map.Entry<String, List<State>> entry : state.getTransitions().entrySet()) {
                String event = entry.getKey();
                for (State target : entry.getValue()) {
                    GraphNode targetNode = nodeMap.get(target.getName());
                    GraphConnection connection = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, sourceNode, targetNode);
                    connection.setLineColor(new Color(0,0,0));
                    if(event!=null) {
                    	connection.setText(event);
                    }
                }
            }
        }
        
        graph.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Object selection = e.item;

                if (selection instanceof GraphNode) {
                    GraphNode node = (GraphNode) selection;
                    String label = node.getText();
                    System.out.println("Noeud cliqué : " + label);
                    NodeSelectedSignal nodeSelectedSignal = new NodeSelectedSignal(this,  stateGraph.getState(label));
                    TmfSignalManager.dispatchSignal(nodeSelectedSignal);
                }                
            }
        });
        
        

        
        graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(TreeLayoutAlgorithm.LEFT_RIGHT), true);
        tabComposite.layout();
    }

    

    @Override
    public void setFocus() {
        fTabFolder.setFocus();
    }
    
    @Override  
    public void dispose() {  
        TmfSignalManager.deregister(this);
        TransactionManager.dispose();
        super.dispose();  
    }
    
    public void refresh() {
    	CTabItem selectedTab = fTabFolder.getSelection();
        if (selectedTab != null) {
            Control control = selectedTab.getControl();
            if (control instanceof Composite) {
                Composite composite = (Composite) control;
                for (Control child : composite.getChildren()) {
                    if (child instanceof Graph) {
                    	((Graph)child).setLayoutAlgorithm(new TreeLayoutAlgorithm(TreeLayoutAlgorithm.LEFT_RIGHT), true);
                        composite.layout();
                    }
                }
            }
        }
    	
    }
    
//    @TmfSignalHandler  
//    public void windowRangeUpdated(TmfWindowRangeUpdatedSignal signal) {  
//    	refresh();
//    }
    
}

