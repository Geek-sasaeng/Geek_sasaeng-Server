package shop.geeksasang.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

    private final RabbitTemplate rabbitTemplate; // rabbitMQ 라이브러리 클래스

    @Bean// 큐 생성 빈
    public PartyChattingQueue partyChattingQueueImpl(){
        System.out.println("=========PartyChattingQueue 생성 및 빈 등록======");
        return new PartyChattingQueue(rabbitTemplate);
    }

    @Bean // 큐, exchange 바인딩 빈
    public Binding partyChattingQueueBinding(PartyChattingQueue queue) {
        System.out.println("=========PartyChattingQueue 바인딩 등록======");
        return BindingBuilder.bind(queue.getQueue()).to(queue.getTopicExchange()).with(PartyChattingQueue.ROUTING_KEY); // bind(바인딩할 큐, exchange, 라우팅 키)
    }
//
//    @Bean// 큐 생성 빈
//    public PartyChattingQueue2 partyChattingQueueImp5(){
//        System.out.println("=========PartyChattingQueue5 생성 및 빈 등록======");
//        return new PartyChattingQueue2(rabbitTemplate);
//    }
//
//    @Bean // 큐, exchange 바인딩 빈
//    public Binding partyChattingQueueBinding2(PartyChattingQueue2 queue) {
//        System.out.println("=========PartyChattingQueue5 바인딩 등록======");
//        return BindingBuilder.bind(queue.getQueue()).to(queue.getTopicExchange()).with(PartyChattingQueue2.ROUTING_KEY); // bind(바인딩할 큐, exchange, 라우팅 키)
//    }

//    @Bean
//    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(PartyChattingQueue.QUEUE_NAME);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }


//    // 5번 튜토리얼 Consumer 빈 등록
//    @Bean
//    public Tut5Receiver receiver() {
//        return new Tut5Receiver();
//    }

    @Bean
    public Queue hello() {
        System.out.println("=========helo 큐 생성 및 빈 등록======");
        return new Queue("hello");
    }

    @Bean
    public Queue PartyChattingRoomQueueTest6() {
        System.out.println("=========chattingRoomQueue 큐 생성 및 빈 등록======");
        return new Queue("chatting-room-queue-test6");
    }

    @Bean
    public TopicExchange PartyChattingRoomTopicTest6() {
        return new TopicExchange("chatting-room-exchange-test6", true, false);
    }
}
