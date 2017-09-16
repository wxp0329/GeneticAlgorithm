package test.tsp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

	public TreeMap<String, Edge> generateUndirectedCompleteGraph(String fileName, int portsNum) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)));
			for (int i = 1; i <= portsNum; i++) {
				String name = i + "";
				new Port(name, this);
			}
			Collection<Port> ports = this.getPorts().values();
			bw.write(ports.size() + "");
			bw.newLine();
			for (Port p : ports) {
				for (Port pp : ports) {
					if (!(p.equals(pp))) {
						int len = new Random().nextInt(100) + 10;
						p.appendAfter(pp, len);
						bw.write(p + " " + pp + " " + len);
						bw.newLine();
						bw.flush();
					}
				}
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return edge;
	}

	public void loadUndirectedCompleteGraph(String fileName) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(fileName)));
			int num = Integer.valueOf(br.readLine().trim());

			for (int i = 1; i <= num; i++) {
				String name = i + "";
				new Port(name, this);
			}
			TreeMap<String, Port> ports = this.getPorts();
			String line =null;
			while ((line= br.readLine())!=null) {
				String[] strs = line.trim().split(" ");
				ports.get(strs[0]).appendAfter(ports.get(strs[1]), Integer.valueOf(strs[2]));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
