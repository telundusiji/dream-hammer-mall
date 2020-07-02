package site.teamo.mall.service.impl.usercenter;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.teamo.mall.bean.OrderStatus;
import site.teamo.mall.bean.Orders;
import site.teamo.mall.bean.vo.MyOrdersVO;
import site.teamo.mall.bean.vo.OrderStatusCountsVO;
import site.teamo.mall.common.enums.OrderStatusEnum;
import site.teamo.mall.common.enums.YesNo;
import site.teamo.mall.common.util.PagedGridResult;
import site.teamo.mall.dao.OrderStatusMapper;
import site.teamo.mall.dao.OrdersMapper;
import site.teamo.mall.service.usercenter.MyOrderService;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {

        Map<String, Object> para = new HashMap<>();
        para.put("userId", userId);
        if (orderStatus != null) {
            para.put("orderStatus", orderStatus);
        }

        PageHelper.startPage(page, pageSize);
        List<MyOrdersVO> myOrdersVOS = ordersMapper.queryMyOrders(para);

        return PagedGridResult.getPagedGridResult(myOrdersVOS, page);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void send(String orderId) {
        OrderStatus orderStatus = OrderStatus.builder()
                .orderStatus(OrderStatusEnum.WAIT_RECEIVE.type)
                .deliverTime(new Date())
                .build();

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);

        orderStatusMapper.updateByExampleSelective(orderStatus, example);
    }

    @Override
    public Orders queryMyOrder(String userId, String orderId) {
        Orders orders = Orders.builder()
                .id(orderId)
                .userId(userId)
                .isDelete(YesNo.NO.type)
                .build();
        return ordersMapper.selectOne(orders);
    }

    @Override
    public void confirmReceive(String orderId) {
        OrderStatus orderStatus = OrderStatus.builder()
                .orderStatus(OrderStatusEnum.SUCCESS.type)
                .successTime(new Date())
                .build();

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);

        orderStatusMapper.updateByExampleSelective(orderStatus, example);
    }

    @Override
    public void deleteOrder(String userId, String orderId) {
        Orders orders = Orders.builder()
                .isDelete(YesNo.YES.type)
                .updatedTime(new Date())
                .build();

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        criteria.andEqualTo("userId", userId);

        ordersMapper.updateByExampleSelective(orders, example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatusCountsVO getOrderStatusCount(String userId) {
        Map<String, Object> para = new HashMap<>();
        para.put("userId", userId);
        para.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        Integer waitPayCount = ordersMapper.getMyOrderStatusCount(para);

        para.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        Integer waitDeliverCount = ordersMapper.getMyOrderStatusCount(para);

        para.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        Integer waitReceiveCount = ordersMapper.getMyOrderStatusCount(para);

        para.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        para.put("isComment", YesNo.NO.type);
        Integer waitCommentCount = ordersMapper.getMyOrderStatusCount(para);

        return new OrderStatusCountsVO(waitPayCount, waitDeliverCount, waitReceiveCount, waitCommentCount);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult getMyOrderTrend(String userId, Integer page, Integer pageSize) {

        Map<String, Object> para = new HashMap<>();
        para.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<OrderStatus> list = ordersMapper.getMyOrderTrend(para);
        return PagedGridResult.getPagedGridResult(list, page);
    }


}
