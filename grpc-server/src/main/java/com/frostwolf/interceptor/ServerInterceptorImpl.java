package com.frostwolf.interceptor;

import com.frostwolf.util.JWTUtil;
import io.grpc.*;
import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @Author:YYX
 * @Description:
 * @Date:Created in 17:55 2019/3/9
 * @Modified By:
 */
public class ServerInterceptorImpl implements ServerInterceptor {
	private static final Logger logger = Logger.getLogger(ServerInterceptorImpl.class.getName());
	private static final List<String> ignoreInterceptorMethods = new ArrayList<String>();

	static {
		ignoreInterceptorMethods.add("weather.WeatherService/login");
	}

	@Override
	public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
		logger.info("服务端拦截器开始,拦截方法 = " + call.getMethodDescriptor().getFullMethodName());
		//筛选不用拦截的方法
		if(ignoreInterceptorMethods.contains(call.getMethodDescriptor().getFullMethodName())) {
			logger.info("方法 = " + call.getMethodDescriptor().getFullMethodName() + "不用拦截");
			//服务端写回参数
			ServerCall<ReqT, RespT> serverCall = new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
				@Override
				public void sendHeaders(Metadata headers) {
					super.sendHeaders(headers);
				}
			};
			return next.startCall(serverCall,headers);
		}

		//获取客户端参数
		final Metadata.Key<String> token = Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);
		final Metadata.Key<String> user_Id = Metadata.Key.of("userId", Metadata.ASCII_STRING_MARSHALLER);
		String tokenStr = headers.get(token);
		logger.info("请求token = " + tokenStr);


		if (StringUtil.isNullOrEmpty(tokenStr)){
			System.err.println("未收到客户端token,关闭此连接");
			call.close(Status.DATA_LOSS,headers);
		}

		//验证token
		final String userId = tokenStr;
		logger.info("token "+tokenStr+" 验证成功");

		//服务端写回参数
		ServerCall<ReqT, RespT> serverCall = new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
			@Override
			public void sendHeaders(Metadata headers) {
				headers.put(user_Id,userId);
				super.sendHeaders(headers);
			}
		};
		return next.startCall(serverCall,headers);
	}
}
