package com.dangdang.product.app.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by devin_yan on 16/11/23.
 */
public class Receiver {
    private static Logger log = LoggerFactory.getLogger(Receiver.class);

    /**
     * 处理消息
     * @param message
     * void
     */
    public void onMessage(String message) {
        log.info("Receiver Receved:"  + message);
    }
}
