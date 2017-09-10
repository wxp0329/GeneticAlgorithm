package test;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JInternalFrame;

import test.graphUI.DrawGraph;

public class Test {

	public static void main(String[] args) throws IOException {
		Graph g = new Graph();
		long start = System.currentTimeMillis();
		g.generateUndirectedCompleteGraph("C:\\Users\\Administrator\\Desktop\\my.graph",5000);
//		g.loadUndirectedCompleteGraph("C:\\Users\\Administrator\\Desktop\\my.graph");
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}
