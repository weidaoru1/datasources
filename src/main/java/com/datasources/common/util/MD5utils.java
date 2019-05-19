package com.datasources.common.util;
import java.security.MessageDigest;

public class MD5utils {
    /**
     * MD5加密方法
     * @param context
     * @return
     */
    public static String encrypt(String context) {
        StringBuffer buf = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(context.getBytes());//update处理
            byte [] encryContext = messageDigest.digest();//调用该方法完成计算
            int i;
            for (int offset = 0; offset < encryContext.length; offset++) {//做相应的转化（十六进制）
                i = encryContext[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            if (buf.length() > 0){
                return buf.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void main(String[] args) {
        String str = "123456";
        String en = encrypt(str);
        System.out.println("加密后：" + en);

    }
}
