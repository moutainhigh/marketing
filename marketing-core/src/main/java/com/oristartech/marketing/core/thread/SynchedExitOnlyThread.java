package com.oristartech.marketing.core.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * 多线程同步工作类，至保证退出同步
 * @author chenjunfei
 *
 */
public class SynchedExitOnlyThread extends Thread {
	private CyclicBarrier exitBarrier;

	public SynchedExitOnlyThread(Runnable runnable, CyclicBarrier exitBarrier) {
		super(runnable);
		this.exitBarrier = exitBarrier;
	}

	public void run() {
		try {
			super.run();
			exitBarrier.await();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
