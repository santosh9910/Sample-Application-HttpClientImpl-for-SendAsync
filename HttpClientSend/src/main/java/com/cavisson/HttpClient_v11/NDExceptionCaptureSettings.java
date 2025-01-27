package com.cavisson.HttpClient_v11;

public class NDExceptionCaptureSettings {
	private static int exceptionCaptureMode = 0; // exception capture mode - 0 1, by default 0 (disable)
	  private static int stackTraceCaptureMode = 0; // stack trace capture mode - 0 1, by default 0 (disable)
	  private static int exceptionCauseCaptureMode = 0; // exception cause capture mode - 0 1, by default 0 (disable)
	  private static int exceptionType = 0; // stack trace is dumped as value(0) or as ID(1)
	  private static int stackTraceMaxDepth = 9999; // maximum stack trace depth upto which the stack is dumped
	  private static int exceptionCaptureTraceLevel = 0; // variable for logging exception related information



	public static int getExceptionCaptureMode() {
		return exceptionCaptureMode;
	}



	public static void setExceptionCaptureMode(int exceptionCaptureMode) {
		NDExceptionCaptureSettings.exceptionCaptureMode = exceptionCaptureMode;
	}



	public static int getStackTraceCaptureMode() {
		return stackTraceCaptureMode;
	}



	public static void setStackTraceCaptureMode(int stackTraceCaptureMode) {
		NDExceptionCaptureSettings.stackTraceCaptureMode = stackTraceCaptureMode;
	}



	public static int getExceptionCauseCaptureMode() {
		return exceptionCauseCaptureMode;
	}



	public static void setExceptionCauseCaptureMode(int exceptionCauseCaptureMode) {
		NDExceptionCaptureSettings.exceptionCauseCaptureMode = exceptionCauseCaptureMode;
	}



	public static int getExceptionType() {
		return exceptionType;
	}



	public static void setExceptionType(int exceptionType) {
		NDExceptionCaptureSettings.exceptionType = exceptionType;
	}



	public static int getStackTraceMaxDepth() {
		return stackTraceMaxDepth;
	}



	public static void setStackTraceMaxDepth(int stackTraceMaxDepth) {
		NDExceptionCaptureSettings.stackTraceMaxDepth = stackTraceMaxDepth;
	}



	public static int getExceptionCaptureTraceLevel() {
		return exceptionCaptureTraceLevel;
	}



	public static void setExceptionCaptureTraceLevel(int exceptionCaptureTraceLevel) {
		NDExceptionCaptureSettings.exceptionCaptureTraceLevel = exceptionCaptureTraceLevel;
	}
	  

	public static void  keyword(StringBuilder sb) {
		  
		  sb.append(" \tinstrExceptions = "+getExceptionCaptureMode() +"\n\n"
					+" stackTraceCaptureMode "+getStackTraceCaptureMode() +"\n\n"
					+" exceptionType "+getExceptionType() +"\n\n"
					+" stackTraceMaxDepth "+getStackTraceMaxDepth() +"\n\n"
					+" exceptionCaptureTraceLevel "+getExceptionCaptureTraceLevel() +"\n\n");
			  
		  
	  }
	
	public static void main(String[] args) {
		StringBuilder sb=new StringBuilder();
		keyword(sb);
		System.out.println(sb.toString());
	}
	
}
