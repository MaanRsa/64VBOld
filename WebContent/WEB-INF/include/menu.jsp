<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ page isELIgnored ="false" %>


<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="menuContent">
			<logic:notEmpty name="menu">
				<%
					String menuHead = "class=\"menuHead\"";
					String menuDisp = "class=\"menuDisp\" style='width:160px;'";
					String dir = "class=\"dir\" style='width:160px;'";
				%>
				<ul class="dropdown">
					<c:forEach var="main" items="${menu}">
						<li <%=menuHead%>>
							<a href="<c:out value="${main.url}" />"><c:out
									value="${main.content}" /> </a>

							<c:if test="${main.list!=null}">
								<ul>
									<c:forEach var="sub" items="${main.list}">
										<li <c:if test="${sub.list != null}"><%=dir%></c:if> 
											<c:if test="${sub.list == null}"><%=menuDisp%></c:if>>
											<a href="<c:out value="${sub.url}" />"><c:out
													value="${sub.content}" /></a>
											<c:if test="${sub.list!=null}">
												<ul>
													<c:forEach var="subsub" items="${sub.list}">
														<li <c:if test="${subsub.list != null}"><%=dir%></c:if>
															<c:if test="${subsub.list == null}"><%=menuDisp%></c:if>>
															<a href="<c:out value="${subsub.url}" />"><c:out
																	value="${subsub.content}" /></a>
															<c:if test="${subsub.list!=null}">
																<ul>
																	<c:forEach var="subsubsub" items="${subsub.list}">
																		<li
																			<c:if test="${subsubsub.list != null}"><%=dir%></c:if>
																			<c:if test="${subsubsub.list == null}"><%=menuDisp%></c:if>>
																			<a href="<c:out value="${subsubsub.url}" />"><c:out
																					value="${subsubsub.content}" /> </a>
																		</li>
																	</c:forEach>
																</ul>
															</c:if>
														</li>
													</c:forEach>
												</ul>
											</c:if>
										</li>
									</c:forEach>
								</ul>
							</c:if>
						</li>
					</c:forEach>
				</ul>
			</logic:notEmpty>
		</td>
		<td class="menuCenter">
			&nbsp;
		</td>
		<td class="menuUserName">
			&nbsp;
		</td>
	</tr>
</table>

