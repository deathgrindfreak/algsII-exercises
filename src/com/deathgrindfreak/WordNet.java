package com.deathgrindfreak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {
	
	private final Map<String, Integer> nounMap;
	private final Digraph hyperGraph;
	
   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {
	   if (synsets == null || hypernyms == null)
		   throw new NullPointerException("Please provide a proper non-null filepath for synsets and hypernyms.");

	   In syns = new In(getClass().getResource(synsets));
	   In hyps = new In(getClass().getResource(hypernyms));
	   
	   nounMap = new HashMap<String, Integer>();
	   while (syns.hasNextLine()) {
		   String[] splits = syns.readLine().split(",");
		   int id = Integer.valueOf(splits[0]);
		   for (String noun : splits[1].split(" "))
			   nounMap.put(noun, id);
	   }
	   
	   String[] edges = hyps.readAllLines();
	   hyperGraph = new Digraph(edges.length + 1);
	   for (String edge : edges) {
		   String[] v = edge.split(",");
		   hyperGraph.addEdge(Integer.valueOf(v[0]), Integer.valueOf(v[1]));
	   }
   }

   // returns all WordNet nouns
   public Iterable<String> nouns() {
	   return nounMap.keySet();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
	   if (word == null)
		   throw new NullPointerException("Word cannot be null!");

	   return nounMap.keySet().contains(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
	   if (nounA == null || nounB == null)
		   throw new NullPointerException("Nouns cannot be null!");
	   
	   Integer idA = nounMap.get(nounA);
	   Integer idB = nounMap.get(nounB);
	   if (idA == null)
		   throw new IllegalArgumentException("Noun A is not a WordNet noun!");
	   if (idB == null)
		   throw new IllegalArgumentException("Noun B is not a WordNet noun!");
	   
	   DepthFirstDirectedPaths dfsp = new DepthFirstDirectedPaths(hyperGraph, idA);
	   if (dfsp.hasPathTo(idB)) {
		   int dist = 0;
		   Iterator<Integer> it = dfsp.pathTo(idB).iterator();
		   while(it.hasNext()) {
			   it.next();
			   dist++;
		   }
		   return dist;
	   }
	   return -1;
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
	   if (nounA == null || nounB == null)
		   throw new NullPointerException("Nouns cannot be null!");

	   Integer idA = nounMap.get(nounA);
	   Integer idB = nounMap.get(nounB);
	   if (idA == null)
		   throw new IllegalArgumentException("Noun A is not a WordNet noun!");
	   if (idB == null)
		   throw new IllegalArgumentException("Noun B is not a WordNet noun!");
	   
	   return "";
   }

   // do unit testing of this class
   public static void main(String[] args) {
	   WordNet wn = new WordNet("synsets100-subgraph.txt", "hypernyms100-subgraph.txt");
   }
}
