package shop.geeksasang.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.utils.jwt.NoIntercept;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rabbitmq")
public class MQController {
    private final AmqpAdmin admin;
    private final RabbitTemplate rabbitTemplate;
    private final String EXCHANGE = "chatting-room-exchange-test2";

    @GetMapping("/create")
    @NoIntercept //TODO:개발을 위해 임시로 jwt 허용되게한 것. 추후 제거 바람.
    public String createChattingRoom(@RequestParam String email, @RequestParam String chattingRoomUUID){

        String exchangeName = "chatting-" + "exchange-" + chattingRoomUUID;
        Exchange exchange = new FanoutExchange(exchangeName);
        admin.declareExchange(exchange);

        Queue queue = new Queue(email, true, false, false);
        QueueInformation queueInfo = admin.getQueueInfo(email); // 기존 큐 조회
        // 큐가 없으면 생성
        if(queueInfo == null){
            admin.declareQueue(queue);
        }

        Binding binding = new Binding(email, Binding.DestinationType.QUEUE, exchangeName, "asdf",null); //TODO: fanoutExchange는 routingKey가 필요없지만 없으면 에러나서 임시로 입력 함.
        admin.declareBinding(binding);
        return "OK";
    }

    @GetMapping
    @NoIntercept//TODO:개발을 위해 임시로 jwt 허용되게한 것. 추후 제거 바람.
    public String joinChattingRoom(@RequestParam String email, @RequestParam String chattingRoomUUID){

        String exchangeName = "chatting-" + "exchange-" + chattingRoomUUID;

        Queue queue = new Queue(email, true, false, false);
        QueueInformation queueInfo = admin.getQueueInfo(email);
        // 큐가 없으면 생성
        if(queueInfo == null){
            admin.declareQueue(queue);
        }

        Binding binding = new Binding(email, Binding.DestinationType.QUEUE, exchangeName, "asdf",null);
        admin.declareBinding(binding);
        return "OK";
    }

    @GetMapping("/msg")
    @NoIntercept//TODO:개발을 위해 임시로 jwt 허용되게한 것. 추후 제거 바람.
    public String sendMessage(@RequestParam String msg, @RequestParam String chattingRoomUUID){
        String exchangeName = "chatting-" + "exchange-" + chattingRoomUUID;

        rabbitTemplate.convertAndSend(exchangeName, "asdf", msg);
        return "OK";
    }

    public String getRoutingKey(String queueName){
        String[] routingKey = queueName.split("\\.");

        StringBuilder sb = new StringBuilder();
        sb.append(routingKey[0]);
        sb.append(".");
        sb.append(routingKey[1]);
        sb.append(".*");

        return sb.toString();
    }
}
