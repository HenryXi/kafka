# Kafka java example
In last [blog](http://www.henryxi.com/install-kafka-on-linux) we have learned 
how to install Kafka in Linux. We also know how to run a producer and a consumer
in commandline. In this tutorial I will show you produce and consume message 
with apache kafka client. 

**Maven dependency**
```xml
<dependencies>
    <dependency>
        <groupId>org.apache.kafka</groupId>
        <artifactId>kafka-clients</artifactId>
        <version>0.9.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
        <version>1.7.13</version>
    </dependency>
</dependencies>
```

**Producer code**
```java
public class SimpleProducer {
    private static final Logger Logger = LoggerFactory.getLogger(SimpleProducer.class);

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "<Your kafka server address>:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        producer.send(new ProducerRecord<>("test", "luck", "luck dog"));
        Logger.info("finish");
        producer.close();
    }
}
```
**Consumer code**
```java
public class SimpleConsumer {
    private static final Logger Logger = LoggerFactory.getLogger(SimpleConsumer.class);
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "<Your kafka server address>:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
                Logger.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
            }
        }
    }
}
```

1. Start your Kafka server(Don't know how to start? Click [here](http://www.henryxi.com/install-kafka-on-linux))
2. Run ``SimpleConsumer`` class.
3. Run ``SimpleProducer`` class.
4. In your consumer console you can see these out put.
```accesslog
2016-03-16 19:58:49,211 INFO  [WhereRU][main|SimpleConsumer] 28 main offset = 6151, key = luck, value = luck dog
```

**Summary**

The most important line of code in producer is ``producer.send(new ProducerRecord<>("test", "luck", "luck dog"));``. This 
line of code send message to ``test`` topic which contains a key and a value. 
For the consumer we use ``consumer.subscribe(Arrays.asList("test"))`` to subscribe 
a topic(named test). When consumer get a message from Kafka it will print into log.

Happy learning.
