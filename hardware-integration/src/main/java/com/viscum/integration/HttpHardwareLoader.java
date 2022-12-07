package com.viscum.integration;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viscum.common.util.HttpUtil;
import com.viscum.integration.base.Hardware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 设备加载器 通过http请求获取设备列表
 */
@Component
@Slf4j
public class HttpHardwareLoader implements HardwareLoader {

	@Value("${device.load.url}")
	private String url;

	@Override
	public List<Hardware> load() {
		return getDevice(url);
	}

	public List<Hardware> getDevice(String url) {
		List<Hardware> dataList = null;
		//此处调用接口获取设备信息数据
		try {
			String res = Optional.ofNullable(HttpUtil.doGet(url, "")).orElse("");
			if (StringUtils.isNotBlank(res)) {
				ObjectMapper mapper = new ObjectMapper();
				JavaType type = mapper.getTypeFactory().constructParametricType(List.class, Hardware.class);
				dataList = mapper.readValue(res, type);
				log.info("==========>load remote config success. size:{}", dataList != null ? dataList.size() : null);
			}
		} catch (Exception e) {
			log.error("==========>load remote config failed.");
		}
		//如果数据依旧为空则使用json配置中的数据
		if (dataList == null) {
			log.info("==========>retry to get device data after 5s");
			try {
				TimeUnit.SECONDS.sleep(5L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return getDevice(url);
		}
		return dataList;
	}
}
