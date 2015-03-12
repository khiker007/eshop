package com.eshop.common.util.DSA;

public interface DSA {
    /**
     * 进行签名的方法
     * @param content 需要签名的内容
     * @return
     * @throws Exception
     */
    public String sign(String content) throws Exception;
    /**
     * 验证签名的方法
     * @param signature 签名
     * @param contect 明文
     * @return
     * @throws Exception
     */
    public boolean verify(String signature,String contect) throws Exception;
}