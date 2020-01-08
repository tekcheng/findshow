package com.megamusic.findshow.controller;

import com.megamusic.findshow.common.constant.SystemConstantsEnum;
import com.megamusic.findshow.common.utils.CommonUtils;
import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.dao.UserRepository;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.domain.dto.GenPayOrderReqDto;
import com.megamusic.findshow.domain.dto.OrderApplyDto;
import com.megamusic.findshow.domain.dto.WXPayRespDto;
import com.megamusic.findshow.domain.entity.Order;
import com.megamusic.findshow.domain.vo.DataCollectionVo;
import com.megamusic.findshow.service.OrderService;
import com.megamusic.findshow.service.WXPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单
 * Created by chengchao on 2018/10/9.
 */
@Controller
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WXPayService wxPayService;


    /**
     * 订单申请
     * @return
     */
    @RequestMapping(value = "apply",method = { RequestMethod.POST })
    @ResponseBody
    public Response apply( @RequestBody OrderApplyDto orderApplyDto ) {
        if( orderApplyDto.getUserId()==null ||
                orderApplyDto.getEntityId()==null ||
                orderApplyDto.getEntityType()==null ||
                StringUtils.isEmpty(orderApplyDto.getContractPerson()) ||
                StringUtils.isEmpty(orderApplyDto.getContractPhone())
                ){
            ResponseUtils.getErrorResponse(SystemConstantsEnum.PARAM_ERROR);
        }


        String orderNo = orderService.orderApply(orderApplyDto);

        if(orderNo==null)
            ResponseUtils.getErrorResponse(SystemConstantsEnum.FAIL);

        return ResponseUtils.getSuccessResponse(orderNo);
    }

    /**
     * 生成预先支付单
     * @return
     */
    @RequestMapping("genPayOrder")
    @ResponseBody
    public Response genPayOrder( @RequestBody GenPayOrderReqDto genPayOrderReqDto,HttpServletRequest request ) {

        if(genPayOrderReqDto.getUserId()==null || StringUtils.isEmpty(genPayOrderReqDto.getOrderNo())){
            ResponseUtils.getErrorResponse(SystemConstantsEnum.PARAM_ERROR);
        }
        String ip = CommonUtils.getIpAddr(request);
        WXPayRespDto wxPayRespDto;
        try {
            wxPayRespDto = orderService.genWxPayOrder(genPayOrderReqDto.getUserId(),genPayOrderReqDto.getOrderNo(),ip);
        } catch (Exception e){
            log.error("[微信支付-生成预支付订单] 失败，参数：{}",genPayOrderReqDto.toString(),e);
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.FAIL);
        }

        return ResponseUtils.getSuccessResponse(wxPayRespDto);
    }

    /**
     * 审核中订单
     * @return
     */
    @RequestMapping("list/auditing")
    @ResponseBody
    public Response auditingList(Long userId,String fpage) {
        Integer pageNum = 0;
        if(!StringUtils.isEmpty(fpage)){
            pageNum = Integer.valueOf(fpage);
        }
        DataCollectionVo data = orderService.getUserOrderListByStatus(userId, Order.PayStatus.AUDITING,pageNum);
        return ResponseUtils.getSuccessResponse(data);
    }

    /**
     * 待支付订单
     * @return
     */
    @RequestMapping("list/pass")
    @ResponseBody
    public Response passList(Long userId,String fpage) {
        Integer pageNum = 0;
        if(!StringUtils.isEmpty(fpage)){
            pageNum = Integer.valueOf(fpage);
        }
        DataCollectionVo data = orderService.getUserOrderListByStatus(userId, Order.PayStatus.PASS,pageNum);
        return ResponseUtils.getSuccessResponse(data);
    }

    /**
     * 审核拒绝订单（以关闭）
     * @return
     */
    @RequestMapping("list/reject")
    @ResponseBody
    public Response rejectList(Long userId,String fpage) {
        Integer pageNum = 0;
        if(!StringUtils.isEmpty(fpage)){
            pageNum = Integer.valueOf(fpage);
        }
        DataCollectionVo data = orderService.getUserOrderListByStatus(userId, Order.PayStatus.REJECT,pageNum);
        return ResponseUtils.getSuccessResponse(data);
    }

    /**
     * 支付完成
     * @return
     */
    @RequestMapping("list/paySuccess")
    @ResponseBody
    public Response paySuccessList(Long userId,String fpage) {
        Integer pageNum = 0;
        if(!StringUtils.isEmpty(fpage)){
            pageNum = Integer.valueOf(fpage);
        }
        DataCollectionVo data = orderService.getUserOrderListByStatus(userId, Order.PayStatus.CALLBACK,pageNum);
        return ResponseUtils.getSuccessResponse(data);
    }

    /**
     * 确认完成
     * @return
     */
    @RequestMapping("list/confirm")
    @ResponseBody
    public Response confirmList(Long userId,String fpage) {
        Integer pageNum = 0;
        if(!StringUtils.isEmpty(fpage)){
            pageNum = Integer.valueOf(fpage);
        }
        DataCollectionVo data = orderService.getUserOrderListByStatus(userId, Order.PayStatus.DONE,pageNum);
        return ResponseUtils.getSuccessResponse(data);
    }

    /**
     * 查询订单支付
     * @param orderNo
     * @param userId
     * @return
     */
    @RequestMapping("queryResult")
    @ResponseBody
    public Response queryResult(String orderNo,Long userId) {
        return ResponseUtils.getSuccessResponse(orderService.getOrderStatus(userId,orderNo));
    }

    /**
     * 确认完成
     * @param orderNo
     * @param userId
     * @return
     */
    @RequestMapping("confirmOrder")
    @ResponseBody
    public Response confirmOrder(String orderNo,Long userId) {
        orderService.confirmOrder(orderNo,userId);
        return ResponseUtils.getSuccessResponse("成功");
    }

    @RequestMapping("wxPay")
    @ResponseBody
    public Response wxPay() {
        return ResponseUtils.getSuccessResponse("");
    }




}
