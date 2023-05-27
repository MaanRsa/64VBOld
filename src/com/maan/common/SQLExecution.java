package com.maan.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.jdbc.core.JdbcTemplate;

import com.maan.common.dbconnection.DBConnection;

import oracle.jdbc.OracleTypes;


/**
 * @author Mathi
 *
 */
public final class SQLExecution
	{
	
	private static SQLExecution instance=new SQLExecution();
	
	private SQLExecution(){
		
	}
	
	public static SQLExecution getInstance(){
		
		return instance;
		
	}
	//	Connection Check
	// Method.........................................................
	    public String[][] result = null;
		public CallableStatement cstmt = null;
		public ResultSet rs = null;
		public Connection con = null;
        public JdbcTemplate mytemplate = null;
		public String values[][] =  null;
		public List getInparam =  null;
		public List getOutparam = null;
		public String procedureName[]=new String[1];
		public Map inparam =  null;
		public Map passParam =  null;
		public Map rtValues =  null;
		public MyStoredProcedure myp=null;
	
	public void connectionCheck(Statement SMT) throws Exception
	{
		/*String sql = "select curdate()";
		ResultSet rs = SMT.executeQuery(sql);
		// check for the execution of the query 
		while(rs.next())
		{
			return;
		}
		rs.close();
		return;*/
	}
	//	 insertion
	// Method.........................................................
	public String[][] insertion(HashMap hsProcedure) {
		
		try {
			result = new String[0][0];
			con = DBConnection.getInstance().getDBConnection();
			if(hsProcedure.get("ProcedureName")!=null)
			{
				String qusCount = "";
				int count = hsProcedure.get("count")!=null?Integer.parseInt((String)hsProcedure.get("count")):1;
				for(int i = 0; i < count; i++)
				{
					qusCount+="?,";
				}
				if(qusCount.length()>0)
				{
					qusCount = qusCount.substring(0,qusCount.length()-1);
				}
				cstmt = con.prepareCall("begin " + (String)hsProcedure.get("ProcedureName") + "("+qusCount+"); end;");
				for(int i = 1; i < count-1; i++)
				{
					cstmt.setString(i, (String)hsProcedure.get("param"+i));
					
				}

				cstmt.registerOutParameter(count-1, OracleTypes.VARCHAR);
				cstmt.registerOutParameter(count, OracleTypes.CURSOR);
				
				cstmt.execute();
				String[] strMsg = new String[1];
				strMsg[0] = cstmt.getString(count-1);
				rs = (ResultSet) cstmt.getObject(count);
				ResultSetMetaData rsm = rs.getMetaData();
				int columnCount = rsm.getColumnCount();
				Vector vectorResult = new Vector();
				
				// untill the records in the result set, get the fields and add into the Vector.
				while(rs.next())
				{
					String record[] = new String[columnCount];
					for(int i=0; i < columnCount; i++)
					{
						record[i] = rs.getString(i+1);
					}
					vectorResult.addElement(record);
					vectorResult.addElement(strMsg);
				}
				//rs.close();
				// convert the vector into two dimension string array and return the array.
				
				String finalResult[][] = new String[vectorResult.size()][columnCount];
				for(int i=0; i < vectorResult.size(); i++)
				{
					finalResult[i] = (String[])vectorResult.elementAt(i);
				}
				result = finalResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
	         try {
	               if (rs != null)
		           rs.close();
                      } catch (Exception e) { e.printStackTrace();}   
                           try {
	                if (cstmt != null)
		             cstmt.close();
                      } catch (Exception e) { e.printStackTrace();} 
	           try {		
	               if (con != null || !con.isClosed())
	              con.close();
   	                 } catch (Exception e) { e.printStackTrace(); }
                   }
		return result;
	}

	//insertion newly added 
	
public String insert(HashMap hsProcedure) {
		
		String returnValue = "";
		try {
			con = DBConnection.getInstance().getDBConnection();
			if(hsProcedure.get("ProcedureName")!=null)
			{
				String qusCount = "";
				int count = hsProcedure.get("count")!=null?Integer.parseInt((String)hsProcedure.get("count")):1;
				for(int i = 0; i < count; i++)
				{
					qusCount+="?,";
				}
				if(qusCount.length()>0)
				{
					qusCount = qusCount.substring(0,qusCount.length()-1);
				}
				cstmt = con.prepareCall("begin " + (String)hsProcedure.get("ProcedureName") + "("+qusCount+"); end;");
				for(int i = 1; i < count; i++)
				{
					LogManager.info("(String)hsProcedure.get)"+i+" "+hsProcedure.get("param"+i));
								
					cstmt.setString(i, (hsProcedure.get("param"+i)==null)?null:(String)hsProcedure.get("param"+i));
				
				}

				cstmt.registerOutParameter(count, OracleTypes.VARCHAR);
				//cstmt.registerOutParameter(count, OracleTypes.CURSOR);
				
				cstmt.execute();
				//String[] strMsg = new String[1];
				returnValue = cstmt.getString(count);
				LogManager.info("Return Value is"+returnValue);
				/*rs = (ResultSet) cstmt.getObject(count);
				ResultSetMetaData rsm = rs.getMetaData();
				int columnCount = rsm.getColumnCount();
				Vector vectorResult = new Vector();
				
				// untill the records in the result set, get the fields and add into the Vector.
				while(rs.next())
				{
					String record[] = new String[columnCount];
					for(int i=0; i < columnCount; i++)
					{
						record[i] = rs.getString(i+1);
					}
					vectorResult.addElement(record);
					vectorResult.addElement(strMsg);
				}
				//rs.close();
				// convert the vector into two dimension string array and return the array.
				
				String finalResult[][] = new String[vectorResult.size()][columnCount];
				for(int i=0; i < vectorResult.size(); i++)
				{
					finalResult[i] = (String[])vectorResult.elementAt(i);
				}
				result = finalResult;*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
	         try {
	               if (rs != null)
		           rs.close();
                      } catch (Exception e) { e.printStackTrace();}   
                           try {
	                if (cstmt != null)
		             cstmt.close();
                      } catch (Exception e) { e.printStackTrace();} 
	           try {		
	               if (con != null || !con.isClosed())
	              con.close();
   	                 } catch (Exception e) { e.printStackTrace(); }
                   }
		return returnValue;
	}

	//	 String[][] selection
	// Method.........................................................
	public String[][] selection(Map hsProcedure) {
		
		getInparam=new ArrayList();
		getOutparam=new ArrayList();
		inparam = new HashMap();
		passParam = new HashMap();
		rtValues = new HashMap();
		if (hsProcedure.get("ProcedureName") != null) {
			try {
				procedureName[0]=(String)hsProcedure.get("ProcedureName");
				
				mytemplate = DBConnection.getInstance().gettemplate();
				getInparam = mytemplate.queryForList("SELECT ARGUMENT_NAME FROM ALL_ARGUMENTS WHERE OBJECT_NAME=upper(?) AND IN_OUT='IN' and owner IN(SELECT USERNAME FROM USER_USERS) ORDER BY SEQUENCE",procedureName);
				getOutparam = mytemplate
						.queryForList("SELECT ARGUMENT_NAME FROM ALL_ARGUMENTS WHERE OBJECT_NAME=upper(?) AND IN_OUT='OUT'  and owner IN(SELECT USERNAME FROM USER_USERS) ORDER BY SEQUENCE",procedureName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			LogManager.info("Selection stored procedure checking b.procdure name is"+procedureName[0]);
			LogManager.info("getInparam.size() is "+getInparam.size());
			//SELECT * FROM ALL_ARGUMENTS where owner='MAANPTS' AND OBJECT_NAME=upper('spGetCourierBranchId')
			if (getInparam.size() > 0) {
				for (int i = 0; i < getInparam.size(); i++) {
					LogManager.info("(String)hsProcedure.get)"+i+" "+hsProcedure.get("param"+(i+1)));
					inparam = (Map) getInparam.get(i);
					passParam.put((String) inparam.get("ARGUMENT_NAME"),
							hsProcedure.get("param" + (i + 1)));
				}
			}

			 myp= new MyStoredProcedure(DBConnection
					.getInstance().getDataSource(), getInparam, getOutparam,
					(String) hsProcedure.get("ProcedureName"));
			rtValues = myp.execute(passParam);
			
			
			Iterator it = rtValues.entrySet().iterator();
			
			while (it.hasNext()) {
				
				Map.Entry pairs = (Map.Entry) it.next();
				values= (String[][]) pairs.getValue();
				
			}
			

		}
		LogManager.info("Exit from selection procedure");
		return values;
	}
	public String increment(HashMap hsProcedure) {
		String result = "";
		
		try {
			con = DBConnection.getInstance().getDBConnection();
			if(hsProcedure.get("ProcedureName")!=null)
			{
				cstmt = con.prepareCall("begin " + (String)hsProcedure.get("ProcedureName") + "(?); end;");
				cstmt.registerOutParameter(1, OracleTypes.CURSOR);
				
				cstmt.execute();
				rs = (ResultSet) cstmt.getObject(1);
				// untill the records in the result set, get the fields and add into the Vector.
				if(rs.next())
				{
						result = rs.getString(1);
				}
				//rs.close();
				// convert the vector into two dimension string array and return the array.
				
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
	         try {
	               if (rs != null)
		           rs.close();
                      } catch (Exception e) { e.printStackTrace();}   
                           try {
	                if (cstmt != null)
		            cstmt.close();
                      } catch (Exception e) { e.printStackTrace();} 
	           try {		
	               if (con != null || !con.isClosed())
	              con.close();
   	                 } catch (Exception e) { e.printStackTrace(); }
                   }
		return result;
	}
	public boolean checkValue(HashMap hsProcedure) {
		boolean b = false;
		
		try {
			con = DBConnection.getInstance().getDBConnection();
			if(hsProcedure.get("ProcedureName")!=null)
			{
				String qusCount = "";
				int count = hsProcedure.get("count")!=null?Integer.parseInt((String)hsProcedure.get("count")):1;
				for(int i = 0; i < count; i++)
				{
					qusCount+="?,";
				}
				if(qusCount.length()>0)
				{
					qusCount = qusCount.substring(0,qusCount.length()-1);
				}
				cstmt = con.prepareCall("begin " + (String)hsProcedure.get("ProcedureName") + "("+qusCount+"); end;");
				for(int i = 1; i < count; i++)
				{
					cstmt.setString(i, (String)hsProcedure.get("param"+i));
				}
				cstmt.registerOutParameter(count, OracleTypes.CURSOR);
	
				cstmt.execute();
				rs = (ResultSet) cstmt.getObject(count);
				if (rs.next())
				{
					b = true;
				}
			}
			else
			{
				b = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			b = false;

		}finally {
	         try {
	               if (rs != null)
		           rs.close();
                      } catch (Exception e) { e.printStackTrace();}   
                           try {
	                if (cstmt != null)
		             cstmt.close();
                      } catch (Exception e) { e.printStackTrace();} 
	           try {		
	               if (con != null || !con.isClosed())
	              con.close();
   	                 } catch (Exception e) { e.printStackTrace(); }
                   }
		return b;
	}
	public String getErrorMsg(String errorCode) {
		String result = new String();
		Statement stmt = null;
		
		try {
			con = DBConnection.getInstance().getDBConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery("select error_desc from error_master where error_id='"
							+ errorCode + "'");
			if (rs.next())
				result = rs.getString(1);

		} catch (Exception e) {
			e.printStackTrace();
			result = "DIDN'T SELECTED";

		}
		finally {
	         try {
	               if (rs != null)
		           rs.close();
                      } catch (Exception e) { e.printStackTrace();}   
                           try {
	                if (stmt != null)
		             stmt.close();
                      } catch (Exception e) { e.printStackTrace();} 
	           try {		
	               if (con != null || !con.isClosed())
	              con.close();
   	                 } catch (Exception e) { e.printStackTrace(); }
                   }
		return result;
	}
// for testing 
public String test(HashMap hsProcedure) {
		
		String returnValue = "";
		try {
			con = DBConnection.getInstance().getDBConnection();
			if(hsProcedure.get("ProcedureName")!=null)
			{
				String qusCount = "";
				int count = hsProcedure.get("count")!=null?Integer.parseInt((String)hsProcedure.get("count")):1;
				for(int i = 0; i < count; i++)
				{
					qusCount+="?,";
				}
				if(qusCount.length()>0)
				{
					qusCount = qusCount.substring(0,qusCount.length()-1);
				}
				cstmt = con.prepareCall("begin " + (String)hsProcedure.get("ProcedureName") + "("+qusCount+"); end;");
				for(int i = 1; i < count+1; i++)
				{
					LogManager.info("(String)hsProcedure.get)"+i+" "+hsProcedure.get("param"+i));
					cstmt.setString(i, (hsProcedure.get("param"+i)==null)?null:(String)hsProcedure.get("param"+i));
					
				}

				//cstmt.registerOutParameter(count, OracleTypes.VARCHAR);
				//cstmt.registerOutParameter(count, OracleTypes.CURSOR);
				
				cstmt.execute();
				//String[] strMsg = new String[1];
				//returnValue = cstmt.getString(count);
				//LogManager.info("Return Value is"+returnValue);
				/*rs = (ResultSet) cstmt.getObject(count);
				ResultSetMetaData rsm = rs.getMetaData();
				int columnCount = rsm.getColumnCount();
				Vector vectorResult = new Vector();
				
				// untill the records in the result set, get the fields and add into the Vector.
				while(rs.next())
				{
					String record[] = new String[columnCount];
					for(int i=0; i < columnCount; i++)
					{
						record[i] = rs.getString(i+1);
					}
					vectorResult.addElement(record);
					vectorResult.addElement(strMsg);
				}
				//rs.close();
				// convert the vector into two dimension string array and return the array.
				
				String finalResult[][] = new String[vectorResult.size()][columnCount];
				for(int i=0; i < vectorResult.size(); i++)
				{
					finalResult[i] = (String[])vectorResult.elementAt(i);
				}
				result = finalResult;*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
	         try {
	               if (rs != null)
		           rs.close();
                      } catch (Exception e) { e.printStackTrace();}   
                           try {
	                if (cstmt != null)
		             cstmt.close();
                      } catch (Exception e) { e.printStackTrace();} 
	           try {		
	               if (con != null)
	              con.close();
   	                 } catch (Exception e) { e.printStackTrace(); }
                   }
		return returnValue;
	}

}