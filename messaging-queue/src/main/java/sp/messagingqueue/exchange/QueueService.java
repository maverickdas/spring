// package sp.messagingqueue.exchange;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.springframework.stereotype.Service;

// @Service
// public class QueueService {
//     // TODO Make into a DAL
//     private HashMap<String, MessageQueue> queueMap;

//     public QueueService() {
//         queueMap = new HashMap<>();
//     }

//     public void registerQueue(String queueName) {
//         if (queueMap.containsKey(queueName)) {
//             throw new IllegalStateException("queue exists!");
//         } else {
//             queueMap.put(queueName, new MessageQueue(queueName));
//         }

//     }

//     public Map<String, Object> describeQueues() {
//         var resp = new HashMap<String, Object>();
//         queueMap.forEach((k,v) -> {
//             resp.put(k, v.description());
//         });
//         return resp;
//     }

//     public void publishToQueue(List<String> queueNames, String message) {
//         for (String qName: queueNames) {
//             if (!queueMap.containsKey(qName))
//                 continue;
//             else {
//                 queueMap.get(qName).getBuffer().add(message);
//             }
//         }
//     }

//     public String consumeFromQueue(String queueName) {
//         if (!queueMap.containsKey(queueName))
//             throw new IllegalStateException("queue does not exist!");
//         else {
//             String message = queueMap.get(queueName).getBuffer().peek();
//             queueMap.get(queueName).getBuffer().remove();
//             return message;
//         }
//     }
    
// }
