import io.grpc.internal.GateWayServerBuilder;
import io.grpc.internal.GateWayServerImpl;
import io.grpc.netty.DefaultChannelFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.logging.Logger;

/**
 * @Author:YYX
 * @Description:
 * @Date:Created in 19:57 2019/3/5
 * @Modified By:
 */
public class Start {

	private static final Logger logger = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws IOException, InterruptedException {
		logger.info("Strarting grpc gateway server");
		SocketAddress address = new InetSocketAddress("localhost", 50050);//创建一个SocketAddress对象，设置该网关的地址和端口
		GateWayServerImpl impl = GateWayServerBuilder
				.forAddress(address)	//forAddress(SocketAddress)	将对应地址放进去
				.setChannelFactory(DefaultChannelFactory.Default("localhost", 8089))		//setChannelFactory(IChannelFactory)	路由到对应的服务器上
				.build();
		impl.start();
		logger.info("grpc gateway server started, listen on port 50050, router to host localhost port 8089");
		impl.awaitTermination();
	}



}
