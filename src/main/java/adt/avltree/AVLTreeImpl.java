package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Implementacao de uma arvore AVL
 * A CLASSE AVLTree herda de BSTImpl. VOCE PRECISA SOBRESCREVER A IMPLEMENTACAO
 * DE BSTIMPL RECEBIDA COM SUA IMPLEMENTACAO "OU ENTAO" IMPLEMENTAR OS SEGUITNES
 * METODOS QUE SERAO TESTADOS NA CLASSE AVLTREE:
 *  - insert
 *  - preOrder
 *  - postOrder
 *  - remove
 *  - height
 *  - size
 *
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements
		AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		int balance = 0;
		if (node != null && !node.isEmpty()) {
			balance = recursiveHeight((BSTNode<T>)node.getLeft()) - recursiveHeight((BSTNode<T>)node.getRight());
		}
		return balance;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		if (node != null && !node.isEmpty()) {

			int balance = calculateBalance(node);
			if (Math.abs(balance) > 1) {
				
				if (balance > 1) {
					if (calculateBalance((BSTNode<T>)node.getLeft()) >= 0) {
						node = Util.rightRotation(node);
					} else {
						node.setLeft(Util.leftRotation((BSTNode<T>)node.getLeft()));
						node = Util.rightRotation(node);
					}
				
				} else if (balance < -1) {
					if (calculateBalance((BSTNode<T>)node.getRight()) <= 0) {
						node = Util.leftRotation(node);
					} else {
						node.setRight(Util.rightRotation((BSTNode<T>)node.getRight()));
						node = Util.leftRotation(node);
					}
				}
				if (node.getParent() == null) {
					this.root = node;
				}
			} 
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		BSTNode<T> aux = node;
		while (aux != null) {
			int balance = calculateBalance(aux);
			if (Math.abs(balance) > 1) {
				rebalance(aux);
			}
			aux = (BSTNode<T>)aux.getParent(); 
		}
	}
}
