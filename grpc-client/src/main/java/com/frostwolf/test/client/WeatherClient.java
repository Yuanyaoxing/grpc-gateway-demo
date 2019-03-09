package com.frostwolf.test.client;

import com.frostwolf.grpc.test.Weather;
import com.frostwolf.grpc.test.WeatherServiceGrpc;
import com.frostwolf.grpc.test.Week;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @Author:YYX
 * @Description:
 * @Date:Created in 18:11 2019/2/15
 * @Modified By:
 */
public class WeatherClient {

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 50050;

    private ManagedChannel channel;
    private WeatherServiceGrpc.WeatherServiceBlockingStub stub;

    public WeatherClient(String host, int port) {
        this(NettyChannelBuilder.forAddress(host, port).negotiationType(NegotiationType.PLAINTEXT).build());
    }

    public WeatherClient(ManagedChannel channel) {
        this.channel = channel;
        this.stub = WeatherServiceGrpc.newBlockingStub(this.channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String getWeatherByWeek(String weekName) {
        Week week = Week.newBuilder().setName(weekName).build();

        Weather weather = stub.getWeatherByWeek(week);

        return weather.getName();
    }

    public static void main(String[] args) throws InterruptedException {
        WeatherClient client = new WeatherClient(DEFAULT_HOST, DEFAULT_PORT);
        try {
            String weekDate = "周二";
            String res = client.getWeatherByWeek(weekDate);
            System.out.println("get result from server: " + res + " as param is " + weekDate);
        } finally {
            client.shutdown();
        }
    }
}
