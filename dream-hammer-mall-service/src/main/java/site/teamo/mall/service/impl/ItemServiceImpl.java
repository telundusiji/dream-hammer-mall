package site.teamo.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.teamo.mall.bean.Items;
import site.teamo.mall.bean.ItemsImg;
import site.teamo.mall.bean.ItemsParam;
import site.teamo.mall.bean.ItemsSpec;
import site.teamo.mall.bean.vo.*;
import site.teamo.mall.common.enums.CommentLevel;
import site.teamo.mall.common.util.DesensitizationUtil;
import site.teamo.mall.common.util.PagedGridResult;
import site.teamo.mall.dao.*;
import site.teamo.mall.service.ItemService;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Override
    public Items queryItemById(String itemId) {

        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Override
    public CommentLevelCountsVO queryCommentCount(String itemId) {
        CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO();
        itemsCommentsMapper.queryCommentCount(itemId).forEach(x -> {
            if (CommentLevel.GOOD.type == x.getCommentLevel()) {
                commentLevelCountsVO.setGoodCounts(x.getCount());
            }
            if (CommentLevel.NORMAL.type == x.getCommentLevel()) {
                commentLevelCountsVO.setNormalCounts(x.getCount());
            }
            if (CommentLevel.BAD.type == x.getCommentLevel()) {
                commentLevelCountsVO.setBadCounts(x.getCount());
            }
        });
        commentLevelCountsVO.setTotalCounts(
                commentLevelCountsVO.getBadCounts()
                        + commentLevelCountsVO.getNormalCounts()
                        + commentLevelCountsVO.getGoodCounts());
        return commentLevelCountsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedItemComments(String itemId, Integer commentLevel, Integer page, Integer pageSize) {
        Map<String, Object> para = new HashMap<>();
        para.put("itemId", itemId);
        para.put("commentLevel", commentLevel);
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVO> itemCommentVOS = itemsCommentsMapper.queryItemComments(para).stream().map(x -> {
            x.setNickname(DesensitizationUtil.commonDisplay(x.getNickname()));
            return x;
        }).collect(Collectors.toList());
        return PagedGridResult.getPagedGridResult(itemCommentVOS, page);
    }

    @Override
    public PagedGridResult searchItems(String key, String sort, Integer page, Integer pageSize) {
        Map<String,Object> para = new HashMap<>();
        para.put("key",key);
        para.put("sort",sort);

        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> searchItemsVOS = itemsMapper.searchItems(para);
        return PagedGridResult.getPagedGridResult(searchItemsVOS,page);
    }

    @Override
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String,Object> para = new HashMap<>();
        para.put("catId",catId);
        para.put("sort",sort);

        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> searchItemsVOS = itemsMapper.searchItems(para);
        return PagedGridResult.getPagedGridResult(searchItemsVOS,page);
    }

    @Override
    public List<ShopcartVO> queryItemBySpecIds(String specIds) {
        return itemsMapper.queryItemBySpecIds(Arrays.asList(specIds.split(",")));
    }


}
