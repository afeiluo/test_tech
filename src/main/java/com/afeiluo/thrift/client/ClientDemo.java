package com.afeiluo.thrift.client;

import static com.google.common.net.HostAndPort.fromParts;
import com.afeiluo.thrift.inter.TestService;
import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.swift.service.ThriftClientManager;

public class ClientDemo {

    public static void main(String[] args) throws Exception {
        ThriftClientManager clientManager = new ThriftClientManager();
        TestService testService = clientManager.createClient(new FramedClientConnector(fromParts("localhost", 12345)), TestService.class).get();
        System.out.println(testService.findPerson(111L));

        int max = 100000;
        Long start = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            testService.findPerson(111L);
        }
        Long end = System.currentTimeMillis();
        Long elapse = end - start;
        int perform = Double.valueOf(max / (elapse / 1000d)).intValue();

        System.out.print("thrift " + max + " 次RPC调用，耗时：" + elapse + "毫秒，平均" + perform + "次/秒");

    }
}
