<%@page import="pt.ist.fenixframework.FenixFramework"%>
<%@page import="module.organization.domain.Unit"%>
<%@page import="module.siadap.presentationTier.renderers.providers.ExceedingQuotaSuggestionProvider"%>
<%@page import="java.util.List"%>
<%@page import="module.siadap.domain.SiadapUniverse"%>
<%@page import="module.siadap.domain.wrappers.SiadapUniverseWrapper"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<bean:define id="unitName" name="unit" property="unit.presentationName" />
<bean:define id="unitId" name="unit" property="unit.externalId" />
<bean:define id="year" name="unit" property="year" />
<bean:define id="siadapUniverseWrappers" name="siadapUniverseWrappers" />


<h2>
	<bean:message key="label.addSugestionToUnit" bundle="SIADAP_RESOURCES" arg0="<%=unitName.toString()%>" />
</h2>
<br/>
<%
//let's make sure that we know if we have a way to add people or not
boolean hasPeopleToSuggest = false;
if (ExceedingQuotaSuggestionProvider.willProviderReturnResults((Unit) FenixFramework.getDomainObject((String)unitId), (Integer)year))
    hasPeopleToSuggest = true;
request.setAttribute("hasPeopleToSuggest", hasPeopleToSuggest);
    
%>

<%-- Error messages: --%>
<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>
<%-- *END* Error messages *END* --%>

<p><html:link page="<%="/siadapManagement.do?method=viewUnitHarmonizationData&year=" + year.toString() + "&unitId=" + unitId.toString() %>">
	<bean:message key="label.harmonization.QuotaSuggestionInterface.backToHarmonization" bundle="SIADAP_RESOURCES"/> 
</html:link></p>
<br/>

 <logic:equal value="true" name="unit" property="harmonizationActive">
 	<%
 		boolean allEmpty = true;
 		for (SiadapUniverseWrapper siadapUniverseWrapper : ((List<SiadapUniverseWrapper>)siadapUniverseWrappers))
 		{
 		    if (!siadapUniverseWrapper.getSiadapUniverseForSuggestions().isEmpty())
 				{allEmpty = false; break;}
 		}
 		request.setAttribute("allUniversesEmpty", allEmpty);
 	%>
 	<h3><bean:message key="label.harmonization.QuotaSuggestionInterface.ListSuggestions" bundle="SIADAP_RESOURCES"/>:</h3>
 	<logic:equal name="allUniversesEmpty" value="true">
	<p><i><bean:message key="label.exceedingQuota.no.suggestions" bundle="SIADAP_RESOURCES"/></i></p>
 	</logic:equal>
 	<logic:equal name="allUniversesEmpty" value="false">
 	<%-- <strong><bean:message bundle="SIADAP_RESOURCES" key="label.harmonization.QuotaSuggestionInterface.fillingInstructions"/></strong> --%>
	<fr:form action="<%="/siadapManagement.do?method=editExceedingQuotaSuggestion&year=" + year.toString() + "&unitId=" + unitId.toString() %>"> 
		<fr:edit id="siadapUniverseWrappersList" name="siadapUniverseWrappers" visible="false"/>
    	<logic:iterate id="siadapUniverseWrapper" name="siadapUniverseWrappers">

<%-- Title --%>
	<logic:notEmpty name="siadapUniverseWrapper" property="siadapUniverseForSuggestions">
		<strong><bean:message key="<%=((module.siadap.domain.wrappers.SiadapUniverseWrapper)siadapUniverseWrapper).getUniverseTitleQuotaSuggestionKey() %>" bundle="SIADAP_RESOURCES"/></strong>
	</logic:notEmpty>

 	<fr:edit name="siadapUniverseWrapper" property="siadapUniverseForSuggestions" nested="true">
		<fr:schema type="module.siadap.domain.wrappers.SiadapSuggestionBean" bundle="SIADAP_RESOURCES">
			<fr:slot name="personWrapper.person.name" key="label.evaluated" bundle="SIADAP_RESOURCES" readOnly="true" />
			<fr:slot name="personWrapper.person.user.username" key="label.login.username" bundle="MYORG_RESOURCES" readOnly="true" />
			<% 
			SiadapUniverseWrapper siadapUWrapper = (SiadapUniverseWrapper) siadapUniverseWrapper;
			if (siadapUWrapper.getSiadapUniverseEnum().equals(SiadapUniverse.SIADAP2))
			{
			    %>
			<fr:slot name="personWrapper.totalEvaluationScoringSiadap2" layout="null-as-label" key="label.totalEvaluationScoring" readOnly="true">
				<fr:property name="subLayout" value="" />
			</fr:slot>
			<fr:slot name="personWrapper.totalQualitativeEvaluationScoringSiadap2" layout="null-as-label" key="label.totalQualitativeEvaluationScoring" readOnly="true">
				<fr:property name="subLayout" value="" />
			</fr:slot>
			<%
			} else {
			%>
			<fr:slot name="personWrapper.totalEvaluationScoringSiadap3" layout="null-as-label" key="label.totalEvaluationScoring" readOnly="true"> 
				<fr:property name="subLayout" value="" />
			</fr:slot>
			<fr:slot name="personWrapper.totalQualitativeEvaluationScoringSiadap3" layout="null-as-label" key="label.totalQualitativeEvaluationScoring" readOnly="true">
				<fr:property name="subLayout" value="" />
			</fr:slot>
			<% } %>
			
			<fr:slot name="exceedingQuotaPriorityNumber" key="label.harmonization.exceedingQuotaPriorityNumber" bundle="SIADAP_RESOURCES" readOnly="true"/>
			<fr:slot name="type" readOnly="true"/>
		</fr:schema>
		<fr:layout name="tabular-row">
			<fr:property name="classes" value="tstyle2" />
			<fr:property name="columnClasses" value="aleft,aleft,," />
		<%-- 	<fr:property name="link(create)" value="/siadapManagement.do?method=createNewSiadapProcess" />
			<fr:property name="bundle(create)" value="MYORG_RESOURCES" />
			<fr:property name="key(create)" value="link.create" />
			<fr:property name="param(create)" value="personWrapper.person.externalId/personId" />
			<fr:property name="order(create)" value="1" />
			<fr:property name="visibleIf(create)" value="personWrapper.currentUserAbleToCreateProcess" /> --%>

			<fr:property name="link(viewProcess)" value="/workflowProcessManagement.do?method=viewProcess" />
			<fr:property name="bundle(viewProcess)" value="MYORG_RESOURCES" />
			<fr:property name="key(viewProcess)" value="link.view" />
			<fr:property name="param(viewProcess)" value="personWrapper.siadap.process.externalId/processId" />
			<fr:property name="order(viewProcess)" value="1" />
 			<fr:property name="visibleIf(viewProcess)" value="personWrapper.accessibleToCurrentUser" />
 			
 			<logic:equal value="false" name="unit" property="harmonizationFinished">
				<fr:property name="link(removeSuggestion)" value="<%="/siadapManagement.do?method=removeExceedingQuotaProposal&year="+year+"&unitId="+unitId%>" />
				<fr:property name="bundle(removeSuggestion)" value="SIADAP_RESOURCES" />
				<fr:property name="key(removeSuggestion)" value="link.remove.ExceedingQuotaProposal" />
				<fr:property name="param(removeSuggestion)" value="proposal.externalId/proposalId" />
				<fr:property name="order(removeSuggestion)" value="2" />
			</logic:equal>

