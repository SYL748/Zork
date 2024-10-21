package Zork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class that represents the tree that reads from and write to file
 * @author ShaoYang Li
 */
public class StoryTree {
	private StoryTreeNode root;
	private StoryTreeNode dummy;
	private StoryTreeNode cursor;
	private GameState state;
	
	/*
	 * default constructor to create a new tree and sets up a dummy root for the tree
	 */
	public StoryTree() {
		dummy=new StoryTreeNode("root", "root", "Hello, and welcome to Zork!");
	}
	/*
	 * get the current game state of the tree
	 * @return
	 * return the game state
	 */
	public GameState getGameState() {
		if(cursor.isLosingNode()) {
			return GameState.GAME_OVER_LOSE;
		}
		else if(cursor.isWinningNode()) {
			return GameState.GAME_OVER_LOSE;
		}
		else
			return GameState.GAME_NOT_OVER;
	}
	/*
	 * a method to read from a file with nodes in pre-order manner and construct a tree from it
	 * @param
	 * file name
	 * @throws IoException
	 * when filename is empty or its name is null
	 * @Exception try catch FileNotFoundException
	 */
	public static StoryTree readTree(String filename) throws IOException{
		StoryTree tree=new StoryTree();
		if (filename.isEmpty()||filename==null) {
			throw new IOException("filename can't be null or empty");
		}
		try {
			BufferedReader reader=new BufferedReader(new FileReader(filename));
			System.out.println("Loading game from file...\n"
					+ "File loaded!\n");
			String line;
			while((line=reader.readLine())!=null) {
				if(line.length()==0) {
					break;
				}
				String[] parts = line.split("\\|");
				String position=parts[0].trim();
				String option=parts[1].trim();
				String message=parts[2].trim();
				StoryTreeNode node=new StoryTreeNode(position, option, message);
				tree.insert(tree.getDummy(), position, node);
			}
			tree.setRoot(tree.getDummy().getLeft());
			tree.setCursor(tree.getRoot());
			reader.close();
		} catch(FileNotFoundException e){
			System.out.println("File not Exist");
			tree.setCursor(tree.getDummy());
		}
		return tree;
	}
	/*
	 * a method to transfer the current tree into a file
	 * @param
	 * file name
	 * @param
	 * tree to be saved
	 * @Exception try catch IOException
	 */
	public static void saveTree(String filename, StoryTree tree) {
		if (filename.isEmpty()||filename==null) {
			throw new IllegalArgumentException("filename can't be null or empty");
		}
		try {
			BufferedWriter writer=new BufferedWriter(new FileWriter(filename));
			writer.write(tree.preOrder(tree.getRoot()));
			writer.close();
		} catch(IOException ioe){
			System.out.println("File not Exist");
		}
		
	}
	/*
	 * insert a node into the tree 
	 * @param
	 * dummy root of the tree
	 * @param
	 * the position to be inserted
	 * @param
	 * the node to be inserted
	 */
	public void insert(StoryTreeNode dummy, String position, StoryTreeNode node) {
		if(position.length()==1) {
			switch (position) {
			case"1":
				dummy.setLeft(node);
				return;
			case"2":
				dummy.setMiddle(node);
				return;
			case"3":
				dummy.setRight(node);
				return;
			}
		}
		else {
			switch(String.valueOf(position.charAt(0))) {
			case"1":
				insert(dummy.getLeft(), position.substring(2), node);
				break;
			case"2":
				insert(dummy.getMiddle(), position.substring(2), node);
				break;
			case"3":
				insert(dummy.getRight(), position.substring(2), node);
				break;
			}
		}
	}
	/*
	 * gets the position of the cursor
	 * @return
	 * a string of the position
	 * @throws IllegalArgumentException
	 * if the cursor is null
	 */
	public String getCursorPosition() throws IllegalArgumentException{
		if(cursor==null) {
			throw new IllegalArgumentException("cursor can't be null");
		}
		return cursor.getPosition();
	}
	/*
	 * gets the cursor's message
	 * @return
	 * a string of the message
	 */
	public String getCursorMessage() {
		return cursor.getMessage();
	}
	/*
	 * gets the numbers of children of the current cursor
	 * @return
	 * the counts of child node
	 */
	public int getChildrenNum() {
		int count=0;
		if(cursor.getLeft()!=null) {
			count++;
		}
		if(cursor.getMiddle()!=null) {
			count++;
		}
		if(cursor.getRight()!=null) {
			count++;
		}
		return count;
	}
	/*
	 * gets the position and option of cursor's child node
	 * @return
	 * an 2-d String array of the position and option
	 */
	public String[][] getOptions(){
		String[][] choices=new String[getChildrenNum()][2];
		int count=0;
		if(cursor.getLeft()!=null) {
			choices[count][0]=cursor.getLeft().getPosition();
			choices[count++][1]=cursor.getLeft().getOption();
		}
		if(cursor.getMiddle()!=null) {
			choices[count][0]=cursor.getMiddle().getPosition();
			choices[count++][1]=cursor.getMiddle().getOption();
		}
		if(cursor.getRight()!=null) {
			choices[count][0]=cursor.getRight().getPosition();
			choices[count++][1]=cursor.getRight().getOption();
		}
		return choices;
	}
	/*
	 * sets the message for the cursor
	 * @param
	 * message
	 */
	public void setCursorMessage(String message) {
		cursor.setMessage(message);
	}
	/*
	 * sets the option for the cursor
	 * @param
	 * option
	 */
	public void setCursorOption(String option) {
		cursor.setOption(option);
	}
	/*
	 * reset the cursor to the root
	 */
	public void resetCursor() {
		cursor=root;
	}
	/*
	 * select a child from the cursor
	 * @param
	 * position of the child 1,2 or 3
	 * @throws InvalidArgumentException
	 * when position is null or empty
	 * @throws NodeNotPresentException
	 * when the child at given position doesn't exist
	 */
	public void selectChild(String position) throws InvalidArgumentException, NodeNotPresentException{
		if (position == null || position.isEmpty()) {
	        throw new InvalidArgumentException("Position cannot be null or empty.");
	    }
		if(getChildrenNum()==0) {
			throw new NodeNotPresentException("no child ");
		}
		int num=Integer.parseInt(position);
		if (num>getChildrenNum()) {
			throw new NodeNotPresentException("Error. No child " + position + " for the current node.");
		}
		switch(position) {
		case"1":
			cursor=cursor.getLeft();
			break;
		case"2":
			cursor=cursor.getMiddle();
			break;
		case"3":
			cursor=cursor.getRight();
			break;
		}
	}
	/*
	 * add the child to to cursor
	 * @param
	 * option of the child
	 * @param
	 * message of the child
	 * @throws TreeFullException
	 * when child number is greater or equal to 3
	 * @throws InvalidArgumentException
	 * when the message is empty or null
	 */
	public void addChild(String option, String message) throws TreeFullException, InvalidArgumentException{
		if(message.isEmpty()||message==null) {
			throw new InvalidArgumentException("empty message");
		}
		if(getChildrenNum()>=3) {
			throw new TreeFullException("Error");
		}
		if(getChildrenNum()==2) {
			cursor.setRight(new StoryTreeNode(cursor.getPosition()+"-3", option, message));
		}
		else if(getChildrenNum()==1) {
			cursor.setMiddle(new StoryTreeNode(cursor.getPosition()+"-2", option, message));
		}
		else if(cursor.isLeaf()){
			cursor.setLeft(new StoryTreeNode(cursor.getPosition()+"-1", option, message));
		}
	}
	/*
	 * remove the child of the cursor from a given position 1,2,or 3
	 * @param
	 * position wanted to be removed
	 * throws NodeNotPresentException
	 * when no child exist at the given position
	 */
	public StoryTreeNode removeChild(String position) throws NodeNotPresentException{
		int pos=Integer.parseInt(position);
		if(pos>getChildrenNum()) {
			throw new NodeNotPresentException("Error. No child " + position + " for the current node.");
		}
		StoryTreeNode removed=null;
		StoryTreeNode middle=cursor.getMiddle();
		StoryTreeNode right=cursor.getRight();
		switch(position) {
		case"1":
			removed=cursor.getLeft();
			cursor.setLeft(middle);
			cursor.setMiddle(right);
			cursor.setRight(null);
			break;
		case"2":
			removed=cursor.getMiddle();
			cursor.setMiddle(right);
			cursor.setRight(null);
			break;
		case "3":
			removed=cursor.getRight();
			cursor.setRight(null);
			break;
		}
		reposition(root,"1-");
		return removed;
	}
	/*
	 * a method to reset the positions of the nodes after the removing process
	 * @param
	 * a node
	 * @param
	 * String path/position
	 */
	public void reposition(StoryTreeNode node, String path) {
		if(node==null) {
			return;
		}
		reposition(node.getLeft(), path+"1-");
		node.setPosition(path.substring(0,path.length()-1));
		reposition(node.getMiddle(), path+"2-");
		node.setPosition(path.substring(0,path.length()-1));
		reposition(node.getRight(), path+"3-");
		node.setPosition(path.substring(0,path.length()-1));
	}
	/*
	 * get the pre-order of the tree
	 * @param
	 * root
	 * @return
	 * string containing the tree in a pre-order manner
	 */
	public String preOrder(StoryTreeNode root) {
		String s="";
		if(root==null)
			return s;
		s+=root.getPosition()+ "|" + root.getOption() + "|" + root.getMessage()+"\n";
		s+=preOrder(root.getLeft());
		s+=preOrder(root.getMiddle());
		s+=preOrder(root.getRight());
		return s;
	}
	/*
	 * set the a node as a given root
	 * @param
	 * node
	 */
	public void setRoot(StoryTreeNode node) {
		this.root=node;
	}
	/*
	 * set the a node as the given cursor
	 * @param
	 * node
	 */
	public void cursor(StoryTreeNode node) {
		this.cursor=node;
	}
	/*
	 * gets the root of the tree
	 * @return
	 * root
	 */
	public StoryTreeNode getRoot() {
		return root;
	}
	/*
	 * gets the dummy
	 * @return 
	 * dummy root
	 */
	public StoryTreeNode getDummy() {
		return dummy;
	}
	/*
	 * sets a node as the dummy root
	 * @param
	 * dummy root
	 */
	public void setDummy(StoryTreeNode dummy) {
		this.dummy=dummy;
	}
	/*
	 * set the a node as the given cursor
	 * @param
	 * node
	 */
	public void setCursor(StoryTreeNode cursor) {
		this.cursor=cursor;
	}
	/*
	 * gets the cursor
	 * @returns
	 * cursor
	 */
	public StoryTreeNode getCursor() {
		return cursor;
	}
}

