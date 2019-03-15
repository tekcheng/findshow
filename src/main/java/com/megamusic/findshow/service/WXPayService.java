package com.megamusic.findshow.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.megamusic.findshow.common.utils.WXPay.WxPayFsConfig;
import com.megamusic.findshow.dao.ArtistRepository;
import com.megamusic.findshow.dao.OrderRepository;
import com.megamusic.findshow.dao.UserRepository;
import com.megamusic.findshow.domain.dto.WXPayRespDto;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.Order;
import com.megamusic.findshow.domain.entity.User;
import com.megamusic.findshow.domain.entity.constant.DeleteCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by chengchao on 2018/9/28.
 */
@Service
@Slf4j
public class WXPayService {

    @Value("${wx_appid}")
    private String appId;

    @Value("${wx_mechid}")
    private String mchId;

    @Value("${wx_payKey}")
    private String key;

    @Value("${wx_callBack}")
    private String callBackUrl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 微信支付 - 查看艺人联系方式
     * @param userId
     * @param ip
     * @param artistId
     * @return
     * @throws Exception
     */
    public WXPayRespDto wxPayArtistContact(Long userId, String ip, Long artistId) throws Exception {

        User user = userRepository.findById(userId);
        if (user == null) {
            log.error("[微信支付] 用户不存在");
            return null;
        }

        Artist artist = artistRepository.findOne(artistId);
        if (artist == null) {
            log.error("[微信支付] 艺人数据不存在");
            return null;
        }

        Long totalFee = 1L; //默认1分钱
        BigDecimal amount = artist.getContactPrice(); //获取艺人联系方式实际价格
        if (amount != null)
            totalFee = amount.multiply(new BigDecimal(100)).longValue();

        //生成订单号
        String orderNo = generateOrderNo(userId,artistId);

        //订单落库
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderNo(orderNo);
        order.setOrderTitle("找演出商演平台-联系艺人");
        order.setOrderInfo("");
        order.setOrderAmount(amount);
        order.setPayAmount(amount);
        order.setArtistId(artistId);
        order.setArtistType(artist.getType());
        order.setOrderType(Order.OrderType.CONTACT.getCode());
        order.setPayStatus(Order.PayStatus.PAY_SUCCESS.getCode());
        order.setIsDeleted(DeleteCodeEnum.NOT_DELETEED.getCode());
        order.setCreated(System.currentTimeMillis());
        order.setUpdated(System.currentTimeMillis());
        orderRepository.save(order);

        return wxPayProcess(user.getOpenid(), "找演出商演平台-联系艺人", ip, totalFee, orderNo);

    }

    /**
     * 执行微信支付请求
     *
     * @param openId
     * @param body
     * @param ip
     * @param totalFeeFen
     * @param orderNo
     * @return
     * @throws Exception
     */
    public WXPayRespDto wxPayProcess(String openId, String body, String ip, Long totalFeeFen, String orderNo) throws Exception {

        WxPayFsConfig config = new WxPayFsConfig(appId, mchId, key);
        WXPayConstants.SignType signType = WXPayConstants.SignType.MD5;
        WXPay wxpay = new WXPay(config, signType);

        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", orderNo); //订单号
        data.put("total_fee", totalFeeFen.toString());  //支付金额
        data.put("body", body); //支付商品名称
        data.put("openid", openId);  //用户的openid
        data.put("spbill_create_ip", ip);
        data.put("fee_type", "CNY");
        data.put("notify_url", callBackUrl);
        data.put("trade_type", "JSAPI");  // 公众号支付
        data.put("device_info", "WEB");

        Map<String, String> resp;
        try {
            resp = wxpay.unifiedOrder(data);
            log.info("[微信支付] 微信支付生成预支付单返回结果。resp:{}", resp);
        } catch (Exception e) {
            log.error("[微信支付] 微信支付生成预支付单，接口异常。", e);
            return null;
        }

        if (resp.get("return_code") != null && resp.get("return_code").equals("FAIL")) {
            log.error("[微信支付] 微信支付生成预支付单，请求失败。resp:{}", resp);
            return null;
        }
        if (resp.get("result_code") != null && resp.get("result_code").equals("FAIL")) {
            log.error("[微信支付] 微信支付生成预支付单，处理失败。resp:{}", resp);
            return null;
        }

        return generateWebResponse(resp.get("prepay_id"), signType);

    }


