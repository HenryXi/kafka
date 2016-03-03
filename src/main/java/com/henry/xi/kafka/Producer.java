package com.henry.xi.kafka;

import com.sinoiov.lhjh.friends.been.DistributeFeedBean;
import com.sinoiov.yyzc.commons.kafka.YyzcKafkaBasicProducer;

public class Producer {
    public static void main(String[] args) {
        YyzcKafkaBasicProducer producer = new YyzcKafkaBasicProducer();
        DistributeFeedBean distributeFeedBean = new DistributeFeedBean();
        distributeFeedBean.setMessageId("1");
        distributeFeedBean.setTimestamp("22");
        try {
            for (int i = 0; i < 1000; i++) {
                distributeFeedBean.setUserId("User" + i);
                producer.send("FRIENDS_DISTRIBUTE_FEED", distributeFeedBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
