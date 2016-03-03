package com.henry.xi.kafka.test;

import com.sinoiov.lhjh.fv.intf.bean.UserOwnerAuthLevelBean;
import com.sinoiov.yyzc.commons.kafka.YyzcKafkaBasicProducer;

public class Producer {
    public static void main(String[] args) {
        YyzcKafkaBasicProducer producer = new YyzcKafkaBasicProducer();
        UserOwnerAuthLevelBean userOwnerAuthLevelBean=new UserOwnerAuthLevelBean();
        userOwnerAuthLevelBean.setPhone("13811837912");
        userOwnerAuthLevelBean.setUserId("03cbb0a9-f104-4975-b34f-a05b0399c042");
        userOwnerAuthLevelBean.setOwnerAuthLevel("1");
        try {
            producer.send("LHJH_USER_OWNER_AUTH_LEVEL", userOwnerAuthLevelBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
