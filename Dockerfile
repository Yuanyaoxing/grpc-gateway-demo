FROM java:8
VOLUME /tmp
COPY target/resource-1.0-SNAPSHOT.jar resource.jar
RUN bash -c "touch /resource.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","resource.jar"]

# docker run -d -p 18080:8080 --name docker-resource leesia/resource:1.0
#基础镜像是java:8，copy命令将/target下的resource jar拷贝到镜像中。ENTRYPOINT是容器启动命令