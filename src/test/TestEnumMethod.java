package test;

import javax.servlet.ServletException;

enum ExceptionHandler {
	MY_EXCEPTION{
		@Override
		public void myException(String cause) throws ServletException {
			throw new ServletException(cause);
		}
	};
	
	public abstract void myException(String cause) throws ServletException;
}

public class TestEnumMethod {
	public static void main(String[] args) throws ServletException {
		ExceptionHandler.MY_EXCEPTION.myException("Servlet Exception thrown");
	}
}
