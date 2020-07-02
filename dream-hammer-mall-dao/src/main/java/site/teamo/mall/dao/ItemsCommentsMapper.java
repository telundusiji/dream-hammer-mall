package site.teamo.mall.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import site.teamo.mall.bean.ItemsComments;
import site.teamo.mall.bean.vo.CommentCountsVO;
import site.teamo.mall.bean.vo.ItemCommentVO;
import site.teamo.mall.bean.vo.MyCommentVO;
import site.teamo.mall.common.dao.DreamHammerMallMapper;

import java.util.List;
import java.util.Map;

@Component
public interface ItemsCommentsMapper extends DreamHammerMallMapper<ItemsComments> {

    List<CommentCountsVO> queryCommentCount(String itemId);

    List<ItemCommentVO> queryItemComments(@Param("para") Map<String,Object> para);

    void saveComment(@Param("para") Map<String,Object> map);

    List<MyCommentVO> queryMyComment(String userId);
}