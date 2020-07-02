package site.teamo.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import site.teamo.mall.bean.bo.SubmitOrderBO;
import site.teamo.mall.bean.vo.MerchantOrdersVO;
import site.teamo.mall.bean.vo.OrderVO;
import site.teamo.mall.common.enums.OrderStatusEnum;
import site.teamo.mall.common.enums.PayMethod;
import site.teamo.mall.common.util.CookieUtils;
import site.teamo.mall.common.util.MallJSONResult;
import site.teamo.mall.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单", tags = "订单")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final String PAY_RETURN_URL = "http://api.z.mukewang.com/foodie-dev-api/orders/notifyMerchantOrderPaid";

    private static final String PAYMENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "创建订单", notes = "创建订单")
    @PostMapping("/create")
    public MallJSONResult create(
            @ApiParam(name = "submitOrderBO", value = "提交订单BO")
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (submitOrderBO.getPayMethod() != PayMethod.WEI_XIN.type && submitOrderBO.getPayMethod() != PayMethod.ALI_PAY.type) {
            return MallJSONResult.errorMsg("不支持的支付方式");
        }
        OrderVO orderVO = orderService.createOrder(submitOrderBO);

        CookieUtils.setCookie(request, response, "shopcart", "", true);

        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(PAY_RETURN_URL);
        merchantOrdersVO.setAmount(1);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.add("imoocUserId", "8102054-1432316254");
        httpHeaders.add("password", "d9aw-fek2-f0l2-5096");

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO,httpHeaders);

        ResponseEntity<MallJSONResult> resultResponseEntity = restTemplate.postForEntity(PAYMENT_URL, entity, MallJSONResult.class);
        MallJSONResult result = resultResponseEntity.getBody();
        if(result.getStatus()!=200){
            return MallJSONResult.errorMsg("支付中心调用失败");
        }
        return MallJSONResult.ok(orderVO.getOrderId());
    }

    @ApiOperation(value = "支付回调", notes = "支付回调")
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(@ApiParam(name = "orderId",value = "订单id") @RequestParam String orderId) {
        orderService.updateOrderStatus(orderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @ApiOperation(value = "查询订单状态",notes = "查询订单状态")
    @PostMapping("/getPaidOrderInfo")
    public MallJSONResult getPaidOrderInfo(@ApiParam(name = "orderId",value = "订单id") @RequestParam String orderId){
        return MallJSONResult.ok(orderService.queryOrderStatusInfo(orderId));
    }
}
