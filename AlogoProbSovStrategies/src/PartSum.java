import java.util.Random;


public class PartSum {



	public static void main(String [] agrv)
	{
		//part_sum();
		//distribution();
		//two_dimension_array_sum();
		get_zero_closest_range();
	}
	
	public static int [] gen_int_array(int n)
	{
		int arr[] = new int[n];
		for( int i = 0 ; i < arr.length ; i++)
		{
			arr[i] = (int)( (Math.random() - 0.5) * 100.0);
			//arr[i] = i;
		}
		
		return arr;
	}
	
	public static void dump_int_array(int arr[])
	{
		for( int i = 0 ; i < arr.length ; i++)
		{
			dbgf("%5d", arr[i]);
		}
		dbgf("\n");
		
	}
	
	
	public static void part_sum()
	{
		int [] arr = gen_int_array(10);
		int partsum_arr[] = new int[10];
		int partsum = 0;
		
		int [] part_sum_arr = get_part_sum(arr);
		
		dbg_ln("part sum = " + get_range_sum(part_sum_arr, 5, 8));
		dbg_ln("part avg = " + get_range_average(part_sum_arr, 5, 8));
		
	}

	
	public static int [] get_part_sum(int [] arr)
	{
		int partsum_arr[] = new int[arr.length];
		int partsum = 0;
		
		for( int i = 0 ; i < arr.length ; i++)
		{
			partsum += arr[i];
			partsum_arr[i] = partsum;
			
			//dbg_ln(i+") value:"+arr[i]+" part sum:"+partsum_arr[i]);
		}

		return partsum_arr;
	}
	
	
	public static int [] get_part_power_sum(int [] arr)
	{
		int partsum_arr[] = new int[arr.length];
		int partsum = 0;
		
		for( int i = 0 ; i < arr.length ; i++)
		{
			partsum += (arr[i]*arr[i]);
			partsum_arr[i] = partsum;
			
			dbg_ln(i+") value:"+arr[i]+" part sum:"+partsum_arr[i]);
		}

		return partsum_arr;
	}
	
	
	public static int get_range_sum(int [] part_sum_arr, int start, int end)
	{
		if( start == 0 )
			return part_sum_arr[end];
		
		return (part_sum_arr[end] - part_sum_arr[start-1]);
	}
	
	
	public static float get_range_average(int [] part_sum_arr, int start, int end)
	{
		if( start == 0 )
			return part_sum_arr[end];
		
		return ((float)(part_sum_arr[end] - part_sum_arr[start-1])) / ((float)(end - start + 1));
	}
	
	
	
	
	
	public static void distribution()
	{
		int [] arr = gen_int_array(10);
		
		int [] cum_sum = get_part_sum(arr);
		
		int [] cum_pow_sum = get_part_power_sum(arr);
		
		dbg_ln("distribution="+calc_distribution(arr, cum_sum,  0, 9));
		
		//dbg_ln("distribution2="+calc_distribution_2(cum_pow_sum, cum_sum,  0, 9));

	}
	
	
	public static double calc_distribution( int [] arr, int [] cum_sum_arr, int start, int end)
	{
		// calc part sum
		int part_sum = 0;
		
		if( start == 0 )
			part_sum = cum_sum_arr[end];
		else
			part_sum = cum_sum_arr[end] - cum_sum_arr[start-1];
		
		// calc part average
		double part_avg = ((double)part_sum) / ((double) ( end - start + 1));
		
		double diff = 0.0;
		double diff_sum = 0.0;
		for( int i = start ; i <= end ; i++ ) 
		{
			diff = (double)arr[i] - part_avg;
			diff *= diff;
			
			diff_sum += diff;
		}
		
		double distribution = diff_sum / ((double) (end - start + 1));
		
		return distribution;
	}

	
	/*
	 *  /* distribution can be changed to as follows....
	 *  dist = ( Sigma[i=a~b] ( A[i] - mean(a,b) )^2 ) / ( b -a + 1)
	 *       = ( Sigma[i=a~b] ( A[i]^ 2 - 2*A[i]*mean(a,b) + mean(a,b)^2 ) ) / ( b -a + 1)
	 *       = ( Sigma[i=a~b] ( A[i] ^ 2 ) - 2*mean(a,b)* Sigma[i=a~b] ( A[i] ) - ( b - a + a) * mean(a, b) ^ 2) 
	 * 
	 */
	
