package com.practice.linkedlist;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class BinaryTree {

	public static void inOrderTraversal(Node root, List<Integer> inOrderList) {
		if(root == null) {
			return;
		}
		inOrderTraversal(root.left, inOrderList);
		inOrderList.add(root.data);
		inOrderTraversal(root.right, inOrderList);
	}
	public static void preOrderTraversal(Node root, List<Integer> preOrderList) {
		if(root == null) {
			return;
		}
		preOrderList.add(root.data);
		preOrderTraversal(root.left, preOrderList);
		preOrderTraversal(root.right, preOrderList);
	}
	public static void postOrderTraversal(Node root, List<Integer> postOrderList) {
		if(root == null) {
			return;
		}
		postOrderTraversal(root.left, postOrderList);
		postOrderTraversal(root.right, postOrderList);
		postOrderList.add(root.data);
	}

	public static void levelOrderTraversal(Node root, List<Integer> levelOrderList) {
		if(root == null) {
			return;
		}
		Queue<Node> q = new LinkedList<>();
		q.add(root);
		while(!q.isEmpty()) {
			Node currNode = q.poll();
			levelOrderList.add(currNode.data);
			if(currNode.left != null) {
				q.add(currNode.left);
			}
			if(currNode.right != null) {
				q.add(currNode.right);
			}
		}
	}

	private static Node insertNode(Node root, int data) {
		if(root == null) {
			return new Node(data);
		}
		if(data < root.data) {
			root.left = insertNode(root.left, data);
		}
		if(data > root.data) {
			root.right = insertNode(root.right, data);
		}
		return root;
	}

	public static Node createNode(int[] data) {
		Node root = null;
		for(int i=0; i< data.length; i++) {
			root = insertNode(root, data[i]);
		}
		return root;
	}

	static int preIndex = 1;
	private static Node constructTree(List<Integer> inOrder, List<Integer> preOrder, int lower, int upper) {
		if(lower > upper) {
			return null;
		}
		int rootData = preOrder.get(preIndex -1);
		preIndex++;
		Node root = new Node(rootData);
		if(lower >= upper) {
			System.out.print(rootData + " ");
			return root;
		}
		int inIndex = -1;
		for(int i=1; i <= inOrder.size() ; i++) {
			if(inOrder.get(i -1) == rootData) {
				inIndex = i;
				break;
			}
		}
		if(inIndex > lower) {
			root.left = constructTree(inOrder, preOrder, lower, inIndex -1);
		}
		if(inIndex < upper ) {
			root.right = constructTree(inOrder, preOrder, inIndex + 1, upper);
		}
		System.out.print(rootData + " ");
		return root;
	}

	private static Node getTree(List<Integer> inOrderSeries, List<Integer> preOrderSeries) {
		return constructTree(inOrderSeries, preOrderSeries, 1, preOrderSeries.size());
	}

	public static void postOrderTraversalFromInAndPreOrder () {
		int size = 10;
		int[] data = new int[size];
		for(int i=1; i <= data.length ; i++) {
			data[i-1] = ThreadLocalRandom.current().nextInt(1, 100);
		}
		System.out.println("\n===========Input Data===========");
		System.out.println(Arrays.toString(data));
		Node root = createNode(data);
		List<Integer> inOrderSeries = new ArrayList<>();
		inOrderTraversal(root, inOrderSeries);
		System.out.println("\n===========In Order Traversal===========");
		System.out.println(inOrderSeries);
		List<Integer> preOrderSeries = new ArrayList<>();
		preOrderTraversal(root, preOrderSeries);
		System.out.println("\n===========Pre Order Traversal===========");
		System.out.println(preOrderSeries);
		Node root2 = getTree(inOrderSeries, preOrderSeries);
		List<Integer> inOrderList2 = new ArrayList<>();
		inOrderTraversal(root2, inOrderList2);
		List<Integer> postOrderList2 = new ArrayList<>();
		postOrderTraversal(root2, postOrderList2);
		System.out.println("\n===========Post Order Traversal===========");
		System.out.println(postOrderList2);
		System.out.println("\n===========In Order Traversal===========");
		System.out.println(inOrderList2);
		List<Integer> preOrderList2 = new ArrayList<>();
		preOrderTraversal(root2, preOrderList2);
		System.out.println("\n===========Pre Order Traversal===========");
		System.out.println(preOrderList2);
		List<Integer> levelOrderList2 = new ArrayList<>();
		levelOrderTraversal(root2, levelOrderList2);
		System.out.println("\n===========Level Order Traversal===========");
		System.out.println(levelOrderList2);
		System.out.println("\n*******************************************");
	}

	//https://www.geeksforgeeks.org/replace-node-binary-tree-sum-inorder-predecessor-successor/
	private static int inOrderIndex = 1;
	public static void replaceInit() {
		int size = 5;
		int[] data = new int[size];
		for(int i=1; i <= data.length ; i++) {
			data[i-1] = ThreadLocalRandom.current().nextInt(1, 100);
		}
		System.out.println("\n===========Input Data===========");
		System.out.println(Arrays.toString(data));
		Node root = createNode(data);
		List<Integer> inOrderSeries = new ArrayList<>();
		inOrderTraversal(root, inOrderSeries);
		System.out.println("\n===========In Order Traversal===========");
		System.out.println(inOrderSeries);
		replaceTree(root, inOrderSeries);
		List<Integer> inOrderSeries2 = new ArrayList<>();
		inOrderTraversal(root, inOrderSeries2);
		System.out.println("\n===========In Order Traversal===========");
		System.out.println(inOrderSeries2);
	}
	private static void replaceTree(Node root, List<Integer> inOrderTraversal) {
		if(root == null) {
			return;
		}
		replaceTree(root.left, inOrderTraversal);
		int predecessor = 0;
		int successor = 0;
		if(inOrderIndex > 1) {
			predecessor = inOrderTraversal.get((inOrderIndex-1) - 1);
		}
		if(inOrderIndex < inOrderTraversal.size()) {
			successor = inOrderTraversal.get((inOrderIndex-1) + 1);
		}
		inOrderIndex++;
		root.data = predecessor + successor;
		replaceTree(root.right, inOrderTraversal);
	}

	static int count = 0;
	private static void findNthNode(Node root, int n) {
		if(root == null) {
			return;
		}
		findNthNode(root.left, n);
		count ++;
		if(count == n) {
			System.out.println(root.data + " ");
		}
		findNthNode(root.right, n);
	}
	//https://www.geeksforgeeks.org/populate-inorder-successor-for-all-nodes/
	public static void populateInOrder() {
		int size = 5;
		int[] data = new int[size];
		for(int i=1; i <= data.length ; i++) {
			data[i-1] = ThreadLocalRandom.current().nextInt(1, 100);
		}
		System.out.println("\n===========Input Data===========");
		System.out.println(Arrays.toString(data));
		Node root = createNode(data);
		List<Integer> inOrderSeries = new ArrayList<>();
		inOrderTraversal(root, inOrderSeries);
		System.out.println("\n===========In Order Traversal===========");
		System.out.println(inOrderSeries);
		findNthNode(root, 3);
	}

	private static void findNthNodePostOrder(Node root, int n) {
		if(root == null) {
			return;
		}
		findNthNodePostOrder(root.left, n);
		findNthNodePostOrder(root.right, n);
		count ++;
		if(count == n) {
			System.out.println(root.data + " ");
		}
	}
	//https://www.geeksforgeeks.org/populate-inorder-successor-for-all-nodes/
	public static void populatePostOrder() {
		int size = 15;
		int[] data = new int[size];
		for(int i=1; i <= data.length ; i++) {
			data[i-1] = ThreadLocalRandom.current().nextInt(1, 100);
		}
		System.out.println("\n===========Input Data===========");
		System.out.println(Arrays.toString(data));
		Node root = createNode(data);
		List<Integer> postOrderSeries = new ArrayList<>();
		postOrderTraversal(root, postOrderSeries);
		System.out.println("\n===========Post Order Traversal===========");
		System.out.println(postOrderSeries);
		findNthNodePostOrder(root, 8);
	}

	public static void main(String[] args) {
		populatePostOrder();
	}


}
