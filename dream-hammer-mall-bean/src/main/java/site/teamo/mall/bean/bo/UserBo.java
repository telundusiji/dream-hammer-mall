package site.teamo.mall.bean.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@ApiModel(value = "用户Bo",description = "用户传输对象")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserBo {

    @ApiModelProperty(value = "用户名",name ="username")
    private String username;
    @ApiModelProperty(value = "密码",name = "password")
    private String password;
    @ApiModelProperty(value = "确认密码",name = "confirmPassword",required = false)
    private String confirmPassword;
}
