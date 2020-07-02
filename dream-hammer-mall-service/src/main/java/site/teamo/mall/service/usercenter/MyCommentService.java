package site.teamo.mall.service.usercenter;

import site.teamo.mall.bean.OrderItems;
import site.teamo.mall.bean.bo.usercenter.OrderItemsCommentBO;
import site.teamo.mall.common.util.PagedGridResult;

import java.util.List;

public interface MyCommentService {

    List<OrderItems> queryPendingComment(String orderId);

    void saveComment(String userId, String orderId, List<OrderItemsCommentBO> list);

    PagedGridResult queryMyComment(String userId,Integer page,Integer pageSize);

}