    /**
     * 生成前端使用的支付请求参数
     *
     * @param prepayId
     * @param signTypeEnum
     * @return
     */
    private WXPayRespDto generateWebResponse(String prepayId, WXPayConstants.SignType signTypeEnum) {
        String nonceStr = WXPayUtil.generateNonceStr();//随机字符串
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String sign;
        String signType = WXPayConstants.MD5;
        if (signTypeEnum.equals(WXPayConstants.SignType.HMACSHA256))
            signType = WXPayConstants.HMACSHA256;
        String strSign = "appId=" + appId + "&nonceStr=" + nonceStr + "&package=" + prepayId + "&signType=" + signType + "&timeStamp=" + timeStamp + "&key=" + key;
        try {
            sign = WXPayUtil.MD5(strSign);
        } catch (Exception e) {
            String errMsg = MessageFormatter.format("[微信支付] 微信支付生成预支付单，处理前端请求加密失败。strSign:{}", strSign).getMessage();
            log.error(errMsg);
            return null;
        }

        WXPayRespDto wxPayRespDto = new WXPayRespDto();
        wxPayRespDto.setAppId(appId);
        wxPayRespDto.setNonceStr(nonceStr);
        wxPayRespDto.setPackageStr("package=" + prepayId);
        wxPayRespDto.setSignType(signType);
        wxPayRespDto.setPaySign(sign);
        wxPayRespDto.setTimeStamp(timeStamp);

        wxPayRespDto.setPrepayId(prepayId);
        return wxPayRespDto;

    }

    /**
     * 处理回调
     * @param xmlString
     */
    public void processNotify(String xmlString) throws Exception {

        //转成map
        Map<String,String> resultMap = WXPayUtil.xmlToMap(xmlString);
        if(!WXPayUtil.isSignatureValid(xmlString,key)){
            log.error("[微信支付] 微信回调sign验证失败，xmlString:{}", xmlString);
            return;
        }

        if( !"SUCCESS".equals(resultMap.get("return_code")) ){
            log.error("[微信支付] 微信回调 回调失败，resultMap:{}", resultMap);
            return;
        }

        if( !"SUCCESS".equals(resultMap.get("result_code")) ){
            log.error("[微信支付] 微信回调 支付处理失败，resultMap:{}", resultMap);
            return;
        }

        log.info("[微信支付] 微信回调 回调成功 回调结果 resultMap:{}", resultMap);

        String wxTranId = resultMap.get("transaction_id"); //微信支付订单号
        log.info("[微信支付] 微信回调 回调成功 微信支付订单号，transaction_id:{}", wxTranId);
        String orderNo = resultMap.get("out_trade_no");//支付单号
        log.info("[微信支付] 微信回调 回调成功 支付单号，out_trade_no:{}", orderNo);
        String totalFee = resultMap.get("total_fee");//订单金额
        log.info("[微信支付] 微信回调 回调成功 订单金额，total_fee:{}", totalFee);
        String timeEnd = resultMap.get("time_end");//支付完成时间
        log.info("[微信支付] 微信回调 回调成功 支付完成时间 time_end:{}", timeEnd);


        Order order = orderRepository.getByOrderNo(orderNo);
        if(order==null){
            log.error("[微信支付] 微信回调 回调失败 订单号不存在 orderNo:{}", orderNo);
            return;
        }

        order.setPayStatus(Order.PayStatus.CALLBACK.getCode());
        order.setUpdated(System.currentTimeMillis());
        order.setTransactionId(wxTranId);
        orderRepository.save(order);

    }

    /**
     * 生成订单号
     * @param userId
     * @param artistId
     * @return
     */
    private static AtomicLong counter = new AtomicLong();

    private static String generateOrderNo(Long userId, Long artistId) {
        String timestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        long seq = counter.getAndIncrement() % 10000;
        return String.format("%s%d%s%d", timestamp, userId, artistId.toString(), seq);
    }


}
