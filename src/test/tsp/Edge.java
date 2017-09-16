package test.tsp;

public class Edge {
	private Port forePort;
	private Port afterPort;
	private int len = 1;

	Edge(Port forePort, Port afterPort) {
		this.forePort = forePort;
		this.afterPort = afterPort;
	}

	Edge(Port forePort, Port afterPort, int len) {
		this.forePort = forePort;
		this.afterPort = afterPort;
		this.len = len;
	}
	public String getName(){
		return forePort.getName()+":"+afterPort.getName();
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Edge) {
			Edge obj_Edge = ((Edge) obj);
			boolean forward = obj_Edge.getForePort().equals(this.getForePort())
					&& obj_Edge.getAfterPort().equals(this.getAfterPort()) && (obj_Edge.len == this.len);
			boolean back = obj_Edge.getForePort().equals(this.getAfterPort())
					&& obj_Edge.getAfterPort().equals(this.getForePort()) && (obj_Edge.len == this.len);
			return forward || back;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.forePort.hashCode() * 13 * this.len * this.afterPort.hashCode();
	}

	@Override
	public String toString() {
		return this.len+"";
	}

	public Port getForePort() {
		return forePort;
	}

	public void setForePort(Port forePort) {
		this.forePort = forePort;
	}

	public Port getAfterPort() {
		return afterPort;
	}

	public void setAfterPort(Port afterPort) {
		this.afterPort = afterPort;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}



}
