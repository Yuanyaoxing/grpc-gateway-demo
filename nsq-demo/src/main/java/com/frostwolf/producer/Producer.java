package com.frostwolf.producer;

import com.github.brainlag.nsq.NSQProducer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 生产者 发布消息
 * */
public class Producer {

    public static final Logger logger = Logger.getLogger(Producer.class.getName());
    private static String host;
    private static int port;

    public Producer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void publish(Map<String, Object> map) {
        logger.info("创建生产者对象");
        //创建生产者对象
        NSQProducer producer = new NSQProducer();
        //设定ip和port并启动
        producer.addAddress(this.host, this.port).start();
        logger.info("生产者对象创建成功，已启动。 host:localhost  port:4150");

        try{
            logger.info("处理发布数据");
            Iterator<String> iterator = map.keySet().iterator();
            int i = 1;
            while(iterator.hasNext()) {
                String key = iterator.next();
                String value = map.get(key).toString();
                producer.produce(key, value.getBytes());
                logger.info("发布第"+i+"条消息，topic:"+key+", value:"+value);
                i ++;
            }
            logger.info("消息发布完成");
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Producer producer = new Producer("localhost", 4150);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("test", "联盟胜利");
        map.put("test1", "晴朗，万里无云");
        producer.publish(map);
    }
}
