package demo;
import java.util.HashMap;
import java.util.Map;
public class BeanDefinition {
    private String id;
    private String className;
    private Map<String, Object> properties;

    public BeanDefinition() {
        properties = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void addProperty(String name, Object value) {
        properties.put(name, value);
    }
}
