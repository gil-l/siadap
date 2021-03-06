/*
 * @(#)SubmitForObjectivesAcknowledge.java
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
package module.siadap.activities;

import module.organization.domain.Person;
import module.siadap.domain.Siadap;
import module.siadap.domain.SiadapProcess;
import module.siadap.domain.exceptions.SiadapException;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.groups.UserGroup;
import org.fenixedu.messaging.domain.Message.MessageBuilder;
import org.fenixedu.messaging.domain.MessagingSystem;
import org.fenixedu.messaging.domain.Sender;
import org.joda.time.LocalDate;

/**
 * 
 * @author João Antunes
 * @author Luis Cruz
 * @author Paulo Abrantes
 * 
 */
public class SubmitForObjectivesAcknowledge extends WorkflowActivity<SiadapProcess, ActivityInformation<SiadapProcess>> {

    @Override
    public boolean isActive(SiadapProcess process, User user) {
        Siadap siadap = process.getSiadap();
        if (siadap.getEvaluator() == null) {
            return false;
        }
        return user == siadap.getEvaluator().getPerson().getUser() && siadap.isWithObjectivesFilled()
                && siadap.isCoherentOnTypeOfEvaluation() && siadap.hasAllEvaluationItemsValid()
                && siadap.getRequestedAcknowledgeDate() == null && siadap.getObjectivesAndCompetencesSealedDate() != null;
    }

    static protected void revertProcess(RevertStateActivityInformation activityInformation, boolean notifyIntervenients) {
        activityInformation.getProcess().getSiadap().setRequestedAcknowledgeDate(null);
        activityInformation.getProcess().getSiadap().setAutoEvaluationSealedDate(null);
        if (notifyIntervenients) {

            SiadapProcess siadapProcess = activityInformation.getProcess();
            Siadap siadap = siadapProcess.getSiadap();
            Person evaluatorPerson = null;
            try {
                evaluatorPerson = activityInformation.getProcess().getSiadap().getEvaluator().getPerson();
                siadapProcess.checkEmailExistenceImportAndWarnOnError(evaluatorPerson);
            } catch (Throwable ex) {
                if (evaluatorPerson != null) {
                    System.out.println("Could not get e-mail for evaluator " + evaluatorPerson.getName());
                } else {
                    System.out.println("Could not get e-mail for evaluator which has no Person object associated!");
                }
            }

            try {
                final Person evaluatedPerson = activityInformation.getProcess().getSiadap().getEvaluated();
                siadapProcess.checkEmailExistenceImportAndWarnOnError(evaluatedPerson);

                Integer year = activityInformation.getProcess().getSiadap().getYear();

                StringBuilder body =
                        new StringBuilder(
                                "O seu processo SIADAP de "
                                        + year
                                        + " foi excepcionalmente revertido para o estado anterior ao de ter sido submetido para seu conhecimento.\n Esta situação ocorre apenas em situações excepcionais, a justificação dada foi: '"
                                        + activityInformation.getJustification() + "'.\n");
                body.append("\nPara mais informações consulte https://dot.ist.utl.pt\n");
                body.append("\n\n---\n");
                body.append("Esta mensagem foi enviada por meio das Aplicações Centrais do IST.\n");

                final Sender sender = MessagingSystem.getInstance().getSystemSender();
                final MessageBuilder message =
                        sender.message("SIADAP - " + year
                                + " Reversão excepcional do estado do processo SIADAP para o estado anterior ao de ",
                                body.toString());
                message.to(UserGroup.of(evaluatedPerson.getUser()));
                message.cc(UserGroup.of(evaluatorPerson.getUser()));
                message.send();
            } catch (Throwable ex) {
                System.out.println("Unable to lookup email address for: "
                        + activityInformation.getProcess().getSiadap().getEvaluated().getUser().getUsername() + " Error: "
                        + ex.getMessage());
                siadapProcess.addWarningMessage("warning.message.could.not.send.email.now");
            }
        }

    }

    @Override
    protected void process(ActivityInformation<SiadapProcess> activityInformation) {
        if (!activityInformation.getProcess().getSiadap().hasAllEvaluationItemsValid()) {
            throw new SiadapException("activity.SealObjectivesAndCompetences.invalid.objectives");
        }
        activityInformation.getProcess().getSiadap().setRequestedAcknowledgeDate(new LocalDate());
        SiadapProcess siadapProcess = activityInformation.getProcess();
        Siadap siadap = siadapProcess.getSiadap();
        Person evaluatorPerson = null;
        try {
            evaluatorPerson = activityInformation.getProcess().getSiadap().getEvaluator().getPerson();
            siadapProcess.checkEmailExistenceImportAndWarnOnError(evaluatorPerson);

        } catch (Throwable ex) {
            if (evaluatorPerson != null) {
                System.out.println("Could not get e-mail for evaluator " + evaluatorPerson.getName());
            } else {
                System.out.println("Could not get e-mail for evaluator which has no Person object associated!");
            }
        }

        try {
            final Person evaluatedPerson = activityInformation.getProcess().getSiadap().getEvaluated();
            siadapProcess.checkEmailExistenceImportAndWarnOnError(evaluatedPerson);

            StringBuilder body =
                    new StringBuilder(
                            "Encontram-se disponiveis em https://dot.ist.utl.pt os objectivos e competências relativos ao ano de "
                                    + siadap.getYear() + ".\n");
            body.append("\nPara mais informações consulte https://dot.ist.utl.pt\n");
            body.append("\n\n---\n");
            body.append("Esta mensagem foi enviada por meio das Aplicações Centrais do IST.\n");

            final Sender sender = MessagingSystem.getInstance().getSystemSender();
            final MessageBuilder message =
                    sender.message("SIADAP - Tomada de conhecimento de objectivos e competências", body.toString());
            message.to(UserGroup.of(evaluatedPerson.getUser()));
            message.cc(UserGroup.of(evaluatorPerson.getUser()));
            message.send();
        } catch (Throwable ex) {
            System.out.println("Unable to lookup email address for: "
                    + activityInformation.getProcess().getSiadap().getEvaluated().getUser().getUsername() + " Error: "
                    + ex.getMessage());
            siadapProcess.addWarningMessage("warning.message.could.not.send.email.now");
        }

    }

    @Override
    public String getUsedBundle() {
        return "resources/SiadapResources";
    }
}
