package site.teamo.mall.controller.usercenter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.teamo.mall.bean.Orders;
import site.teamo.mall.bean.bo.usercenter.OrderItemsCommentBO;
import site.teamo.mall.common.enums.YesNo;
import site.teamo.mall.common.util.MallJSONResult;
import site.teamo.mall.service.usercenter.MyCommentService;
import site.teamo.mall.service.usercenter.MyOrderService;

import java.util.List;

@Api(value = "我的评价", tags = "我的评价")
@RestController
@RequestMapping("/mycomments")
public class MyCommentController {

    @Autowired
    private MyCommentService myCommentService;

    @Autowired
    private MyOrderService myOrderService;

    @ApiOperation(value = "查询我的订单", notes = "查询我的订单")
    @PostMapping("/pending")
    public MallJSONResult pending(
            @ApiParam(name = "userId", value = "用户Id")
            @RequestParam(required = true)
                    String userId,
            @ApiParam(name = "orderId", value = "订单Id")
            @RequestParam
                    String orderId) {

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

        if (orders.getIsComment() == YesNo.YES.type) {
            return MallJSONResult.errorMsg("已经评价过了");
        }

        return MallJSONResult.ok(myCommentService.queryPendingComment(orderId));

    }

    @ApiOperation(value = "添加商品评价", notes = "添加商品评价")
    @PostMapping("/saveList")
    public MallJSONResult saveList(
            @ApiParam(name = "userId", value = "用户Id")
            @RequestParam(required = true)
                    String userId,
            @ApiParam(name = "orderId", value = "订单Id")
            @RequestParam
                    String orderId,
            @ApiParam(name = "commentList", value = "评论内容")
            @RequestBody
                    List<OrderItemsCommentBO> commentList) {

        if (StringUtils.isBlank(userId)) {
            return MallJSONResult.errorMsg("用户id不能为空");
        }
        if (StringUtils.isBlank(orderId)) {
            return MallJSONResult.errorMsg("订单Id不能为空");
        }

        if (commentList == null || commentList.size() == 0) {
            return MallJSONResult.errorMsg("评论内容不能为空");
        }

        Orders orders = myOrderService.queryMyOrder(userId, orderId);
        if (orders == null) {
            return MallJSONResult.errorMsg("用户和订单不匹配");
        }

        if (orders.getIsComment() == YesNo.YES.type) {
            return MallJSONResult.errorMsg("已经评价过了");
        }

        myCommentService.saveComment(userId,orderId,commentList);
        return MallJSONResult.ok();

    }

    @ApiOperation(value = "查询我的评价", notes = "查询我的评价")
    @PostMapping("/query")
    public MallJSONResult query(
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

        return MallJSONResult.ok(myCommentService.queryMyComment(userId, page, pageSize));

    }

}
