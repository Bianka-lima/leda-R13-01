package adt.avltree;

import adt.bst.BSTNode;

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

}
