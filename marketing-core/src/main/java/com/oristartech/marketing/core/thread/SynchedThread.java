package com.oristartech.marketing.core.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * 多线程同步工作 工具类
 * @author chenjunfei
 *
 */
public class SynchedThread extends Thread {
	private CyclicBarrier entryBarrier;
	private CyclicBarrier exitBarrier;

	public SynchedThread(Runnable runnable, CyclicBarrier entryBarrier, CyclicBarrier exitBarrier) {
		super(runnable);
		this.entryBarrier = entryBarrier;
		this.exitBarrier = exitBarrier;
	}

	public void run() {
		try {
			entryBarrier.await();
			super.run();
			exitBarrier.await();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
