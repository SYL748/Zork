package Zork;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
public class Zork {
	static Scanner input=new Scanner(System.in);
	static StoryTree tree=new StoryTree();
	public static void main(String[] args) {
		System.out.print(tree.getDummy().getMessage()+"\nPlease enter the file name: ");
		String file=input.nextLine();
		try {
			tree=tree.readTree(file);
		} catch (IOException e) {
			System.out.println(e.getMessage());
            return;
		}
		
		while(true) {
			System.out.println("Would you like to edit (E), play (P) or quit (Q)?");
			String choice =input.nextLine().toUpperCase();
			switch(choice) {
			case "E":
				editTree(tree);
				break;
			case "P":
				playTree(tree);
				break;
			/*
			 * terminate and save the tree
			 */
			case "Q":
				tree.setCursor(tree.getRoot());
				StoryTree.saveTree(file, tree);
				System.out.println("Game being saved to " + file + "\n"
						+ "\n"
						+ "Save successful!\n"
						+ "\n"
						+ "Program terminating normally.");
				System.exit(0);
			default:
				System.out.println("Invalid option");
			}
		}
	}
	/*
	 * play method that allows the user to play/interact with tree
	 * @Exception try catch InvalidArgumentException, NodeNotPresentException
	 */
	public static void playTree(StoryTree tree) {
			System.out.println(tree.getRoot().getOption()+"\n");
			while(!tree.getCursor().isLosingNode() && !tree.getCursor().isWinningNode()) {
				System.out.println(tree.getCursorMessage());
				int num=tree.getChildrenNum();
				String[][] option=tree.getOptions();
				for(int i=0; i<num; i++) {
					System.out.println(i+1 + ") " + option[i][1]);
				}
				System.out.println("Please make a choice. ");
				String choice=input.nextLine();
				try {
					switch(choice) {
					case "1":
						tree.selectChild("1");
						break;
					case "2":
						tree.selectChild("2");
						break;
					case "3":
						tree.selectChild("3");
						break;
					default:
						tree.selectChild(choice);
					}
					System.out.println();
				} catch(InvalidArgumentException iae) {
					System.out.println(iae.getMessage()+"\n");
				}catch(NodeNotPresentException nnpe) {
					System.out.println(nnpe.getMessage()+"\n");
				}
			}
			System.out.println(tree.getCursorMessage() + "\nThanks for playing.");
            tree.resetCursor();
			System.out.println();
		}
	/*
	 * method that allows the user to edit the tree
	 */
	public static void editTree(StoryTree tree) {
		System.out.println("Zork Editor:\n"
				+ "    V: View the cursor's position, option and message.\n"
				+ "    S: Select a child of this cursor (options are 1, 2, and 3).\n"
				+ "    O: Set the option of the cursor.\n"
				+ "    M: Set the message of the cursor.\n"
				+ "    A: Add a child StoryNode to the cursor.\n"
				+ "    D: Delete one of the cursor's children and all its descendants.\n"
				+ "    R: Move the cursor to the root of the tree.\n"
				+ "    Q: Quit editing and return to main menu.\n");
		boolean done=true;
		while (done) {
			System.out.println("Please select an option: ");
			String choice=input.nextLine().toUpperCase();
			switch(choice) {
			/*
			 * view the position option and message
			 */
			case "V":
				System.out.println("Position: "+tree.getCursorPosition());
				System.out.println("Option: "+tree.getCursor().getOption());
				System.out.println("Message: "+tree.getCursorMessage());
				System.out.println();
				break;
			/*
			 * select a child position from the current node
			 * @Exception try catch InvalidArgumentException, NodeNotPresentException
			 */
			case "S":
				int num=tree.getChildrenNum();
				if(num==0) {
					System.out.println("no child");
					break;
				}
				int[] s=new int[num];
				for (int i=0; i<num; i++) {
					s[i]=i+1;
				}
				System.out.println("Please select a child: " + Arrays.toString(s));
				String position=input.nextLine();
				try {
					tree.selectChild(position);
				}catch(InvalidArgumentException iae) {
					System.out.println(iae.getMessage());
				}catch(NodeNotPresentException nnpe) {
					System.out.println(nnpe.getMessage());
				}
				break;
			/*
			 * change the option for the current node
			 */
			case "O":
				System.out.println("Please enter a new option: ");
				String option=input.nextLine();
				tree.setCursorOption(option);
				System.out.println("Option set.\n");
				break;
			/* 
			 * change the message for the current node
			 */
			case "M":
				System.out.println("Please enter a new Message: ");
				String message=input.nextLine();
				tree.setCursorMessage(message);
				System.out.println("Message set.\n");
				break;
			case "A":
			/*
			 * add a child to the current node, and enter the option and message wanted for the new child
			 * @Exception try catch TreeFullException, InnvalidArgumentException
			 */
				System.out.println("Enter an option: ");
				String option1=input.nextLine();
				System.out.println("Enter a message: ");
				String message1=input.nextLine();
				try {
					tree.addChild(option1, message1);
				} catch (TreeFullException e) {
					System.out.println(e.getMessage());
				} catch (InvalidArgumentException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("Child added.\n");
				break;
			/*
			 * delete a child from the current node
			 * @Exception try catch NodeNotPresentException
			 */
			case "D":
				int num1=tree.getChildrenNum();
				if(num1==0) {
					System.out.println("no child ");
					break;
				}
				int[] child=new int[num1];
				for(int i=0; i<num1;i++) {
					child[i]=i+1;
				}
				System.out.println("Please select a child: " + Arrays.toString(child));
				String ch=input.nextLine();
				try {
					tree.removeChild(ch);
					System.out.println("Subtree deleted.");
				} catch (NodeNotPresentException e) {
					System.out.println(e.getMessage());
				}
				break;
			/*
			 * resets the cursor back to root
			 */
			case "R":
				tree.resetCursor();
				System.out.println("Cursor moved to root.\n");
				break;
			/*
			 * exit edit
			 */
			case "Q":
				done=false;
			}
		}
	}
}
