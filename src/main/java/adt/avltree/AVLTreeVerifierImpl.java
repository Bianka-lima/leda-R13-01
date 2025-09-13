package adt.avltree;

import adt.bst.BSTNode;
import adt.bst.BSTVerifierImpl;

/**
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeVerifierImpl<T extends Comparable<T>> extends BSTVerifierImpl<T> implements AVLTreeVerifier<T> {

	private AVLTreeImpl<T> avlTree;

	public AVLTreeVerifierImpl(AVLTree<T> avlTree) {
		super(avlTree);
		this.avlTree = (AVLTreeImpl<T>) avlTree;
	}

	private AVLTreeImpl<T> getAVLTree() {
		return avlTree;
	}

	@Override
	public boolean isAVLTree() {

		boolean isAVL = true;

		AVLTreeImpl<T> avl = getAVLTree();
		if (avl != null && !avl.isEmpty()) {
			isAVL= recursiveIsAVLTree(avl.getRoot());
		}

		return isBST() && isAVL;
	}

	private boolean recursiveIsAVLTree(BSTNode<T> node) {
		boolean isAVL = true;
		if (node != null && !node.isEmpty()) {

			if (Math.abs(calculateBalance(node)) > 1) {
				isAVL = false;
			} else {
				isAVL = recursiveIsAVLTree((BSTNode<T>)node.getLeft()) && recursiveIsAVLTree((BSTNode<T>)node.getRight());
			}
		}
		return isAVL;
	}

	protected int calculateBalance(BSTNode<T> node) {
		int balance = 0;
		if (node != null && !node.isEmpty()) {
			balance = recursiveHeight((BSTNode<T>)node.getLeft()) - recursiveHeight((BSTNode<T>)node.getRight());
		}
		return balance;
	}

	public int recursiveHeight(BSTNode<T> node) {
		int height = -1;
		if (node != null && !(node.isEmpty())) {
			height = 1 + Math.max(recursiveHeight((BSTNode<T>)node.getLeft()), recursiveHeight((BSTNode<T>)node.getRight()));
		}
		return height;
	}

}
