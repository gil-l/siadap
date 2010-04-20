<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>

<bean:define id="processId" name="process" property="externalId" type="java.lang.String"/>
<bean:define id="name" name="information" property="activityName"/>

<div class="dinline forminline">
<fr:form action='<%= "/workflowProcessManagement.do?method=process&processId=" + processId + "&activity=" + name %>'>
	
		<fr:edit id="activityBean" name="information" visible="false"/>
		
		<bean:message key="label.objectives" bundle="SIADAP_RESOURCES"/>:
		<table class="tstyle2">
		<logic:iterate id="evaluation" name="information" property="process.siadap.objectiveEvaluations" type="module.siadap.domain.ObjectiveEvaluation">
			<tr>
				<th><fr:view name="evaluation" property="objective"/></th>
				<td>
					<fr:edit name="evaluation" slot="autoEvaluation"/>					
				</td>
			</tr>
		</logic:iterate>		
		</table>
	    
	 <bean:message key="label.competences" bundle="SIADAP_RESOURCES"/>:
	    <table class="tstyle2">
	    <logic:iterate id="competence" name="information" property="process.siadap.competenceEvaluations">
			<tr>
				<th><fr:view name="competence" property="competence.name"/></th>
				<td>
					<fr:edit name="competence" slot="autoEvaluation"/>					
				</td>
			</tr>
		</logic:iterate>	
		</table>	
	
	<html:submit styleClass="inputbutton"><bean:message key="renderers.form.submit.name" bundle="RENDERER_RESOURCES"/></html:submit>
</fr:form>
<fr:form action='<%= "/workflowProcessManagement.do?method=viewProcess&processId=" + processId %>'>
	<html:submit styleClass="inputbutton"><bean:message key="renderers.form.cancel.name" bundle="RENDERER_RESOURCES"/> </html:submit>
</fr:form>
</div>