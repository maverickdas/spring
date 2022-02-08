package sp.messagingqueue.exchange;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ExchangeService {
    private Map<String, Exchange> exchangeMap;

    public ExchangeService() {
        exchangeMap = new HashMap<>();
    }

    public void registerExchange(String exchangeName, String type) {
        if (isValidExchange(exchangeName))
            throw new IllegalStateException("Exchange " + exchangeName + " already exists!");
        var newExchange = new Exchange(exchangeName, type);
        exchangeMap.put(exchangeName, newExchange);
    }

    public void deleteExchange(String exchangeName) {

    }

    private boolean isValidExchange(String exchangeName) {
        return exchangeMap.containsKey(exchangeName);
    }
    private void assertValidExchange(String exchangeName) {
        if (!isValidExchange(exchangeName))
            throw new IllegalStateException("Exchange " + exchangeName + " does not exist!");
    }

    public Map<String, Object> describeExchange(String exchangeName) {
        assertValidExchange(exchangeName);
        return exchangeMap.get(exchangeName).description();
    }

    public void createQueue(String exchangeName, String queueName, String bindingKey) {
        assertValidExchange(exchangeName);
        exchangeMap.get(exchangeName).registerQueue(queueName, bindingKey);
    }

    public void publishMessage(String exchangeName, String routingKey, String message) {
        assertValidExchange(exchangeName);
        exchangeMap.get(exchangeName).publishMessage(
            new Message(message),
            routingKey);
    }

    public Message consumeMessage(String exchangeName, String queueName) {
        assertValidExchange(exchangeName);
        return exchangeMap.get(exchangeName).consumeFromQueue(queueName);
    }
}
