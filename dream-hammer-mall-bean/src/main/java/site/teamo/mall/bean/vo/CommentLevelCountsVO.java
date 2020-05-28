package site.teamo.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于展示商品评价数量的vo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLevelCountsVO {

    private Integer totalCounts = 0;
    private Integer goodCounts = 0;
    private Integer normalCounts = 0;
    private Integer badCounts = 0;

}
