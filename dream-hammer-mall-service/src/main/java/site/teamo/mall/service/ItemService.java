package site.teamo.mall.service;

import site.teamo.mall.bean.Items;
import site.teamo.mall.bean.ItemsImg;
import site.teamo.mall.bean.ItemsParam;
import site.teamo.mall.bean.ItemsSpec;
import site.teamo.mall.bean.vo.CommentCountsVO;
import site.teamo.mall.bean.vo.CommentLevelCountsVO;
import site.teamo.mall.bean.vo.ItemCommentVO;
import site.teamo.mall.bean.vo.ShopcartVO;
import site.teamo.mall.common.util.PagedGridResult;

import java.util.List;

public interface ItemService {
    Items queryItemById(String itemId);

    List<ItemsImg> queryItemImgList(String itemId);

    List<ItemsSpec> queryItemSpecList(String itemId);

    ItemsParam queryItemParam(String itemId);

    CommentLevelCountsVO queryCommentCount(String itemId);

    PagedGridResult queryPagedItemComments(String itemId, Integer commentLevel, Integer page, Integer pageSize);

    PagedGridResult searchItems(String key, String sort, Integer page, Integer pageSize);

    PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    List<ShopcartVO> queryItemBySpecIds(String specIds);

    ItemsSpec queryItemSpecBySpecId(String specId);

    String queryItemMainImgById(String itemId);

    void decreaseItemSpecStock(String specId,Integer buyCounts);
}
