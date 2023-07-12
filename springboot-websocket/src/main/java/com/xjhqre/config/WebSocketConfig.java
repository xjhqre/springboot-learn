package com.xjhqre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * <p>
 * WebSocketConfig配置类
 * <p>
 *
 * @author xjhqre
 * @since 7月 12, 2023
 */
@Configuration
public class WebSocketConfig {
    /**
     * ServerEndpointExporter的作用是自动注册使用@ServerEndpoint注解声明的WebSocket端点。在WebSocket应用中，需要将WebSocket端点注册到服务器才能进行通信。ServerEndpointExporter类实现了Spring的EndpointExporter接口，它会扫描Spring上下文中所有使用@ServerEndpoint注解声明的类，并自动注册这些类作为WebSocket端点。通过将ServerEndpointExporter对象定义为一个Bean，可以使其在应用启动时自动执行注册操作，简化了手动注册WebSocket端点的步骤。
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
