package sp.messagingqueue.exchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exchange {
    private String exchangeName;
    private ExchangeType exchangeType;
    private Map<String, MessageQueue> queueMap;

    public Exchange(String exchangeName) {
        this.exchangeName = exchangeName;
        this.queueMap = new HashMap<>();
    }

    public Exchange(String exchangeName, ExchangeType exchangeType) {
        this.exchangeName = exchangeName;
        this.exchangeType = exchangeType;
        this.queueMap = new HashMap<>();
    }

    public Exchange(String exchangeName, String exchangeType) {
        this.exchangeName = exchangeName;
        this.exchangeType = ExchangeType.valueOf(exchangeType);
        this.queueMap = new HashMap<>();
    }

    private boolean isValidQueue(String queueName) {
        return queueMap.containsKey(queueName);
    }
    private void assertValidQueue(String queueName) {
        if (!isValidQueue(queueName))
            throw new IllegalStateException("Queue " + queueName + " does not exist!");
    }

    public void registerQueue(String queueName, String bindingKey) {
        if (isValidQueue(queueName)) {
            throw new IllegalStateException("Queue " + queueName + " already exists!");
        } else {
            queueMap.put(queueName, new MessageQueue(queueName, bindingKey));
        }

    }

    public Map<String, Object> description() {
        var resp = new HashMap<String, Object>();
        var nest = new HashMap<String, Object>();
        queueMap.forEach((k,v) -> {
            nest.put(k, v.description());
        });
        resp.put("queues", nest);
        resp.put("name", exchangeName);
        resp.put("type", exchangeType);
        return resp;
    }

    private void publishToQueues(List<String> queueNames, Message message) {
        for (String qName: queueNames) {
            if (!isValidQueue(qName))
                continue;
            else {
                queueMap.get(qName).addMessage(message);;
            }
        }
    }

    public void publishMessage(Message message, String routingKey) {
        // Decide queues
        List<String> routeQueueNames = new ArrayList<>();
        if (exchangeType == ExchangeType.DIRECT)
            queueMap.forEach((k,v) -> {
                if (v.getBindingKey().equals(routingKey))
                    routeQueueNames.add(k);
            });
        else if (exchangeType == ExchangeType.FANOUT)
            routeQueueNames.addAll(queueMap.keySet());
        // Publish
        publishToQueues(routeQueueNames, message);
    }

    public Message consumeFromQueue(String queueName) {
        assertValidQueue(queueName);
        if (queueMap.get(queueName).getBufferSize() == 0)
            return null;
        var message = queueMap.get(queueName).popMessage();
        return message;
    }
}
