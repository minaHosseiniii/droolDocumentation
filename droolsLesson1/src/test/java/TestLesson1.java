import org.example.model.Account;
import org.example.model.AccountingPeriod;
import org.example.model.CashFlow;
import org.example.model.Customer;
import org.example.util.DateHelper;
import org.example.util.KnowledgeSessionHelper;
import org.example.util.OutputDisplay;
import org.junit.*;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;


public class TestLesson1 {
    static KieContainer kieContainer;
    KieSession stateFulSession = null;

    @BeforeClass
    public static void beforeClass() {
        kieContainer = KnowledgeSessionHelper.createRuleBase();
    }

    @Before
    public void setUp() {
        System.out.println("-----------Before-------------");
    }

    @After
    public void tearDown() {
        System.out.println("-----------After-----------");
    }

    @Test
    public void testRuleOneFactWithoutFact() {
        stateFulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        stateFulSession.fireAllRules();
    }

    @Test
    public void testRuleOneFactWithFact() {
        stateFulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        Account account = new Account();
        OutputDisplay display = new OutputDisplay();
        stateFulSession.setGlobal("showResult", display);
        stateFulSession.insert(account);
        stateFulSession.fireAllRules();
    }

    @Test
    public void testRuleOneFactAndUsageOfGlobal() {
        stateFulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        Customer c = new Customer();
        OutputDisplay display = new OutputDisplay();
        stateFulSession.setGlobal("showResult", display);
        stateFulSession.insert(c);
        stateFulSession.fireAllRules();
    }

    @Test
    public void testRuleOneFactAndUsageOfGlobalAndCallBack() {
        stateFulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        stateFulSession.addEventListener(new RuleRuntimeEventListener() {
            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println("Object inserted \n" + event.getObject().toString());
            }

            public void objectUpdated(ObjectUpdatedEvent event) {
                System.out.println("Object was updated \n" + "new Content \n" + event.getObject().toString());
            }

            public void objectDeleted(ObjectDeletedEvent event) {
                System.out.println("Object retracted \n" + event.getOldObject().toString());
            }
        });

