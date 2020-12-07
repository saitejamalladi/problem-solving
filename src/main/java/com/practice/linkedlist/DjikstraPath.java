package com.practice.linkedlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DjikstraPath {


	private static String getPath(String[] LABELS, int[] prevVertexIndex, int destinationVertex) {
		String path = LABELS[destinationVertex];
		while(destinationVertex != 0 ) {
			if(prevVertexIndex[destinationVertex] < 0) {
				return "Path Doesnt exist";
			}
			path = LABELS[prevVertexIndex[destinationVertex]] + " -> " + path;
			destinationVertex = prevVertexIndex[destinationVertex];
		}
		return path;
	}
	private static int[] shortestPath(int[][] adjMatrix) {
		List<Integer> visited = new ArrayList<>();
		List<Integer> unVisited = new ArrayList<>();
		//Declaring Every node as Unvisited
		for(int i=0; i< adjMatrix.length; i++) {
			unVisited.add(i);
		}
		int[] shortestPath = new int[adjMatrix.length];
		shortestPath[0] =0;
		for(int i=1; i< shortestPath.length; i++) {
			shortestPath[i] = Integer.MAX_VALUE;
		}
		int[] prevVertexIndex = new int[adjMatrix.length];
		prevVertexIndex[0] = 0;
		for(int i=1; i< shortestPath.length; i++) {
			prevVertexIndex[i] = -1;
		}
		while(!unVisited.isEmpty()) {
			int shortestVertexIndex = unVisited.size();
			int minValue = Integer.MAX_VALUE;
			for(int unVisitedIndex: unVisited) {
				if(minValue > shortestPath[unVisitedIndex]) {
					minValue = shortestPath[unVisitedIndex];
					shortestVertexIndex = unVisitedIndex;
				}
			}
			unVisited.remove(unVisited.indexOf(shortestVertexIndex));
			visited.add(shortestVertexIndex);
			int currentDistance = shortestPath[shortestVertexIndex];
			for(int destVertex=0; destVertex < adjMatrix[0].length; destVertex++) {
				if(!visited.contains(destVertex) &&  adjMatrix[shortestVertexIndex][destVertex] != Integer.MAX_VALUE) {
					if(currentDistance + adjMatrix[shortestVertexIndex][destVertex] < shortestPath[destVertex]) {
						shortestPath[destVertex] = currentDistance + adjMatrix[shortestVertexIndex][destVertex];
						prevVertexIndex[destVertex] = shortestVertexIndex;
					}
				}
			}
		}
		System.out.println(Arrays.toString(shortestPath));
		return prevVertexIndex;
	}

	private static void prettyPrint (int[][] table) {
		for(int i=0; i < table.length; i++){
			for(int j=0; j < table.length; j++){
				System.out.print(String.format("%20s", table[i][j]));
			}
			System.out.println("");
		}
	}
	public static void main(String[] args) {
		final String[] ALPHABETS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		int numberOfVertices = 5;
		String[] LABELS = Arrays.copyOfRange(ALPHABETS, 0, numberOfVertices);
		int[][] adjMatrix = new int[numberOfVertices][numberOfVertices];
		for(int i=0; i < numberOfVertices; i++) {
			for(int j=i; j < numberOfVertices; j++) {
				if(i == j) {
					adjMatrix[i][j] = 0;
				} else {
					int randomValue = Integer.MAX_VALUE;
					if(ThreadLocalRandom.current().nextInt(0, 2) == 1) {
						randomValue = ThreadLocalRandom.current().nextInt(1, 11);
					}
					adjMatrix[i][j] = randomValue;
					adjMatrix[j][i] = randomValue;
				}
			}
		}

		prettyPrint(adjMatrix);
		int[] prevVertexIndices = shortestPath(adjMatrix);
		for(int i=0; i < adjMatrix.length; i++) {
			System.out.println(getPath(LABELS, prevVertexIndices, i));
		}
	}
}
