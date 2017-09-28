package com.znz.compass.znzlibray.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author Zhoujun 说明：处理一下字符串的常用操作，字符串校验等
 */
public class StringUtil {
    /**
     * 判断字符串是否为空或者空字符串 如果字符串是空或空字符串则返回true，否则返回false
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (str == null || "".equals(str) || str.equals("null")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证邮箱输入是否合法
     *
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        // String strPattern =
        // "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        String strPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 验证是否是手机号码
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(str))
            return false;
        else
            return str.matches(telRegex);
    }

    /**
     * 判断密码是否过于简单
     *
     * @param psd
     * @return
     */
    public static boolean isPsdSimple(String psd) {
        if (psd.length() < 6) {
            return true;
        }
        return false;
    }

    /**
     * MD5加密
     *
     * @param secret_key
     * @return
     */
    public static String createSign(String secret_key) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(secret_key.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }

    /**
     * 将网络图片路径md5加密作为文件名
     *
     * @param imageUrl
     * @return
     */
    public static String createImageName(String imageUrl) {
        return createSign(imageUrl) + ".jpg";
    }

    /**
     * 将网络图片路径md5加密作为文件名,可以设置图片类型
     *
     * @param imageUrl
     * @param imgSuffix
     * @return
     */
    public static String createImageName(String imageUrl, String imgSuffix) {
        return createSign(imageUrl) + imgSuffix;
    }

