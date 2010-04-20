package module.siadap.domain;

import module.siadap.domain.scoring.IScoring;

public class ObjectiveEvaluation extends ObjectiveEvaluation_Base {

    public ObjectiveEvaluation(Siadap siadap, String objective, String measurementIndicator, String superationCriteria) {
	super();
	setObjective(objective);
	setMeasurementIndicator(measurementIndicator);
	setSuperationCriteria(superationCriteria);
	setSiadap(siadap);
	setFromVersion(siadap.getCurrentObjectiveVersion());
	setUntilVersion(null);
    }

    private ObjectiveEvaluation(Siadap siadap, String objective, String measurementIndicator, String superationCriteria,
	    String justification) {
	this(siadap, objective, measurementIndicator, superationCriteria);
	setJustificationForModification(justification);
    }

    public boolean isValidForVersion(Integer version) {
	Integer untilVersion = getUntilVersion();
	return getFromVersion() >= version && (untilVersion == null || untilVersion <= version);
    }

    public ObjectiveEvaluation edit(String objective, String measurementIndicator, String superationCriteria,
	    String editionJustification) {
	Siadap siadap = getSiadap();
	Integer currentObjectiveVersion = siadap.getCurrentObjectiveVersion();
	int newVersion = currentObjectiveVersion + 1;
	setUntilVersion(currentObjectiveVersion);
	siadap.setCurrentObjectiveVersion(newVersion);
	return new ObjectiveEvaluation(siadap, objective, measurementIndicator, superationCriteria, editionJustification);
    }

    @Override
    public IScoring getItemAutoEvaluation() {
	return getAutoEvaluation();
    }

    @Override
    public IScoring getItemEvaluation() {
	return getEvaluation();
    }
}