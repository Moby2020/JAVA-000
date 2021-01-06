package com.geekbang.rpc.demo;

import com.geekbang.rpc.demo.model.Order;
import com.geekbang.rpc.demo.model.User;
import com.geekbang.rpc.demo.proxy.RpcClient;
import com.geekbang.rpc.demo.proxy.RpcClientCglib;
import com.geekbang.rpc.demo.proxy.RpcClientJdk;
import com.geekbang.rpc.demo.service.OrderService;
import com.geekbang.rpc.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientApplication {
    public static void main(String[] args) {
        RpcClient jdk = new RpcClientJdk();
        UserService userService = jdk.create(UserService.class, "http://localhost:8080/");
        /**
         * throw error:  com.alibaba.fastjson.JSONException: autoType is not support. com.example.demo.model.User
         * add VM param to fix error: -Dfastjson.parser.autoTypeAccept=com.example.demo.
         */
        User user = userService.findById(1);
        if (user == null) {
            log.info("Clint service invoke Error");
            return;
        }
        log.info("find user by id:1 from server: {}", user);

        RpcClient cglib = new RpcClientCglib();
        OrderService orderService = cglib.create(OrderService.class, "http://localhost:8080/");
        Order order = orderService.findById(1992129);
        if (order == null) {
            log.info("Clint service invoke Error");
            return;
        }
        System.out.println(String.format("find order name=%s, user=%d", order.getName(), user.getId()));

        // throw error
        /*order = orderService.findError();
        if (order == null) {
            log.info("Clint service invoke Error");
        }*/
    }
}