        Customer customer = new Customer();
        customer.setName("Mina");
        FactHandle handle = stateFulSession.insert(customer);
        customer.setSurname("Hosseini");
        stateFulSession.update(handle, customer);
        stateFulSession.delete(handle);
        stateFulSession.fireAllRules();
    }

    @Test
    public void testFirstOneTwoFireAllRules() {
        stateFulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        Account account = new Account();
        OutputDisplay display = new OutputDisplay();
        stateFulSession.setGlobal("showResult", display);
        stateFulSession.insert(account);
        System.out.println("first fire all rules");
        stateFulSession.fireAllRules();
        System.out.println("second fire all rules");
        stateFulSession.fireAllRules();
    }

    @Test
    public void testFirstOneTwoFireAllRulesAndASetter() {
        stateFulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        Account account = new Account();
        OutputDisplay display = new OutputDisplay();
        stateFulSession.setGlobal("showResult", display);
        stateFulSession.insert(account);
        System.out.println("first fire all rules");
        stateFulSession.fireAllRules();
        account.setAccountno(22);
        System.out.println("second fire all rules");
        stateFulSession.fireAllRules();
    }

    @Test
    public void testFirstOneTwoFireAllRulesWithUpdateInBetween() {
        stateFulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        Account account = new Account();
        OutputDisplay display = new OutputDisplay();
        stateFulSession.setGlobal("showResult", display);
        FactHandle handle = stateFulSession.insert(account);
        System.out.println("first fire all rules");
        stateFulSession.fireAllRules();
        stateFulSession.update(handle, account);
        System.out.println("second fire all rules");
        stateFulSession.fireAllRules();
    }

    @Test
    public void testRuleOneFactThatInsertObject() {
        stateFulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        OutputDisplay display = new OutputDisplay();
        stateFulSession.setGlobal("showResult", display);
        stateFulSession.addEventListener(new RuleRuntimeEventListener() {
            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println("Object inserted \n" + event.getObject().toString());
            }

            public void objectUpdated(ObjectUpdatedEvent event) {
                System.out.println("Object was updated \n" + "new Content \n" + event.getObject().toString());
            }

            public void objectDeleted(ObjectDeletedEvent event) {
                System.out.println("Object retracted \n" + event.getOldObject().toString());
            }
        });

        CashFlow cashFlow = new CashFlow();
        FactHandle handle = stateFulSession.insert(cashFlow);
        stateFulSession.fireAllRules();
    }

    @Test
    public void testGetStatefulKnowledgeSessionWithCallback() {
        stateFulSession = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson1-session");
        OutputDisplay display = new OutputDisplay();
        stateFulSession.setGlobal("showResult", display);
        Account account = new Account();
        stateFulSession.insert(account);
        AccountingPeriod accountingPeriod = new AccountingPeriod();
        stateFulSession.insert(accountingPeriod);
        stateFulSession.fireAllRules();
    }

    @Test
    public void testTwoFacts() {
        stateFulSession = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson1-session");
        OutputDisplay outputDisplay = new OutputDisplay();
        stateFulSession.setGlobal("showResult", outputDisplay);
        Account account = new Account();
        account.setBalance(0);
        account.setAccountno(1);
        stateFulSession.insert(account);
        CashFlow cashFlow = new CashFlow();
        cashFlow.setAccountNo(1);
        cashFlow.setAmount(1000);
        cashFlow.setType(1);
        stateFulSession.insert(cashFlow);
        stateFulSession.fireAllRules();
        Assert.assertEquals(account.getBalance(), 1000, 0);

    }

    @Test
    public void testTwoFactsTwoCashFlowMovement() throws Exception {
        stateFulSession = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson1-session");
        OutputDisplay display = new OutputDisplay();
        stateFulSession.setGlobal("showResult", display);
        Account account = new Account();
        account.setAccountno(1);
        account.setBalance(0);
        stateFulSession.insert(account);

        CashFlow cashFlow = new CashFlow();
        cashFlow.setAccountNo(1);
        cashFlow.setAmount(1000);
        cashFlow.setMvtDate(DateHelper.getDate("2010-01-15"));
        cashFlow.setType(1);
        stateFulSession.insert(cashFlow);

        CashFlow cashFlow1 = new CashFlow();
        cashFlow1.setAccountNo(2);
        cashFlow1.setType(1);
        cashFlow1.setAmount(1000);
        cashFlow1.setMvtDate(DateHelper.getDate("2010-01-15"));
        stateFulSession.insert(cashFlow1);
        stateFulSession.fireAllRules();
        Assert.assertEquals(account.getBalance(), 1000, 0);
    }

    @Test
    public void testCalculateBalance() throws Exception {
        stateFulSession = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson1-session");
        OutputDisplay outputDisplay = new OutputDisplay();
        stateFulSession.setGlobal("showResult", outputDisplay);
        Account account = new Account();
        account.setAccountno(1);
        account.setBalance(0);
        stateFulSession.insert(account);

        CashFlow cashFlow = new CashFlow();
        cashFlow.setAccountNo(1);
        cashFlow.setType(1);
        cashFlow.setAmount(1000);
        cashFlow.setMvtDate(DateHelper.getDate("2016-01-15"));
        stateFulSession.insert(cashFlow);

        CashFlow cashFlow1 = new CashFlow();
        cashFlow1.setMvtDate(DateHelper.getDate("2016-02-15"));
        cashFlow1.setType(2);
        cashFlow1.setAccountNo(1);
        cashFlow1.setAmount(500);
        stateFulSession.insert(cashFlow1);

        AccountingPeriod accountingPeriod = new AccountingPeriod();
        accountingPeriod.setStartDate(DateHelper.getDate("2016-01-01"));
        accountingPeriod.setEndDate(DateHelper.getDate("2016-03-31"));
        stateFulSession.insert(accountingPeriod);
        stateFulSession.fireAllRules();
        Assert.assertEquals(account.getBalance(), 500, 0);
    }
}
