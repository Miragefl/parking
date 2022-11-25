package com.viscum.device.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 定向发送数据包
 *
 */
public class DirectionalDatagramPacket {
    private final static Logger log = LoggerFactory.getLogger(DirectionalDatagramPacket.class);
    private InetAddress address;
    private int port;
    private int max_bytes = 0;

    public static DirectionalDatagramPacket getInstance(String ip, int port, int max_byte){
        if (!Helper.isIpv4(ip)) {
            return null;
        }

        DirectionalDatagramPacket ret = new DirectionalDatagramPacket();
        ret.init(ip, port, max_byte);
        return ret;
    }

    public DatagramPacket newPacket(byte[] bytes){
        if (address == null) {
            return null;
        }
        if (this.max_bytes > 0 && bytes.length > this.max_bytes){
            log.warn("超过长度显示，数据长度:{}, 最大长度:{}", bytes.length, this.max_bytes);
            return null;
        }
        return new DatagramPacket(bytes ,bytes.length , address , port);
    }

    private void init(String ip, int port, int max_byte) {
        this.max_bytes = max_byte;
        this.port = port;
        try {
            this.address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            log.error("", e);
        }
    }

    private DirectionalDatagramPacket() {
        super();
    }
}
