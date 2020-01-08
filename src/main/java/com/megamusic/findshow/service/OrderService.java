package com.megamusic.findshow.service;

import com.github.wxpay.sdk.WXPayConstants;
import com.megamusic.findshow.common.utils.CommonUtils;
import com.megamusic.findshow.dao.ArtistRepository;
import com.megamusic.findshow.dao.OrderApplyDetailRepository;
import com.megamusic.findshow.dao.OrderRepository;
import com.megamusic.findshow.dao.UserRepository;
import com.megamusic.findshow.domain.dto.OrderApplyDto;
import com.megamusic.findshow.domain.dto.OrderInfoDto;
import com.megamusic.findshow.domain.dto.WXPayRespDto;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.Order;
import com.megamusic.findshow.domain.entity.OrderApplyDetail;
import com.megamusic.findshow.domain.entity.User;
import com.megamusic.findshow.domain.entity.constant.DeleteCodeEnum;
import com.megamusic.findshow.domain.entity.constant.EntityTypeEnum;
import com.megamusic.findshow.domain.vo.DataCollectionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengchao on 2019/3/18.
 */
@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderApplyDetailRepository orderApplyDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private WXPayService wxPayService;

    /**
     * 下单申请
     *
     * @param orderApplyDto
     */
    public String orderApply(OrderApplyDto orderApplyDto) {

        Long userId = orderApplyDto.getUserId();
        User user = userRepository.findById(userId);
        if (user == null) {
            log.error("[微信支付] 用户不存在");
            return null;
        }

        Artist artist = artistRepository.findOne(orderApplyDto.getEntityId());
        if (artist == null) {
            log.error("[微信支付] 艺人数据不存在");
            return null;
        }

        //生成订单号
        String orderNo = CommonUtils.generateOrderNo(userId, orderApplyDto.getEntityId());

        //订单落库
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderNo(orderNo);
        order.setOrderTitle("找演出商演平台-下单申请");
        order.setOrderInfo("");
        order.setOrderAmount(new BigDecimal(0));
        order.setPayAmount(new BigDecimal(0));
        order.setArtistId(orderApplyDto.getEntityId());
        order.setOrderImage(artist.getCover());
        order.setArtistType(EntityTypeEnum.ARTIST.getCode());
        order.setOrderType(Order.OrderType.RESERVE.getCode());
        order.setPayStatus(Order.PayStatus.AUDITING.getCode());
        order.setIsDeleted(DeleteCodeEnum.NOT_DELETEED.getCode());
        order.setCreated(System.currentTimeMillis());
        order.setUpdated(System.currentTimeMillis());
        orderRepository.save(order);

        //订单详情落库
        OrderApplyDetail orderApplyDetail = new OrderApplyDetail();
        orderApplyDetail.setOrderNo(orderNo);
        orderApplyDetail.setActiveDetail(orderApplyDto.getActiveDetail());
        orderApplyDetail.setActiveTime(orderApplyDto.getActiveTime());
        orderApplyDetail.setActiveType(orderApplyDto.getActiveType());
        orderApplyDetail.setContractPerson(orderApplyDto.getContractPerson());
        orderApplyDetail.setContractPhone(orderApplyDto.getContractPhone());
        orderApplyDetail.setServiceInfo(orderApplyDto.getServiceInfo());
        orderApplyDetail.setCreated(System.currentTimeMillis());
        orderApplyDetail.setUpdated(System.currentTimeMillis());
        orderApplyDetailRepository.save(orderApplyDetail);

        return orderNo;
    }


    /**
     * 微信支付生成预支付订单
     *
     * @param userId
     * @param orderNo
     * @return
     * @throws Exception
     */
    public WXPayRespDto genWxPayOrder(Long userId, String orderNo, String ip) throws Exception {
        //获取用户
        User user = userRepository.findById(userId);
        if (user == null) {
            log.error("[微信支付-生成预支付单] 用户不存在 userId:{}", userId);
            return null;
        }

        //获取订单
        Order order = orderRepository.getByOrderNo(orderNo);
        if (order == null) {
            log.error("[微信支付-生成预支付单] 订单不存在 orderNo:{}", orderNo);
            return null;
        }

        //验证订单状态 是否为审核通过
        if (!Order.PayStatus.PASS.getCode().equals(order.getPayStatus())) {
            log.error("[微信支付-生成预支付单] 订单状态非可支付状态 orderNo:{}", orderNo);
            return null;
        }

        //金额确认
        Long amount = order.getPayAmount().multiply(new BigDecimal(100)).longValue();
        if (amount <= 0) {
            log.error("[微信支付-生成预支付单] 订单支付金额异常 orderNo:{}", orderNo);
            return null;
        }

        //生成预支付单的直接返回
        if( !StringUtils.isEmpty(order.getPrepayId()) ){
            return wxPayService.generateWebResponse(order.getPrepayId(),WXPayConstants.SignType.MD5);
        }

        WXPayRespDto resp = wxPayService.wxPayProcess(user.getOpenid(), order.getOrderTitle(), ip, amount, orderNo);

        //更新订单
        order.setPrepayId(resp.getPrepayId());
        orderRepository.save(order);

        return resp;
    }

    /**
     * 根据类型获取用户订单
     *
     * @param userId
     * @param payStatus
     * @return
     */
    public DataCollectionVo getUserOrderListByStatus(Long userId, Order.PayStatus payStatus,Integer pageNum) {

        DataCollectionVo<OrderInfoDto> dataCollectionVo = new DataCollectionVo<OrderInfoDto>();

        int pageSize = 20;
        if (pageNum == null || pageNum < 0) pageNum = 0;

        Sort.Order sortOrder = new Sort.Order(Sort.Direction.DESC, "id");
        List<Sort.Order> sortOrderList = new ArrayList<Sort.Order>();
        sortOrderList.add(sortOrder);
        Sort sort = new Sort(sortOrderList);
        Pageable pageable = new PageRequest(pageNum, pageSize, sort);

        List<Order> orderList = orderRepository.getByUserIdAndAndPayStatusAndOrderType(userId,
                payStatus.getCode(), Order.OrderType.RESERVE.getCode(),pageable);
        if (CollectionUtils.isEmpty(orderList)) {
            dataCollectionVo.setEnd(true);
            return dataCollectionVo;
        }


        List<OrderInfoDto> resultList = new ArrayList<OrderInfoDto>();
        for (Order order : orderList) {
            //获取订单详情
            OrderApplyDetail orderApplyDetail = orderApplyDetailRepository.findByOrderNo(order.getOrderNo());
            if (orderApplyDetail == null) {
                log.error("[订单列表] 查询订单详情信息不存在，orderNo:{}", order);
                continue;
            }

            OrderInfoDto orderInfoDto = new OrderInfoDto();
            orderInfoDto.setOrderNo(order.getOrderNo());
            orderInfoDto.setImage(order.getOrderImage());
            orderInfoDto.setPayPrice(order.getOrderAmount().multiply(new BigDecimal(100)).longValue());
            orderInfoDto.setOrderTitle(order.getOrderTitle());
            orderInfoDto.setOrderInfo(order.getOrderInfo());
            orderInfoDto.setActiveType(orderApplyDetail.getActiveType());
            orderInfoDto.setActiveTime(orderApplyDetail.getActiveTime());

            resultList.add(orderInfoDto);
        }

        dataCollectionVo.setFpage(String.valueOf(pageNum+1));
        dataCollectionVo.setList(resultList);

        return dataCollectionVo;
    }

    /**
     * 获取订单支付结果
     * @param userId
     * @param orderNo
     * @return 0为支付中，结果未知 1为交易成功 2交易失败
     */
    public Integer getOrderStatus(Long userId,String orderNo){
        Order order = orderRepository.getByOrderNo(orderNo);
        if( !userId.equals(order.getUserId()) ){
            log.error("[订单结果查询失败] 订单生成用户与查询用户不一致，orderNo:{},userId:{}",orderNo,userId);
            return 0;
        }
        if(Order.PayStatus.CALLBACK.getCode().equals(order.getPayStatus()) ||
                Order.PayStatus.DONE.getCode().equals(order.getPayStatus()) ){
            return 1;
        }
        return 0;
    }

    /**
     * 确认订单完成
     */
    public void confirmOrder(String orderNo,Long userId) {
        Order order = orderRepository.getByOrderNo(orderNo);
        if( !userId.equals(order.getUserId()) ){
            log.error("[订单确认操作失败] 订单生成用户与查询用户不一致，orderNo:{},userId:{}",orderNo,userId);
            return;
        }
        if( !Order.PayStatus.CALLBACK.getCode().equals(order.getPayStatus()) ){
            log.error("[订单确认操作失败] 订单状态异常，payStatus,orderNo",order.getPayStatus().toString(),orderNo);
            return;
        }
        order.setPayStatus(Order.PayStatus.DONE.getCode());
        orderRepository.save(order);
    }

}
