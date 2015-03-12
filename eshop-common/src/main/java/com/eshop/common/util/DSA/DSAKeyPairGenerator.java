package com.eshop.common.util.DSA;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 生成DSA密钥对的工具类
 * 使用方法：java DSAKeyPairGenerator -genkey public.key private.key
 * public.key--生成的公钥文件名
 * private.key--生成的私钥文件名
 *
 */
public class DSAKeyPairGenerator {
    private static final int KEYSIZE=512;
    /**
     * 生成DSA密钥对的工具类
     * 使用方法：java DSAKeyPairGenerator -genkey public.key private.key
     * public.key--生成的公钥文件名
     * private.key--生成的私钥文件名
     * @param args
     */
    public static void main(String[] args) {
        if(args[0].equals("-genkey")){
            try {
                KeyPairGenerator pairgen=KeyPairGenerator.getInstance("DSA");
                SecureRandom random=new SecureRandom();
                pairgen.initialize(KEYSIZE, random);
                KeyPair keyPair=pairgen.generateKeyPair();
                ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(args[1]));
                out.writeObject(keyPair.getPublic());
                out.close();
                out=new ObjectOutputStream(new FileOutputStream(args[2]));
                out.writeObject(keyPair.getPrivate());
                out.close();
            } catch (NoSuchAlgorithmException e) {
                // TODO 自动生成 catch 块
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                // TODO 自动生成 catch 块
                e.printStackTrace();
            } catch (IOException e) {
                // TODO 自动生成 catch 块
                e.printStackTrace();
            }
        }
    }

}
