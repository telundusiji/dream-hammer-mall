package site.teamo.mall.controller.usercenter;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.teamo.mall.common.util.MallJSONResult;
import site.teamo.mall.service.usercenter.CenterUserService;

@Api(value = "用户中心",tags = "用户中心")
@RestController
@RequestMapping("/center")
public class UserCenterController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "用户信息",notes = "用户信息")
    @GetMapping("/userInfo")
    public MallJSONResult userInfo(@ApiParam(name = "userId",value = "用户id") @RequestParam String userId){
        return MallJSONResult.ok(centerUserService.queryUserInfo(userId));
    }
}