<%--
			<fr:property name="sortParameter" value="sortByQuotas" />
			<fr:property name="sortUrl" value="<%= "/siadapManagement.do?method=viewUnitHarmonizationData&unitId=" + unitId + "&year=" + year.toString()%>" />
			<fr:property name="sortBy" value="<%= request.getParameter("sortByQuotas") == null ? "person.partyName=asc" : request.getParameter("sortByQuotas") %>" />
 --%>
		</fr:layout>
	</fr:edit>

 </logic:iterate>
	<%--		<html:submit styleClass="inputbutton">
				<bean:message key="label.save" bundle="SIADAP_RESOURCES" />
			</html:submit>--%>
	</fr:form> 
</logic:equal>
<logic:equal value="false" name="unit" property="harmonizationActive">
	<p><i><bean:message key="label.exceedingQuota.harmonization.not.active" bundle="SIADAP_RESOURCES"/></i></p>
</logic:equal>
</logic:equal>
	


<br/>
<%-- Adds suggestions TODO: make this outside of this method so that the data that we insert above is not lost with submissions of this form --%>
<logic:equal name="hasPeopleToSuggest" value="false">
	<p><i><bean:message key="label.exceedingQuota.no.one.to.add.exceedingQuotaTo" bundle="SIADAP_RESOURCES"/></i></p>
</logic:equal>

<logic:equal value="false" name="unit" property="harmonizationFinished">
	<logic:equal name="hasPeopleToSuggest" value="true">
		<fr:edit name="bean" id="bean"
			action='<%="/siadapManagement.do?unitId=" + unitId + "&method=addExceedingQuotaSuggestion&year=" + year.toString()%>'>
			<fr:schema
				type="module.siadap.domain.wrappers.SiadapSuggestionBean"
				bundle="SIADAP_RESOURCES">
				<fr:slot name="autoCompletePerson" layout="autoComplete" key="label.person"
					bundle="ORGANIZATION_RESOURCES">
					<fr:property name="labelField" value="partyName.content" />
					<fr:property name="format" value="\${presentationName}" />
					<fr:property name="minChars" value="3" />
					<fr:property name="args"
						value="<%="provider=module.siadap.presentationTier.renderers.providers.ExceedingQuotaSuggestionProvider,unitId="+unitId+",year="+year%>" />
					<fr:property name="size" value="60" />
					<fr:validator
						name="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredAutoCompleteSelectionValidator">
						<fr:property name="message" value="label.pleaseSelectOne.person" />
						<fr:property name="bundle" value="ORGANIZATION_RESOURCES" />
						<fr:property name="key" value="true" />
					</fr:validator>
				</fr:slot>
				<fr:slot name="type" required="true">
					<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				</fr:slot>
			</fr:schema>
			<fr:destination name="input"
				path='<%="/siadapManagement.do?unitId=" + unitId + "&method=prepareAddExceedingQuotaSuggestion&year=" + year.toString()%>' />
			<fr:destination name="cancel"
				path='<%="/siadapManagement.do?unitId=" + unitId + "&method=viewUnitHarmonizationData&year=" + year.toString()%>' />
		</fr:edit>
	</logic:equal>
</logic:equal>
<jsp:include page="/module/siadap/tracFeedBackSnip.jsp">
	<jsp:param name="href" value="https://fenix-ashes.ist.utl.pt/trac/siadap/report/12" />
</jsp:include>
