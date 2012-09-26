package ru.goodsreview.api.grabber;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 26.07.12
 * Time: 21:18
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/grabber-test-bean.xml")
public class CategoryGrabberTest {
    public static final Logger log = Logger.getLogger(CategoryGrabberTest.class);

    @Autowired
    private CategoryGrabber categoryGrabber;

    @Test
    public void paramTest(){
        Assert.assertNotNull(categoryGrabber.getContentApiProvider());
        Assert.assertNotNull(categoryGrabber.getGrabberBatch());
    }

    @Test
    public void grabMainCategoriesTest(){
        List<JSONObject> mainCategoriesList = categoryGrabber.grabMainCategories();

        Assert.assertEquals(mainCategoriesList.size(), 23);

        String[] mainCategoriesNames = new String[]{"Авто, мото","Аптека","Бытовая техника",
                "Все для дома и дачи","Все для офиса","Досуг и развлечения","Животные и растения",
                "Книги","Компьютеры","Красота и здоровье","Мебель","Музыка и видеофильмы",
                "Оборудование","Одежда, обувь и аксессуары","Подарки, сувениры, цветы","Продукты, напитки, табак",
                "Путешествия, туризм","Спортивные товары","Строительство и ремонт","Телефоны",
                "Товары для детей","Услуги","Электроника и Фото"};

        Set<String> mainCategoriesNamesFromJSON = new HashSet<String>();

        for(JSONObject category : mainCategoriesList){
            try {
                mainCategoriesNamesFromJSON.add(category.getString("name"));
            } catch (JSONException e) {
                log.error("Error occurs when getting category name");
                throw new RuntimeException();
            }
        }
        for(String name : mainCategoriesNamesFromJSON){
            log.debug(name);
        }
        Assert.assertTrue(mainCategoriesNamesFromJSON.containsAll(Arrays.asList(mainCategoriesNames)));
    }

    @Test
    public void grabSpecificCategoriesTest(){
        List<JSONObject> childCategoriesList = categoryGrabber.grabCategories("Компьютеры");

        for(JSONObject childCategory : childCategoriesList){
            try {
                log.debug(childCategory.getString("name"));
            } catch (JSONException e) {
                log.error("No such key \"name\" in json object " + childCategory.toString());
            }
        }
    }

    @Test
    public void grabAllChildCategoriesListTest(){
        List<JSONObject> childCategoriesList = categoryGrabber.grabAllCategories();
        Set<String> childCategoriesNamesFromJSON = new HashSet<String>();

        for(JSONObject childCategory : childCategoriesList){
            try {
                String name = childCategory.getString("name");
                childCategoriesNamesFromJSON.add(name);
            } catch (JSONException e) {
                log.error("No such key \"name\" in json object " + childCategory.toString());
            }
        }

        String[] someChildCategories = new String[]{"Шины", "Колесные диски", "Аксессуары", "Аудио- и видеотехника",
                "Дача, сад и огород", "Освещение", "Кухонная посуда и принадлежности",
                "Рукоделие и творчество", "Аксессуары", "Комплектующие", "Сетевое оборудование",
                "Ноутбуки", "Музыкальные инструменты", "Музыка", "Видеофильмы", "Тренажеры",
                "Туризм", "Охота и рыбалка", "Велосипеды", "Сотовые телефоны", "Радиотелефоны",
                "Bluetooth-гарнитуры", "Проводные телефоны", "Аудиотехника", "Аксессуары", "Фото",
                "Портативная аудиотехника", "Медицинские приборы и изделия", "Ортопедические изделия и обувь",
                "Оптика", "Витамины, минералы, пищевые добавки", "Для кухни", "Климатическое оборудование",
                "Для дома", "Для индивидуального ухода", "Оборудование для презентаций", "Оргтехника",
                "Канцелярские принадлежности", "Мебель для малышей", "Матрасы", "Готовые комплекты" ,
                "Корпусная мебель", "Наручные часы", "Новогодние товары", "Украшения и бижутерия",
                 "Ювелирные изделия", "Сантехника", "Инструменты", "Электрика", "Юридические услуги",
                "Насосы бытовые", "Товары для малышей", "Велосипеды для малышей", "Игрушки и игровые комплексы",
                "Для школы", "Системы охраны и сигнализации", "Оборудование для магазинов",
                "Производственно-техническое оборудование", "Строительное оборудование", "Женская одежда",
                "Обувь", "Мужская одежда", "Детская одежда", "Табачные изделия", "Чай, кофе, какао",
                "Спортивное питание", "Детское питание", "Туры", "Авиабилеты", "Железнодорожные билеты",
                "Обучение, семинары и тренинги", "Техническое обслуживание", "Маркетинг, реклама, PR"
        };
        //Assert.assertNotNull(childCategoriesNamesFromJSON);
        //Assert.assertTrue(childCategoriesNamesFromJSON.containsAll(Arrays.asList(someChildCategories)));
    }
}
