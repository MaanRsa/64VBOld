package com.maan.common;

import java.sql.Connection;
//import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import com.maan.common.dbconnection.DBConnection;
//import java.util.Vector;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
//import com.maan.common.LogManager;
//import proj.sql.QueryBuilder;

public class Runner
{
 private final static String UPDATED="UPDATED";
	
	//Multiple selection Method.........................................................
	public static void deletion(final String query)
	{
		Statement stmt=null;
		Connection con=null;
		try
		{
				con=DBConnection.getInstance().getDBConnection();
				stmt=con.createStatement();
				stmt.executeQuery(query);
		}
		catch(Exception e)
		{
			LogManager.push(e.getMessage());
			//LogManager.info("query....."+q);
			//LogManager.info("  runner DELETION   "+e.toString());
			//e.printStackTrace();
		}
		finally
		{
				try
				{
					if(stmt!=null)
					{
						stmt.close();
					}
					if(con!=null)
					{
						con.close();
					}
				}
				catch(Exception e)
				{
					LogManager.push(e.getMessage());
					//LogManager.info("ERROR "+e.toString());e.printStackTrace();
				}
			}
	}

	public static String insertion(final String query)
	{
		String result=null;
		Statement stmt=null;
		Connection con=null;
		ResultSet rSet=null;
		try
		{
				con=DBConnection.getInstance().getDBConnection();
				stmt=con.createStatement();
				rSet=stmt.executeQuery(query);
				result="INSERRTED";
				//LogManager.info("Insertion Method..Query ..."+q);
		}
		catch(Exception e)
		{
			//LogManager.info("Insertion Method..Query ..."+q);
			//LogManager.info("  runner insertion   "+e.toString());
			//e.printStackTrace();
			result="DIDN'T INSERTED";
		}
		finally
		{
				try
				{
					if(rSet!=null)
					{
						rSet.close();
					}
					if(stmt!=null)
					{
						stmt.close();
					}
					if(con!=null)
					{
						con.close();
					}
				}
				catch(Exception e)
				{
					LogManager.push(e.getMessage());
					//LogManager.info("ERROR "+e.toString());e.printStackTrace();
				}
			}
		return result;
	}

	public static String updation(final String query)
	{
		String result=null;
		Statement stmt=null;
		Connection con=null;
		final ResultSet rSet=null;
		try
		{
				con=DBConnection.getInstance().getDBConnection();
				stmt=con.createStatement();
				stmt.executeUpdate(query);
				result=UPDATED;
		}
		catch(Exception e)
		{
			//LogManager.info("updation..Query.."+q);
			//LogManager.info("  runner updation   "+e.toString());
			//e.printStackTrace();
			result="DIDN'T UPDATE";
		}
		finally
			{
				try
				{
					if(rSet!=null)
					{
					rSet.close();
					}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(con!=null)
				{
					con.close();
				}
				}catch(Exception e)
				{
					LogManager.push(e.getMessage());
					//LogManager.info("ERROR "+e.toString());e.printStackTrace();
				}
			}
		return result;
	}

	/*public static String[][] multipleSelection(String q)
	{
			String[][] result=null;
			Statement stmt=null;
			QueryBuilder qc;
			Connection con=null;

			try
			{
				result=new String[0][0];
				con=DBConnection.getInstance().getDBConnection();
				stmt=con.createStatement();
				qc = new QueryBuilder(stmt);	
				result=qc.getResultSet(q); 
				LogManager.info("Query......."+q);
			}
			catch(Exception e)
			{
				LogManager.info("query....."+q);
				LogManager.info("Error in runner 21 ..."+e.toString());
				e.printStackTrace();
			}
			finally
			{
				try
				{
					qc=null;
					if(stmt!=null)
						stmt.close();
					if(con!=null)
						con.close();
				}catch(Exception e)
				{e.printStackTrace();}
			}
			return result;
	}*/

	public static String singleSelection(final String query)
	{
			String result="";
			Statement stmt=null;
			ResultSet rSet=null;
			Connection con=null;
			try
			{
				con=DBConnection.getInstance().getDBConnection();
				stmt=con.createStatement();
				rSet=stmt.executeQuery(query);
				if(rSet.next())
				{
					result=rSet.getString(1);
				}
			}
			catch(Exception e)
			{
				LogManager.push(e.getMessage());
				//LogManager.info("Query......."+q);
				//LogManager.info("Error in runner 21 ..."+e.toString());
				//e.printStackTrace();
				//result="DIDN'T SELECTED";
			}
			finally
			{
				try
				{
					if(rSet!=null)
						{
					rSet.close();
						}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(con!=null)
				{					
					con.close();
				}
				}
				catch(Exception e)
				{
					LogManager.push(e.getMessage());
					//LogManager.info("ERROR "+e.toString());e.printStackTrace();
				}
			}
			return result;
	}

