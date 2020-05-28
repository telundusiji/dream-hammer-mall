package site.teamo.mall.common.util;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedGridResult {
	
	private int page;			// 当前页数
	private int total;			// 总页数	
	private long records;		// 总记录数
	private List<?> rows;		// 每行显示的内容

	public static PagedGridResult getPagedGridResult(List<?> list, Integer page) {
		PageInfo<?> pageList = new PageInfo<>(list);
		PagedGridResult gridResult = PagedGridResult.builder()
				.page(page)
				.rows(list)
				.total(pageList.getPages())
				.records(pageList.getTotal())
				.build();
		return gridResult;
	}
}
