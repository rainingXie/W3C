package com.example.w3c_school.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorManager {
	// 单例模式,不能多次new
	
	private ExecutorManager() {
		init();
	}

	private static ExecutorManager instance;

	public static ExecutorManager getInstance() {
		if (instance == null) {
			synchronized (ExecutorManager.class) {//防止多线程创建对象
				if (instance == null) {
					return new ExecutorManager();
				}
			}

		}
		return instance;
	}

	private ExecutorService executorService;
	//初始化线程池
	private void init(){
		int max = 8;
		int num = Runtime.getRuntime().availableProcessors()*2+1;
		num = num >max?max:num;
		executorService = Executors.newFixedThreadPool(num);

	}
	
	public void execute(Runnable runnable){
		this.executorService.execute(runnable);
	}
	
	public ExecutorService getExecutors(){
		return executorService;
		
	}
	
	
	
}
