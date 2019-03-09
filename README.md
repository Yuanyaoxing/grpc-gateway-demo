# grpc-gateway-demo

简单RPC
包含了Server、GateWay、Client模块
Server监听了8089端口，实现一个根据传递过来的Week(周一)，返回一个Weather(天气)
GateWay监听了50050端口，将请求路由给同host下面的8089端口
Client 发送一个Week给localhost的50050端口，获取一个Weather

添加的JWT验证拦截器
登录时生成token，返回给客户端
客户端请求其它接口时将token放入header
服务端在拦截器中会验证非登录接口是否有token


客户端和服务端各添加trace拦截器