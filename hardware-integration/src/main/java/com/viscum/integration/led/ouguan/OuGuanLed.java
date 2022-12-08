package com.viscum.integration.led.ouguan;


import com.alibaba.fastjson2.JSON;
import com.viscum.common.util.HexUtil;
import com.viscum.integration.base.*;
import com.viscum.integration.util.TcpClient;
import com.viscum.model.Hardware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 欧冠LED
 *
 * @author viscum
 */
public class OuGuanLed extends AbstractParkHardware implements  LedFunction {


	private static final Logger log = LoggerFactory.getLogger(OuGuanLed.class);

	private final OuGuanLedConfig config;

	public OuGuanLed(Hardware hardware) {
		super(hardware);
		config = JSON.parseObject(hardware.getHwParam(), OuGuanLedConfig.class);
	}

	@Override
	public int play(String text) {
		try {
			byte[] mag = magbody(text, 7);
			sendDataToDevice(getMagData(mag, (byte) 0x02));
		} catch (IOException e) {
			return 0;
		}
		return 1;
	}


	public byte[] getMagData(byte[] mag, byte tcp) throws IOException {

		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); DataOutputStream dos = new DataOutputStream(bos);) {
			dos.writeByte(0xA5);
			dos.writeByte(0xA5);
			dos.write(tcp);
			dos.write(mag.length);
			dos.write(mag);
			dos.writeByte(0xBE);
			dos.writeByte(0xEF);
			return bos.toByteArray();
		}

	}

	/**
	 * 报文体
	 *
	 * @param text led信息
	 * @return byte
	 * @throws IOException 抛出
	 */
	private byte[] magbody(String text, int line) throws IOException {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); DataOutputStream dos = new DataOutputStream(bos)) {
			byte[] message = text.getBytes(Charset.forName("gb2312"));
			dos.write(getLine(line));
			dos.write(message.length);
			dos.write(message);
			int sum = checksum(bos.toByteArray());
			dos.write(sum);
			return bos.toByteArray();
		}
	}


	@Override
	public int show(List<LedText> ledTextList) {
		log.info("-----ip:{},port:{}", config.getIp(), config.getPort());
		final long sleep = 200;
		try (TcpClient tcpClient = new TcpClient(config.getIp(), config.getPort())) {
			for (LedText text : ledTextList) {
				try {
					byte[] mag = magbody(text.getText(), text.getLine());
					byte[] bytes = getMagData(mag, (byte) 0x01);
					log.info("bytes lengths is: {}", bytes.length);
					doSendDataToDevice(bytes, tcpClient);
					TimeUnit.MILLISECONDS.sleep(sleep);
				} catch (Exception e) {
					log.error("led-og推送消息异常", e);
				}
			}
		}
		return 1;

	}


	private int checksum(byte[] data) {
		byte e = 0;
		for (byte t : data) {
			e += t;
		}
		return 255 - e;
	}

	private byte getLine(int line) {

		byte lineby = 0;
		switch (line) {
			case 1:
				lineby = (byte) 0x05;
				break;
			case 2:
				lineby = (byte) 0x06;
				break;
			case 3:
				lineby = (byte) 0x17;
				break;
			case 4:
				lineby = (byte) 0x18;
				break;
			default:
				lineby = (byte) 0x02;
				break;
		}
		return lineby;
	}

	/**
	 * 建立连接
	 *
	 * @param bytes 报文byte
	 */
	private void sendDataToDevice(byte[] bytes) {

		log.info("-----ip:{},port:{}", config.getIp(), config.getPort());
		final long sleep = 200;
		try (TcpClient tcpClient = new TcpClient(config.getIp(), config.getPort())) {
			log.info("bytes lengths is: {}", bytes.length);
			doSendDataToDevice(bytes, tcpClient);
			TimeUnit.MILLISECONDS.sleep(sleep);

		} catch (IOException e) {
			log.warn("Send data to device error: {}", e.getMessage());
		} catch (InterruptedException e) {
			log.warn("Sleep with InterruptedException {}", e.getMessage());
		}
	}


	/**
	 * 发送数据
	 *
	 * @param data      报文
	 * @param tcpClient tcp连接
	 * @throws IOException 抛出
	 */
	private static void doSendDataToDevice(byte[] data, TcpClient tcpClient) throws IOException {
		if (data != null) {
			log.debug("DS=> " + HexUtil.toHex(data));
			tcpClient.write(data);
			byte[] rsp = new byte[200];
			int len = tcpClient.read(rsp);
			if (len > 0) {
				log.debug("DS<= " + HexUtil.toHex(rsp, len));
			}
		}
	}
}
