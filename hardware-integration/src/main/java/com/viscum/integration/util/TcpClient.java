package com.viscum.integration.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public class TcpClient implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(TcpClient.class);
    private static final int CONNECT_TIMEOUT = 3 * 1000;
    private static final int READ_TIMEOUT = 5 * 1000;

    private final String host;
    private final int port;
    private final int readTimeout;

    private Socket socket;

    public TcpClient(String host, int port) {
        this(host, port, READ_TIMEOUT);
    }

    public TcpClient(String host, int port, int readTimeout) {
        this.host = host;
        this.port = port;
        this.readTimeout = readTimeout;
    }

    public void write(byte[] data) throws IOException {
        if (data == null || data.length <= 0) {
            return;
        }

        if (!isOpen()) {
            open();
        }

        socket.getOutputStream().write(data);
        socket.getOutputStream().flush();
    }

    public int read(byte[] bytes) throws IOException {
        return socket.getInputStream().read(bytes);
    }

    @Override
    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            log.warn("Close socket error", e);
        } finally {
            socket = null;
        }
    }

    private boolean isOpen() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    private void open() throws IOException {
        close();
        socket = new Socket();
        socket.setSoTimeout(readTimeout);
        socket.setKeepAlive(false);
        socket.connect(new InetSocketAddress(host, port), CONNECT_TIMEOUT);
        socket.setKeepAlive(true);
        socket.setTcpNoDelay(true);
    }
}
