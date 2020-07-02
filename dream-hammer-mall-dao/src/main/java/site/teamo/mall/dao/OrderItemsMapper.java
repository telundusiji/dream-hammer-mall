package site.teamo.mall.dao;

import org.springframework.stereotype.Component;
import site.teamo.mall.bean.OrderItems;
import site.teamo.mall.common.dao.DreamHammerMallMapper;

@Component
public interface OrderItemsMapper extends DreamHammerMallMapper<OrderItems> {
}