import org.example.model.Account;
import org.example.util.KnowledgeSessionHelper;
import org.example.util.OutputDisplay;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class TestLesson4 {
    static KieContainer kieContainer;
    KieSession statefulSession = null;

    @BeforeClass
    public static void beforeClass() {
        kieContainer = KnowledgeSessionHelper.createRuleBase();
    }

    @Before
    public void setup() {
        System.out.println("-----Before--------");
    }

    @After
    public void tearDown() {
        System.out.println("-----After---------");
    }

    @Test
    public void testRuleFlow() throws Exception{
        statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSessionForJBPM(kieContainer, "lesson4-session");
        OutputDisplay display = new OutputDisplay();
        statefulSession.setGlobal("showResult", display);
        Account account = new Account();
        statefulSession.insert(account);
        statefulSession.fireAllRules();
    }

}
