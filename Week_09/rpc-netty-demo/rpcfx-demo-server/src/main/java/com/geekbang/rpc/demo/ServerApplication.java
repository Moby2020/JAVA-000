package com.geekbang.rpc.demo;

import com.geekbang.rpc.demo.netty.server.RpcNettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ServerApplication implements ApplicationRunner {

    private final RpcNettyServer rpcNettyServer;

    public ServerApplication(RpcNettyServer rpcNettyServer) {
        this.rpcNettyServer = rpcNettyServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            rpcNettyServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rpcNettyServer.destroy();
        }
    }
}

