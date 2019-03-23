
import com.frostwolf.dao.IUserDAO;
import com.frostwolf.dao.IWeatherDAO;
import com.frostwolf.dao.impl.UserDAOImpl;
import com.frostwolf.dao.impl.WeatherDAOImpl;
import com.frostwolf.grpc.test.User;
import com.frostwolf.grpc.test.Weather;
import com.frostwolf.grpc.test.WeatherServiceGrpc;
import com.frostwolf.grpc.test.Week;
import com.frostwolf.util.JWTUtil;
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

//    private IUserDAO userDAO = new UserDAOImpl();
    private IWeatherDAO weatherDAO = new WeatherDAOImpl();

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
            logger.info("根据日期获取天气， week:" + week);
            String weather = weatherDAO.getWeatherByDate(week);
            if(null == weather) {
                return "你猜今天天气怎么样？";
            }
            logger.info("获取天气成功, weather:" + weather);



//            logger.info("开始封装NSQ消息");
//            //封装消息
//            Map<String, Object> nsqMessage = new HashMap<String, Object>();
//            nsqMessage.put("test", weather);
//            logger.info("封装NSQ消息完毕，创建发布者");
//            //
//            Producer producer = new Producer("localhost", 4150);
//            producer.publish(nsqMessage);
//            logger.info("消息发布成功");
            return weather;
        }


        /*public void publish() {
            List<com.frostwolf.bean.User> users = userDAO.findAll();
            logger.info("数据库获取用户成功 数量：" + (null == users ? 0 : users.size()));
            Producer producer = new Producer("localhost", 4150);
            Map<String, Object> messageMap = new HashMap<String, Object>();
            messageMap.put("test", "没事查询了一下用户数量，总共有：" + (null == users ? 0 : users.size()));
            producer.publish(messageMap);
        }*/

    }

}
