package com.java.example.demo.connectionMySql;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
@Component
public class SshContextListener implements ServletContextListener {
    private SshConnection sshConnection;

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            sshConnection = new SshConnection();
            sshConnection.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        try {
            sshConnection.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
