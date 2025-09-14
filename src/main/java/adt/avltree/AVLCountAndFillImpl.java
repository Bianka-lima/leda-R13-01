package adt.avltree;

import adt.bst.BSTNode;
import adt.bt.Util;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends
		AVLTreeImpl<T> implements AVLCountAndFill<T> {

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;

	public AVLCountAndFillImpl() {
		
	}

	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	@Override
	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}


	@Override
	public void fillWithoutRebalance(T[] array) {

		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				insertion(array[i]);
			}
		}
	}

	public void insertion(T element) {
 		if (element != null) {
			recursiveInsert(this.root, element);
		}
	}

	private void recursiveInsert(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			((BSTNode<T>) node.getLeft()).setParent(node);
			((BSTNode<T>) node.getRight()).setParent(node);
		} else {
			if (element.compareTo(node.getData()) > 0) {
				recursiveInsert((BSTNode<T>)node.getRight(), element);
			} else {
				recursiveInsert((BSTNode<T>)node.getLeft(), element);
			}
		}
	}

	@Override
	protected void rebalance(BSTNode<T> node) {
		if (node != null && !node.isEmpty()) {

			int balance = calculateBalance(node);
			if (Math.abs(balance) > 1) {
				
				if (balance > 1) {
					if (calculateBalance((BSTNode<T>)node.getLeft()) >= 0) {
						node = Util.rightRotation(node);
						LLcounter++;
					} else {
						node.setLeft(Util.leftRotation((BSTNode<T>)node.getLeft()));
						node = Util.rightRotation(node);
						LRcounter++;
					}
				
				} else if (balance < -1) {
					if (calculateBalance((BSTNode<T>)node.getRight()) <= 0) {
						node = Util.leftRotation(node);
						RRcounter++;
					} else {
						node.setRight(Util.rightRotation((BSTNode<T>)node.getRight()));
						node = Util.leftRotation(node);
						RLcounter++;
					}
				}
				if (node.getParent() == null) {
					this.root = node;
				}
			} 
		}
	}

}
