package com.henry.xi.kafka;


import com.sinoiov.yyzc.commons.kafka.YyzcKafkaMessageExecutor;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

@Service("messageExecutor")
public class MessageExecutor implements YyzcKafkaMessageExecutor<Message> {
    public void execute(Object o) throws Exception {
        Message message = (Message) o;
        System.out.println(message.toString());
    }

    public TypeReference<Message> transferToObjectClass() throws Exception {
        return new TypeReference<Message>() {
        };
    }
}
