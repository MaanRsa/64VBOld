package com.maan.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import oracle.jdbc.OracleTypes;


public class MyStoredProcedure extends StoredProcedure {

	public MyStoredProcedure(final DataSource dsource, final List inparam, final List outparam,
			final String STORED_PROC_NAME) {
		super(dsource, STORED_PROC_NAME);
		Map getInparam;
		Map getOutparam;
		
		if ((inparam.isEmpty())) {
			LogManager.push("inparam.isEmpty");
			/*for (int i = 0; i < inparam.size(); i++) {
				getInparam = (Map) inparam.get(i);
				declareParameter(new SqlParameter((String) getInparam
						.get("ARGUMENT_NAME"), OracleTypes.VARCHAR));
			
		}*/
			
		}
		else
		{
			for (int i = 0; i < inparam.size(); i++) {
				getInparam = (Map) inparam.get(i);
				declareParameter(new SqlParameter((String) getInparam
						.get("ARGUMENT_NAME"), OracleTypes.VARCHAR));
			}
		
		}
		
		getOutparam = (Map) outparam.get(0);
		declareParameter(new SqlOutParameter((String) getOutparam
				.get("ARGUMENT_NAME"), OracleTypes.CURSOR, new TitleExt(new TitleMapper())));
		 
		compile();

	}

	public Map execute(final Map inParams) {
		Map result;
		if(inParams.isEmpty())
		{
			result= super.execute(new HashMap());
		}else
		{
			result=super.execute(inParams);
		}
		
		return result;
	}

}
