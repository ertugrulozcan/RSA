package com.aero.rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.aero.Math.BinaryModularExponentiation;
import com.aero.Math.Euclidean;
import com.aero.Math.Montgomery;

public class RSA
{
	//
	// MAIN
	//
	public static void main(String[] args)
	{
		MontgomeryTest();
	}
	
	public static void RSA_Test()
	{
		RSA rsa = new RSA();
		Encoder encoder = new Encoder(rsa);
		Decoder decoder = new Decoder(rsa);
		
		String message = "Ertuğrul Özcan";
		
		System.out.println("Şifrelenecek mesaj : \"" + message + "\"");
		BigInteger[] crypto = encoder.Encode(message);
		
		System.out.print("Şifreli mesaj : \"");
		for(int i = 0; i < crypto.length; i++)
			System.out.print(crypto[i] + " ");
		System.out.println("\"");
		
		BigInteger[] messageArray = decoder.Decode(crypto);
		
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < messageArray.length; i++)
			stringBuilder.append((char)messageArray[i].intValue());
		
		String message2 = stringBuilder.toString();
		
		System.out.println("Çözülmüş mesaj : \"" + message2 + "\"");
	}
	
	public static void RSA_Test2()
	{
		RSA rsa = new RSA();
		Encoder encoder = new Encoder(rsa);
		Decoder decoder = new Decoder(rsa);
		
		BigInteger message = BigInteger.valueOf(97);
		
		System.out.println("Şifrelenecek mesaj : \"" + message + "\"");
		BigInteger crypto = encoder.Encode(message);
		
		System.out.println("Şifreli mesaj : " + crypto);
		
		BigInteger message2 = decoder.Decode(crypto);
		
		System.out.println("Çözülmüş mesaj : \"" + message2 + "\"");
	}
	
	public static void BinaryTest()
	{
		/*
		 * Binary modüler üs alma metodunun çalışmasını test eder.
		 */
		
		BigInteger r = new BigInteger("9716").modPow(new BigInteger("1884"), new BigInteger("3446"));
		System.out.println("Gerçek değer = " + r);
		BigInteger t = BinaryModularExponentiation.ModPower(new BigInteger("9716"), new BigInteger("1884"), new BigInteger("3446"));
		System.out.println("Test değeri = " + t);
	}
	
	public static void MontgomeryTest()
	{
		/*
		 * Montgomery modüler çarpma metodunun çalışmasını test eder.
		 */
		
		BigInteger r = new BigInteger("971601234567898765").multiply(new BigInteger("659874118845798054321")).mod(new BigInteger("1673987"));
		System.out.println("Gerçek değer = " + r);
		BigInteger t = Montgomery.ModularMultiply(new BigInteger("971601234567898765"), new BigInteger("659874118845798054321"), new BigInteger("1673987"));
		System.out.println("Test değeri = " + t);
	}
	
	// GEREKLİLİKLER:
	/*
	 * 1) p ve q asallarının üretimi 2) n = p*q 3) Totient'in bulunması : T(n) = (p - 1)*(q - 1) 4) 1 < e < T(n)
	 * aralığında ve EBOB(e, T(n))=1 olan bir e sayısı üretilmesi 5) d.e % T(n) = 1 olacak biçimde bir d sayısı
	 * üretilmesi
	 * 
	 * ORTAK ANAHTAR : (n, e) ÖZEL ANAHTAR : (n, d)
	 * 
	 * NOT : d, p, q ve T(n) değerleri kesinlikle gizli kalmalıdır.
	 */
	
	//
	// Degiskenler, sabitler, sinif uyeleri
	//
	private BigInteger p, q, n, t, e, d;
	
	//
	// Kurucu metod - Constructor
	//
	public RSA()
	{
		this.GenerateParameters();
	}
	
	//
	// Getter / Setter Metodlar
	//
	public BigInteger getP()
	{
		return this.p;
	}
	
	public BigInteger getQ()
	{
		return this.q;
	}
	
	public BigInteger getN()
	{
		return this.n;
	}
	
	public BigInteger getT()
	{
		return this.t;
	}
	
	public BigInteger getE()
	{
		return this.e;
	}
	
	public BigInteger getD()
	{
		return this.d;
	}
	
	public void setD(BigInteger d)
	{
		this.d = d;
	}
	
	//
	// Parametrelerin uretilmesi
	//
	private void GenerateParameters()
	{
		this.GeneratePQ();
		System.out.println("p = " + this.p);
		System.out.println("q = " + this.q);
		
		this.GenerateN();
		System.out.println("n = " + this.n);
		
		this.GenerateT();
		System.out.println("t = " + this.t);
		
		this.GenerateE();
		System.out.println("e = " + this.e);
		
		this.GenerateD();
		System.out.println("d = " + this.d);
	}
	
	private void GeneratePQ()
	{
		Random random = new Random();
		// P asalının uretilmesi
		this.p = BigInteger.probablePrime(1024, random);
		
		// Q asalının uretilmesi
		// Not : P ile Q eşit olmamalıdır.
		do
		{
			random = new Random();
			this.q = BigInteger.probablePrime(1024, random);
		}
		while (p.compareTo(q) == 0);
	}
	
	private void GenerateN()
	{
		this.n = this.p.multiply(this.q);
	}
	
	private void GenerateT()
	{
		this.t = Totient(this.p, this.q);
	}
	
	private void GenerateE()
	{
		Random random = new Random();
		BigInteger tempE = BigInteger.probablePrime(16, random).mod(t);
		
		while (this.t.mod(tempE).compareTo(BigInteger.ZERO) == 0)
			tempE = BigInteger.probablePrime(8, random).mod(t);
		
		this.e = tempE;
	}
	
	private void GenerateD()
	{
		this.d = Euclidean.ExtendedEuclidean(this.e, this.t)[1];
		// this.d = this.e.modInverse(this.t);
		if (this.d.compareTo(BigInteger.ZERO) == 0)
			this.d = this.d.add(this.t);
	}
	
	//
	// Yardimci metodlar
	//
	private BigInteger Totient(BigInteger p, BigInteger q)
	{
		BigInteger t1 = p.subtract(BigInteger.ONE);
		BigInteger t2 = q.subtract(BigInteger.ONE);
		return t1.multiply(t2);
	}
}
