package com.xjhqre.enums;

/**
 * 限流类型
 *
 * @author ruoyi
 */

public enum LimitType {
    /**
     * 默认策略全局限流，按接口名称限流
     */
    DEFAULT,

    /**
     * 根据请求者IP进行限流 ip + 接口名称限流
     */
    IP
}