package com.viscum.device.base;

/**
 * 道闸控制
 */
public interface GateFunction {

	/**
	 * 开闸
	 */
	int open();

	/**
	 * 关闸
	 */
	int close();

	/**
	 * 锁闸
	 */
	int lock();

	/**
	 * 解锁道闸
	 */
	int unlock();

}
