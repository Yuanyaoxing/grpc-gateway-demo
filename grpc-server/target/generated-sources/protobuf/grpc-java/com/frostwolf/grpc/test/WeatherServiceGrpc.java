package com.frostwolf.grpc.test;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;

@javax.annotation.Generated("by gRPC proto compiler")
public class WeatherServiceGrpc {

  private WeatherServiceGrpc() {}

  public static final String SERVICE_NAME = "weather.WeatherService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<com.frostwolf.grpc.test.Week,
      com.frostwolf.grpc.test.Weather> METHOD_GET_WEATHER_BY_WEEK =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "weather.WeatherService", "getWeatherByWeek"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.frostwolf.grpc.test.Week.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.frostwolf.grpc.test.Weather.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<com.frostwolf.grpc.test.User,
      com.frostwolf.grpc.test.User> METHOD_LOGIN =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "weather.WeatherService", "login"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.frostwolf.grpc.test.User.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.frostwolf.grpc.test.User.getDefaultInstance()));

  public static WeatherServiceStub newStub(io.grpc.Channel channel) {
    return new WeatherServiceStub(channel);
  }

  public static WeatherServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new WeatherServiceBlockingStub(channel);
  }

  public static WeatherServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new WeatherServiceFutureStub(channel);
  }

  public static interface WeatherService {

    public void getWeatherByWeek(com.frostwolf.grpc.test.Week request,
        io.grpc.stub.StreamObserver<com.frostwolf.grpc.test.Weather> responseObserver);

    public void login(com.frostwolf.grpc.test.User request,
        io.grpc.stub.StreamObserver<com.frostwolf.grpc.test.User> responseObserver);
  }

  public static interface WeatherServiceBlockingClient {

    public com.frostwolf.grpc.test.Weather getWeatherByWeek(com.frostwolf.grpc.test.Week request);

    public com.frostwolf.grpc.test.User login(com.frostwolf.grpc.test.User request);
  }

  public static interface WeatherServiceFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<com.frostwolf.grpc.test.Weather> getWeatherByWeek(
        com.frostwolf.grpc.test.Week request);

    public com.google.common.util.concurrent.ListenableFuture<com.frostwolf.grpc.test.User> login(
        com.frostwolf.grpc.test.User request);
  }

  public static class WeatherServiceStub extends io.grpc.stub.AbstractStub<WeatherServiceStub>
      implements WeatherService {
    private WeatherServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WeatherServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeatherServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WeatherServiceStub(channel, callOptions);
    }

    @java.lang.Override
    public void getWeatherByWeek(com.frostwolf.grpc.test.Week request,
        io.grpc.stub.StreamObserver<com.frostwolf.grpc.test.Weather> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_WEATHER_BY_WEEK, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void login(com.frostwolf.grpc.test.User request,
        io.grpc.stub.StreamObserver<com.frostwolf.grpc.test.User> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_LOGIN, getCallOptions()), request, responseObserver);
    }
  }

  public static class WeatherServiceBlockingStub extends io.grpc.stub.AbstractStub<WeatherServiceBlockingStub>
      implements WeatherServiceBlockingClient {
    private WeatherServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WeatherServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeatherServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WeatherServiceBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public com.frostwolf.grpc.test.Weather getWeatherByWeek(com.frostwolf.grpc.test.Week request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_WEATHER_BY_WEEK, getCallOptions(), request);
    }

    @java.lang.Override
    public com.frostwolf.grpc.test.User login(com.frostwolf.grpc.test.User request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LOGIN, getCallOptions(), request);
    }
  }

  public static class WeatherServiceFutureStub extends io.grpc.stub.AbstractStub<WeatherServiceFutureStub>
      implements WeatherServiceFutureClient {
    private WeatherServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WeatherServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeatherServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WeatherServiceFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.frostwolf.grpc.test.Weather> getWeatherByWeek(
        com.frostwolf.grpc.test.Week request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_WEATHER_BY_WEEK, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.frostwolf.grpc.test.User> login(
        com.frostwolf.grpc.test.User request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_LOGIN, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_WEATHER_BY_WEEK = 0;
  private static final int METHODID_LOGIN = 1;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WeatherService serviceImpl;
    private final int methodId;

    public MethodHandlers(WeatherService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_WEATHER_BY_WEEK:
          serviceImpl.getWeatherByWeek((com.frostwolf.grpc.test.Week) request,
              (io.grpc.stub.StreamObserver<com.frostwolf.grpc.test.Weather>) responseObserver);
          break;
        case METHODID_LOGIN:
          serviceImpl.login((com.frostwolf.grpc.test.User) request,
              (io.grpc.stub.StreamObserver<com.frostwolf.grpc.test.User>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServerServiceDefinition bindService(
      final WeatherService serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(SERVICE_NAME)
        .addMethod(
          METHOD_GET_WEATHER_BY_WEEK,
          asyncUnaryCall(
            new MethodHandlers<
              com.frostwolf.grpc.test.Week,
              com.frostwolf.grpc.test.Weather>(
                serviceImpl, METHODID_GET_WEATHER_BY_WEEK)))
        .addMethod(
          METHOD_LOGIN,
          asyncUnaryCall(
            new MethodHandlers<
              com.frostwolf.grpc.test.User,
              com.frostwolf.grpc.test.User>(
                serviceImpl, METHODID_LOGIN)))
        .build();
  }
}
