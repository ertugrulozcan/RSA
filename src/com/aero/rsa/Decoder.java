package com.aero.rsa;

import java.math.BigInteger;

import com.aero.Math.BinaryModularExponentiation;

public class Decoder
{
	private RSA rsa;
	
	public Decoder(RSA rsa)
	{
		this.rsa = rsa;
	}
	
	// Tek sayý Þifre cozme metodu
	public BigInteger Decode(BigInteger crypto)
	{
		//BigInteger message = BinaryModularExponentiation.ModPowerForQuadCore(crypto, rsa.getD(), rsa.getN());
		BigInteger message = crypto.modPow(rsa.getD(), rsa.getN());
		return message;
	}
	
	// Dizin formda þifre çözme metodu
	public BigInteger[] Decode(BigInteger[] cryptoArray)
	{
		BigInteger[] messageArray = new BigInteger[cryptoArray.length];
		for (int i = 0; i < cryptoArray.length; i++)
			messageArray[i] = Decode(cryptoArray[i]);
		
		return messageArray;
	}
	
	// String formda þifre çözme metodu
	public String Decode(String cryptoString)
	{
		// String > char array dönüþümü
		char[] charArray = cryptoString.toCharArray();
		// char dizisinin ascii dizisine dönüþtürülmesi
		BigInteger[] asciiArray = new BigInteger[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			asciiArray[i] = BigInteger.valueOf((int)charArray[i]);
		
		// Dizideki tüm elemanlar Decode() ile çözülür.
		BigInteger[] messageArray = new BigInteger[asciiArray.length];
		messageArray = Decode(asciiArray);
		
		// Elde edilen yeni diziden string oluþturulur.
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < charArray.length; i++)
			stringBuilder.append((char)messageArray[i].intValue());
		
		return stringBuilder.toString();
	}
}
