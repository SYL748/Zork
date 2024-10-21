package Zork;

/**
 * A class that represents the nodes inside of the tree
 * @author ShaoYang Li
 */
public class StoryTreeNode {
	static final String WIN_MESSAGE = "YOU WIN";
	static final String LOSE_MESSAGE = "YOU LOSE";
	private String position;
	private String option;
	private String message;
	private StoryTreeNode leftChild;
	private StoryTreeNode middleChild;
	private StoryTreeNode rightChild;
	/*
	 * a constructor that takes position, option, and message and creates a node
	 * @param
	 * position, option, and message
	 */
	public StoryTreeNode(String position, String option, String message) {
		this.position=position;
		this.option=option;
		this.message=message;
	}
	/*
	 * checks if the current node is leaf
	 * @returns
	 * true if node is a leaf
	 */
	public boolean isLeaf() {
		return leftChild == null && middleChild == null && rightChild == null;
	}
	/*
	 * checks if the current node is a winning node
	 * @return
	 * true if the current node is a leaf and contains the winning message
	 */
	public boolean isWinningNode() {
		return isLeaf() && message.contains(WIN_MESSAGE);
	}
	/*
	 * checks if the current node is a losing node
	 * @return
	 * true if the current node is a leaf and contains the losing message
	 */
	public boolean isLosingNode() {
		return isLeaf() && !message.contains(WIN_MESSAGE);
	}
	/*
	 * gets the position of the node
	 * @return
	 * String position
	 */
	public String getPosition() {
		return position;
	}
	/*
	 * gets the option of the node
	 * @return
	 * String option
	 */
	public String getOption() {
		return option;
	}
	/*
	 * gets the message of the node
	 * @return 
	 * String message
	 */
	public String getMessage() {
		return message;
	}
	/*
	 * gets the left child of the node
	 * @return
	 * left child node
	 */
	public StoryTreeNode getLeft() {
		return leftChild;
	}
	/*
	 * gets the right child of the node
	 * @return
	 * right child node
	 */
	public StoryTreeNode getRight() {
		return rightChild;
	}
	/*
	 * gets the middle child of the node
	 * @return
	 * middle child node
	 */
	public StoryTreeNode getMiddle() {
		return middleChild;
	}
	/*
	 * sets the position for the current node
	 * @param
	 * String position
	 */
	public void setPosition(String position) {
		this.position=position;
	}
	/*
	 * sets the option for the current node
	 * @param
	 * String option
	 */
	public void setOption(String option) {
		this.option=option;
	}
	/*
	 * sets the message for the current node
	 * @param
	 * String message
	 */
	public void setMessage(String message) {
		this.message=message;
	}
	/*
	 * sets the left child for the current node
	 * @param
	 * left child node
	 */
	public void setLeft(StoryTreeNode leftChild) {
		this.leftChild=leftChild;
	}
	/*
	 * sets the middle child for the current node
	 * @param
	 * middle child node
	 */
	public void setMiddle(StoryTreeNode middleChild) {
		this.middleChild=middleChild;
	}
	/*
	 * sets the right child for the current node
	 * @param
	 * right child node
	 */
	public void setRight(StoryTreeNode rightChild) {
		this.rightChild=rightChild;
	}
}
