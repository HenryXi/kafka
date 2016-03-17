# Consumer commit offset example
We have learned how to send message to Kafka and get message from it with 
java client. In demo of previous blog when Consumer get a message Kafka 
"think" this message have been consumed. Kafka continue to "send" next message.
Let's image another scenario while the Consumer processing a message an exception was thrown. Kafka "think" 
this message was consumed, actually is not. In this scenario we should commit offset manually. In other
words, when we make sure a message is processed successfully then tell Kafka "I have handled previous message,
now give me next message".

In previous blog we set ``enable.auto.commit`` to true. That means when Consumer get a message
it will commit a "success" message to Kafka. After getting this message Kafka change the offset.
In order to prove it we create two Consumers.

**Consumer1**
```
public class ManualOffsetCustomer1 {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ManualOffsetCustomer1.class);

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "Your Kafka server address:9092");
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
```
What you should note here is ``props.put("enable.auto.commit", "false")``. Consumer1 throw an exception 
when it get a message.

**Producer**

The producer code is same as [previous blog](http://www.henryxi.com/kafka-java-example).

**Consumer2**
```
public class ManualOffsetConsumer2 {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ManualOffsetConsumer2.class);

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "<Your Kafka server address>:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                Logger.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
                consumer.commitSync();
            }
        }

    }
}
```

**Steps**

* Start your Kafka server 
* Run Consumer1
* Run Producer, you will get the log like following
```
2016-03-17 22:28:27,938 INFO  [WhereRU][main|ManualOffsetConsumer1] 29 main offset = 6176, key = luck, value = luck dog
2016-03-17 22:28:27,938 ERROR [WhereRU][main|ManualOffsetConsumer1] 34 main Exception thrown during processing message.
```
* Run Consumer2, you will get the log like following
```
2016-03-17 22:30:19,020 INFO  [WhereRU][main|ManualOffsetConsumer2] 28 main offset = 6176, key = luck, value = luck dog
```
Same message with Consumer1. That means Kafka don't think message(offset is 6167) consumed. When Consumer2 start
Kafka send this message(offset is 6167) again. After Consumer2 processing this message and "tell" Kafka by 
``consumer.commitSync()`` Kafka "think" this message consumed. When we rerun Consumer2 again we will get nothing.

**Summary**

If you want manually control the offset during process message. You need set ``enable.auto.commit`` to false.
After processing the message you have to call ``consumer.commitSync()`` method to make Kafka update offset.