package sp.practice.kafkaexample;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;

// import sp.practice.kafkaexample.config.*;

@SpringBootApplication
public class KafkaApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    // KafkaApplication() {
    //     // ! Non CommandlineRunner methods dont have access to annotated Beans and Env objects
    //     // String topicName = env.getProperty("kafka.test.topic");
    //     String topicName = "springkfk_test", bootServer = "localhost:9092";
    //     // var producerConfig = new KafkaProducerConfig();
    //     // var kafkaTemplate = producerConfig.kafkaTemplate();

    //     // Parameterized init can be used for non-annotated startups
    //     // See - https://github.com/douevencode/spring-kafka-sample
    //     var myConsumer = new MyKafkaConsumer(bootServer, topicName);
    //     myConsumer.startTopicListener();

    //     // kafkaTemplate.send(topicName, "Hello");
    // }

    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaProducerTemplate,
                                        MyKafkaConsumer consumer) {
        return args -> {
            consumer.startTopicListener();
            String topicName = env.getProperty("kafka.test.topic");
            kafkaProducerTemplate.send(topicName, "Hello Kafka");
        };
    }
}
