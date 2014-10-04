import java.math.BigInteger;
import java.text.Format;
import java.util.Formatter;


public class BitMask {

	public static void main(String [] args)
	{
		bitmask_study();  
		EratosthenesSieve(1000000);
		fifteen_puzzle();
		explode_chemical_check();
		bitinteger_study();
	}	

		
	public static void bitmask_study()
	{
		long topping = (1 << 20) -1 ;
		System.out.println("All Topping: 0x"+Long.toHexString(topping));
		
		topping = 0;
		topping = turnOnBit(topping, 0);
		topping = turnOnBit(topping, 1);
		topping = turnOnBit(topping, 2);
		topping = turnOnBit(topping, 3);
		topping = turnOnBit(topping, 4);
		topping = turnOnBit(topping, 5);
		topping = turnOnBit(topping, 6);
		topping = turnOnBit(topping, 7);

		System.out.println("Topping: 0x"+Long.toHexString(topping));
		System.out.println("Is Topping 1: "+isBitOn(topping, 1));
		System.out.println("Is Topping 2: "+isBitOn(topping, 1));

		topping = turnOffBit(topping, 0);
		System.out.println("Del 0 Topping: 0x"+Long.toHexString(topping));

		topping = toggleBit(topping, 1);
		System.out.println("Toggle 1 Topping: 0x"+Long.toHexString(topping));
		System.out.println("Topping Count: "+bitCount(topping));

		topping = toggleBit(topping, 1);
		System.out.println("Toggle 1 Topping: 0x"+Long.toHexString(topping));
		System.out.println("Topping Count: "+bitCount(topping));
		
		topping = turnOffBit(topping, 4);
		System.out.println("Toggle 4 Topping: 0x"+Long.toHexString(topping));
		System.out.println("Topping Count: "+bitCount(topping));

		System.out.println("Least Topping: "+leastBitNumber(topping));
		
		topping = turnOffBit(topping, 1);
		System.out.println("Del 1 Topping: 0x"+Long.toHexString(topping));
		System.out.println("Topping Count: "+bitCount(topping));

		System.out.println("Least Topping: "+leastBitNumber(topping));

		topping = turnOffLeastBit(topping);
		System.out.println("Del Least Topping: 0x"+Long.toHexString(topping));
		System.out.println("Topping Count: "+bitCount(topping));
		System.out.println("Least Topping: "+leastBitNumber(topping));
		
		subBitMask(topping);
		
		subBitMask((long)0xff);
		
		
	}

	public static long turnOnBit(long target, int bitnum)
	{
		return target |= ( (long)1 << bitnum );
	}

	/* Check Point */
	public static long turnOffBit(long target, int bitnum)
	{
		return target &= ~( (long)1 << bitnum );
	}


	public static long toggleBit(long target, int bitnum)
	{
		return target ^= ( (long)1 << bitnum );
	}

	
	public static boolean isBitOn(long target, int bitnum)
	{
		return ( target & ( (long)1 << bitnum)) != 0 ; 
	}
	
	/* Check point : Count the number of '1' bit */
	public static long bitCount(long target)
	{
		/* Check Point */
		//return Long.bitCount(target);
		if(target == 0 ) return 0;
			
		//return target % 2 + bitCount( target / 2 );
		return target % 2 + bitCount( target >> 1 );
	}
	
	
	public static long leastBitNumber(long target)
	{
		/* Check Point */	
		/* Use Java API */
		return (long)Long.numberOfTrailingZeros(target);
		
		// Page 584, Actually this value is not same with above Java API
		// Below value is 2^leastTopping
		// return target & (~target+1); 
	}
	
	/* checking Power of 2 */
	public static boolean isPowerOf2(long target)
	{
		long rslt = target & ( target - 1 );
		
		if( rslt == (long) 0 ) return true;
		else
			return false;
	}
	
	
	public static long turnOffLeastBit(long target)
	{
		return target & (target - 1 );
	}
	
	
	/* Check Point (Important) */
	// Check all subset
	// - number of element : N, the number of subset : 2^N (containing null subset)
	// Refer to: http://blog.naver.com/alwaysneoi?Redirect=Log&logNo=100164370435
	public static void subBitMask(long target)
	{
		long i = 0;
		
		/* 'target &' is so important.
		 * If it does not exist, it is not subset. It's a simple '-1'.
		 * It is checked by counting the number of the subset ( the number of subset should be 2^N)
		 */
		
		for( long subset = target ; subset != (long)0 ;  subset = target & (subset -1) )
		{
			System.out.println((i++)+") 0x"+Long.toHexString(subset) +"("+ Long.toBinaryString(subset)+")");
		}
		
	}

	// Finding Prime Number
	
	public static void EratosthenesSieve(int n)
	{
		long [] bitmap = createBitMap(n);
		
		turnOffBit(bitmap, 0);
		turnOffBit(bitmap, 1);
		
		int minPrime = 0;
		
		
		for( minPrime = findMinPrime(bitmap, n) ; minPrime < n ; minPrime = findMinPrime(bitmap, n) )
		{
			if( minPrime == -1)
			{
				//dbg("failed to find");
				break;
			}
		
			dbg_ln("Prime="+minPrime);
			turnOffPrimeMultipleBit(bitmap, n,  minPrime);
		}
		
	}
	
	
	public static long [] createBitMap(int n)
	{
		long [] bitmap = new long[(n + 31) / 32];
		for( int i = 0 ; i < bitmap.length ; i++)
		{
			bitmap[i] = 0xffffffffL; // long type number 
		}
		
		System.out.println("bitmap("+bitmap.length+") was created");
		
		return bitmap;
	}
	
