<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2>
	<bean:message key="label.harmonizationUnits" bundle="SIADAP_RESOURCES"/>
</h2>

<bean:define id="mode" name="mode"/>

<fr:view name="harmonizationUnits">
	<fr:schema type="module.siadap.domain.wrappers.UnitSiadapWrapper" bundle="SIADAP_RESOURCES">
			<fr:slot name="unit.partyName"  key="label.unit" bundle="ORGANIZATION_RESOURCES" />
			<fr:slot name="relevantEvaluationPercentage"/>
			<fr:slot name="excellencyEvaluationPercentage"/>
			<fr:slot name="harmonizationFinished" layout="boolean-icon"/>
	</fr:schema>
	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle2"/>
		<fr:property name="link(view)" value="<%= "/siadapManagement.do?method=harmonizationData&mode=" + mode.toString()%>"/>
		<fr:property name="bundle(view)" value="MYORG_RESOURCES"/>
		<fr:property name="key(view)" value="link.view"/>
		<fr:property name="param(view)" value="unit.externalId/unitId"/>
		<fr:property name="order(view)" value="1"/>
	</fr:layout>	
</fr:view>
<jsp:include page="/module/siadap/tracFeedBackSnip.jsp">	
   <jsp:param name="href" value="https://fenix-ashes.ist.utl.pt/trac/siadap/report/17" />	
</jsp:include>