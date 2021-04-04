package com.agalvez.account.controllers.config;

public class RestRoutes {

	private RestRoutes() {

	}
	
	public static class ACCOUNTS {
		private ACCOUNTS() {
			
		}
		public static final String MAIN = "/accounts";
		
		public static final String GET_ACCOUNT = "/get/";
		public static final String GET_ALL_ACCOUNTS = "/getAll";
		public static final String FIND_BY_NAME = "/find/";
		public static final String CREATE_ACCOUNT = "/create";
		public static final String TRANSFER = "/transfer";

	}

}
