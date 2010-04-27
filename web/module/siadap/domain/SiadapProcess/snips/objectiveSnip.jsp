<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<bean:define id="objectiveEvaluation" name="objectiveEvaluation"/>
<bean:define id="index" name="index"/>

<bean:define id="objectiveOID" name="objectiveEvaluation" property="externalId" type="java.lang.String"/>

<div>
<table class="tstyle2 width100pc">
	<wf:isActive processName="process" activityName="EditObjectiveEvaluation" scope="request">
	<tr>
		<td colspan="5">
				<wf:activityLink id="<%= "edit-" + objectiveOID %>" processName="process" activityName="EditObjectiveEvaluation" scope="request" paramName0="evaluation" paramValue0="<%= objectiveOID %>">
						<bean:message key="link.edit" bundle="MYORG_RESOURCES"/>
				</wf:activityLink>			
		</td>
	</tr>
	</wf:isActive>	
	
	<tr>
		<th rowspan="4" valign="middle" style="width: 3%">
			<fr:view name="index"/>
		</th>
		<th style="width: 10%"> <bean:message key="label.objective" bundle="SIADAP_RESOURCES"/> </th>
		<td style="width: 57%" class="aleft"> <fr:view name="objectiveEvaluation" property="objective"/>	</td>
		<th style="width: 15%">
			<strong><bean:message key="label.autoEvaluation" bundle="SIADAP_RESOURCES"/></strong>
		</th>
		<th  style="width: 15%"> 
		<strong>	<bean:message key="label.evaluation" bundle="SIADAP_RESOURCES"/></strong>
		</th>
	</tr>
	<tr>
		<th> <bean:message key="label.measurementIndicator" bundle="SIADAP_RESOURCES"/> </th>
		<td class="aleft"> <fr:view name="objectiveEvaluation" property="measurementIndicator"/>	</td>
		<td rowspan="3" valign="middle">
			<fr:view name="objectiveEvaluation" property="autoEvaluation" type="module.siadap.domain.scoring.SiadapObjectivesEvaluation"/>
		</td>
		<td rowspan="3" valign="middle">
			<fr:view name="objectiveEvaluation" property="evaluation" type="module.siadap.domain.scoring.SiadapObjectivesEvaluation"/>
		</td> 
	</tr>
	<tr>
		<th> <bean:message key="label.superationCriteria" bundle="SIADAP_RESOURCES"/> </th>
		<td class="aleft"> <fr:view name="objectiveEvaluation" property="superationCriteria"/>	</td>
	</tr>
	<tr>
		<th> <bean:message key="label.type" bundle="SIADAP_RESOURCES"/> </th>
		<td class="aleft"> <fr:view name="objectiveEvaluation" property="type" type="module.siadap.domain.SiadapEvaluationObjectivesType"/>	</td>
	</tr>
</table>
</div>