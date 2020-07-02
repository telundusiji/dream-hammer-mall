package site.teamo.mall.service;

import site.teamo.mall.bean.OrderStatus;
import site.teamo.mall.bean.bo.SubmitOrderBO;
import site.teamo.mall.bean.vo.OrderVO;

public interface OrderService {

    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    void updateOrderStatus(String orderId, Integer orderStatus);

    OrderStatus queryOrderStatusInfo(String orderId);

    void closeTimeoutOrder();
}
