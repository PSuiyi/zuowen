package com.znz.compass.znzpay.alipay;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.znz.compass.znzpay.common.PayKeys;

import java.util.Map;

import static com.znz.compass.znzpay.common.PayKeys.RSA_PRIVATE;


public class AliPayUtil {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;

    public static final String ALIPAY_ACTION = "ALIPAY_ACTION_0x222";
    public static final String ALIPAY_STATUS = "ALIPAY_STATUS";

    private Activity activity;

    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static AliPayUtil instance = null;

    /* 私有构造方法，防止被实例化 */
    private AliPayUtil(Activity activity) {
        this.activity = activity;
    }

    /* 1:懒汉式，静态工程方法，创建实例 */
    public static AliPayUtil getInstance(Activity activity) {
        if (instance == null) {
            instance = new AliPayUtil(activity);
        }
        return instance;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    Intent mIntent = new Intent(ALIPAY_ACTION);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    switch (resultStatus) {
                        case "9000":
                            Toast.makeText(activity, "支付成功", Toast.LENGTH_LONG).show();
                            mIntent.putExtra(ALIPAY_STATUS, "成功");
                            activity.sendBroadcast(mIntent);
                            break;
                        case "8000":
                            Toast.makeText(activity, "支付结果确认中", Toast.LENGTH_LONG).show();
                            break;
                        case "6001":
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(activity, "支付取消", Toast.LENGTH_LONG).show();
                            mIntent.putExtra(ALIPAY_STATUS, "取消");
                            activity.sendBroadcast(mIntent);
                            break;
                        default:
                            Toast.makeText(activity, "支付失败", Toast.LENGTH_LONG).show();
                            mIntent.putExtra(ALIPAY_STATUS, "失败");
                            activity.sendBroadcast(mIntent);
                            break;
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(activity, "检查结果为：" + msg.obj, Toast.LENGTH_LONG)
                            .show();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void startAliPay(String orderNO, String price) {
        // 订单
//        final String orderInfo = getOrderInfo(orderNO, price);
        final String orderInfo = orderNO;

//        // 对订单做RSA 签名
//        String sign = sign(orderInfo);
//        try {
//            // 仅需对sign 做URL编码
//            sign = URLEncoder.encode(sign, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        // 完整的符合支付宝参数规范的订单信息
//        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
//                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);
                System.out.println("result--->" + result);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝支付
     *
     * @param sign
     */
    public void startAliPay(String sign) {
        final String orderInfo = sign;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);
                System.out.println("result--->" + result);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String orderNO, String price) {
        boolean rsa2 = (PayKeys.RSA_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil.buildOrderParamMap(orderNO, price, rsa2);
        String orderParam = OrderInfoUtil.buildOrderParam(params);

        String privateKey = RSA_PRIVATE;
        String sign = OrderInfoUtil.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;


//        // 签约合作者身份ID
//        String orderInfo = "partner=" + "\"" + PayKeys.PARTNER + "\"";
//
//        // 签约卖家支付宝账号
//        orderInfo += "&seller_id=" + "\"" + PayKeys.SELLER + "\"";
//
//        // 商户网站唯一订单号
//        orderInfo += "&out_trade_no=" + "\"" + orderNO + "\"";
//
//        // 商品名称
//        orderInfo += "&subject=" + "\"" + subject + "\"";
//
//        // 商品详情
//        orderInfo += "&body=" + "\"" + body + "\"";
//
//        // 商品金额
//        orderInfo += "&total_fee=" + "\"" + price + "\"";
//
//        // 服务器异步通知页面路径
//        orderInfo += "&notify_url=" + "\"" + PayKeys.ALI_CALLBACK + "\"";
//
//        // 服务接口名称， 固定值
//        orderInfo += "&service=\"mobile.securitypay.pay\"";
//
//        // 支付类型， 固定值
//        orderInfo += "&payment_type=\"1\"";
//
//        // 参数编码， 固定值
//        orderInfo += "&_input_charset=\"utf-8\"";
//
//        // 设置未付款交易的超时时间
//        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
//        // 取值范围：1m～15d。
//        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
//        // 该参数数值不接受小数点，如1.5h，可转换为90m。
//        orderInfo += "&it_b_pay=\"30m\"";
//
//        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
//        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
//
//        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
}
