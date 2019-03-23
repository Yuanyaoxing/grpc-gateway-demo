package com.frostwolf.test.client;

import com.frostwolf.grpc.test.User;
import com.frostwolf.grpc.test.Weather;
import com.frostwolf.grpc.test.WeatherServiceGrpc;
import com.frostwolf.grpc.test.Week;
import com.frostwolf.producer.Producer;
import com.frostwolf.test.client.interceptor.ClientInterceptorImpl;
import io.grpc.*;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @Author:YYX
 * @Description:
 * @Date:Created in 18:11 2019/2/15
 * @Modified By:
 */
public class WeatherClient {
    private static final Logger logger = Logger.getLogger(WeatherClient.class.getName());
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 8089;

    private ManagedChannel channel;
    private Channel channel1;
    private WeatherServiceGrpc.WeatherServiceBlockingStub stub;

    public WeatherClient(String host, int port) {
        this(NettyChannelBuilder.forAddress(host, port).negotiationType(NegotiationType.PLAINTEXT).build());
        logger.info("根据host和port创建ManagedChannel，host：" + host + "; posrt:" + port);
    }

    public WeatherClient(ManagedChannel channel) {

        this.channel = channel;
        logger.info("将通道与拦截器绑定起来，生成新的通道Channel");
        this.channel1 = ClientInterceptors.intercept(channel, new ClientInterceptorImpl());
        this.stub = WeatherServiceGrpc.newBlockingStub(this.channel1);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String getWeatherByWeek(String weekName) {
        logger.info("开始请求getWeatherByWeek方法，请求参数 weekName = " + weekName);
        Week week = Week.newBuilder().setName(weekName).build();

        Weather weather = stub.getWeatherByWeek(week);
        logger.info("请求getWeatherByWeek方法成功，返回参数 ：" + weather.getName());

        logger.info("发布信息");
        publish(weather.getName());
        return weather.getName();
    }

    public String login(String name, String id) {
        logger.info("开始请求login方法，请求参数 name = " + name + "   id = " + id);
        User user = User.newBuilder().setId(id).setName(name).build();
        User response = stub.login(user);
        logger.info("请求login方法成功，返回参数 ：" + response.getToken());
        return response.getToken();
    }

    public static void publish(String weather) {
        Producer producer = new Producer("localhost", 4150);
        Map<String, Object> messageMap = new HashMap<String, Object>();
        messageMap.put("test6", "发布从服务器更新的天气信息， weather：" + weather);
        producer.publish(messageMap);
    }

    public static void main(String[] args) throws InterruptedException {
        WeatherClient client = new WeatherClient(DEFAULT_HOST, DEFAULT_PORT);
        try {
            String weekDate = "周六";
            String res = client.getWeatherByWeek(weekDate);
            System.out.println("get result from server: " + res + " as param is " + weekDate);
//            String res2 = client.login("aaa", "123");
        } finally {
            client.shutdown();
        }
    }
}
