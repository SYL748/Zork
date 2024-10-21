package Zork;

/**
 * An custom Exception
 * @author ShaoYang Li
 */
public class TreeFullException extends Exception{
	
	/*
	 * @param
	 * a message
	 * @return TreeFullException
	 * when been thrown
	 */
	TreeFullException(String message){
		super(message);
	}
}
