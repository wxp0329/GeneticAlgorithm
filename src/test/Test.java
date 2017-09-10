package test;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeSet;

import javax.swing.JInternalFrame;

import test.graphUI.DrawGraph;

public class Test {

	public static void main(String[] args) throws IOException {
		Graph g = new Graph();
		int num = 5;
		for (int i = 1; i <= num; i++) {
			String name = "p" + i;
			new Port(name, g);
		}
		g.generateUndirectedCompleteGraph();
		System.out.println(g.getGraph().values());
		long start = System.currentTimeMillis();
		ArrayList<Port> ports = new ArrayList<Port>(g.getPorts().values());
		for(int i = 0;i<30;i++){
			int sum = GeneticAlgorithm.fitness(ports, g.getGraph());
			System.out.println(sum + "##" + ports);
			Collections.shuffle(ports);
		}
		long end = System.currentTimeMillis();
		DrawGraph.drawGraph(g);
	}
}