	public static String getErrormsg(final String errorCode)
	{
			String result="";
			Statement stmt=null;
			ResultSet rSet=null;
			Connection con=null;
			try
			{
				con=DBConnection.getInstance().getDBConnection();
				stmt=con.createStatement();
				rSet=stmt.executeQuery("select error_desc from error_master where error_id='"+errorCode+"'");
				if(rSet.next())
				{
					result=rSet.getString(1);
				}

			}
			catch(Exception e)
			{
				LogManager.push(e.getMessage());
				//LogManager.info("Error in runner 21 ..."+e.toString());e.printStackTrace();
			}
			finally
			{
				try
				{if(rSet!=null)
				{
					rSet.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(con!=null)
				{
					con.close();
				}
				}catch(Exception e)
				{
					LogManager.push(e.getMessage());
					//LogManager.info("ERROR "+e.toString());e.printStackTrace();
				}
			}
			return result;
	}

	public static void inserion(final String query)
	{
			Connection con=null;
			Statement stmt=null;
			ResultSet rSet=null;
		try
		{
				con=DBConnection.getInstance().getDBConnection();
				stmt=con.createStatement();
				rSet=stmt.executeQuery(query);
		}catch(Exception e)
		{
			LogManager.push(e.getMessage());
			//LogManager.info("Query......."+q);
			//LogManager.info("Error in runner 80....."+e.toString());
			//LogManager.info("DIDN'T INSERTED in insertion()  runner    "+e.toString());e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rSet!=null)
				{
					rSet.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(con!=null)
				{
					con.close();
				}
			}
			catch(Exception e)
			{
				LogManager.push(e.getMessage());
				//e.printStackTrace();
			}
		}
	}

	public static String multipleInsertion(final String query,final String[] cols)
	{
		String result="";
		PreparedStatement pre=null;
		Connection con=null;
		try
		{
			con=DBConnection.getInstance().getDBConnection();
			pre	=con.prepareStatement(query);
			//LogManager.info("s...........s.............."+cols.length);
			for(int i=0;i<cols.length;i++)
			{
				//LogManager.info("pre.setString("+i+",'"+cols[i]+"')");
				pre.setString(i+1,cols[i]);
			}
			pre.executeUpdate();
			con.commit();
			result="INSERTED";
		}
		catch(Exception e)
		{
			//LogManager.push(e.getMessage());
			//LogManager.info("Query......."+q);
			//LogManager.info("Error in runner 80....."+e.toString());e.printStackTrace();
			result="DIDN'T INSERTED  "+e.toString();
		}
		finally
		{
			try
			{
				if(pre!=null)
				{
					pre.close();
				}
				if(con!=null)
				{
					con.close();
				}
			}
			catch(Exception e)
			{
				LogManager.push(e.getMessage());
				//e.printStackTrace();
			}
		}
		return result;
	}	

	/*public static String autoGenerate(String code) throws Exception
	{

		Connection con=null;
		con=DBConnection.getInstance().getDBConnection();
		Statement stmt=null;
		String storeAutoGenerate = "";
		String strSerialNo = "";
		String[][] result = new String[0][0];
		int intSerialNo = 0;
		QueryBuilder qb =null;
		try
		{
			stmt=con.createStatement();;
			String sql = "select distinct (substr(proposal_no,5)) from position_master where proposal_no like '%"+code+"%'";
		 qb = new QueryBuilder(stmt);
		result = qb.getResultSet(sql);
		
		for(int i=0;i<result.length;i++) 
		{
			if(i==0) intSerialNo=Integer.parseInt(result[i][0]);
			if (intSerialNo<Integer.parseInt(result[i][0]))
			{
				intSerialNo = Integer.parseInt(result[i][0]);
			}
		}
		intSerialNo = intSerialNo+1;
		strSerialNo = ""+intSerialNo;
		int intSerialNoLength = strSerialNo.length();
		if (intSerialNoLength == 0)			storeAutoGenerate = code + "00001";
		else if (intSerialNoLength == 1)	storeAutoGenerate = code + "0000";
		else if (intSerialNoLength == 2)	storeAutoGenerate = code + "000";
		else if (intSerialNoLength == 3)	storeAutoGenerate = code + "00";
		else if (intSerialNoLength == 4)	storeAutoGenerate = code + "0";
		/*else if (intSerialNoLength == 5)	storeAutoGenerate = code + "00";*/
		/*else if (intSerialNoLength == 5)	storeAutoGenerate = code;

		strSerialNo = storeAutoGenerate + strSerialNo;
		}
		catch(Exception e)
		{
			LogManager.info("<BR>Exception in AutoGenerate : " + e);e.printStackTrace();
		}
		finally
		{
			try
			{
				if(qb!=null);
				qb=null;
				if(stmt!=null)
					stmt.close();
				if(con!=null)
					con.close();
			}
			catch(Exception e){e.printStackTrace();}
		}
		return strSerialNo;
	}*/

	public static int autoGenCustId(final String codes,final String table) 
	{

		final Connection con=null;

		Statement stmt=null;
		ResultSet rSet=null;
		int customerId=1;
		//int intSerialNo = 0;
		try
		{
			stmt = DBConnection.getInstance().getDBConnection().createStatement();

		//String sql = "select max(customer_id)+1 from position_master where employee_id='"+employeeId+"'";
		 final String sql = "select max("+codes+")+1 from "+table;

		rSet = stmt.executeQuery(sql);
		if (rSet.next()) {
			//intSerialNo = rSet.getInt(1);
			customerId = rSet.getInt(1);
			//return customerId;
		}

		}
		catch(Exception e)
		{
			LogManager.push(e.getMessage());
			//LogManager.info("<BR>Exception in autoGenCustId : " + e);e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rSet!=null)
				{
				rSet.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(con!=null)
				{
					con.close();
				}
			}
			catch(Exception e){
				LogManager.push(e.getMessage());
				//e.printStackTrace();
				}
		}
		return customerId;
	}

	public static String[][] multipleSelection(final String query,final String cols[])
	{
			String[][] finalResult = null;
			PreparedStatement pre = null;
			Connection con =null;
			ResultSet rSet = null;
			try
			{
				con = DBConnection.getInstance().getDBConnection();
				pre	= con.prepareStatement(query);
				LogManager.push("multipleSelection...qry is..."+query);
				for(int i=0;i<cols.length;i++)
				{
					pre.setString(i+1,cols[i]);
					LogManager.push("pre.setString("+i+",'"+cols[i]+"')");
				}
				rSet=pre.executeQuery();
				final ResultSetMetaData rsmd = rSet.getMetaData();
				final List result = new ArrayList();
				final int col = rsmd.getColumnCount();
				// untill the records in the result set, get the fields and add into the Vector.
				
				while(rSet.next())
				{
					String record[] = new String[col];
					for(int i=0; i < col; i++)
					{
						
						record[i] = rSet.getString(i+1);
						
					}
					result.add(record);
				}
				rSet.close();
				// convert the vector into two dimension string array and return the array.
				finalResult = new String[result.size()][col];
				for(int i=0; i < result.size(); i++)
				{
					finalResult[i] = (String[])result.get(i);
				}
			}
			catch(Exception e)
			{
				finalResult = new String[0][0];
				//LogManager.info("Query is ..."+q);
				//LogManager.info("Error in runner multipleSelection preparedstatement ..."+e.toString());
				//e.printStackTrace();
			}
			finally
			{
				try
				{
					if(pre!=null)
					{
						pre.close();
					}
					if(con!=null)
					{
						con.close();
					}
				}
				catch(Exception e)
				{
					LogManager.push(e.getMessage());
					//e.printStackTrace();
				}
			}
			return finalResult;
	}

	public static String singleSelection(final String query,final String[] cols)
	{
			String result="";
			PreparedStatement pre=null;
			ResultSet rSet=null;
			Connection con=null;
			try
			{
				con = DBConnection.getInstance().getDBConnection();
				pre	= con.prepareStatement(query);
				//LogManager.info("singleSelection Query is ..."+q);
				for(int i=0;i<cols.length;i++)
				{
					pre.setString(i+1,cols[i]);
					//LogManager.info("pre.setString("+i+",'"+cols[i]+"')");
				}
				rSet=pre.executeQuery();
				if(rSet.next())
				{
					result=rSet.getString(1);
				}

			}
			catch(Exception e)
			{
				LogManager.push(e.getMessage());
				//LogManager.info("query....."+q);
				//LogManager.info("Error in runner 21 ..."+e.toString());e.printStackTrace();
				result="";

			}
			finally
			{
				try
				{
					if(rSet!=null)
					{
						rSet.close();
					}
					if(pre!=null)
					{
						pre.close();
					}
					if(con!=null)
					{
						con.close();
					}
				}
				catch(Exception e)
				{
					LogManager.push(e.getMessage());
					//LogManager.info("ERROR in singleSelection prepared statement...."+e.toString());e.printStackTrace();
				}
			}
		return result;
	}

	public static String multipleUpdation(final String query,final String[] cols)
	{
		String result="";
		PreparedStatement pre=null;
		Connection con=null;
		try
		{
			con=DBConnection.getInstance().getDBConnection();
			pre	=con.prepareStatement(query);
			//LogManager.info("Query......."+q);
			for(int i=0;i<cols.length;i++)
			{
				//LogManager.info("pre.setString("+i+",'"+cols[i]+"')");
				pre.setString(i+1,cols[i]==null?"":cols[i]);
			}
			pre.executeUpdate();
			con.commit();
			
			result=UPDATED;
		}
		catch(Exception e)
		{
			//LogManager.push(e.getMessage());
			//LogManager.info("Query......."+q);
			//LogManager.info("Error in runner 80....."+e.toString());
			result="DIDN'T UDATED  "+e.toString();
			//e.printStackTrace();
		}
		finally
		{
			try
			{
				if(pre!=null)
				{
					pre.close();
				}
				if(con!=null)
				{
					con.close();
				}
			}
			catch(Exception e)
			{
				LogManager.push(e.getMessage());
				//e.printStackTrace();
			}
		}
		return result;
	}	
	public static String multipleUpdateTransaction(final Map queryCollection)
	{
		String result="";
		PreparedStatement pre=null;
		Connection con=null;
		int counts = 0;
		String query = "";
		try
		{
			con=DBConnection.getInstance().getDBConnection();
			con.setAutoCommit(false);
			counts = Integer.parseInt((String)queryCollection.get("Count"));
			for(int j=0;j<counts;j++)
			{
				query = (String)queryCollection.get("Query"+j);
			final	String cols[] = (String[])queryCollection.get("Args"+j);
				pre	=con.prepareStatement(query);
				for(int i=0;i<cols.length;i++)
				{
					pre.setString(i+1,cols[i]==null?"":cols[i]);
				}
				pre.executeUpdate();
				result=UPDATED;
			}
		}
		catch(Exception e)
		{
			//LogManager.info("Exception Query......."+q);
			result="DIDN'T UDATED  "+e.toString();
			//e.printStackTrace();
		}
		finally
		{
			try
			{
				if(result.equalsIgnoreCase(UPDATED)&&con!=null)
				{
					con.commit();
				}
				else if(con!=null)
				{
					con.rollback();
				}
				if(pre!=null)
				{
					pre.close();
				}
				if(con!=null)
				{
					con.close();
				}
			}
			catch(Exception e)
			{
				LogManager.push(e.getMessage());
				//e.printStackTrace();
			}
		}
		return result;
	}
	public static String insertionTransaction(final Map queryCollection)
	{
		String result="";
		Statement stmt=null;
		Connection con=null;
		ResultSet rSet=null;
		int counts = 0;
		String query = "";
		try
		{
				con=DBConnection.getInstance().getDBConnection();
				con.setAutoCommit(false);
				counts = Integer.parseInt((String)queryCollection.get("Count"));
				for(int j=0;j<counts;j++)
				{
					query = (String)queryCollection.get("Query"+j);
					//LogManager.info("Query......."+q);
					stmt=con.createStatement();
					rSet=stmt.executeQuery(query);
					result="INSERRTED";
				}
		}
		catch(Exception e)
		{
			//LogManager.info("Insertion Method..Query ..."+q);
			//e.printStackTrace();
			result="DIDN'T INSERTED";
		}
		finally
		{
				try
				{
					if(result.equalsIgnoreCase("INSERRTED")&&con!=null)
					{
						con.commit();
					}
					else if(con!=null)
					{
						//LogManager.info("Exception in Query so rollback is executed......."+result);
						con.rollback();
					}
					if(rSet!=null)
					{
						rSet.close();
					}
					if(stmt!=null)
					{
						stmt.close();
					}
					if(con!=null)
					{
						con.close();
					}
				}
				catch(Exception e)
				{
					LogManager.push(e.getMessage());
					//LogManager.info("ERROR "+e.toString());e.printStackTrace();
				}
		}
		return result;
	}
}