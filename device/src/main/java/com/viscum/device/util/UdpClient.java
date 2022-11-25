package com.viscum.device.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;


public class UdpClient implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(UdpClient.class);

    private volatile static UdpClient instance;

    private DatagramSocket socket;

    private UdpClient() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            log.error("construct udp socket error: {}", e.getMessage());
        }
    }

    private static DatagramSocket getInstance() {
        if (instance == null) {
            synchronized (UdpClient.class) {
                if (instance == null) {
                    instance = new UdpClient();
                }
            }
        }
        return instance.socket;
    }

    public static void send(DatagramPacket packet, long delay) {
        try {
            getInstance().send(packet);
            if (delay > 0) {
                TimeUnit.MILLISECONDS.sleep(delay);
            }
        } catch (IOException | InterruptedException e) {
            log.error("udp send data error: ", e);
        }
    }

    public static DatagramPacket sendAndReceive(DatagramPacket packet) {
        try {
            DatagramSocket datagramSocket = getInstance();
            datagramSocket.send(packet);
            datagramSocket.setSoTimeout(90);
            datagramSocket.receive(packet);
        } catch (SocketTimeoutException e) {
            log.error("send error.");
        } catch (IOException e) {
            log.error("udp send data error: ", e);
        }
        return packet;
    }

    @Override
    public void close() {
        socket.close();
    }
}
