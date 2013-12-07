package com.aero.rsa;

import java.math.BigInteger;

import com.aero.Math.BinaryModularExponentiation;

public class Encoder
{
	private RSA rsa;
	
	public Encoder(RSA rsa)
	{
		this.rsa = rsa;
	}
	
	// Tek sayý Þifreleme Metodu
	public BigInteger Encode(BigInteger message)
	{
		// Þifreli mesaj (crypto)
		//BigInteger crypto = BinaryModularExponentiation.ModPowerForQuadCore(message, rsa.getE(), rsa.getN());
		BigInteger crypto = message.modPow(rsa.getE(), rsa.getN());
		return crypto;
	}
	
	// Dizin formda þifreleme metodu
	public BigInteger[] Encode(BigInteger[] messageArray)
	{
		BigInteger[] cryptoArray = new BigInteger[messageArray.length];
		for (int i = 0; i < messageArray.length; i++)
			cryptoArray[i] = Encode(messageArray[i]);
			
		return cryptoArray;
	}
	
	// String formda þifreleme metodu
	public BigInteger[] Encode(String messageString)
	{
		// String > char array dönüþümü
		char[] charArray = messageString.toCharArray();
		
		// char dizisinin ascii dizisine dönüþtürülmesi
		BigInteger[] asciiArray = new BigInteger[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			asciiArray[i] = BigInteger.valueOf((int)charArray[i]);
		
		// Dizideki tüm elemanlar Encode() ile þifrelenir.
		BigInteger[] cryptoArray = new BigInteger[asciiArray.length];
		cryptoArray = Encode(asciiArray);
		
		return cryptoArray;
	}
	
}
