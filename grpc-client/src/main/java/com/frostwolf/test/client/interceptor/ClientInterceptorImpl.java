package com.frostwolf.test.client.interceptor;

import com.frostwolf.test.client.WeatherClient;
import io.grpc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @Author:YYX
 * @Description:
 * @Date:Created in 18:10 2019/3/9
 * @Modified By:
 */
public class ClientInterceptorImpl implements ClientInterceptor {
	private static final Logger logger = Logger.getLogger(ClientInterceptorImpl.class.getName());
	private static final List<String> ignoreInterceptorMethods = new ArrayList<String>();
	private static final Metadata.Key<String> token = Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);

	static {
		ignoreInterceptorMethods.add("weather.WeatherService/login");
	}

	public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(final MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
		return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

			@Override
			public void start(Listener<RespT> responseListener, Metadata headers) {
				if(!ignoreInterceptorMethods.contains(method.getFullMethodName())) {
					logger.info("在header里添加token值");
					headers.put(token, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiYWFhIiwiaWQiOiIxMjMiLCJleHAiOjE1NTIxMjk5NzV9.0NsUM40v04i51qIF2fWcHeBPiEpPr3llL7j2KEut_Jc");
				}
				super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
					@Override
					public void onHeaders(Metadata headers) {
						/**
						 * if you don't need receive header from server, you can
						 * use {@link io.grpc.stub.MetadataUtils#attachHeaders}
						 * directly to send header
						 */
						logger.info("header received from server:" + headers);
						super.onHeaders(headers);
					}
				}, headers);
			}
		};
	}
}
