package com.viscum.device.led.minwang;

import com.alibaba.fastjson2.JSON;
import com.viscum.common.util.HexUtil;
import com.viscum.device.base.*;
import com.viscum.device.util.DirectionalDatagramPacket;
import com.viscum.device.util.UdpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 民望 led
 *
 * @author viscum
 */
public class MinWangLed extends AbstractParkDevice implements LedFunction {

	private static final Logger log = LoggerFactory.getLogger(MinWangLed.class);

	private final DirectionalDatagramPacket packet;

	private final MinWangLedConfig config;

	private final String playOptionsCache;

	private Device device;


	public MinWangLed(Device device) {
		this.device = device;
		config = JSON.parseObject(device.getDeviceParam(), MinWangLedConfig.class);
		log.info("民望LED配置参数: {}", config);
		packet = DirectionalDatagramPacket.getInstance(config.getIp(), config.getPort(), Integer.MAX_VALUE);
		playOptionsCache = buildPlayOptionsCache();
		log.info("playOptionsCache:{}", playOptionsCache);
	}

	public MinWangLedConfig getConfig() {
		return config;
	}


	@Override
	public int play(String text) {
		try {
			doSendDataToDevice(getVoiceBytes(text));
		} catch (Exception e) {
			log.error("民望LED播放异常 ", e);
			return 0;
		}
		return 1;
	}


	private byte[] getVoiceBytes(String text) {
		byte[] message = buildPlayMessageByteWithOptions(text);
		int voiceLength = 2 + message.length;
		int commandLength = 1 + 2 + voiceLength + 2;
		int totalLength = 4 + 4 + 1 + 4 + 4 + commandLength + 2;
		ByteBuffer buffer = ByteBuffer.allocate(totalLength);
		byte[] head = {(byte) 0xFE, (byte) 0x5C, (byte) 0x4B, (byte) 0x89};
		buffer.put(head);
		// 总长度
		buffer.put(intTo4ByteLE(totalLength));
		// 语音命令
		buffer.put((byte) 0x68);
		// 232-2 的 2
		buffer.put(intTo4ByteLE(2));
		// 数据长度
		buffer.put(intTo4ByteLE(commandLength));

		// 数据协议固定头
		buffer.put((byte) 0xFD);
		// 语音文本长度
		buffer.put(intTo2ByteBE(voiceLength));
		// 语音文本协议固定头
		buffer.put(new byte[]{0x01, 0x01});
		// 语音内容
		buffer.put(message);


		buffer.put(new byte[]{0x00, 0x00});
		buffer.put(new byte[]{(byte) 0xFF, (byte) 0xFF});
		return buffer.array();
	}

	private byte[] buildPlayMessageByteWithOptions(String text) {
		log.info("待播放内容：{}", playOptionsCache + text);
		return (playOptionsCache + text).getBytes(Charset.forName("GBK"));
	}

	private String buildPlayOptionsCache() {
		Optional<MinWangLedConfig.PlayOptions> playOptions = Optional.ofNullable(config.getPlayOptions());
		// 音量 0-10
		Integer volume = playOptions.map(MinWangLedConfig.PlayOptions::getVolume).filter(i -> i > 0 && i < 10).orElse(5);
		// 音速 0-10
		Integer speed = playOptions.map(MinWangLedConfig.PlayOptions::getSpeed).filter(i -> i > 0 && i < 10).orElse(5);
		// 音调 0-10
		Integer intonation = playOptions.map(MinWangLedConfig.PlayOptions::getIntonation).filter(i -> i > 0 && i < 10).orElse(5);
		// 0:female 1:male
		Integer soundMode = playOptions.map(MinWangLedConfig.PlayOptions::getSoundMode).filter(i -> i.equals(0) || i.equals(1)).orElse(0);

		StringBuilder builder = new StringBuilder().append("[v").append(volume).append(']')
				.append("[s").append(speed).append(']')
				.append("[t").append(intonation).append(']');

		if (soundMode.equals(0)) {
			builder.append("[m3]");
		} else {
			builder.append("[m51]");
		}
		return builder.toString();
	}

	private void doSendDataToDevice(byte[] data) {
		if (data != null) {
			log.info("DS ==> {}", HexUtil.toHex(data));
			UdpClient.sendAndReceive(packet.newPacket(data));
		}
	}

