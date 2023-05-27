package com.maan.reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.jdbc.core.RowMapper;

import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;
import com.maan.search.SearchVB;

public class ReportsDAOImpl extends CommonBaseDAOImpl implements ReportsDAO {

	public void getReportsList(final ReportsFormBean bean) throws CommonBaseException {
	
		final NumberFormat total = new  DecimalFormat("###,###,###,###,##0"); 
		final NumberFormat amount = new  DecimalFormat("###,###,###,###,##0.00"); 
    	String arg[]=new String[3];
		String query=getQuery(DBConstants.REPORTS_QUERY);
		arg[0]=bean.getStartDate();
		arg[1]=bean.getEndDate();
		arg[2]=bean.getPaymentType();
		try{
		this.mytemplate.query(query, arg,new RowMapper(){
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				if(rset.getString("TYPE").equalsIgnoreCase("CITI"))
				{
					bean.setCitiTotal(total.format(rset.getInt("TOTAL_RECORDS")));
					bean.setCitiMatched(total.format(rset.getInt("MATCHED_RECORDS")));
					bean.setCitiNonMatched(total.format(rset.getInt("UNMATCHED_RECORDS")));
					bean.setCitiUnmatchedAmount(amount.format(rset.getInt("UNMATCHED_AMOUNT")));
					bean.setCitiReversal(total.format(rset.getInt("REVERSAL_CHECK")));
				}
				if(rset.getString("TYPE").equalsIgnoreCase("AXIS"))
				{
					bean.setAxisTotal(total.format(rset.getInt("TOTAL_RECORDS")));
					bean.setAxisMatched(total.format(rset.getInt("MATCHED_RECORDS")));
					bean.setAxisNonMatched(total.format(rset.getInt("UNMATCHED_RECORDS")));
					bean.setAxisUnmatchedAmount(amount.format(rset.getInt("UNMATCHED_AMOUNT")));
					bean.setAxisReversal(total.format(rset.getInt("REVERSAL_CHECK")));
				}
				if(rset.getString("TYPE").equalsIgnoreCase("HDFC"))
				{
					bean.setHdfcTotal(total.format(rset.getInt("TOTAL_RECORDS")));
					bean.setHdfcMatched(total.format(rset.getInt("MATCHED_RECORDS")));
					bean.setHdfcNonMatched(total.format(rset.getInt("UNMATCHED_RECORDS")));
					bean.setHdfcUnmatchedAmount(amount.format(rset.getInt("UNMATCHED_AMOUNT")));
					bean.setHdfcReversal(total.format(rset.getInt("REVERSAL_CHECK")));
				}
				if(rset.getString("TYPE").equalsIgnoreCase("HSBC"))
				{
					bean.setHsbcTotal(total.format(rset.getInt("TOTAL_RECORDS")));
					bean.setHsbcMatched(total.format(rset.getInt("MATCHED_RECORDS")));
					bean.setHsbcNonMatched(total.format(rset.getInt("UNMATCHED_RECORDS")));
					bean.setHsbcUnmatchedAmount(amount.format(rset.getInt("UNMATCHED_AMOUNT")));
					bean.setHsbcReversal(total.format(rset.getInt("REVERSAL_CHECK")));
				}
				if(rset.getString("TYPE").equalsIgnoreCase("SCB"))
				{
					bean.setScbTotal(total.format(rset.getInt("TOTAL_RECORDS")));
					bean.setScbMatched(total.format(rset.getInt("MATCHED_RECORDS")));
					bean.setScbNonMatched(total.format(rset.getInt("UNMATCHED_RECORDS")));
					bean.setScbUnmatchedAmount(amount.format(rset.getInt("UNMATCHED_AMOUNT")));
					bean.setScbReversal(total.format(rset.getInt("REVERSAL_CHECK")));
				}
				if(rset.getString("TYPE").equalsIgnoreCase("RECEIPT"))
				{
					bean.setReceiptTotal(total.format(rset.getInt("TOTAL_RECORDS")));
					bean.setReceiptMatched(total.format(rset.getInt("MATCHED_RECORDS")));
					bean.setReceiptNonMatched(total.format(rset.getInt("UNMATCHED_RECORDS")));
					bean.setReceiptUnmatchedAmount(amount.format(rset.getInt("UNMATCHED_AMOUNT")));
					
				}
				return null;
			}
		});
		}catch(Exception e){
			LogManager.debug(e);
		}
	}

}
