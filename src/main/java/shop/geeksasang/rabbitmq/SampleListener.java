package shop.geeksasang.rabbitmq;//package shop.geeksasangchat.rabbitmq;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;

//TODO: 이 리스너 코드는 rabbitmq에서 인식 못함.
//@Component
//class SampleListener {
//    private static final Logger log = LoggerFactory.getLogger(SampleListener.class);  // 로깅
//
//    @RabbitListener(queues = "chatting-room-queue")    // rabbitMQ 리스너 지정 어노테이션
//    public void receiveMessage(final Message message) {
//        log.info(message.toString());
//    }
//}
