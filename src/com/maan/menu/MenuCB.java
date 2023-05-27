package com.maan.menu;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseCB;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;
import com.maan.login.LoginForm;

public class MenuCB extends CommonBaseCB {

	
	public List getMenu(final String loginId, final String userType)
			throws CommonBaseException {

		List result;
		LogManager.push("getMenu() method ");
		LogManager.logEnter();

		final MenuDAO menuDAO = (MenuDAOImpl) CommonDaoFactory.getDAO(MenuDAO.class.getName());
		result = menuDAO.getMenu(loginId, userType);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public List getAdminMenu(final String loginId) throws CommonBaseException {

		List result;
		LogManager.push("getAdminMenu() method ");
		LogManager.logEnter();

		
		final MenuDAO menuDAO = (MenuDAOImpl) CommonDaoFactory.getDAO(MenuDAO.class.getName());
		result = menuDAO.getAdminMenu(loginId);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	
	public HashMap getStateListCB () throws CommonBaseException{
		HashMap result;
		LogManager.push("GetStateListCB  method ");
		LogManager.logEnter();
		
		final MenuDAO menuDAO = (MenuDAOImpl) CommonDaoFactory.getDAO(MenuDAO.class.getName());
		result = menuDAO.getStateMaster();

		LogManager.logExit();
		LogManager.popRemove(); 
		return result;
	}
	
	public HashMap getStateListCB1 (final String loginID) throws CommonBaseException{
		HashMap result;
		LogManager.push("GetStateListCB1  method ");
		LogManager.logEnter();
		
		final MenuDAO menuDAO = (MenuDAOImpl) CommonDaoFactory.getDAO(MenuDAO.class.getName());
		result = menuDAO.getStateMaster1(loginID);

		LogManager.logExit();
		LogManager.popRemove(); 
		return result;
	}
	
	public HashMap getDistrictListCB(final String stateId) throws CommonBaseException{
		HashMap result;
		LogManager.push("GetDistrictListCB  method ");
		LogManager.logEnter();
		
		final MenuDAO menuDAO = (MenuDAOImpl) CommonDaoFactory.getDAO(MenuDAO.class.getName());
		result = menuDAO.getDistrictMaster(stateId);

		LogManager.logExit();
		LogManager.popRemove(); 
		return result;
	}
	
	public HashMap getDistrictListCB1(final String stateId, final String loginId) throws CommonBaseException{
		HashMap result;
		LogManager.push("GetDistrictListCB1  method ");
		LogManager.logEnter();
		
		final MenuDAO menuDAO = (MenuDAOImpl) CommonDaoFactory.getDAO(MenuDAO.class.getName());
		result = menuDAO.getDistrictMaster1(stateId,loginId);

		LogManager.logExit();
		LogManager.popRemove(); 
		return result;
	}
	
	public String getStateNameCB  (final String stateId) throws CommonBaseException{
		String  result;		
		LogManager.push("GetStateNameCB  method ");
		LogManager.logEnter();
		final MenuDAO menuDAO = (MenuDAOImpl) CommonDaoFactory.getDAO(MenuDAO.class.getName());
		result = menuDAO.getStateName(stateId);
		 
		LogManager.logExit();
		LogManager.popRemove();
		return result;
	}	
	
	public String getDistrictNameCB  (final String districtId, final String stateId) throws CommonBaseException
	{
		String  result;
		
		LogManager.push("GetDistrictNameCB  method ");
		LogManager.logEnter();
		
		final MenuDAO menuDAO = (MenuDAOImpl) CommonDaoFactory.getDAO(MenuDAO.class.getName());
		result = menuDAO.getDistrictName(districtId,stateId);
		 
		LogManager.logExit();
		LogManager.popRemove();
		return result;
	}
	
	
	
	
	public String[] sortGreater(int [] array,String namArr[])
	{
	int a,b;
	int temp;
	String nameTemp;
	String numTemp="";
	int sortTheNumbers = array.length-1;
	
	for (a = 0; a < sortTheNumbers; ++a)
	{
	for (b = 0; b < sortTheNumbers; ++b)
	if(array[b] < array[b + 1])
	{
	temp = array[b];
	nameTemp = namArr[b];
	array[b] = array[b + 1];
	namArr[b] = namArr[b + 1];
	array[b + 1] = temp;
	namArr[b + 1] = nameTemp;
	}
	}
	
	for(int i=0;i<array.length;i++)
	{
		numTemp=numTemp+array[i]+",";
	}
	numTemp=numTemp.substring(0, numTemp.length()-1);
	namArr[namArr.length-1]=numTemp;

	return namArr;
	}
	
}
