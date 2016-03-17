package com.henry.xi.kafka.manual.offset;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

public class ManualOffsetConsumer1 {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ManualOffsetConsumer1.class);

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "123.57.136.60:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));
        try{
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    Logger.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
                    throw new Exception("Exception thrown during processing message.");
                }
            }
        }catch (Exception e){
            Logger.error(e.getMessage());
        }
    }
}
