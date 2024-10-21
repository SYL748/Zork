package Zork;

/**
 * An custom Exception
 * @author ShaoYang Li
 */
public class NodeNotPresentException extends Exception{
	/*
	 * @param
	 * a message
	 * @return NodeNotPresentException
	 * when been thrown
	 */
	NodeNotPresentException(String message){
		super(message);
	}
}