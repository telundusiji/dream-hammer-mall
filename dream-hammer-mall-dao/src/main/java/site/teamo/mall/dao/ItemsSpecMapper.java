package site.teamo.mall.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import site.teamo.mall.bean.ItemsSpec;
import site.teamo.mall.common.dao.DreamHammerMallMapper;

import java.util.Map;

@Component
public interface ItemsSpecMapper extends DreamHammerMallMapper<ItemsSpec> {
    Integer decreaseItemSpecStock(@Param("para") Map<String,Object> para);
}