	public static int findMinPrime(long [] bitmap, int n)
	{
		int i = 0;
		for( i = 0 ; i < bitmap.length ; i++)
		{
			if( bitmap[i] != 0L )
			{
				break;
			}
		}
		
		if( i == bitmap.length ) return -1;
		
		int leastBitNum = Long.numberOfTrailingZeros(bitmap[i]);
		
		//dbg("findMinPrime bitmap idx:" + i +" leastBitNum:" + leastBitNum +"("+Long.toHexString(bitmap[i])+")");
		
		return (i * 32 + leastBitNum);
		
	}
	
	
	public static void turnOffPrimeMultipleBit(long [] bitmap, int n, int base)
	{
		for( int i = 1 ; (i*base) < n ; i++)
		{
			turnOffBit(bitmap, (i*base));
		}
	}
	
	public static void turnOffBit(long [] bitmap, int bitnum)
	{
		//int idx = bitnum / 32;
		//int offset = bitnum % 32;

		int idx = bitnum >> 5;
		int offset = bitnum & (32-1);

		
		//dbg( "turn off: "+bitnum+" idx:" + idx+" offset: "+offset);
		//dbg( "   Before 0x"+Long.toHexString(bitmap[idx]));
		bitmap[idx] = bitmap[idx] & ~( 1 << offset );
		//dbg( "   After 0x"+Long.toHexString(bitmap[idx]));
	}
	
	
	public static void fifteen_puzzle()
	{
		long db_4by4 = 0L;

		int i = 1;
		for( int row = 0 ; row < 4 ; row++)
			for( int col = 0 ; col < 4 ; col++ )
				db_4by4 = set_4by4_ele_val(db_4by4, row, col , (i++)%16);

		
		dump_db(db_4by4);
		
	}

	
	public static void dump_db(long db_4by4)
	{
		for( int row = 0 ; row < 4 ; row++)
		{	
			for( int col = 0 ; col < 4 ; col++ )
			{
				dbg("("+row+", "+col+")="+get_4by4_ele_val(db_4by4, row, col)+" ");
			}
			dbg_ln("");
		}	
	}
	
	
	public static int get_4by4_ele_val(long db_4by4, int row, int col)
	{
		return (int) (( db_4by4 >> (( row * 4 + col) * 4) ) & 0xFL) ;		
	}
	

	public static long set_4by4_ele_val(long db_4by4, int row, int col, int val)
	{
		long lv = ((long)( val & 0xF))  << (( row * 4 + col) * 4); 
		
		return db_4by4 | lv;
	}

	
	public static void explode_chemical_check()
	{
		long chemi_set = 0xFL; /* A:B:C:D */
		long subset = 0;
		
		long i = 0;
		
		for( subset = chemi_set ; subset != 0L ; subset =( chemi_set & ( subset - 1 )) )
		{
			//dbg_ln( (i++)+") " + " 0x"+Long.toHexString(subset) +"("+ Long.toBinaryString(subset) + ")"
			//		 + getChemiSetStr(subset));
			if( ! isExploding(subset))
			{
				dbg_ln( "Exploding subset ("+ (i++)+") " + " 0x"+Long.toHexString(subset) 
						+"("+ Long.toBinaryString(subset) + ") " + getChemiSetStr(subset));
			}
		}
		
	}
	

	public static long [] explodMask = {
		0xCL, /* A | B */
		0x3L, /* C | D */
	};
	
	
	public static boolean isExploding(long subset)
	{
		if( (subset & 0xCL) == 0xCL || (subset & 0x3L) == 0x3L )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static String getChemiSetStr(long subset)
	{
		String str = "";
		str += (( (subset & 0x8L) == 0x8L) ? "A": "0"); 
		str += (( (subset & 0x4L) == 0x4L) ? "B": "0"); 
		str += (( (subset & 0x2L) == 0x2L) ? "C": "0"); 
		str += (( (subset & 0x1L) == 0x1L) ? "D": "0"); 
		
		return str;
	}
	
	
	public static void bitinteger_study()
	{
		BigInteger a = new BigInteger("FFFFFFFFFFFFFFFF", 16);
		dbg_ln("0x"+a.toString(16));

		byte [] ba = {
				(byte)0x11,
				(byte)0x22,
				(byte)0x33,
				(byte)0x44,
				(byte)0x55,
				(byte)0x66,
				(byte)0x77,
				(byte)0xff
		};
		
		a = new BigInteger(ba);
		dbg_ln("0x"+a.toString(16));
		
	}
	
	
	
	
	public static void study1()
	{
		for( int i = 0 ; i < 100 ; i++)
		{
			isSet(1, i);
		}
	}
	
	public static boolean isSet(long v, int shift)
	{
		long sh = (long)1 << shift;
		
		System.out.println("sh=("+shift+")0x"+Long.toHexString(sh));
		
		return ((v & sh) != 0);
		
	}

	public static void dbg(String msg)
	{
		System.out.print(msg);
	}
	
	
	public static void dbg_ln(String msg)
	{
		System.out.println(msg);
	}
	
}
