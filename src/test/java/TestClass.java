/**
 * Created by P088466 on 2016-07-10.
 */
public class TestClass {
    String name;
    Integer age;

    public TestClass(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return super.toString(); }

    public String getName() {
        String s;
        return name;
    }
}