	/*
	public static double calc_distribution_2( int [] cum_power_sum_arr, int [] cum_sum_arr, int start, int end)
	{
		int range_sum = get_range_sum(cum_sum_arr, start, end);
		int pow_range_sum = get_range_sum(cum_power_sum_arr, start, end);
		
		double range_sum_avg = ((double)range_sum) / ((double)( end - start + 1));
		
		double dist = 
				pow_range_sum - 2 * range_sum_avg * range_sum - (end - start + 1) * range_sum_avg * range_sum_avg;
		

		return dist / ((double)( end - start + 1));
	}
	*/
	
	
	public static void two_dimension_array_sum()
	{
		int [][] arr_2d = gen_two_dimension_array(10, 10);
		dump_two_dimension_array(arr_2d);
		
		dbgf("\n");
		int [][] cum_arr_2d = cum_sum_two_dimension_array(arr_2d);
		dump_two_dimension_array(cum_arr_2d);
		
		int y1 = 7, x1 = 9;
		int y2 = 9, x2 = 9;
		
		dbg_ln("sum       = " + get_grid_sum(cum_arr_2d, y1, x1, y2, x2));
		dbg_ln("sum check = " + get_grid_sum_beast(arr_2d, y1, x1, y2, x2));
		
	}
	
	
	public static int [][] gen_two_dimension_array(int y_len, int x_len)
	{
		
		int [][] arr_2d = new int[y_len][];
		
		for( int i = 0 ; i < arr_2d.length ; i++)
		{
			arr_2d[i] = new int[x_len];
			
			for( int j = 0 ; j < arr_2d[i].length ; j++)
			{
				arr_2d[i][j] = ( i + j);
				//arr_2d[i][j] = 1;

			}
			
		}
		
		return arr_2d;
	}
	

	public static void dump_two_dimension_array(int [][] arr_2d)
	{
		for( int i = 0 ; i < arr_2d.length ; i++)
		{
			for( int j = 0 ; j < arr_2d[i].length ; j++)
			{
				dbgf("%5d", arr_2d[i][j]);				
			}
			dbgf("\n");
		}
		
	}

	
	public static int [][] cum_sum_two_dimension_array( int [][] arr_2d)
	{
		int [][] cum_sum_arr_2d = new int[arr_2d.length][];
		
		for( int i = 0 ; i < cum_sum_arr_2d.length ; i++ )
		{
			cum_sum_arr_2d[i] = new int[arr_2d[0].length];
		}
		
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		
		for( int i = 0 ; i < cum_sum_arr_2d.length ; i++ )
		{
			for( int j = 0; j < cum_sum_arr_2d[i].length ; j++)
			{
				a = (( i > 0 && j > 0 )? cum_sum_arr_2d[ i - 1][ j - 1 ]: 0 );
				b = (( i > 0          )? cum_sum_arr_2d[ i - 1][ j     ]: 0 );	
				c = ((          j > 0 )? cum_sum_arr_2d[ i    ][ j - 1 ]: 0 );	
				d = arr_2d[i][j];	
				
				cum_sum_arr_2d[i][j] = b + c + d -a ;
			}
		}
		
		return cum_sum_arr_2d;
	}
	
	
	public static int get_grid_sum(int [][] cum_arr_2d, int y1, int x1, int y2, int x2)
	{
		int sum_a = cum_arr_2d[y2][x2];
		int sum_b = (( x1 > 0 )?cum_arr_2d[y2][x1-1]:0);
		int sum_c = (( y1 > 0 )?cum_arr_2d[y1-1][x2]:0);
		int sum_d = (( y1 > 0 && x1 > 0 )?cum_arr_2d[y1-1][x1-1]: 0);
		
		return sum_a - sum_b - sum_c + sum_d;
	}
	

	public static int get_grid_sum_beast(int [][] arr_2d, int y1, int x1, int y2, int x2)
	{
		int sum = 0;
		
		for( int i = y1; i <= y2 && i < arr_2d.length ; i++)
		{
			for( int j = x1; j <= x2 && j < arr_2d[0].length ; j++ )
			{
				sum += arr_2d[i][j];
			}
		}
	
		return sum;
	}

	
	public static void get_zero_closest_range()
	{
		int [] arr = gen_int_array(10000);
		//dump_int_array(arr);

		int [] cum_arr = get_part_sum(arr);
		//dump_int_array(cum_arr);

		int range_sum = 0;
		int abs_range_sum = 0;
		int abs_min_range_sum = 0x7fffffff;
		int min_range_sum_start = -1;
		int min_range_sum_end = -1;
		
		for( int end = 0 ; end < cum_arr.length ; end++)
		{
			for( int start = 0 ; start <= end ; start++)
			{
				if( start > 0)
				{		
					range_sum = cum_arr[end] - cum_arr[start-1];
				}
				else
				{
					range_sum = cum_arr[end];
				}
				
				abs_range_sum = Math.abs(range_sum);

				//dbg_ln("start: " + start + " end:" + end + " range_sum:"+range_sum + " abs_range_sum:" + abs_range_sum);
				
				if( abs_range_sum < abs_min_range_sum )
				{
					abs_min_range_sum = abs_range_sum;
					min_range_sum_start = start;
					min_range_sum_end = end;
				}
				
			}
		}
		
		dbg_ln("start: "+min_range_sum_start+" end: "+min_range_sum_end);
		
		
		
	}
	
	
	
	
	
	
	public static void dbgf(String msg, Object ... args)
	{
		System.out.printf(msg, args);
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
