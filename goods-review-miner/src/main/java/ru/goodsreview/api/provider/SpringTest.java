package ru.goodsreview.api.provider;

/**
 * achugr, achugr@yandex-team.ru
 * 08.07.12
 */
public class SpringTest {
    private String name;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static void main(String[] args) {
        SpringTest springTest = new SpringTest();
        System.out.println(springTest.getName());
    }
}
