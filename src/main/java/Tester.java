public class Tester {
    private Object RuntimeException;

    @BeforeSuite
    public void begin(){
        System.out.println("It is BeforeSuite");
    }


    @AfterSuite
    public void end(){
        System.out.println("It is AfterSuite");
    }

    @Test
    public void test0(){
        System.out.println("Test0");
    }

    @Test(priority = 4)
    public void test1(){
        System.out.println("Test1");
    }
}
