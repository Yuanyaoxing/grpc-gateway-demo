// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: weather.proto

package com.frostwolf.grpc.test;

public final class WeatherTest {
  private WeatherTest() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_weather_Week_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_weather_Week_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_weather_Weather_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_weather_Weather_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_weather_User_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_weather_User_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rweather.proto\022\007weather\"\024\n\004Week\022\014\n\004name" +
      "\030\001 \001(\t\"\027\n\007Weather\022\014\n\004name\030\001 \001(\t\"/\n\004User\022" +
      "\n\n\002id\030\001 \001(\t\022\014\n\004name\030\002 \001(\t\022\r\n\005token\030\003 \001(\t" +
      "2p\n\016WeatherService\0225\n\020getWeatherByWeek\022\r" +
      ".weather.Week\032\020.weather.Weather\"\000\022\'\n\005log" +
      "in\022\r.weather.User\032\r.weather.User\"\000B-\n\027co" +
      "m.frostwolf.grpc.testB\013WeatherTestP\001\242\002\002L" +
      "Rb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_weather_Week_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_weather_Week_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_weather_Week_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_weather_Weather_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_weather_Weather_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_weather_Weather_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_weather_User_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_weather_User_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_weather_User_descriptor,
        new java.lang.String[] { "Id", "Name", "Token", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
