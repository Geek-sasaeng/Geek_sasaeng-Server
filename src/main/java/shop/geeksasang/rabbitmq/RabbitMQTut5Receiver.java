package shop.geeksasang.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

//TODO: rabbitmq 5번 튜토리얼 Consumer 코드임, 구독 및 수신 로컬 스프링에서는 테스트 성공.
public class RabbitMQTut5Receiver {

    @RabbitListener(queues = "87")
    public void receive1(String in) throws InterruptedException {
        receive(in, 1);
    }

    @RabbitListener(queues = "neo")
    public void receive2(String in) throws InterruptedException {
        receive(in, 2);
    }

    public void receive(String in, int receiver) throws
            InterruptedException {
        System.out.println("===============Received 5번 튜토리얼 소비자<" + in + ">");
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + receiver + " [x] Received '"
                + in + "'");
        doWork(in);
        watch.stop();
        System.out.println("instance " + receiver + " [x] Done in "
                + watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}