package site.teamo.mall.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import site.teamo.mall.common.util.MallJSONResult;
import site.teamo.mall.common.util.MobileEmailUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBO {
    private String addressId;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;

    public MallJSONResult check(){
        if (StringUtils.isBlank(receiver)) {
            return MallJSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return MallJSONResult.errorMsg("收货人姓名不能太长");
        }

        if (StringUtils.isBlank(mobile)) {
            return MallJSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return MallJSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return MallJSONResult.errorMsg("收货人手机号格式不正确");
        }
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return MallJSONResult.errorMsg("收货地址信息不能为空");
        }
        return MallJSONResult.ok();
    }
}
