package site.teamo.mall.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import site.teamo.mall.bean.Items;
import site.teamo.mall.bean.vo.SearchItemsVO;
import site.teamo.mall.bean.vo.ShopcartVO;
import site.teamo.mall.common.dao.DreamHammerMallMapper;

import java.util.List;
import java.util.Map;

@Component
public interface ItemsMapper extends DreamHammerMallMapper<Items> {
    List<SearchItemsVO> searchItems(@Param("para") Map<String,Object> para);
    List<SearchItemsVO> searchItemsByThirdCat(@Param("para") Map<String,Object> para);
    List<ShopcartVO> queryItemBySpecIds(@Param("paraList") List<String> specIds);
}