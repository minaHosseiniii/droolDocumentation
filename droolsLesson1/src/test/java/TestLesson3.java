import org.example.model.*;
import org.example.repositories.services.CustomerService;
import org.example.util.DateHelper;
import org.example.util.KnowledgeSessionHelper;
import org.example.util.OutputDisplay;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import java.text.ParseException;

public class TestLesson3 {
    static KieContainer kieContainer;
    KieSession statefulSession = null;

    @BeforeClass
    public static void beforeClass() {
        kieContainer = KnowledgeSessionHelper.createRuleBase();
    }

    @Before
    public void setup() {
        System.out.println("--------Before----------");
    }

    @After
    public void tearDown() {
        System.out.println("------------- After -----------");
    }

    @Test
    public void testInConstraint() {
        statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson3-session");
        OutputDisplay display = new OutputDisplay();
        statefulSession.setGlobal("showResult", display);
        CashFlow cashFlow = new CashFlow();
        cashFlow.setType(1);
        statefulSession.insert(cashFlow);
        statefulSession.fireAllRules();
    }

    @Test
    public void testNestedAccessor() {
        statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson3-session");
        OutputDisplay outputDisplay = new OutputDisplay();
        statefulSession.setGlobal("showResult", outputDisplay);
        Customer customer = new Customer();
        customer.setName("Mina");
        PrivateAccount privateAccount = new PrivateAccount();
        privateAccount.setOwner(customer);
        statefulSession.insert(privateAccount);
        statefulSession.fireAllRules();
    }

    @Test
    public void testInOrFact() {
        statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson3-session");
        OutputDisplay display = new OutputDisplay();
        statefulSession.setGlobal("showResult", display);
        Customer customer = new Customer();
        customer.setCountry("US");
        statefulSession.insert(customer);
        PrivateAccount pAccount = new PrivateAccount();
        pAccount.setOwner(customer);
        statefulSession.insert(pAccount);
        statefulSession.fireAllRules();
    }

    @Test
    public void testNotCondition() {
        statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson3-session");
        AccountingPeriod accountingPeriod = new AccountingPeriod();
        OutputDisplay display = new OutputDisplay();
        statefulSession.setGlobal("showResult", display);
        statefulSession.insert(accountingPeriod);
        statefulSession.fireAllRules();
    }

    @Test
    public void testExistsCondition() {
        statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson3-session");
        OutputDisplay display = new OutputDisplay();
        statefulSession.setGlobal("showResult", display);
        Account account = new Account();
        statefulSession.insert(account);
        statefulSession.fireAllRules();
    }

    @Test
    public void testForALl() {
        statefulSession = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson3-session");
        OutputDisplay display = new OutputDisplay();
        statefulSession.setGlobal("showResult", display);
        Account a = new Account();
        a.setAccountno(1);
        a.setBalance(0);
        statefulSession.insert(a);
        CashFlow cash1 = new CashFlow();
        cash1.setAccountNo(1);


        statefulSession.insert(cash1);
        CashFlow cash2 = new CashFlow();
        cash2.setAccountNo(1);

        statefulSession.insert(cash2);
        Account a2 = new Account();
        a2.setAccountno(2);
        a2.setBalance(0);
        statefulSession.insert(a2);
        CashFlow cash3 = new CashFlow();
        cash3.setAccountNo(2);
        statefulSession.insert(cash3);
        statefulSession.fireAllRules();
    }

    @Test
    public void testFromLHS() {
        statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson3-session");
        OutputDisplay display = new OutputDisplay();
        statefulSession.setGlobal("showResult", display);
        statefulSession.setGlobal("customerService", new CustomerService());
        Customer customer = new Customer("Maya", "Boomer", "IR");
        statefulSession.insert(customer);
        statefulSession.fireAllRules();
    }


    @Test
    public void testCollecting() throws Exception {
        statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer,
                "lesson3-session");
        OutputDisplay display = new OutputDisplay();
        statefulSession.setGlobal("showResult", display);
        Account a = new Account();
        a.setAccountno(1);
        a.setBalance(0);
        statefulSession.insert(a);
        statefulSession.insert(new CashFlow(DateHelper.getDate("2010-01-15"), 1000, CashFlow.CREDIT, 1));
        statefulSession.insert(new CashFlow(DateHelper.getDate("2010-02-15"), 500, CashFlow.DEBIT, 1));
        statefulSession.insert(new CashFlow(DateHelper.getDate("2010-04-15"), 1000, CashFlow.CREDIT, 1));
        statefulSession
                .insert(new AccountingPeriod(DateHelper.getDate("2010-01-01"), DateHelper.getDate("2010-12-29")));
        statefulSession.fireAllRules();
    }

    @Test
    public void testAccumulate() throws ParseException {
        statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson3-session");
        OutputDisplay display = new OutputDisplay();
        statefulSession.setGlobal("showResult", display);
        statefulSession.insert(new Account(1,0));
        FactHandle handle = statefulSession.insert(new CashFlow(DateHelper.getDate("2010-01-04"), 1000, 1, 1));
        statefulSession.insert(new CashFlow(DateHelper.getDate("2010-02-15"),500,2,1));
        statefulSession.insert(new CashFlow(DateHelper.getDate("2010-04-15"),1000,1,1));
        statefulSession.insert(new AccountingPeriod(DateHelper.getDate("2010-01-01"),DateHelper.getDate("2010-12-31")));
        statefulSession.fireAllRules();
        statefulSession.delete(handle);
        statefulSession.fireAllRules();
    }
}