	private byte[] getTextBytes(String text, int line, int color, int font, int fontSize) {

		byte[] message = text.getBytes(Charset.forName("GBK"));
		byte[] head = {(byte) 0xFE, (byte) 0x5C, (byte) 0x4B, (byte) 0x89};
		// 文本长度
		int textLength = message.length + 2 + 2 + 4 + 2;
		// 内容长度: (内容长度, 素材属性长度), 素材总长度, 文本内容长度占 4 字节, 文本内容
		int contentLength = 25 + 23 + 4 + textLength;
		// 总长度: 头，内容长度，内容长度占 4 字节，文本内容长度， 结束符
		int totalLength = 13 + 4 + contentLength + 2;
		ByteBuffer buffer = ByteBuffer.allocate(totalLength);
		buffer.put(head);
		// 总长度
		buffer.put(intTo4ByteLE(totalLength));
		// 文本命令
		buffer.put((byte) 0x31);
		// 发送编号
		buffer.put(new byte[]{(byte) 0x92, 0x79, (byte) 0x95, 0x72});
		// 内容长度
		buffer.put(intTo4ByteLE(contentLength));
		// 窗口编号
		buffer.put(new byte[]{0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30});
		buffer.put((byte) (0x30 + line));
		// 固定分隔符
		buffer.put((byte) 0x2C);

		putShowStyle(text, line, buffer);
		// 停留时间 0 - 255
		buffer.put((byte) 0x00);
		// 开始时间
		buffer.put(new byte[]{0x30, 0x31, 0x30, 0x31, 0x30, 0x31});
		// 结束时间
		buffer.put(new byte[]{0x39, 0x39, 0x31, 0x32, 0x33, 0x31});
		// 素材属性长度 19位
		buffer.put(intTo4ByteLE(19));
		// 素材属性前部固定
		// 第 7 个改为 0x32 原为 0x31 1-等所有发送完毕更新所有 2-当前发送更新当前(BUG：如果不是第一节目会自动切换到第一节目)
		// 第 9 个 0x31 为 单基色， 0x32 为双基色
		byte colorType = config.isSingleColor() ? (byte) 0x31 : (byte) 0x32;
		buffer.put(new byte[]{0x55, (byte) 0xAA, 0x00, 0x00, 0x37, 0x32, 0x32, 0x31, colorType, 0x31, 0x00, 0x00, 0x08, 0x00, 0x10, 0x00});

		// 字体颜色
//		putShowColor(line, buffer);
		buffer.put((byte) color);
		// 字体字号
		buffer.put((byte) (font * 16 + fontSize));
		// 宋体 16 * 16
//		buffer.put((byte) 0x11);
		// 保留固定
		buffer.put((byte) 0x00);
		// 文本内容长度
		buffer.put(intTo4ByteLE(textLength));
		// 文本
		buffer.put(message);
		// 文本固定结尾
		buffer.put(new byte[]{(byte) 0xFF, 0x00, 0x01, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x00});
		// 结束符号
		buffer.put(new byte[]{(byte) 0xFF, (byte) 0xFF});
		return buffer.array();
	}

	private void putShowSpeed(int line, ByteBuffer buffer) {
		Predicate<Integer> validSpeed = i -> i <= 10 && i > 0;
		buffer.put(getShowOption(line, MinWangLedConfig.ShowOptions::getSpeed, validSpeed, 7));
	}

	private void putShowColor(int line, ByteBuffer buffer) {
		Predicate<Integer> validColor = i -> i >= 1 && i <= 3;
		buffer.put(getShowOption(line, MinWangLedConfig.ShowOptions::getColor, validColor, 1));
	}

	private byte getShowOption(int line,
	                           Function<MinWangLedConfig.ShowOptions, Integer> getOption,
	                           Predicate<Integer> valid,
	                           int defaultValue) {
		List<MinWangLedConfig.ShowOptions> showOptions = config.getShowOptions();
		if (showOptions != null && showOptions.size() >= line) {
			int index = line - 1;
			return (byte) (Optional.of(showOptions.get(index)).map(getOption).filter(valid).orElse(defaultValue).intValue());
		}
		return (byte) defaultValue;
	}

	/**
	 * 根据配置和显示的文本长度放入显示效果
	 *
	 * @param text   显示的文本
	 * @param buffer byteBuffer
	 */
	private void putShowStyle(String text, int line, ByteBuffer buffer) {

		int wordCount = getWordCount(text);
		// 根据显示长度来决定显示方式
		if (wordCount <= 8) {
			// 显示效果 没有效果立即显示
			buffer.put((byte) 0x09);
		} else {
			// 左移
			buffer.put((byte) 0x01);
		}
		// 显示速度 越大越快
		putShowSpeed(line, buffer);
	}

	@Override
	public int show(List<LedText> ledTextList) {
		log.info("MinWang Show: {}", ledTextList);
		sendDataToDevice(this::doSendDataToDevice, ledTextList.stream()
				//.sorted(Comparator.comparing(LedText::getLine))
				.map(this::ledTextToByte)
				.toArray(byte[][]::new));
		return 1;
	}

	private byte[] ledTextToByte(LedText ledText) {
		log.info("mingwang led: {}", ledText);
		return getTextBytes(ledText.getText(), ledText.getLine(), ledText.getColor(), ledText.getFront(), ledText.getFrontSize());
	}


	protected static byte[] intTo4ByteLE(int value) {
		ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
		return buffer.putInt(value).array();
	}

	protected static byte[] intTo2ByteBE(int value) {
		byte[] result = new byte[2];
		result[0] = (byte) (value >> 8);
		result[1] = (byte) (value /*>> 0*/);
		return result;
	}

	protected void sendDataToDevice(Consumer<byte[]> consumer, byte[]... data) {
		for (byte[] bytes : data) {
			consumer.accept(bytes);
		}
	}

	public static int getWordCount(String s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255) {
				length++;
			} else {
				length += 2;
			}
		}
		return length;

	}
}
