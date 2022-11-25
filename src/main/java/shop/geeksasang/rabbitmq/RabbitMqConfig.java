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


    // 5번 튜토리얼 Consumer 빈 등록
    @Bean
    public Tut5Receiver receiver() {
        return new Tut5Receiver();
    }

    @Bean
    public Queue hello2() {
        System.out.println("=========helo 큐 생성 및 빈 등록======");
        return new Queue("hello2");
    }

    @Bean
    public Queue PartyChattingRoomQueueTest2() {
        System.out.println("=========chattingRoomQueue 큐 생성 및 빈 등록======");
        return new Queue("chatting-room-queue-test2");
    }

    @Bean
    public Queue PartyChattingRoomQueueTest9() {
        System.out.println("=========chattingRoomQueue 큐 생성 및 빈 등록======");
        return new Queue("chatting-room-queue-test9");
    }

    @Bean
    public Queue PartyChattingRoomQueueTest10() {
        System.out.println("=========chattingRoomQueue 큐 생성 및 빈 등록======");
        return new Queue("chatting-room-queue-test10");
    }

    @Bean
    public TopicExchange partyChattingRoomTopicTest2() {
        return new TopicExchange("chatting-room-exchange-test2", true, false);
    }

//    @Bean
//    public FanoutExchange fanoutTest1() {
//        return new FanoutExchange("test1.fanout", true, false);
//    }

//    @Bean
//    public Binding binding1(FanoutExchange fanoutTest1,
//                            Queue PartyChattingRoomQueueTest9) {
//        return BindingBuilder.bind(PartyChattingRoomQueueTest9).to(fanoutTest1);
//    }
//
//    @Bean
//    public Binding binding2(FanoutExchange fanoutTest1,
//                            Queue PartyChattingRoomQueueTest10) {
//        return BindingBuilder.bind(PartyChattingRoomQueueTest10).to(fanoutTest1);
//    }

    //==============================

//
//    @Bean
//    public Queue PartyChattingRoomQueueTest11() {
//        System.out.println("=========chattingRoomQueue 큐 생성 및 빈 등록======");
//        return new Queue("chatting-room-queue-test11");
//    }
//
//    @Bean
//    public Queue PartyChattingRoomQueueTest12() {
//        System.out.println("=========chattingRoomQueue 큐 생성 및 빈 등록======");
//        return new Queue("chatting-room-queue-test12");
//    }
//
//    @Bean
//    public TopicExchange PartyChattingRoomTopicTest3() {
//        return new TopicExchange("chatting-room-exchange-test3", true, false);
//    }
//
//    @Bean
//    public Binding binding3(TopicExchange PartyChattingRoomTopicTest3,
//                            Queue PartyChattingRoomQueueTest11) {
//        return BindingBuilder.bind(PartyChattingRoomQueueTest11).to(PartyChattingRoomTopicTest3).with("chatting.test.room.#");
//    }
//
//    @Bean
//    public Binding binding4(TopicExchange PartyChattingRoomTopicTest3,
//                            Queue PartyChattingRoomQueueTest12) {
//        return BindingBuilder.bind(PartyChattingRoomQueueTest12).to(PartyChattingRoomTopicTest3).with("chatting.test.room.#");
//    }
}
