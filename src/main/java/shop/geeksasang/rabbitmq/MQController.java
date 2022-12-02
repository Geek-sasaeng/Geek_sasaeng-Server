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

    @GetMapping("/create")
    @NoIntercept //TODO:개발을 위해 임시로 jwt 허용되게한 것. 추후 제거 바람.
    public String createChatRoom(@RequestParam String email, @RequestParam String ChatRoomUUID){

        String exchangeName = "chatting-" + "exchange-" + ChatRoomUUID;
        Exchange exchange = new FanoutExchange(exchangeName);
        admin.declareExchange(exchange);
        System.out.println("rabbitMQ fanOutExchange가 생성되었습니다.");

        return "OK";
    }

    @GetMapping
    @NoIntercept//TODO:개발을 위해 임시로 jwt 허용되게한 것. 추후 제거 바람.
    public String joinChatRoom(@RequestParam int memberId, @RequestParam String ChatRoomUUID){

        String memberIdStr = Integer.toString(memberId);
        String exchangeName = "chatting-" + "exchange-" + ChatRoomUUID;

        Queue queue = new Queue(memberIdStr, true, false, false);
        QueueInformation queueInfo = admin.getQueueInfo(memberIdStr);
        // 큐가 없으면 생성
        if(queueInfo == null){
            admin.declareQueue(queue);
        }
        System.out.println("");
        Binding binding = new Binding(memberIdStr, Binding.DestinationType.QUEUE, exchangeName, "asdf",null);
        admin.declareBinding(binding);
        System.out.println("rabbitmq 멤버 큐 바인딩 성공했습니다.");
        return "OK";
    }

    @GetMapping("/msg")
    @NoIntercept//TODO:개발을 위해 임시로 jwt 허용되게한 것. 추후 제거 바람.
    public String sendMessage(@RequestParam String msg, @RequestParam String ChatRoomUUID){
        String exchangeName = "chatting-" + "exchange-" + ChatRoomUUID;

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
