package com.frostwolf.consumer;

import com.frostwolf.util.NSQLookupFactory;
import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.NSQMessage;
import com.github.brainlag.nsq.callbacks.NSQMessageCallback;
import com.github.brainlag.nsq.lookup.NSQLookup;

import java.util.logging.Logger;

/**
 * 消费者 接收消息
 */
public class Consumer {

    private static final Logger logger = Logger.getLogger(Consumer.class.getName());
    private static String host;
    private static int port;

    public Consumer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void subscribe(String topic, String channel) {
        logger.info("获取订阅消息");
        logger.info("创建一个NSQLookup对象，管理nsqd节点拓扑信息并提供最终一致性的发现服务的守护进程");
        //管理nsqd节点拓扑信息并提供最终一致性的发现服务的守护进程
        NSQLookup lookup = NSQLookupFactory.init();
        //外网ip地址和lookup端口号
        lookup.addLookupAddress(this.host, this.port);
        logger.info("添加NSQLookup地址， host:" + this.host + ", port:" + this.port);

        //定义一个NSQConsumer对象
        //lookup
        //topic 与生产者约定好的topic，接收该topic下对应的消息
        //channel 工具，将消息写入/tmp文件的日志文件，文件名默认由主题topic+主机+日期时间戳组成
        //回调函数 处理订阅的消息
        //lookup, topic名称 订阅消息
        logger.info("创建NSQConsumer对象，");
        NSQConsumer consumer = new NSQConsumer(lookup, topic, channel, new NSQMessageCallback() {
            public void message(NSQMessage nsqMessage) {
                //订阅的消息内容
                byte[] bytes = nsqMessage.getMessage();
                String message = new String(bytes);
                logger.info("消费者接收到订阅消息内容：" + message);
                nsqMessage.finished();
            }
        });

        //启动消费者
        consumer.start();
        logger.info("NSQConsumer启动");

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer("localhost", 4161);
        consumer.subscribe("test", "nsq_to_file");
    }
}
