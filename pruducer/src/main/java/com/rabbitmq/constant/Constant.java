package com.rabbitmq.constant;

/**
 * Created by Barry
 * 2018/10/25 11:32
 */
public class Constant {

    /**
     * 投递中
     */
    public static final String SENDING = "0";
    /**
     * 投递成功
     */
    public static final String SEND_SUCCESS = "1";
    /**
     * 投递失败
     */
    public static final String SEND_FAILURE = "2";
    /**
     * 消息超时时间（单位：分钟）
     */
    public static final int ORDER_TIMEOUT = 1;
    /**
     * 消息最大重试次数
     */
    public static final int MAX_RETRY_COUNT = 3;
}
