package iGoMaster.exception;

@SuppressWarnings("serial")
public class NetworkException extends Exception
{

	/** 
	* Crée une nouvelle instance de NetworkException 
	*/  
	public NetworkException() {}  
	
	/** 
	* Crée une nouvelle instance de NetworkException 
	* @param message le message détaillant l'exception 
	*/  
	public NetworkException(String message) {  
		super(message); 
	}  
	/** 
	* Crée une nouvelle instance de NetworkException 
	* @param cause l'exception à l'origine de cette exception 
	*/  
	public NetworkException(Throwable cause) {  
		super(cause); 
	}  
	/** 
	* Crée une nouvelle instance de NetworkException 
	* @param message le message détaillant cette exception 
	* @param cause l'exception à l'origine de cette exception 
	*/  
	public NetworkException(String message, Throwable cause) {  
		super(message, cause); 
	} 

}
