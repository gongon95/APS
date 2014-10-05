import java.util.Scanner;


/* 

Graduation Input Example
-----------------------
1
4 4 4 4
0
1 0
3 0 1 3
0
4 0 1 2 3
4 0 1 2 3
3 0 1 3
4 0 1 2 3

-----------------------
1
4 2 2 4
1 1
0
1 3
1 2
3 0 2 3
3 1 2 3

-----------------------
2
4 4 4 4
0
1 0
3 0 1 3
0
4 0 1 2 3
4 0 1 2 3
3 0 1 3
4 0 1 2 3
4 2 2 4
1 1
0
1 3
1 2
3 0 2 3
3 1 2 3

-----------------------
1
12 12 10 10
0
1 0
3 0 1 3
0
0
1 0
3 0 1 3
0
0
1 0
3 0 1 3
0
12 0 1 2 3 4 5 6 7 8 9 10 11
12 0 1 2 3 4 5 6 7 8 9 10 11
12 0 1 2 3 4 5 6 7 8 9 10 11
12 0 1 2 3 4 5 6 7 8 9 10 11
12 0 1 2 3 4 5 6 7 8 9 10 11
12 0 1 2 3 4 5 6 7 8 9 10 11
12 0 1 2 3 4 5 6 7 8 9 10 11
12 0 1 2 3 4 5 6 7 8 9 10 11
12 0 1 2 3 4 5 6 7 8 9 10 11
12 0 1 2 3 4 5 6 7 8 9 10 11

-----------------------

 */


public class Graduation {

	
	public static void main(String [] args)
	{
		
		Scanner scan = new Scanner(System.in);
		tc_cnt = scan.nextInt();

		long start_time = System.currentTimeMillis();
		for( int i = 0 ; i < tc_cnt ; i++)
		{
			dbg_cri_ln("\ntest case: "+i);
			graduation(scan);
		}

		long end_time = System.currentTimeMillis();
		
		long diff = end_time - start_time;
		
		dbg_cri_ln("diff:" + diff + " ms");

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
	
	public static int [][] memoization;
	
	public static int [][] minmap;
	
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
			memoization = new int[MAX_TOT_SEASON+1][];
			minmap = new int[MAX_TOT_SEASON+1][];
			
			for( int j = 0 ; j < memoization.length ; j++ )
			{
				memoization[j] = new int[1<<MAX_TOT_COURSE];
				minmap[j] = new int[1<<MAX_TOT_COURSE];
				
				for( int k = 0 ; k < (1<<MAX_TOT_COURSE)  ; k++)
				{
					memoization[j][k] = MAX_TOT_SEASON;
					minmap[j][k] = 0;
				}
			}
			
			int min_semester = take_semester(0, 0);
			
			if( min_semester == -1 )
			{
				System.out.println(i + ") IMPOSSIBLE");
			}
			else
			{
				System.out.println(i + ") min_semester ="+min_semester);
				
				dump_min_semester_courses();
				
			}
		}
	}
	
	
	/*
	 * @return last semester
	 */
	public static int take_semester(int semester, int taken_course)
	{
		//dbg_ln(indent(semester)+"take_semester semester("+semester+") taken_course("+Integer.toBinaryString(taken_course)+")");

		if( Integer.bitCount(taken_course) >= req_course )
		{
			//dbg_ln(indent(semester)+"*taken_course meets req_course("+req_course+")");
			return 0;
		}
		
		if( semester >= tot_season )
		{
			//dbg_ln(indent(semester)+"*semester is over tot_season");
			return -1;
		}
		
		/* courses which can be taken in this semester */
		int course_set = ses_course_msk[semester] & (~taken_course); // remove already taken courses from semester open courses
		
		course_set = removeNeedPreCourse(taken_course, course_set); // remove courses which needs prerequisite course
		
		if( course_set == 0 )
		{
			//dbg_ln(indent(semester)+"*semester course_set is zero");
			//minmap[semester][taken_course] = 0;
			return take_semester( semester + 1, taken_course );
		}
		
		
		int min_last_semester = MAX_TOT_SEASON;
		int last_semester = 0;
		
		for( int sub_course_set = course_set ; sub_course_set != 0 ; sub_course_set = ( course_set & ( sub_course_set - 1)))
		{	
			if( Integer.bitCount(sub_course_set) > max_course) continue;
			
			//dbg_ln(indent(semester)+"*took("+Integer.toBinaryString(sub_course_set)+")");

			last_semester = memoization[semester + 1][sub_course_set | taken_course];
			
			if( last_semester == MAX_TOT_SEASON)
			{
				last_semester = take_semester(semester + 1, sub_course_set | taken_course);
				memoization[semester + 1][sub_course_set | taken_course] = last_semester;
			}
			
			
			if( last_semester == -1 ) continue;
			
			if( last_semester < min_last_semester)
			{
				min_last_semester = last_semester;
				minmap[semester][taken_course] = sub_course_set;
			}

			if( last_semester == 0 )
			{
				min_last_semester = last_semester;
				minmap[semester][taken_course] = sub_course_set;
				break;
			}
		}
		
		if(min_last_semester == MAX_TOT_SEASON)
		{	
			//dbg_ln(indent(semester)+"*return -1");
			return -1;
		}

		//dbg_ln(indent(semester)+"*return "+(min_last_semester + 1));
		return (min_last_semester + 1);
	}
	
		
	
	public static int removeNeedPreCourse(int taken_course, int course_set)
	{
		for( int i = 0 ; i < pre_course_msk.length ; i++)
		{
			if( ((course_set & ( 1 << i)) != 0) && ( (taken_course & pre_course_msk[i]) != pre_course_msk[i] ) )
			{
				course_set &= ( ~(1 << i) );
			}
		}
		
		return course_set;
	}
	
	
	public static void dump_min_semester_courses()
	{
		int sub_course_set;
		int taken_course = 0;
		
		for( int i = 0 ; i < max_course ; i++)
		{
			sub_course_set = minmap[i][taken_course];
			taken_course |= sub_course_set;

			System.out.println(i+") sub_course_set="+Integer.toBinaryString(sub_course_set)
									+" taken_course="+Integer.toBinaryString(taken_course) );
			
			if( Integer.bitCount(taken_course) == req_course )
				break;
		}
		
	}
	
	
	
	public static String indent(int len)
	{
		String str = "";
		for( int i = 0 ; i < len ; i++)
		{
			str += "-";
		}
		return str;
	}
	
	public static void dbg(String msg)
	{
		System.out.print(msg);
	}
	
	public static void dbg_ln(String msg)
	{
		dbg(msg+"\n");
	}
	
	public static void dbg_cri(String msg)
	{
		System.out.print(msg);
	}
	
	public static void dbg_cri_ln(String msg)
	{
		dbg_cri(msg+"\n");
	}
	
	
}
