package site.teamo.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.teamo.mall.bean.bo.ShopcartBO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {

    private String orderId;         // 商户订单号
    private MerchantOrdersVO merchantOrdersVO;
}