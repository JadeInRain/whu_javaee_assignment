package demo;
import java.util.Map;
import java.util.HashMap;

public class MiniApplicationContext {
    private Map<String, Object> beans;

    public MiniApplicationContext(String s) {
        beans = new HashMap<>();
    }

    public void createBean(BeanDefinition beanDefinition) {
        try {
            String className = beanDefinition.getClassName();
            Class<?> beanClass = Class.forName(className);
            Object beanInstance = beanClass.newInstance();
            beans.put(beanDefinition.getId(), beanInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String beanName) {
        return beans.get(beanName);
    }
}

