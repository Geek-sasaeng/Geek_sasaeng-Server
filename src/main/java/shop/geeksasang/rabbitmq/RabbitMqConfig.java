package shop.geeksasang.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {
    @Value("${spring.rabbitmq.host}")
    private String address;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    private final RabbitTemplate rabbitTemplate; // rabbitMQ 라이브러리 클래스

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(rabbitTemplate.getConnectionFactory());
    }

//    @Bean
//    public RabbitMQTut5Receiver receiver(){
//        return new RabbitMQTut5Receiver();
//    }
}
