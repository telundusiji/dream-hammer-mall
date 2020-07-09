package site.teamo.mall.service;

import site.teamo.mall.bean.OrderStatus;
import site.teamo.mall.bean.bo.ShopcartBO;
import site.teamo.mall.bean.bo.SubmitOrderBO;
import site.teamo.mall.bean.vo.OrderVO;

import java.util.Map;

public interface OrderService {

    OrderVO createOrder(Map<String, ShopcartBO> shopcartBOMap, SubmitOrderBO submitOrderBO);

    void updateOrderStatus(String orderId, Integer orderStatus);

    OrderStatus queryOrderStatusInfo(String orderId);

    void closeTimeoutOrder();
}
