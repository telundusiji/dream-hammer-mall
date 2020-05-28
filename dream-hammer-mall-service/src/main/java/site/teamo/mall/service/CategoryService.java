package site.teamo.mall.service;

import site.teamo.mall.bean.Category;
import site.teamo.mall.bean.vo.CategoryVO;
import site.teamo.mall.bean.vo.NewItemsVO;

import java.util.List;

public interface CategoryService {

    List<Category> queryAllRootLevelCat();

    List<CategoryVO> getSubCatList(Integer fatherId);

    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
