package site.teamo.mall.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import site.teamo.mall.bean.OrderStatus;
import site.teamo.mall.bean.Orders;
import site.teamo.mall.bean.vo.MyOrdersVO;
import site.teamo.mall.common.dao.DreamHammerMallMapper;

import java.util.List;
import java.util.Map;

@Component
public interface OrdersMapper extends DreamHammerMallMapper<Orders> {

    List<MyOrdersVO> queryMyOrders(@Param("para") Map<String,Object> para);

    Integer getMyOrderStatusCount(@Param("para") Map<String,Object> para);

    List<OrderStatus> getMyOrderTrend(@Param("para") Map<String,Object> para);
}