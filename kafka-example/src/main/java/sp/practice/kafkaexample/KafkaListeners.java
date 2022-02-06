package sp.practice.kafkaexample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// @Component
// public class KafkaListeners {

// TODO: Dynamic args in annotations - https://stackoverflow.com/a/12568437/9587133
//     @Value("$(kafka.test.topic)")
//     String topicName;

//     // @KafkaListener(topics = topicName, )
// }
