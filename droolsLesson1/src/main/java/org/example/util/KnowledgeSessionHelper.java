package org.example.util;

import org.jbpm.workflow.instance.node.RuleSetNodeInstance;
import org.kie.api.KieServices;
import org.kie.api.event.process.*;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

public class KnowledgeSessionHelper {

    public static KieContainer createRuleBase() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        return kieContainer;
    }

    public static StatelessKieSession getStatelessKnowledgeSession(KieContainer kieContainer, String sessionName) {
        StatelessKieSession session = kieContainer.newStatelessKieSession(sessionName);
        return session;
    }
    public static KieSession getStatefulKnowledgeSession(KieContainer kieContainer, String sessionName) {
        KieSession session = kieContainer.newKieSession(sessionName);
        return session;
    }

    public static KieSession getStatefulKnowledgeSessionWithCallback(KieContainer kieContainer, String sessionName) {
        KieSession session = getStatefulKnowledgeSession(kieContainer, sessionName);
        session.addEventListener(new RuleRuntimeEventListener() {
            @Override
            public void objectInserted(ObjectInsertedEvent objectInsertedEvent) {
                System.out.println("Object Inserted \n" +
                        objectInsertedEvent.getObject().toString());
            }

            @Override
            public void objectUpdated(ObjectUpdatedEvent objectUpdatedEvent) {
                System.out.println("Object was updated \n" +
                        "new Content \n" + objectUpdatedEvent.getObject().toString());
            }

            @Override
            public void objectDeleted(ObjectDeletedEvent objectDeletedEvent) {
                System.out.println("Object retracted \n" +
                        objectDeletedEvent.getOldObject().toString());
            }
        });

        session.addEventListener(new AgendaEventListener() {
            @Override
            public void matchCreated(MatchCreatedEvent matchCreatedEvent) {
                System.out.println("The rule " +
                        matchCreatedEvent.getMatch().getRule().getName() +
                        " can be fired in agenda");
            }

            @Override
            public void matchCancelled(MatchCancelledEvent matchCancelledEvent) {
                System.out.println("The rule " +
                        matchCancelledEvent.getMatch().getRule().getName() +
                        " can not be fired");
            }

            @Override
            public void beforeMatchFired(BeforeMatchFiredEvent beforeMatchFiredEvent) {
                System.out.println("The rule " +
                        beforeMatchFiredEvent.getMatch().getRule().getName() +
                        " will be fired");
            }

            @Override
            public void afterMatchFired(AfterMatchFiredEvent afterMatchFiredEvent) {
                System.out.println("The rule " +
                        afterMatchFiredEvent.getMatch().getRule().getName() +
                        " has be fired");
            }

            @Override
            public void agendaGroupPopped(AgendaGroupPoppedEvent agendaGroupPoppedEvent) {

            }

            @Override
            public void agendaGroupPushed(AgendaGroupPushedEvent agendaGroupPushedEvent) {

            }

            @Override
            public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent ruleFlowGroupActivatedEvent) {

            }

            @Override
            public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent ruleFlowGroupActivatedEvent) {

            }

            @Override
            public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {

            }

            @Override
            public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {

            }
        });

        return session;
    }

    public static KieSession getStatefulKnowledgeSessionForJBPM(
            KieContainer kieContainer, String sessionName) {
        KieSession session = getStatefulKnowledgeSessionWithCallback(kieContainer,sessionName);
        session.addEventListener(new ProcessEventListener() {

            @Override
            public void beforeVariableChanged(ProcessVariableChangedEvent arg0) {

            }

            @Override
            public void beforeProcessStarted(ProcessStartedEvent arg0) {
                System.out.println("Process Name "+arg0.getProcessInstance().getProcessName()+" has been started");


            }

            @Override
            public void beforeProcessCompleted(ProcessCompletedEvent arg0) {

            }

            @Override
            public void beforeNodeTriggered(ProcessNodeTriggeredEvent arg0) {

            }

            @Override
            public void beforeNodeLeft(ProcessNodeLeftEvent arg0) {
                if (arg0.getNodeInstance() instanceof RuleSetNodeInstance){
                    System.out.println("Node Name "+ arg0.getNodeInstance().getNodeName()+" has been left");
                }

            }

            @Override
            public void afterVariableChanged(ProcessVariableChangedEvent arg0) {


            }

            @Override
            public void afterProcessStarted(ProcessStartedEvent arg0) {

            }

            @Override
            public void afterProcessCompleted(ProcessCompletedEvent arg0) {
                System.out.println("Process Name "+arg0.getProcessInstance().getProcessName()+" has stopped");


            }

            @Override
            public void afterNodeTriggered(ProcessNodeTriggeredEvent arg0) {
                if (arg0.getNodeInstance() instanceof RuleSetNodeInstance){
                    System.out.println("Node Name "+ arg0.getNodeInstance().getNodeName()+" has been entered");
                }
            }

            @Override
            public void afterNodeLeft(ProcessNodeLeftEvent arg0) {
            }
        });
        return session;
    }


}
