package site.teamo.mall.service.usercenter;

import site.teamo.mall.bean.Orders;
import site.teamo.mall.bean.vo.MyOrdersVO;
import site.teamo.mall.bean.vo.OrderStatusCountsVO;
import site.teamo.mall.common.util.PagedGridResult;

import java.util.List;

public interface MyOrderService {

    PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);

    void send(String orderId);

    Orders queryMyOrder(String userId, String orderId);

    void confirmReceive(String orderId);

    void deleteOrder(String userId, String orderId);

    OrderStatusCountsVO getOrderStatusCount(String userId);

    PagedGridResult getMyOrderTrend(String userId,Integer page,Integer pageSize);
}
