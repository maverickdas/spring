package sp.practice.kafkaexample.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Autowired
    private Environment env;

    @Bean
    public NewTopic getMyNewTopic() {
        String topicName = env.getProperty("kafka.test.topic");
        return TopicBuilder.name(topicName).build();
    }
}
