import com.frostwolf.grpc.test.User;
import com.frostwolf.grpc.test.Weather;
import com.frostwolf.grpc.test.WeatherServiceGrpc;
import com.frostwolf.grpc.test.Week;
import com.frostwolf.interceptor.ServerInterceptorImpl;
import com.frostwolf.util.JWTUtil;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;
import io.opentracing.ScopeManager;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.contrib.grpc.ServerTracingInterceptor;
import io.opentracing.propagation.Format;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author:YYX
 * @Description:
 * @Date:Created in 17:43 2019/2/15
 * @Modified By:
 */
public class Server {

    private Logger logger = Logger.getLogger(Server.class.getName());
    private static final int DEFAULT_PORT = 8089;
    private int port;//服务端口号
    private io.grpc.Server server;
    private final Tracer tracer = new Tracer() {
        @Override
        public ScopeManager scopeManager() {
            return null;
        }

        @Override
        public Span activeSpan() {
            return null;
        }

        @Override
        public SpanBuilder buildSpan(String s) {
            return null;
        }

        @Override
        public <C> void inject(SpanContext spanContext, Format<C> format, C c) {

        }

        @Override
        public <C> SpanContext extract(Format<C> format, C c) {
            return null;
        }
    };


    public Server(int port) {
        this(port, ServerBuilder.forPort(port));
    }

    public Server(int port, ServerBuilder<?> serverBuilder) {
        this.port = port;
        server = serverBuilder
                .forPort(this.port)
                .addService(ServerInterceptors.intercept(WeatherServiceGrpc.bindService(new WeatherServiceImpl()), new ServerInterceptorImpl(), new ServerTracingInterceptor(this.tracer)))    //给这个方法添加拦截器
                .build();
    }

    private void start() throws IOException {
        server.start();
        logger.info("Server has started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                Server.this.stop();
            }
        });
    }

    private void stop() {
        if(null != server) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if(null != server) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server;
        if(args.length > 0) {
            server = new Server(Integer.parseInt(args[0]));
        } else {
            server = new Server(DEFAULT_PORT);
        }

        server.start();
        server.blockUntilShutdown();
    }

    public class WeatherServiceImpl implements WeatherServiceGrpc.WeatherService {
        private final Logger logger = Logger.getLogger(WeatherServiceImpl.class.getName());

        private Map<String, String> map = new HashMap<String, String>(7);
        private final String LOGIN_NAME = "aaa";
        private final String LOGIN_ID = "123";

        public WeatherServiceImpl() {
            map.put("周一", "晴天");
            map.put("周二", "阴天");
            map.put("周三", "雨天");
            map.put("周四", "大晴天");
            map.put("周五", "大阴天");
            map.put("周六", "大雨天");
            map.put("周日", "今天天气真不错");
        }

        public void getWeatherByWeek(Week request, StreamObserver<Weather> responseObserver) {
            logger.log(Level.INFO, "request is coming, request=" + request.getName());

            Weather weather = Weather.newBuilder().setName(getWeatherByWeek(request.getName())).build();

            responseObserver.onNext(weather);
            responseObserver.onCompleted();
        }

        @Override
        public void login(User request, StreamObserver<User> responseObserver) {
            logger.info("调用登录方法");
            String userName = request.getName();
            String id = request.getId();
            logger.info("请求参数:name = " + userName + ";id + " + id);

            if(LOGIN_NAME.equals(userName) && LOGIN_ID.equals(id)) {
                String token = JWTUtil.sign(userName, id);
                logger.info("登录成功，生成token = " + token);
                User user = User.newBuilder().setId(id).setName(userName).setToken(token).build();

                responseObserver.onNext(user);
                responseObserver.onCompleted();
            }
        }

        public String getWeatherByWeek(String week) {
            String weather = map.get(week);
            if(null == weather) {
                return "你猜今天天气怎么样？";
            }
            return weather;
        }
    }

}
