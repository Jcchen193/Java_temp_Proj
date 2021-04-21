package com.java.example.demo.connectionMySql;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SshConnection {

	//aliyun connection
    String username = "root";
    String password = "ll940422..";
    String host = "47.97.8.196";
    int port = 22;

    //aliyun mysql connection
    int local_port = 3307;
    String remote_host = "127.0.0.1";
    int remote_port = 3306;
    Session session;

    public void init() {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            session.setPortForwardingL(local_port, remote_host, remote_port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        this.session.disconnect();
    }

}
