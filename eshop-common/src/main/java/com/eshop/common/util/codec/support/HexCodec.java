package com.eshop.common.util.codec.support;

import org.apache.commons.codec.binary.Hex;

import com.eshop.common.util.codec.Decoder;
import com.eshop.common.util.codec.DecoderException;
import com.eshop.common.util.codec.Encoder;
import com.eshop.common.util.codec.EncoderException;

/**
 * Hex算法编码器。处理byte[]到char[]类型。
 * 
 * @author Spencer
 * @since 16 Aug 2014
 * @see Encoder
 * @see Decoder
 */
public class HexCodec implements Encoder<byte[], char[]>, Decoder<char[], byte[]>{

	@Override
	public byte[] decode(char[] source) throws DecoderException {
		try {
    		return Hex.decodeHex(source);
    	} catch (org.apache.commons.codec.DecoderException e) {
    		throw new DecoderException(e.getMessage(), e);
    	}
	}

	@Override
	public char[] encode(byte[] source) throws EncoderException {
		try {
			return Hex.encodeHex(source);
		} catch (Exception e) {
			throw new EncoderException(e.getMessage(), e);
		}
	}

}
