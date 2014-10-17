import java.util.Scanner;


public class Chrismas {

	public static void main(String [] argv)
	{
		Scanner scan = new Scanner(System.in);
		
		int num_child = scan.nextInt();
		int tot_box = scan.nextInt();
		//int start = scan.nextInt();
		//int end = scan.nextInt();
		
		
		dbg_ln("num child: " + num_child);
		dbg_ln("tot box  : " + tot_box);
		//dbg_ln("start    : " + start);
		//dbg_ln("end      : " + end );
		
		int [] boxes = new int [tot_box];
		set_rand_int_arr(boxes);

		int [] cum_sum = get_cum_sum_arr(boxes);
		
		int sum = 0;
		
		int cnt = 0;
		
		for( int end = 0 ; end < boxes.length ; end++)
		{
			for( int start = 0 ; start <= end ; start++ )
			{
				if( start > 0)
					sum = cum_sum[end] - cum_sum[start-1];
				else
					sum = cum_sum[end];
				
				if( (sum % num_child) == 0 )
					cnt++;
			}
		}
		
		dbg_ln("cnt: " + cnt);
	}
	
	public static int [] get_cum_sum_arr(int [] arr)
	{
		int [] cum_arr = new int [arr.length];
		int cum_sum = 0;
		
		for( int i = 0 ; i < arr.length ; i++)
		{
			cum_arr[i] = cum_sum + arr[i];
			cum_sum += arr[i];
		}
		
		return cum_arr;
	}
	
	
	public static void set_rand_int_arr(int [] arr)
	{
		for( int i = 0 ; i < arr.length ; i++)
		{
			arr[i] = (int)( Math.random() * 100 );
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
