package com.afeiluo.thrift.swift.server;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.Executors.newFixedThreadPool;

import java.util.concurrent.ExecutorService;

import org.apache.thrift.TProcessor;
import org.junit.Assert;

import com.afeiluo.thrift.swift.inter.TestService;
import com.afeiluo.thrift.swift.inter.impl.TestServiceImpl;
import com.facebook.nifty.core.NettyServerConfig;
import com.facebook.nifty.core.NiftyBootstrap;
import com.facebook.nifty.core.ThriftServerDef;
import com.facebook.nifty.core.ThriftServerDefBuilder;
import com.facebook.nifty.guice.NiftyModule;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.ThriftEventHandler;
import com.facebook.swift.service.ThriftServer;
import com.facebook.swift.service.ThriftServiceProcessor;
import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Stage;

/**
 */
public class ServerCreator {
    private ExecutorService taskWorkerExecutor;
    private ThriftServer server;
    private ExecutorService bossExecutor;
    private ExecutorService ioWorkerExecutor;

    public ThriftServer getServer() {
        return server;
    }

    public ServerCreator invoke(int port) {
        ThriftServiceProcessor processor = new ThriftServiceProcessor(new ThriftCodecManager(), ImmutableList.<ThriftEventHandler> of(),
                new TestServiceImpl());

        taskWorkerExecutor = newFixedThreadPool(1);

        ThriftServerDef serverDef = ThriftServerDef.newBuilder().listen(port).withProcessor(processor).using(taskWorkerExecutor).build();

        bossExecutor = newCachedThreadPool();
        ioWorkerExecutor = newCachedThreadPool();

        NettyServerConfig serverConfig = NettyServerConfig.newBuilder().setBossThreadExecutor(bossExecutor).setWorkerThreadExecutor(ioWorkerExecutor)
                .build();

        server = new ThriftServer(serverConfig, serverDef);
        return this;
    }

    public void checkExecutorsTerminated() {
        Assert.assertTrue(bossExecutor.isTerminated());
        Assert.assertTrue(ioWorkerExecutor.isTerminated());
        Assert.assertTrue(taskWorkerExecutor.isTerminated());
    }

    public void stop() {
        server.close();
    }
}