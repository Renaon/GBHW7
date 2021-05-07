import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Starter {
    public static void start(Class s) {
        List<Method> methods= new ArrayList<>();
        Method[] clasmeth = s.getDeclaredMethods();

        for (Method m: clasmeth){
            if(m.isAnnotationPresent(Test.class)){
                methods.add(m);
            }
        }
        methods.sort(Comparator.comparingInt((Method i) -> i.getAnnotation(Test.class).priority()).reversed());

        for(Method m : clasmeth){
            if(m.isAnnotationPresent(BeforeSuite.class)){
                if(methods.size() > 0 && methods.get(0).isAnnotationPresent(BeforeSuite.class)){
                    throw new RuntimeException("@BeforeSuite annotation method > 1");
                }
                methods.add(0, m);
            }
        }
        
        for(Method m: clasmeth){
            if(m.isAnnotationPresent(AfterSuite.class)){
                if(methods.size() > 0 && methods.get(methods.size()-1).isAnnotationPresent(AfterSuite.class)){
                    throw new RuntimeException("@AfterSuite annotation method > 1");
                }
                methods.add(m);
            }
        }
        try {
            Object c = s.newInstance();
            for(Method m: methods){
                try{
                    m.invoke(c);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
