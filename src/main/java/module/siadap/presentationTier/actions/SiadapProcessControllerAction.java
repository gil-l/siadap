/*
 * @(#)SiadapProcessControllerAction.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: Paulo Abrantes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the SIADAP Module.
 *
 *   The SIADAP Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version
 *   3 of the License, or (at your option) any later version.
 *
 *   The SIADAP Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the SIADAP Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.siadap.presentationTier.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.siadap.activities.ChangeCustomScheduleActivityInformation;
import module.siadap.activities.CreateObjectiveEvaluationActivityInformation;
import module.siadap.domain.exceptions.SiadapException;
import module.workflow.domain.WorkflowProcess;
import module.workflow.presentationTier.actions.ProcessManagement;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.fenixedu.bennu.struts.annotations.Mapping;
import org.fenixedu.bennu.struts.base.BaseAction;
import org.fenixedu.bennu.struts.portal.StrutsFunctionality;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;

@Mapping(path = "/siadapProcessController")
/**
 * 
 * @author João Antunes
 * @author Paulo Abrantes
 * 
 */
public class SiadapProcessControllerAction extends BaseAction {

    public ActionForward addNewIndicator(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        CreateObjectiveEvaluationActivityInformation information = getRenderedObject("activityBean");
        try {

            information.addNewIndicator();
        } catch (SiadapException ex) {
            addLocalizedMessage(request, ex.getLocalizedMessage());
        }
        RenderUtils.invalidateViewState();
        return new ProcessManagement().performActivityPostback(information, request);
    }

    public ActionForward addNewScheduleRepresentation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ChangeCustomScheduleActivityInformation information = getRenderedObject("activityBean");
        information.addCustomScheduleRepresentation();
        RenderUtils.invalidateViewState();
        return new ProcessManagement().performActivityPostback(information, request);
    }

    public ActionForward removeScheduleRepresentation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ChangeCustomScheduleActivityInformation information = getRenderedObject("activityBean");
        Integer removeIndex = Integer.valueOf(request.getParameter("removeIndex"));
        RenderUtils.invalidateViewState();

        information.removeCustomScheduleRepresentation(removeIndex);
        return new ProcessManagement().performActivityPostback(information, request);
    }

    public ActionForward removeIndicator(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        CreateObjectiveEvaluationActivityInformation information = getRenderedObject("activityBean");
        Integer removeIndex = Integer.valueOf(request.getParameter("removeIndex"));
        RenderUtils.invalidateViewState();

        information.removeIndicator(removeIndex);
        return new ProcessManagement().performActivityPostback(information, request);
    }

    protected <T extends WorkflowProcess> T getProcess(HttpServletRequest request) {
        return (T) getDomainObject(request, "processId");
    }
}
