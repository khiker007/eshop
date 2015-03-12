package com.eshop.common.util.codec.support;

import org.apache.commons.codec.binary.Base64;

import com.eshop.common.util.codec.Decoder;
import com.eshop.common.util.codec.DecoderException;
import com.eshop.common.util.codec.Encoder;
import com.eshop.common.util.codec.EncoderException;

/**
 * Base64编码和解码器。处理byte[]到byte[]类型。
 * 
 * @author Spencer
 * @since 18 Aug 2014
 * @see Encoder
 * @see Decoder
 */
public class Base64Codec implements Encoder<byte[], byte[]>, Decoder<byte[], byte[]> {

	@Override
	public byte[] decode(byte[] source) throws DecoderException {
		try {
			return Base64.decodeBase64(source);
		} catch (Exception e) {
			throw new DecoderException(e.getMessage(), e);
		}
		
	}

	@Override
	public byte[] encode(byte[] source) throws EncoderException {
		try {
			return Base64.encodeBase64(source);
		} catch (Exception e) {
			throw new EncoderException(e.getMessage(), e);
		}
		
	}

}
