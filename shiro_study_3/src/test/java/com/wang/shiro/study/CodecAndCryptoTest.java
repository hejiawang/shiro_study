package com.wang.shiro.study;

import java.security.Key;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Assert;
import org.junit.Test;

public class CodecAndCryptoTest {
	
	/**
	 * base64编码/解码操作
	 */
	@Test
    public void test1() {
		String str = "hello";  
		String base64Encoded = Base64.encodeToString(str.getBytes());  
		String str2 = Base64.decodeToString(base64Encoded);  
		Assert.assertEquals(str, str2); 
	}
	
	/**
	 * 16进制字符串编码/解码操作
	 */
	@Test
    public void test2() {
		String str = "hello";  
		String base64Encoded = Hex.encodeToString(str.getBytes());  
		String str2 = new String(Hex.decode(base64Encoded.getBytes()));  
		Assert.assertEquals(str, str2);   
	}
	
	/**
	 * 通过盐“123”MD5散列“hello”
	 */
	@Test
    public void test3() {
		String str = "hello";  
		String salt = "123";  
		//String md5 = new Md5Hash(str, salt).toString();//还可以转换为 toBase64()/toHex()
		//String md5 = new Md5Hash(str, salt).toBase64();
		String md5 = new Md5Hash(str, salt, 2).toString();	//指定散列次数，如2次表
		System.out.println(md5);
	}
	
	@Test
    public void test4() {
		String str = "hello";  
		String salt = "123";  
		String sha1 = new Sha256Hash(str, salt).toString();   
		System.out.println(sha1);
	}
	
	@Test
    public void test5() {
		String str = "hello";  
		String salt = "123";  
		//内部使用MessageDigest  
		String simpleHash = new SimpleHash("SHA-1", str, salt).toString();   
		System.out.println(simpleHash);
	}
	
	@Test
    public void test6() {
		DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512  
		hashService.setHashAlgorithmName("SHA-512");  
		hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无  
		hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false  
		hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个  
		hashService.setHashIterations(1); //生成Hash值的迭代次数  
		  
		HashRequest request = new HashRequest.Builder()  
		            .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))  
		            .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();  
		String hex = hashService.computeHash(request).toHex();  
		System.out.println(hex);
	}
	
	/**
	 * 生成一个随机数
	 */
	@Test
    public void test7() {
		SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();  
		randomNumberGenerator.setSeed("123".getBytes());  
		String hex = randomNumberGenerator.nextBytes().toHex();  
		System.out.println(hex);
	}
	
	@Test
    public void test8() {
		AesCipherService aesCipherService = new AesCipherService();  
		aesCipherService.setKeySize(128); //设置key长度  
		//生成key  
		Key key = aesCipherService.generateNewKey();  
		String text = "hello";  
		//加密  
		String encrptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();  
		//解密  
		String text2 = new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());  
		  
		Assert.assertEquals(text, text2);   
	}
	
	
}
