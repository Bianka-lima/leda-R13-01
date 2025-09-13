package adt.bst;

/**
 * 
 * Performs consistency validations within a BST instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class BSTVerifierImpl<T extends Comparable<T>> implements BSTVerifier<T> {
	
	private BSTImpl<T> bst;

	public BSTVerifierImpl(BST<T> bst) {
		this.bst = (BSTImpl<T>) bst;
	}
	
	private BSTImpl<T> getBSt() {
		return bst;
	}

	@Override
	public boolean isBST() {
		boolean isBST = true;
		BSTImpl<T> bst = getBSt();

		if (bst != null && !bst.isEmpty()) {
			isBST = recursiveIsBST(bst.root);
		}
		return isBST;
	}

	private boolean recursiveIsBST(BSTNode<T> node) {
		boolean isBST = true;
		if (node != null) {
			BSTNode<T> left = (BSTNode<T>)node.getLeft();
			BSTNode<T> right = (BSTNode<T>)node.getLeft();
			if (left != null && left.getData().compareTo(node.getData()) > 0) {
				isBST = false;
			} else if (right != null && right.getData().compareTo(node.getData()) < 0) {
				isBST = false;
			} else if (left != null || right != null) {
				boolean isBSTL = true;
				boolean isBSTR = true;
				if (left != null) {
					isBSTL = recursiveIsBST((BSTNode<T>)left);
				}
				if (right != null) {
					isBSTR = recursiveIsBST((BSTNode<T>)right);
				}
				isBST = isBSTL && isBSTR;
			}
		}
		return isBST;
	}
	
}
