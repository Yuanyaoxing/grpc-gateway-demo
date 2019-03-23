package com.frostwolf.consumer;

public class ConsumerOne {

    public static void main(String[] args) {
        Consumer consumer = new Consumer("localhost", 4161);
        consumer.subscribe("test6", "nsq_to_file");
    }

}
