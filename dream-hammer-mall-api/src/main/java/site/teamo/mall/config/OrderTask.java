package site.teamo.mall.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.teamo.mall.service.OrderService;

@Component
@EnableScheduling
public class OrderTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderTask.class);

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder(){
        orderService.closeTimeoutOrder();
    }
}
