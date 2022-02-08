package sp.messagingqueue.exchange;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MessageQueue {
    private String queueName;
    private String bindingKey;
    private Queue<Message> queueBuffer;

    public MessageQueue(String queueName, String bindingKey) {
        this.queueName = queueName;
        this.bindingKey = bindingKey;
        queueBuffer = new LinkedList<>();
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getBindingKey() {
        return bindingKey;
    }

    public void setBindingKey(String bindingKey) {
        this.bindingKey = bindingKey;
    }

    public Queue<Message> getBuffer() {
        return queueBuffer;
    }

    public int getBufferSize() {
        return queueBuffer.size();
    }

    @Override
    public String toString() {
        return "MessageQueue [queueName=" + queueName + "]";
    }

    public Map<String, Object> description() {
        return Map.of(
            "name", queueName,
            "currSize", getBufferSize()
        );
    }

    public Queue<Message> getQueueBuffer() {
        return queueBuffer;
    }

    public void setQueueBuffer(Queue<Message> queueBuffer) {
        this.queueBuffer = queueBuffer;
    }

    public void addMessage(Message message) {
        queueBuffer.add(message);
    }

    public Message popMessage() {
        Message m = queueBuffer.peek();
        queueBuffer.remove();
        return m;
    }
}
