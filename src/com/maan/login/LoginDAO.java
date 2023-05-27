package com.maan.login;

import java.util.List;

import com.maan.common.exception.CommonBaseException;

public interface LoginDAO {

	 List makeAuthendication(LoginVB loginVB)throws CommonBaseException;
	 boolean insertSessionInfo(LoginVB loginVB,final String sessionID)throws CommonBaseException;
	 String updateSessionInfo(LoginVB loginVB,final String sessionID)throws CommonBaseException;
	 String getInvestigationUserCode(final LoginForm loginForm) throws CommonBaseException;
}
