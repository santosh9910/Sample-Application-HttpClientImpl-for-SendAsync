package com.cavisson.HttpClient_v11;

import net.bytebuddy.asm.Advice.OnMethodEnter;
import net.bytebuddy.asm.Advice.OnMethodExit;

public class HttpClientAgent {
	
	@OnMethodEnter
	public static void onEntry() {
		System.out.println("Entry method in ===================> HttpClientAgent");
	}
	
	@OnMethodExit
public static void onExit() {
		System.out.println("Exit method in ===================> HttpClientAgent");
	}

}
