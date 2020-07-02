package site.teamo.mall.service.impl.usercenter;

import com.github.pagehelper.PageHelper;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.teamo.mall.bean.OrderItems;
import site.teamo.mall.bean.OrderStatus;
import site.teamo.mall.bean.Orders;
import site.teamo.mall.bean.bo.usercenter.OrderItemsCommentBO;
import site.teamo.mall.bean.vo.MyCommentVO;
import site.teamo.mall.common.enums.YesNo;
import site.teamo.mall.common.util.PagedGridResult;
import site.teamo.mall.dao.ItemsCommentsMapper;
import site.teamo.mall.dao.OrderItemsMapper;
import site.teamo.mall.dao.OrderStatusMapper;
import site.teamo.mall.dao.OrdersMapper;
import site.teamo.mall.service.usercenter.MyCommentService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MyCommentServiceImpl implements MyCommentService {

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {

        OrderItems orderItems = OrderItems.builder()
                .orderId(orderId)
                .build();

        return orderItemsMapper.select(orderItems);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(String userId, String orderId, List<OrderItemsCommentBO> list) {
        list = list.stream().map(x->{
            x.setCommentId(sid.nextShort());
            return x;
        }).collect(Collectors.toList());

        Map<String,Object> para = new HashMap<>();
        para.put("userId",userId);
        para.put("commentList",list);

        itemsCommentsMapper.saveComment(para);

        Orders orders = Orders.builder()
                .id(orderId)
                .isComment(YesNo.YES.type)
                .updatedTime(new Date())
                .build();

        ordersMapper.updateByPrimaryKeySelective(orders);

        OrderStatus orderStatus = OrderStatus.builder()
                .orderId(orderId)
                .commentTime(new Date())
                .build();

        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComment(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<MyCommentVO> list = itemsCommentsMapper.queryMyComment(userId);
        return PagedGridResult.getPagedGridResult(list,page);
    }
}
