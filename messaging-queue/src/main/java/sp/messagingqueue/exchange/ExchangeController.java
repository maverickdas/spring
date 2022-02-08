package sp.messagingqueue.exchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import io.swagger.annotations.Api;

@RestController
// @Api(value = "/exchange", description = "Exchange")
@RequestMapping("exchange/")
public class ExchangeController {
    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping
    public String hello() {
        return "Hello World";
    }

    @GetMapping(path = "{exName}/show")
    public Map<String, Object> describe(
            @PathVariable("exName") String exchangeName) {
        return exchangeService.describeExchange(exchangeName);
    }

    @PutMapping(path = "{exName}/create")
    public void createExchange(
            @PathVariable("exName") String exchangeName,
            @RequestParam("type") String exchangeType) {
        exchangeService.registerExchange(exchangeName, exchangeType);
    }

    // Producer
    @PutMapping(path = "{exName}/queue/{queueName}")
    public void createQueue(
            @PathVariable("exName") String exchangeName,
            @PathVariable("queueName") String queueName,
            @RequestParam("key") String bindingKey) {
        exchangeService.createQueue(exchangeName, queueName, bindingKey);
    }

    @PostMapping(path = "{exName}/publish")
    public void publishToQueue(
            @PathVariable("exName") String exchangeName,
            @RequestParam("key") String routingKey,
            @RequestBody String message) {
        exchangeService.publishMessage(exchangeName, routingKey, message);
    }

    // Consumer
    @GetMapping(path = "{exName}/queue/{queueName}")
    public Message consumeFromQueue(
            @PathVariable("exName") String exchangeName,
            @PathVariable("queueName") String queueName) {
        return exchangeService.consumeMessage(exchangeName, queueName);
    }

}
