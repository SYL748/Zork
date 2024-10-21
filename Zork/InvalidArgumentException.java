package Zork;
/**
 * An custom Exception
 * @author ShaoYang Li
 */
public class InvalidArgumentException extends Exception{
	/*
	 * @param
	 * a message
	 * @return InvalidArgumentException
	 * when been thrown
	 */
	InvalidArgumentException(String message){
		super(message);
	}
}