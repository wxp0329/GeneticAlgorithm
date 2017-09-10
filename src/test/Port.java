package test;

import java.util.ArrayList;

public class Port {
	private String name;
	private Graph g;
	private int inDegree = 0;
	private int outDegre = 0;
	private ArrayList<Port> childs = new ArrayList<>();
	public Port(String name) {
		this.name = name;
		 
	}
	public Port(String name, Graph g) {
		this.name = name;
		this.g = g;
		g.addPort(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Port insertFore(Port fore, int len) {
		this.inDegree++;
		fore.outDegre++;
		g.addEdge(new Edge(fore, this, len));
		return fore;
	}

	public Port insertFore(Port fore) {
		this.inDegree++;
		fore.outDegre++;
		g.addEdge(new Edge(fore, this));
		return fore;
	}

	public Port appendAfter(Port after, int len) {
		this.outDegre++;
		after.inDegree++;
		childs.add(after);
		g.addEdge(new Edge(this, after, len));
		return after;
	}

	public Port appendAfter(Port after) {
		this.outDegre++;
		after.inDegree++;
		childs.add(after);
		g.addEdge(new Edge(this, after));
		return after;
	}
	public ArrayList<Port> getChilds(){
		return childs;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Port) {
			return ((Port) obj).getName().equals(this.name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}

	public int getInDegree() {
		return inDegree;
	}

	public void setInDegree(int inDegree) {
		this.inDegree = inDegree;
	}

	public int getOutDegre() {
		return outDegre;
	}

	public void setOutDegre(int outDegre) {
		this.outDegre = outDegre;
	}
}
