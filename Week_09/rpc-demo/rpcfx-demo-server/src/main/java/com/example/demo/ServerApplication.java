package com.example.demo;

import com.example.demo.api.RpcfxRequest;
import com.example.demo.api.RpcfxResponse;
import com.example.demo.server.RpcServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Slf4j
public class ServerApplication {

    private final RpcServer rpcServer;

    public ServerApplication(RpcServer rpcServer) {
        this.rpcServer = rpcServer;
    }

    @PostMapping("/")
    public RpcfxResponse invoke(@RequestBody RpcfxRequest request) {
        log.info("Server receive request, will invoke service return");
        return rpcServer.invoke(request);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
