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
	
	// Tek say� �ifre cozme metodu
	public BigInteger Decode(BigInteger crypto)
	{
		//BigInteger message = BinaryModularExponentiation.ModPowerForQuadCore(crypto, rsa.getD(), rsa.getN());
		BigInteger message = crypto.modPow(rsa.getD(), rsa.getN());
		return message;
	}
	
	// Dizin formda �ifre ��zme metodu
	public BigInteger[] Decode(BigInteger[] cryptoArray)
	{
		BigInteger[] messageArray = new BigInteger[cryptoArray.length];
		for (int i = 0; i < cryptoArray.length; i++)
			messageArray[i] = Decode(cryptoArray[i]);
		
		return messageArray;
	}
	
	// String formda �ifre ��zme metodu
	public String Decode(String cryptoString)
	{
		// String > char array d�n���m�
		char[] charArray = cryptoString.toCharArray();
		// char dizisinin ascii dizisine d�n��t�r�lmesi
		BigInteger[] asciiArray = new BigInteger[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			asciiArray[i] = BigInteger.valueOf((int)charArray[i]);
		
		// Dizideki t�m elemanlar Decode() ile ��z�l�r.
		BigInteger[] messageArray = new BigInteger[asciiArray.length];
		messageArray = Decode(asciiArray);
		
		// Elde edilen yeni diziden string olu�turulur.
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < charArray.length; i++)
			stringBuilder.append((char)messageArray[i].intValue());
		
		return stringBuilder.toString();
	}
}
