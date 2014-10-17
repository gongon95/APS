import java.util.Scanner;


/* sample inputs

10
10
JKLMNOPQRS
EFGHGHMNOP
ABDFGIKMQS
DEFGHMPQRS
ACFJKLMQRS
HHHHHHHHHS
DEGAGHIOPS
EFIJKLODPS
ABEDFHALNS
CEFGHIJKLM

-------------------------------
10
10
ABCDEFGHIJ
BCDEFGHIJK
CDEFGHIJLM
DEFGHIJLMN
EFGHIJLMNO
FGHIJLMNOP
GHIJLMNOPQ
HIJLMNOPQR
IJLMNOPQRS
ABCDEFGHIJ

-------------------------------
3
2
AA
CA
EA

-------------------------------
10
8
JKLMNQRS
EFGHGNOP
ABDFGMQA
DEFGHQRA
ACFJKQRA
HHHHHHHA
DEGAGOPA
EFIJKDPA
ABEDFLNA
CEFGHKLM


 */


public class LongestContinuousAlphaInMaxtrix {

	
	public static char [][] ch_arr;
	public static int [][] r_arr;
	public static int [][] u_arr;
	public static int [][] ru_arr;
	public static int [][] lu_arr;
	public static int [][] max_arr;

	
	public static void main(String [] args)
	{
		Scanner scan = new Scanner(System.in);
		
		int y_size = scan.nextInt();
		int x_size = scan.nextInt();
		
		dbg_ln("y_size: "+y_size);
		dbg_ln("x_size: "+x_size);
		
		
		ch_arr = new char[y_size][];
		r_arr = new int[y_size][];
		u_arr = new int[y_size][];
		ru_arr = new int[y_size][];
		lu_arr = new int[y_size][];
		max_arr  = new int[y_size][];
		
		String line ;
		line = scan.nextLine();
		
		for( int y = 0 ; y < y_size ; y++)
		{
			ch_arr[y] = new char[x_size];
			r_arr[y] = new int[x_size];
			u_arr[y] = new int[x_size];
			ru_arr[y] = new int[x_size];
			lu_arr[y] = new int[x_size];
			max_arr[y] = new int[x_size];

			line = scan.nextLine();

			for( int x = 0 ; x < x_size ; x++)
			{
				ch_arr[y][x] = line.charAt(x); 
			}
		}
		
		dump_ch_arr(ch_arr);
		//dump_int_arr(r_arr);

		int max_len = 0;
		int len = 0;
		
		for( int y = 0 ; y < y_size ; y++)
		{
			for( int x = 0 ; x < x_size ; x++)
			{
				max_len = 0;
				
				len = get_length(y, x, ch_arr, r_arr, RI);
				if( len > max_len ) max_len = len;

				len = get_length(y, x, ch_arr, u_arr, UP);
				if( len > max_len ) max_len = len;

				len = get_length(y, x, ch_arr, ru_arr, RU);
				if( len > max_len ) max_len = len;

				len = get_length(y, x, ch_arr, lu_arr, LU);
				if( len > max_len ) max_len = len;
				
				max_arr[y][x] = max_len;
			}
		}
	
		//dbg_ln("RI map....");
		//dump_int_arr(max_arr);

		
		dbg_ln("max map....");
		dump_int_arr(max_arr);
		
		max_len = 0;
		char max_ch = 0x7f;
		int max_y = 0;
		int max_x = 0;
		for( int y = 0 ; y < y_size ; y++)
		{
			for( int x = 0 ; x < x_size ; x++)
			{
				len = max_arr[y][x];
				
				if( len > max_len)
				{
					max_len = len;
					max_y = y;
					max_x = x;
					max_ch = ch_arr[y][x];
				}
				else if ( len == max_len)
				{
					if( ch_arr[y][x] < max_ch)
					{
						max_y = y;
						max_x = x;
						max_ch = ch_arr[y][x];
					}
				}
			}
		}
		
		System.out.println("longest char = " +max_ch);
		
	}
	
	
	
	public static final int RI = 0;
	public static final int UP = 1;
	public static final int RU = 2;
	public static final int LU = 3;
	
	public static int get_length(int start_y, int start_x, char [][] ch_arr, int [][] map, int dir)
	{
		int cnt = map[start_y][start_x];
		if( cnt != 0)
		{
			return 1;
		}
		
		char ch = ch_arr[start_y][start_x];
		
		int y = start_y;
		int x = start_x;
		
		char ch2 ;
		
		for(  ; y >= 0 && y < ch_arr.length && x >= 0 && x < ch_arr[0].length ;)
		{
			ch2 = ch_arr[y][x];
			if( ch == ch2 )
			{
				cnt++;
				map[y][x] = 1;
			}
			else
			{
				break;
			}
			
			
			switch(dir)
			{
			case RI:
				x++;
				break;
			case UP:
				y++;
				break;
			case RU:
				x++;
				y++;
				break;
			case LU:
				x--;
				y++;
				break;
			}
			
		}
		
		return cnt;
	}
	

	public static void dump_ch_arr(char [][] arr)
	{
		for( int y = 0 ; y < arr.length ; y++)
		{
			for( int x = 0 ; x < arr[y].length ; x++)
			{
				dbg(""+arr[y][x]);
			}
			dbg("\n");
		}
	}

	public static void dump_int_arr(int [][] arr)
	{
		for( int y = 0 ; y < arr.length ; y++)
		{
			for( int x = 0 ; x < arr[y].length ; x++)
			{
				dbg(""+arr[y][x]);
			}
			dbg("\n");
		}
	}
	
	public static void dbg(String msg)
	{
		System.out.print(msg);
	}
	
	public static void dbg_ln(String msg)
	{
		dbg(msg+"\n");
	}
	
}
