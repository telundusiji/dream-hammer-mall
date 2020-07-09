package site.teamo.mall.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.teamo.mall.bean.*;
import site.teamo.mall.bean.bo.ShopcartBO;
import site.teamo.mall.bean.bo.SubmitOrderBO;
import site.teamo.mall.bean.vo.MerchantOrdersVO;
import site.teamo.mall.bean.vo.OrderVO;
import site.teamo.mall.common.enums.OrderStatusEnum;
import site.teamo.mall.common.enums.YesNo;
import site.teamo.mall.dao.OrderItemsMapper;
import site.teamo.mall.dao.OrderStatusMapper;
import site.teamo.mall.dao.OrdersMapper;
import site.teamo.mall.service.AddressService;
import site.teamo.mall.service.ItemService;
import site.teamo.mall.service.OrderService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(Map<String, ShopcartBO> shopcartBOMap, SubmitOrderBO submitOrderBO) {
        Integer postAmount = 0;
        //step:新订单
        UserAddress address = addressService.queryAddress(submitOrderBO.getUserId(), submitOrderBO.getAddressId());
        String orderId = sid.nextShort();
        Orders order = Orders.builder()
                .id(orderId)
                .userId(submitOrderBO.getUserId())
                .receiverName(address.getReceiver())
                .receiverMobile(address.getMobile())
                .receiverAddress(address.getProvince()
                        + address.getCity()
                        + address.getDistrict()
                        + address.getDetail())
                .postAmount(postAmount)
                .payMethod(submitOrderBO.getPayMethod())
                .leftMsg(submitOrderBO.getLeftMsg())
                .isComment(YesNo.NO.type)
                .isDelete(YesNo.NO.type)
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
        //step:订单商品信息
        List<ItemsSpec> itemSpec = Arrays.asList(submitOrderBO.getItemSpecIds().split(","))
                .stream()
                .map(x -> itemService.queryItemSpecBySpecId(x))
                .collect(Collectors.toList());
        Integer totalAmount = itemSpec.stream().mapToInt(x -> x.getPriceNormal() * 1).sum();
        Integer realPayAmount = itemSpec.stream().mapToInt(x -> x.getPriceDiscount() * 1).sum();
        itemSpec.stream().map(x -> {
            Items item = itemService.queryItemById(x.getItemId());
            ShopcartBO shopcartBO = shopcartBOMap.remove(x.getId());
            return OrderItems.builder()
                    .id(sid.nextShort())
                    .orderId(orderId)
                    .itemId(x.getItemId())
                    .itemName(item.getItemName())
                    .itemImg(itemService.queryItemMainImgById(x.getItemId()))
                    .buyCounts(shopcartBO.getBuyCounts())
                    .itemSpecId(x.getId())
                    .itemSpecName(x.getName())
                    .price(x.getPriceDiscount())
                    .build();
        }).forEach(x -> {
            orderItemsMapper.insert(x);
            itemService.decreaseItemSpecStock(x.getItemSpecId(),x.getBuyCounts());
        });
        order.setTotalAmount(totalAmount);
        order.setRealPayAmount(realPayAmount);
        ordersMapper.insert(order);
        //step:订单状态
        OrderStatus orderStatus = OrderStatus.builder()
                .orderId(orderId)
                .orderStatus(OrderStatusEnum.WAIT_PAY.type)
                .createdTime(new Date())
                .build();
        orderStatusMapper.insert(orderStatus);

        //调用支付中心
        MerchantOrdersVO merchantOrdersVO = MerchantOrdersVO.builder()
                .merchantOrderId(orderId)
                .merchantUserId(submitOrderBO.getUserId())
                .amount(realPayAmount+postAmount)
                .payMethod(submitOrderBO.getPayMethod())
                .build();

        OrderVO orderVO = OrderVO.builder()
                .orderId(orderId)
                .merchantOrdersVO(merchantOrdersVO)
                .build();

        return orderVO;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus os = OrderStatus.builder()
                .orderId(orderId)
                .orderStatus(orderStatus)
                .payTime(new Date())
                .build();

        orderStatusMapper.updateByPrimaryKeySelective(os);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    private static final long TIMEOUT = 3600*1000L;

    @Override
    public void closeTimeoutOrder() {
        OrderStatus orderStatus = OrderStatus.builder()
                .orderStatus(OrderStatusEnum.WAIT_PAY.type)
                .build();

        orderStatusMapper.select(orderStatus).stream().forEach(x->{
            long time = System.currentTimeMillis() - x.getCreatedTime().getTime();
            if(time > TIMEOUT){
                closeOrder(x.getOrderId());
            }
        });
    }

    private void closeOrder(String orderId){
        OrderStatus orderStatus = OrderStatus.builder()
                .orderId(orderId)
                .orderStatus(OrderStatusEnum.CLOSE.type)
                .closeTime(new Date())
                .build();

        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }
}
