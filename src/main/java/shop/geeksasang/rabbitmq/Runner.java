package shop.geeksasang.rabbitmq;

//@Component
//public class Runner implements CommandLineRunner {
//
//    private final RabbitTemplate rabbitTemplate;
//    private final Receiver receiver;
//
//    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
//        this.receiver = receiver;
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Sending message...");
//        rabbitTemplate.convertAndSend( "foo.bar.baz", "Hello from RabbitMQ!");
//        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
//    }
//
//}
//
