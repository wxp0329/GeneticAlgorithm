package test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Set;

public class Graph {
	private TreeMap<String, Edge> edge = new TreeMap<String, Edge>();
	private TreeMap<String, Port> hs = new TreeMap<String, Port>();

	public Graph() {
	}

	public void addPort(Port p) {
		hs.put(p.getName(), p);
	}

	public TreeMap<String, Port> getPorts() {
		return hs;
	}

	public void addEdge(Edge e) {
		String[] names = e.getName().split(":");
		if (edge.keySet().contains(names[0] + ":" + names[1]) || edge.keySet().contains(names[1] + ":" + names[0])) {
			return;
		}
		edge.put(e.getName(), e);
		edge.put(names[1] + ":" + names[0], new Edge(e.getAfterPort(), e.getForePort(), e.getLen()));
	}

	public TreeMap<String, Edge> getGraph() {
		return edge;
	}

	public TreeMap<String, Edge> generateUndirectedCompleteGraph() {
		Collection<Port> ports = this.getPorts().values();
		for (Port p : ports) {
			for (Port pp : ports) {
				if (p != pp) {
					p.appendAfter(pp, new Random().nextInt(100) + 10);
				}
			}
		}
		return edge;
	}
}
