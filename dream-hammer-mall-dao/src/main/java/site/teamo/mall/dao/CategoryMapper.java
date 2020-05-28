package site.teamo.mall.dao;

import org.springframework.stereotype.Component;
import site.teamo.mall.bean.Category;
import site.teamo.mall.bean.vo.CategoryVO;
import site.teamo.mall.bean.vo.NewItemsVO;
import site.teamo.mall.common.dao.DreamHammerMallMapper;

import java.util.List;

@Component
public interface CategoryMapper extends DreamHammerMallMapper<Category> {
    List<CategoryVO> getSubCatList(Integer fatherId);

    List<NewItemsVO> getSixNewItemsLazy(Integer catRootId);
}