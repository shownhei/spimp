/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * AES加密解密算法工具类。
 */
public abstract class AES {
	/**
	 * 解密。
	 * 
	 * @param key
	 * @param aesString
	 * @return
	 */
	public static String decodeAes128(String key, String aesString) {
		KeyGenerator kgen = null;
		try {
			kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);

			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(Hex.decodeHex(aesString.toCharArray()));
			return new String(original);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密。
	 * 
	 * @param key
	 * @param text
	 * @return
	 */
	public static String encodeAes128(String key, String text) {
		KeyGenerator kgen = null;
		try {
			kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);

			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(text.getBytes());
			return asHex(encrypted);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if ((buf[i] & 0xff) < 0x10) {
				strbuf.append("0");
			}

			strbuf.append(Long.toString(buf[i] & 0xff, 16));
		}

		return strbuf.toString();
	}
}
