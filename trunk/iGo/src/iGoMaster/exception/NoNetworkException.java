package iGoMaster.exception;

@SuppressWarnings("serial")
public class NoNetworkException extends Exception{

		/** 
		* Crée une nouvelle instance de NoNetworkException 
		*/  
		public NoNetworkException() {}  
		
		/** 
		* Crée une nouvelle instance de NoNetworkException 
		* @param message le message détaillant l'exception 
		*/  
		public NoNetworkException(String message) {  
			super(message); 
		}  
		/** 
		* Crée une nouvelle instance de NoNetworkException 
		* @param cause l'exception à l'origine de cette exception 
		*/  
		public NoNetworkException(Throwable cause) {  
			super(cause); 
		}  
		/** 
		* Crée une nouvelle instance de NoNetworkException 
		* @param message le message détaillant cette exception 
		* @param cause l'exception à l'origine de cette exception 
		*/  
		public NoNetworkException(String message, Throwable cause) {  
			super(message, cause); 
		} 
	


}