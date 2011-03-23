/**
 * 
 */
package module.siadap.presentationTier.renderers.providers;

import java.util.ArrayList;

import module.siadap.domain.SiadapProcess;
import module.siadap.domain.SiadapProcessStateEnum;
import module.workflow.activities.ActivityInformation;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

/**
 * @author João Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public class RevertStatesProvider implements DataProvider {

    @Override
    public Converter getConverter() {
	return null;
    }

    @Override
    public Object provide(Object source, Object currentValue) {
	ActivityInformation<SiadapProcess> activityInformation = (ActivityInformation<SiadapProcess>) source;
	ArrayList<SiadapProcessStateEnum> enums = new ArrayList<SiadapProcessStateEnum>();
	SiadapProcess siadapProcess = activityInformation.getProcess();
	if (siadapProcess.isProcessSealed()) {
	    enums.add(SiadapProcessStateEnum.NOT_SEALED);
	}
	if (siadapProcess.isSubmittedForEvalObjsConfirmation()) {
	    enums.add(SiadapProcessStateEnum.NOT_YET_SUBMITTED_FOR_ACK);
	}
	if (siadapProcess.isEvalObjectivesAcknowledged()) {
	    enums.add(SiadapProcessStateEnum.WAITING_EVAL_OBJ_ACK);
	}
	return enums;
    }

}
