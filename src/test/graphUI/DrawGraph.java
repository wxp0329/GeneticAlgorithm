/**
 * Copyright (c) 2007-2012, JGraph Ltd
 */
package test.graphUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxEdgeLabelLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxDomUtils;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;

import test.tsp.Edge;
import test.tsp.Graph;
import test.tsp.Port;

public class DrawGraph extends JFrame {

	Graph g;

	public DrawGraph(Graph g) {
		this.g = g;
		
		mxGraph graph = new mxGraph();
		// Sets the default vertex style
		Map<String, Object> style = graph.getStylesheet().getDefaultVertexStyle();
		style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try {
			insertPortsAndEdges(g, graph, parent);
			mxIGraphLayout layout = new mxCircleLayout(graph);
			layout.execute(parent);
		} finally {
			graph.getModel().endUpdate();
		}

		// Overrides method to create the editing value
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		mxGraphModel graphModel  = (mxGraphModel)graphComponent.getGraph().getModel(); 
		Collection<Object> cells =  graphModel.getCells().values(); 
		mxUtils.setCellStyles(graphComponent.getGraph().getModel(), 
		    cells.toArray(), mxConstants.STYLE_ENDARROW, mxConstants.NONE);
		getContentPane().add(graphComponent);

	}

	void insertPortsAndEdges(Graph g, mxGraph graph, Object parent) {
		HashMap<String, Object> hs = new HashMap<>();
		for (Port p : g.getPorts().values()) {
			hs.put(p.getName(), graph.insertVertex(parent, null, p, 20, 20, 30, 30,"fontSize=15"));
		}

		for (Edge e : g.getGraph().values()) {
			graph.insertEdge(parent, null, e, hs.get(e.getForePort().getName()), hs.get(e.getAfterPort().getName()),"strokeColor=blue;fontSize=15");
		}
	}

	public static void drawGraph(Graph g) {
		DrawGraph frame = new DrawGraph(g);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);
	}

}
