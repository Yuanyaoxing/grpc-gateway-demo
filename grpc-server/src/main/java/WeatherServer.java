import com.frostwolf.grpc.test.Weather;
import com.frostwolf.grpc.test.WeatherServiceGrpc;
import com.frostwolf.grpc.test.Week;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

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
public class WeatherServer {

    private Logger logger = Logger.getLogger(WeatherServer.class.getName());

    private static final int DEFAULT_PORT = 8089;

    private int port;//服务端口号

    private Server server;

    public WeatherServer(int port) {
        this(port, ServerBuilder.forPort(port));
    }

    public WeatherServer(int port, ServerBuilder<?> serverBuilder) {
        this.port = port;
        server = serverBuilder.forPort(this.port).addService(WeatherServiceGrpc.bindService(new WeatherServiceImpl())).build();
    }

    private void start() throws IOException {
        server.start();
        logger.info("Server has started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                WeatherServer.this.stop();
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
        WeatherServer weatherServer;
        if(args.length > 0) {
            weatherServer = new WeatherServer(Integer.parseInt(args[0]));
        } else {
            weatherServer = new WeatherServer(DEFAULT_PORT);
        }

        weatherServer.start();
        weatherServer.blockUntilShutdown();
    }

    public class WeatherServiceImpl implements WeatherServiceGrpc.WeatherService {
        private final Logger logger = Logger.getLogger(WeatherServiceImpl.class.getName());

        private Map<String, String> map = new HashMap<String, String>();

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

        public String getWeatherByWeek(String week) {
            String weather = map.get(week);
            if(null == weather) {
                return "你猜今天天气怎么样？";
            }
            return weather;
        }
    }

}
