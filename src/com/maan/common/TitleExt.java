package com.maan.common;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

public class TitleExt extends RowMapperResultSetExtractor  {

	
	public TitleExt(final RowMapper arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	public Object extractData(final ResultSet rset) throws SQLException {
		
		List listResult = null;
		String[][] result =null;
		
		listResult =new ArrayList();
		
		final ResultSetMetaData rsm = rset.getMetaData();
		final int columnCount = rsm.getColumnCount();
		
		

		// untill the records in the result set, get the fields and add into the List.
		while(rset.next())
		{
		String record[]=new String[columnCount];
		
		for (int i = 0; i < columnCount; i++) {
			record[i] = rset.getString(i + 1);
						
		}
		listResult.add(record);
		}

		// convert the vector into two dimension string array and return the array.

		String finalResult[][] = new String[listResult.size()][columnCount];
		for (int i = 0; i < listResult.size(); i++) {
			finalResult[i] = (String[]) listResult.get(i);
		}
        
		result = finalResult;
		
		 try {
             if (rset != null)
             {
	           rset.close();
             }
                } catch (Exception e) { LogManager.debug(e);}   

		return result;	
		
	}

}
