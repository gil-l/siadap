package module.siadap.presentationTier.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.struts.annotations.Mapping;

import module.siadap.domain.Competence;
import module.siadap.domain.CompetenceType;
import module.siadap.domain.SiadapRootModule;
import module.siadap.domain.dto.CompetenceBean;
import module.siadap.domain.dto.CompetenceTypeBean;
import myorg.presentationTier.actions.ContextBaseAction;

@Mapping(path = "/competencesManagement")
public class CompetencesManagement extends ContextBaseAction {

    public ActionForward manageCompetences(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	request.setAttribute("competenceTypes", SiadapRootModule.getInstance().getCompetenceTypes());
	return forward(request, "/module/siadap/competences/manageCompetences.jsp");
    }

    public ActionForward prepareCompetenceTypeCreation(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {

	CompetenceTypeBean competenceTypeBean = new CompetenceTypeBean();
	request.setAttribute("bean", competenceTypeBean);

	return forward(request, "/module/siadap/competences/createCompetenceType.jsp");
    }

    public ActionForward createCompetenceType(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {

	CompetenceTypeBean competenceTypeBean = getRenderedObject("bean");
	CompetenceType.createNewCompetenceType(competenceTypeBean.getName());

	return manageCompetences(mapping, form, request, response);
    }

    public ActionForward prepareCompetenceCreation(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) throws Exception {

	CompetenceType competenceType = getDomainObject(request, "competenceTypeId");
	CompetenceBean competenceBean = new CompetenceBean(competenceType);

	request.setAttribute("bean", competenceBean);

	return forward(request, "/module/siadap/competences/createCompetence.jsp");
    }

    public ActionForward createCompetence(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {

	CompetenceBean competenceBean = getRenderedObject("bean");
	Competence.createNewCompetence(competenceBean.getCompetenceType(), competenceBean.getName(), competenceBean
		.getDescription());

	return manageCompetences(mapping, form, request, response);
    }
}
