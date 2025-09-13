package adt.bst;

import java.util.ArrayList;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		int height = -1;
		if (!(isEmpty())) {
			height = recursiveHeight(this.root);
		}
		return height;
	}

	private int recursiveHeight(BSTNode<T> node) {
		int height = -1;
		if (node != null && !(node.isEmpty())) {
			height = 1 + Math.max(recursiveHeight((BSTNode<T>)node.getLeft()), recursiveHeight((BSTNode<T>)node.getRight()));
		}
		return height;
	}

	@Override
	public BSTNode<T> search(T element) {
		return recursiveSearch(this.root, element);
	}

	private BSTNode<T> recursiveSearch(BSTNode<T> node, T element) {
		BSTNode<T> resp = new BSTNode<T>();
		if (node != null && !(node.isEmpty())) {
			if (node.getData().equals(element)) {
				resp = node;
			} else if (element.compareTo(node.getData()) < 0) {
				resp = recursiveSearch((BSTNode<T>)node.getLeft(), element);
			} else {
				resp = recursiveSearch((BSTNode<T>)node.getRight(), element);
			}
		}
		return resp;
	}

	@Override
	public void insert(T element) {
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
	public BSTNode<T> maximum() {
		BSTNode<T> max = null;
		if (!(isEmpty())) {
			max = recursiveMaximum(this.root);
		}
		return max;
	}

	private BSTNode<T> recursiveMaximum(BSTNode<T> node) {
		BSTNode<T> max = node;
		if (!node.isEmpty() && !(node.getRight().isEmpty())) {
			max = recursiveMaximum((BSTNode<T>)node.getRight());
		}
		return max;
	}

	@Override
	public BSTNode<T> minimum() {
		BSTNode<T> min = null;
		if (!(isEmpty())) {
			min = recursiveMinimum(this.root);
		}
		return min;
	}

	private BSTNode<T> recursiveMinimum(BSTNode<T> node) {
		BSTNode<T> min = node;
		if (!node.isEmpty() && !(node.getLeft().isEmpty())) {
			min = recursiveMinimum((BSTNode<T>)node.getLeft());
		}
		return min;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> suc = null;
		BSTNode<T> node = search(element);
		if (node != null && !(node.isEmpty())) {
			if (!(node.getRight().isEmpty())) {
				suc = recursiveMinimum((BSTNode<T>)node.getRight());
			} else {
				BSTNode<T> aux = (BSTNode<T>)node.getParent();
				while (aux != null && !(aux.isEmpty()) && (aux.getData().compareTo(element) < 0)) {
					aux = (BSTNode<T>)aux.getParent();
				}
				suc = aux;
			}
		}
		return suc;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> pred = null;
		BSTNode<T> node = search(element);
		if (node != null && ! (node.isEmpty())) {
			if (node.getLeft() != null && !(node.getLeft().isEmpty())) {
				pred = recursiveMaximum((BSTNode<T>)node.getLeft());
			} else {
				BSTNode<T> aux = (BSTNode<T>)node.getParent();
				while (aux != null && !(aux.isEmpty()) && (element.compareTo(aux.getData()) < 0)) {
					aux = (BSTNode<T>)aux.getParent();
				}
				pred  = aux;
			}
		}
		return pred;
	}

	@Override
	public void remove(T element) {
		if (element != null) {
			BSTNode<T> toRemove = search(element);
			recursiveRemove(toRemove);
		}
	}

	private void recursiveRemove(BSTNode<T> node) {
		if (node != null && !node.isEmpty()) {
			if (node.isLeaf()) {
				node.setData(null);
				node.setLeft(null);
				node.setRight(null);
			} else if(node.getLeft().isEmpty() || node.getRight().isEmpty()) {
				BSTNode<T> filho;
				if (node.getLeft().isEmpty()) {
					filho = (BSTNode<T>)node.getRight();
				} else {
					filho = (BSTNode<T>)node.getLeft();
				}

				if (node.getParent() == null) {
					this.root = filho;
					this.root.setParent(null);
				} else {
					filho.setParent(node.getParent());
					if (filho.getData().compareTo(filho.getParent().getData()) < 0) {
						filho.getParent().setLeft(filho);
					} else {
						filho.getParent().setRight(filho);
					}
				}
 			} else {
				BSTNode<T> next = sucessor(node.getData());
				node.setData(next.getData());
				recursiveRemove(next);
			}
		}
	}

	@Override
	public T[] preOrder() {
		ArrayList<T> preOrder = new ArrayList<>();
			
		recursivePreOrder(this.root, preOrder);
		
		return preOrder.toArray((T[]) new Comparable[preOrder.size()]);
	}

	private void recursivePreOrder(BSTNode<T> node, ArrayList<T> list) {
		if (!node.isEmpty()) {
			list.add(node.getData());
			recursivePreOrder((BSTNode<T>)node.getLeft(), list);
			recursivePreOrder((BSTNode<T>)node.getRight(), list);	
		}
	}

	@Override
	public T[] order() {
		ArrayList<T> orderList = new ArrayList<>();
			
		recursiveOrder(this.root, orderList);
		
		return orderList.toArray((T[]) new Comparable[orderList.size()]);
	}

	private void recursiveOrder(BSTNode<T> node, ArrayList<T> list) {
		if (!node.isEmpty()) {
			recursiveOrder((BSTNode<T>)node.getLeft(), list);
			list.add(node.getData());
			recursiveOrder((BSTNode<T>)node.getRight(), list);	
		}
	}

	@Override
	public T[] postOrder() {
		ArrayList<T> postOrder = new ArrayList<>();
		recursivePostOrder(this.root, postOrder);
		return postOrder.toArray((T[]) new Comparable[postOrder.size()]);
	}

	private void recursivePostOrder(BSTNode<T> node, ArrayList<T> list) {
		if (!node.isEmpty()) {
			recursivePostOrder((BSTNode<T>)node.getLeft(), list);
			recursivePostOrder((BSTNode<T>)node.getRight(), list);
			list.add(node.getData());
		}
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
