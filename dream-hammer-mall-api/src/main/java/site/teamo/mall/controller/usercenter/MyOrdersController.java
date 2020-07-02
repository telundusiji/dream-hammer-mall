package site.teamo.mall.controller.usercenter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.teamo.mall.bean.Orders;
import site.teamo.mall.common.util.MallJSONResult;
import site.teamo.mall.service.usercenter.MyOrderService;

@Api(value = "我的订单", tags = "我的订单")
@RestController
@RequestMapping("/myorders")
public class MyOrdersController {

    @Autowired
    private MyOrderService myOrderService;

    @ApiOperation(value = "查询我的订单", notes = "查询我的订单")
    @PostMapping("/query")
    public MallJSONResult query(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam(required = true)
                    String userId,
            @ApiParam(name = "orderStatus", value = "订单状态")
            @RequestParam
                    Integer orderStatus,
            @ApiParam(name = "page", value = "页码")
            @RequestParam
                    Integer page,
            @ApiParam(name = "pageSize", value = "页面大小")
            @RequestParam
                    Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return MallJSONResult.errorMsg("用户id不能为空");
        }

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        return MallJSONResult.ok(myOrderService.queryMyOrders(userId, orderStatus, page, pageSize));

    }

    @ApiOperation(value = "发货", notes = "发货")
    @GetMapping("/send")
    public MallJSONResult send(
            @ApiParam(name = "orderId", value = "订单id")
            @RequestParam(required = true)
                    String orderId
    ) {

        if (StringUtils.isBlank(orderId)) {
            return MallJSONResult.errorMsg("订单id不能为空");
        }
        myOrderService.send(orderId);
        return MallJSONResult.ok();
    }


    @ApiOperation(value = "确认收货", notes = "确认收货")
    @PostMapping("/confirmReceive")
    public MallJSONResult confirmReceive(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam(required = true)
                    String userId,
            @ApiParam(name = "orderId", value = "订单Id")
            @RequestParam
                    String orderId
    ) {

        if (StringUtils.isBlank(userId)) {
            return MallJSONResult.errorMsg("用户id不能为空");
        }
        if (StringUtils.isBlank(orderId)) {
            return MallJSONResult.errorMsg("订单Id不能为空");
        }

        Orders orders = myOrderService.queryMyOrder(userId, orderId);
        if (orders == null) {
            return MallJSONResult.errorMsg("用户和订单不匹配");
        }
        myOrderService.confirmReceive(orderId);
        return MallJSONResult.ok();

    }

    @ApiOperation(value = "删除订单", notes = "删除订单")
    @PostMapping("/delete")
    public MallJSONResult delete(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam(required = true)
                    String userId,
            @ApiParam(name = "orderId", value = "订单Id")
            @RequestParam
                    String orderId
    ) {

        if (StringUtils.isBlank(userId)) {
            return MallJSONResult.errorMsg("用户id不能为空");
        }
        if (StringUtils.isBlank(orderId)) {
            return MallJSONResult.errorMsg("订单Id不能为空");
        }

        Orders orders = myOrderService.queryMyOrder(userId, orderId);
        if (orders == null) {
            return MallJSONResult.errorMsg("用户和订单不匹配");
        }

        myOrderService.deleteOrder(userId, orderId);
        return MallJSONResult.ok();

    }

    @ApiOperation(value = "根据订单状态查询订单数", notes = "根据订单状态查询订单数")
    @PostMapping("/statusCounts")
    public MallJSONResult orderStatusCount(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam(required = true)
                    String userId) {

        if (StringUtils.isBlank(userId)) {
            return MallJSONResult.errorMsg("用户id不能为空");
        }
        return MallJSONResult.ok(myOrderService.getOrderStatusCount(userId));

    }

    @ApiOperation(value = "查询订单动向", notes = "查询订单动向")
    @PostMapping("/trend")
    public MallJSONResult trend(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam(required = true)
                    String userId,
            @ApiParam(name = "page", value = "页码")
            @RequestParam
                    Integer page,
            @ApiParam(name = "pageSize", value = "页面大小")
            @RequestParam
                    Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return MallJSONResult.errorMsg("用户id不能为空");
        }

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        return MallJSONResult.ok(myOrderService.getMyOrderTrend(userId, page, pageSize));

    }
}
