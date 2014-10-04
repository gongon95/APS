import java.util.Scanner;


public class Graduation {

	
	public static void main(String [] args)
	{
		
		Scanner scan = new Scanner(System.in);
		tc_cnt = scan.nextInt();

		long start_time = System.currentTimeMillis();
		for( int i = 0 ; i < tc_cnt ; i++)
		{
			dbg_ln("\ntest case: "+i);
			graduation(scan);
		}

		long end_time = System.currentTimeMillis();
		
		long diff = end_time - start_time;
		
		dbg_ln("diff:" + diff + " ms");

	}
	
	public static int tc_cnt;
	public static int tot_course;
	public static int MAX_TOT_COURSE = 12;
	public static int req_course;
	public static int tot_season;
	public static int MAX_TOT_SEASON = 10;
	public static int max_course;
	public static int [] pre_course_msk;
	public static int [] ses_course_msk;
	
	public static void graduation(Scanner scan)
	{
		tot_course = scan.nextInt();
		req_course = scan.nextInt();
		tot_season = scan.nextInt();
		max_course = scan.nextInt();
		
		int course_set = 0;
		
		dbg_ln("tot_course :"+tot_course );
		dbg_ln("req_course :"+req_course );
		dbg_ln("season     :"+tot_season );
		dbg_ln("max_course :"+max_course );
		
		pre_course_msk = new int[tot_course]; // index is course number
		int pre_course_cnt = 0;
		int pre_course_num = 0;
		
		dbg_ln("pre_courese: ");
		for( int i = 0 ; i < tot_course ; i++)
		{
			course_set |= ( 1 << i );
			
			pre_course_cnt = scan.nextInt();

			pre_course_msk[i] = 0;
			for( int j = 0 ; j < pre_course_cnt ; j++)
			{
				pre_course_num = scan.nextInt();
				pre_course_msk[i] |= ( 1 << pre_course_num );
			}

			dbg_ln("("+i+") pre_course_cnt: "+ pre_course_cnt + " pre course mask: " + Integer.toBinaryString(pre_course_msk[i]));
		}
		
		ses_course_msk = new int[tot_season]; // index is seasone
		int ses_course_cnt = 0;
		int ses_course_num = 0;

		dbg_ln("ses_courese: ");
		for( int i = 0 ; i < tot_season ; i++ )
		{
			ses_course_cnt = scan.nextInt();
			
			ses_course_msk[i] = 0;
			for( int j = 0 ; j < ses_course_cnt ; j++)
			{
				ses_course_num = scan.nextInt();
				ses_course_msk[i] |= ( 1 << ses_course_num );
			}

			dbg_ln("("+i+") ses_course_cnt: "+ ses_course_cnt + " pre ses_course_msk: " + Integer.toBinaryString(ses_course_msk[i]));
		}
		

		for( int i = 0 ; i < 1 ; i++)
		{
			int min_season = MAX_TOT_SEASON;
			int course_subset_min_seaseon = 0;
			
			/* subset of total course */
			for( int course_subset = course_set ; course_subset != 0 ; course_subset = (course_set & (course_subset - 1) ) )
			{
				if( Integer.bitCount(course_subset) == req_course )
				{
					//dbg_ln("possible course_subset : " + Integer.toBinaryString(course_subset));
					
					course_subset_min_seaseon = getMinLastSeason(0, course_subset, 0, -1);
					
					if( course_subset_min_seaseon == -1) continue;
					
					if( course_subset_min_seaseon < min_season ) 
					{
						min_season = course_subset_min_seaseon;
					}
				}
			}
			
			if( min_season == MAX_TOT_SEASON)
			{
				System.out.println("IMPOSSIBLE");
			}
			else
			{
				System.out.println(i+")"+min_season);
			}
		}
		

		
	}
	
	
	public static int getMinLastSeason( int complete_course, int remaining_course, int season_course_set, int season )
	{
		int new_complete_course = complete_course;
		int new_remaining_course = remaining_course;
		
		if( season >= 0 )
		{
			new_complete_course |= season_course_set;
			new_remaining_course &= (~season_course_set);
		}

		if( new_remaining_course == 0 ) return 0;
		
		int new_season_course_tot_set = 0;
		
		int new_season = 0;
		
		for( new_season = (season+1) ; new_season < ses_course_msk.length ; new_season++ )
		{
			new_season_course_tot_set = new_remaining_course & ses_course_msk[new_season]; 
			
			new_season_course_tot_set = removeUnqualifiedCourse(new_complete_course, new_season_course_tot_set);
			
			if( new_season_course_tot_set != 0 ) break;
		}
		
		if( new_season == ses_course_msk.length ) return -1; 
				
		
		int min_last_season = MAX_TOT_SEASON ; 
		
		int subset_last_season = 0;
		
		for( int subset = new_season_course_tot_set ; subset != 0 ; subset = ( new_season_course_tot_set & ( subset - 1 ) ) )
		{
			if( Integer.bitCount(subset) <= max_course )
			{
				subset_last_season = getMinLastSeason(new_complete_course, new_remaining_course, subset, new_season);
				
				if( subset_last_season == -1 ) continue;
				
				if( subset_last_season == 0 )
				{
					min_last_season = subset_last_season;
					break;
				}
				
				if( subset_last_season < min_last_season )
				{
					min_last_season = subset_last_season;
				}
			}
		}
		
		if( min_last_season == MAX_TOT_SEASON)
		{
			return -1;
		}
		else
		{
			return min_last_season + 1;
		}
	}
	
	
	public static int removeUnqualifiedCourse(int complete_course, int course_set)
	{
		for( int i = 0 ; i < pre_course_msk.length ; i++)
		{
			if( ((course_set & ( 1 << i)) != 0) && ( (complete_course & pre_course_msk[i]) != pre_course_msk[i] ) )
			{
				course_set &= ( ~(1 << i) );
			}
		}
		
		return course_set;
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
