package sp.practice.kafkaexample;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaConsumer {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${kafka.test.topic}")
    private String topicName;

    // @Autowired
    public MyKafkaConsumer(String bootstrapServers, String topicName) {
        // Parameterized init can be used for non-annotated startups
        // See - https://github.com/douevencode/spring-kafka-sample
        this.bootstrapServers = bootstrapServers;
        this.topicName = topicName;
    }

    public MyKafkaConsumer() {
        // Used in case of auto-init during DI
        // member methods are initialized via @Value annotations

        // TODO: Parameterized constructor does not work in case of DI. WHY??
        // Parameter 0 of constructor in sp.practice.kafkaexample.MyKafkaConsumer required a bean of type 'java.lang.String' that could not be found.
        // possible alternative - https://stackoverflow.com/questions/53684852/consider-defining-a-bean-of-type-java-lang-string-in-your-configuration
    }

    void startTopicListener() {
        System.out.println("Preparing Listener ..");
        MessageListener<String, String> messageListener = record -> {
            System.out.println("Received " + record.value());
        };

        var container = new ConcurrentMessageListenerContainer<String, String> (
                                consumerFactory(bootstrapServers),
                                containerProperties(topicName, messageListener)
                            );
        container.start();
        System.out.println("Listener started.");
    }

    // get config
    // @Bean
    private Map<String, Object> consumerConfig(String bootstrapServers) {
        return Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
            // ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.GROUP_ID_CONFIG, "group0",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    }

    // @Bean
    private ContainerProperties containerProperties(
                String topicName,
                MessageListener<String, String> messageListener) {
        var containerProps = new ContainerProperties(topicName);
        containerProps.setMessageListener(messageListener);
        return containerProps;
    }

    // fn to define producer factory from config
    // @Bean
    private ConsumerFactory<String, String> consumerFactory(String bootstrapServers) {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(bootstrapServers));
    }
}
