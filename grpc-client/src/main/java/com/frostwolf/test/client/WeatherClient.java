package com.frostwolf.test.client;

import com.frostwolf.grpc.test.User;
import com.frostwolf.grpc.test.Weather;
import com.frostwolf.grpc.test.WeatherServiceGrpc;
import com.frostwolf.grpc.test.Week;
import com.frostwolf.test.client.interceptor.ClientInterceptorImpl;
import io.grpc.*;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import io.opentracing.ScopeManager;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.contrib.grpc.ClientTracingInterceptor;
import io.opentracing.propagation.Format;

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
    private static final int DEFAULT_PORT = 50050;

    private ManagedChannel channel;
    private Channel channel1;
    private WeatherServiceGrpc.WeatherServiceBlockingStub stub;
    private final Tracer tracer = new Tracer() {
        public ScopeManager scopeManager() {
            return null;
        }

        public Span activeSpan() {
            return null;
        }

        public SpanBuilder buildSpan(String s) {
            return null;
        }

        public <C> void inject(SpanContext spanContext, Format<C> format, C c) {

        }

        public <C> SpanContext extract(Format<C> format, C c) {
            return null;
        }
    };

    public WeatherClient(String host, int port) {
        this(NettyChannelBuilder.forAddress(host, port).negotiationType(NegotiationType.PLAINTEXT).build());
        logger.info("根据host和port创建ManagedChannel，host：" + host + "; posrt:" + port);
    }

    public WeatherClient(ManagedChannel channel) {

        this.channel = channel;
        logger.info("将通道与拦截器绑定起来，生成新的通道Channel");
        this.channel1 = ClientInterceptors.intercept(channel, new ClientInterceptorImpl(), new ClientTracingInterceptor(this.tracer));
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
        return weather.getName();
    }

    public String login(String name, String id) {
        logger.info("开始请求login方法，请求参数 name = " + name + "   id = " + id);
        User user = User.newBuilder().setId(id).setName(name).build();
        User response = stub.login(user);
        logger.info("请求login方法成功，返回参数 ：" + response.getToken());
        return response.getToken();
    }

    public static void main(String[] args) throws InterruptedException {
        WeatherClient client = new WeatherClient(DEFAULT_HOST, DEFAULT_PORT);
        try {
            String weekDate = "周二";
            String res = client.getWeatherByWeek(weekDate);
            System.out.println("get result from server: " + res + " as param is " + weekDate);

//            String res2 = client.login("aaa", "123");
        } finally {
            client.shutdown();
        }
    }
}
