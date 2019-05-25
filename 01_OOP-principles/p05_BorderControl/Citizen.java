package p05_BorderControl;

public class Citizen implements Inhabitant{
    private String name;
    private int age;
    private String id;

    public Citizen(String name, int age, String id) {
        setName(name);
        setAge(age);
        setId(id);
     }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
