package site.teamo.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.teamo.mall.bean.bo.SubmitOrderBO;
import site.teamo.mall.common.util.MallJSONResult;

@Api(value = "订单",tags = "订单")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @ApiOperation(value = "创建订单",notes = "创建订单")
    @PostMapping("/create")
    public MallJSONResult create(
            @ApiParam(name = "submitOrderBO",value = "提交订单BO")
            @RequestBody SubmitOrderBO submitOrderBO){

        return MallJSONResult.ok();
    }
}
