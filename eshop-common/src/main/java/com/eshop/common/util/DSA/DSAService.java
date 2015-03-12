package com.eshop.common.util.DSA;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import com.eshop.common.util.string.StringUtils;

/**
 * 在Spring中使用的DSA工具
 *
 * @author Buffon
 *
 */
public class DSAService implements DSA {
	private static Log log = LogFactory.getLog(DSAService.class);

	private Resource privateKeyResource;

	private Resource publicKeyResource;

	private PublicKey publicKey;

	private PrivateKey privateKey;

	public String sign(String content) throws Exception {
		try {
			Signature signalg = Signature.getInstance("DSA");
			signalg.initSign(privateKey);
			signalg.update(content.getBytes());
			byte[] signature = signalg.sign();
			return StringUtils.bytesToHexString(signature);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	public boolean verify(String signature, String contecnt) throws Exception {
		try {
			Signature verifyalg = Signature.getInstance("DSA");
			verifyalg.initVerify(publicKey);
			verifyalg.update(contecnt.getBytes());
			return verifyalg.verify(StringUtils.hexStringToBytes(signature));
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	public void setPrivateKey(Resource privateKeyResource) throws Exception {
		try {
			this.privateKeyResource = privateKeyResource;
			ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(this.privateKeyResource.getFile()));
			privateKey = (PrivateKey) keyIn.readObject();
			keyIn.close();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	public void setPublicKey(Resource publicKeyResource) throws Exception {
		try {
			this.publicKeyResource = publicKeyResource;
			ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(this.publicKeyResource.getFile()));
			publicKey = (PublicKey) keyIn.readObject();
			keyIn.close();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

}