    /**
     * 判断指定字符串是否在指定字符数范围（minLength-maxLength）内，即：minLength <= x <=maxLength
     *
     * @param value     指定字符串
     * @param minLength 指定最小字符长度
     * @param maxLength 指定最大字符长度
     * @return -1:小于minLength; 0:在范围内; 1:大于maxLength
     * @author syghh
     */
    public static int isStringLengthInLimit(String value, int minLength,
                                            int maxLength) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        if (valueLength >= minLength && valueLength <= maxLength) {
            return 0;
        } else if (valueLength > maxLength) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 判断指定字符串的字符数是否小于限定字符长度
     *
     * @param value       指定字符串
     * @param limitLength 限定字符长度
     * @return 未超出 true; 超出 false
     * @author syghh
     */
    public static boolean isStringLengthOut(String value, int limitLength) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        if (valueLength > limitLength) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 限制EditText的长度
     *
     * @param editText
     * @param limitLength 字符数
     * @param context
     */
    public static void limitEditTextLength(final EditText editText,
                                           final int limitLength, final Context context) {
        // 输入框限制输入字数
        editText.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                          int arg3) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                selectionStart = editText.getSelectionStart();
                selectionEnd = editText.getSelectionEnd();
                Log.i("gongbiao1", "" + selectionStart);
                boolean isStringLengthOut = isStringLengthOut(
                        String.valueOf(temp), limitLength);
                if (!isStringLengthOut) {
                    Toast.makeText(context, "长度已超出" + limitLength + "个字",
                            Toast.LENGTH_SHORT).show();
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    editText.setText(s);
                    editText.setSelection(tempSelection);// 设置光标在最后
                }
            }
        });
    }

    /**
     * 将数组转换字符串
     *
     * @param array 数据
     */
    public static String StringToArray(String[] array) {
        String str = null;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
        }
        str = sb.toString();

        return str;
    }

    /**
     * sha1加密
     *
     * @param str
     * @return
     */
    public static String sha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes());

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取VersionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取PackageName
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        try {
            String pkName = context.getPackageName();
            return pkName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取versionCode
     *
     * @param context
     * @return
     */
    public static String getVersionCode(Context context) {
        try {
            String pkName = context.getPackageName();
            int versionCode = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionCode;
            return versionCode + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 四舍五入
     *
     * @param value
     * @param scale
     * @return
     */
    public static Double round(Double value, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static Double mul(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }


    /**
     * String 转 int
     *
     * @param str
     * @return
     */
    public static int stringToInt(String str) {
        try {
            if (isBlank(str)) {
                return 0;
            } else {
                return Integer.parseInt(str);
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * String 转 long
     *
     * @param str
     * @return
     */
    public static long stringToLong(String str) {
        try {
            if (isBlank(str)) {
                return 0;
            } else {
                return Long.parseLong(str);
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * String 转 boolean
     *
     * @param str
     * @return
     */
    public static boolean stringToBoolean(String str) {
        try {
            if (isBlank(str)) {
                return false;
            } else {
                if (str.equals("true")) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * String 转 float
     *
     * @param str
     * @return
     */
    public static float stringToFloat(String str) {
        try {
            if (isBlank(str)) {
                return 0.0f;
            } else {
                return Float.parseFloat(str);
            }
        } catch (Exception e) {
            return 0.0f;
        }
    }

    /**
     * String 转 double
     *
     * @param str
     * @return
     */
    public static double stringToDouble(String str) {
        try {
            if (isBlank(str)) {
                return 0.0;
            } else {
                return Double.parseDouble(str);
            }
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * trim
     *
     * @param str
     * @return
     */
    public static String stringToTrim(String str) {
        if (isBlank(str)) {
            return "";
        } else {
            return str.trim();
        }
    }

    /**
     * 转字节数组
     *
     * @param str
     * @return
     */
    public static byte[] stringToByte(String str) {
        byte[] srtbyte = null;
        if (isBlank(str)) {
            srtbyte = null;
        } else {
            try {
                srtbyte = str.getBytes("gbk");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return srtbyte;
    }

    /**
     * 获取加一的值
     *
     * @param str
     * @return
     */
    public static String getNumUP(String str) {
        return StringUtil.stringToInt(str) + 1 + "";
    }

    /**
     * 获取减一的值
     *
     * @param str
     * @return
     */
    public static String getNumDown(String str) {
        int temp = StringUtil.stringToInt(str);
        if (temp <= 0) {
            return "0";
        } else {
            return temp - 1 + "";
        }
    }


    /**
     * 保留两位小数
     */
    public static String getDataFormat(String str) {
        String data = "";
        if (isBlank(str)) {
            return null;
        }
        DecimalFormat df = null;
        try {
            df = new DecimalFormat("#0.00");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (str.startsWith("+")) {
            data = "+" + df.format(stringToDouble(str.replace("+", "")));
        } else if (str.startsWith("-")) {
            data = "-" + df.format(stringToDouble(str.replace("-", "")));
        } else {
            data = "" + df.format(stringToDouble(str));
        }
        return data;
    }

    /**
     * 截取小数点后两位,不四舍五入
     *
     * @return
     */
    public static float decimalsTwo(double finalMoney) {
        DecimalFormat formater = new DecimalFormat("#0.##");
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return StringUtil.stringToFloat(formater.format(finalMoney));
    }

    /**
     * a为一个带有未知位小数的实数
     * 对其取b位小数
     *
     * @param a
     * @param b
     * @return
     */
    public static double getDouble(double a, int b) {
        int x = 0;
        int y = 1;
        for (int i = 0; i < b; i++) {
            y = y * 10;
        }
        System.out.println(y);
        x = (int) (a * y);
        System.out.println("x=" + x);
        return (double) x / y;
    }

    /**
     * 截取小数点后两位
     *
     * @param data
     * @param num  要截取几位
     * @return
     */
    public static double decimals(String data, int num) {
        try {
            if (isBlank(data)) {
                return 0;
            }
            BigDecimal bd = new BigDecimal(data);
            bd = bd.setScale(num, BigDecimal.ROUND_HALF_UP);
            return bd.doubleValue();// 截取小数点后2位
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 截取小数点后两位
     *
     * @param data
     * @param num  要截取几位
     * @return
     */
    public static double decimalsInt(int data, int num) {
        BigDecimal bd = new BigDecimal(data);
        bd = bd.setScale(num, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();// 截取小数点后2位
    }

    /**
     * 截取小数点后两位
     *
     * @param data
     * @param num  要截取几位
     * @return
     */
    public static double decimalsFloat(float data, int num) {
        BigDecimal bd = new BigDecimal(data);
        bd = bd.setScale(num, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();// 截取小数点后2位
    }


    /**
     * 将科学技术法，12345E-10 转换为 0.0000012345
     *
     * @return
     */
    public static String ScienceNum(String number) {
        if (isBlank(number)) {
            return "";
        }
        BigDecimal db = new BigDecimal(number);
        String data = db.toPlainString();
        return data;
    }


    /**
     * 判断传入的字符串是不是纯数字
     *
     * @param num
     * @return
     */
    public static boolean judgeNum(String num) {
        boolean result = num.matches("[0-9]+");
        if (result == true) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * yyyy-M-d 转 yyyy-MM-dd
     *
     * @param data
     */
    public static String measureDate(String data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format2.format(format.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 计算出该TextView中文字的长度(像素)
    public static float getTextViewLength(TextView textView, String text) {
        TextPaint paint = textView.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        float textLength = paint.measureText(text);
        return textLength;
    }

}