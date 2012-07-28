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
import ru.goodsreview.api.provider.ContentAPIProvider;

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
@ContextConfiguration("/content-api-provider-test-bean.xml")
public class CategoryGrabberTest {
    public static final Logger log = Logger.getLogger(CategoryGrabberTest.class);

    @Autowired
    private ContentAPIProvider contentApiProvider;

    @Test
    public void grabMainCategoriesListTest(){
        CategoryGrabber categoryGrabber = new CategoryGrabber(contentApiProvider);
        List<JSONObject> mainCategoriesList = categoryGrabber.grabMainCategoriesList();

        Assert.assertEquals(mainCategoriesList.size(), 23);

        String[] mainCategoriesNames = new String[]{"Авто, мото","Аптека","Бытовая техника",
                "Все для дома и дачи","Все для офиса","Досуг и развлечения","Животные и растения",
                "Книги","Компьютеры","Красота и здоровье","Мебель","Музыка и видеофильмы",
                "Оборудование","Одежда и обувь","Подарки, сувениры, цветы","Продукты, напитки, табак",
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
        Assert.assertTrue(mainCategoriesNamesFromJSON.containsAll(Arrays.asList(mainCategoriesNames)));
    }

    @Test
    public void grabChildCategoriesListTest(){
        CategoryGrabber categoryGrabber = new CategoryGrabber(contentApiProvider);
        List<JSONObject> childCategoriesList = categoryGrabber.grabChildCategoriesList();

        Set<String> childCategoriesNamesFromJSON = new HashSet<String>();

        for(JSONObject childCategory : childCategoriesList){
            try {
                childCategoriesNamesFromJSON.add(childCategory.getString("name"));
            } catch (JSONException e) {
                log.error("Error occurs when getting child category name");
                throw new RuntimeException();
            }
        }
        Assert.assertTrue(childCategoriesNamesFromJSON.containsAll(Arrays.asList(
                new String[]{"Шины", "Колесные диски", "Аксессуары", "Аудио- и видеотехника",
                        "Дача, сад и огород", "Освещение", "Кухонная посуда и принадлежности",
                        "Рукоделие и творчество", "Аксессуары", "Комплектующие", "Сетевое оборудование",
                        "Ноутбуки", "Музыкальные инструменты", "Музыка", "Видеофильмы", "Тренажеры",
                        "Туризм", "Охота и рыбалка", "Велосипеды", "Сотовые телефоны", "Радиотелефоны",
                        "Bluetooth-гарнитуры", "Проводные телефоны", "Аудиотехника", "Аксессуары", "Фото",
                        "Портативная аудиотехника", "Медицинские приборы и изделия", "Ортопедические изделия и обувь",
                        "Оптика", "Витамины, минералы, пищевые добавки", "Для кухни", "Климатическое оборудование",
                        "Для дома", "Для индивидуального ухода", "Оборудование для презентаций", "Оргтехника",
                        "Канцелярские принадлежности", "Аксессуары", "Мебель для малышей", "Матрасы",
                        "Готовые комплекты" , "Корпусная мебель", "Наручные часы", "Новогодние товары",
                        "Украшения и бижутерия", "Ювелирные изделия", "Сантехника", "Инструменты", "Электрика",
                        "Насосы бытовые", "Товары для малышей", "Велосипеды для малышей", "Игрушки и игровые комплексы",
                        "Для школы", "Системы охраны и сигнализации", "Оборудование для магазинов",
                        "Производственно-техническое оборудование", "Строительное оборудование", "Женская одежда",
                        "Обувь", "Мужская одежда", "Детская одежда", "Табачные изделия", "Чай, кофе, какао",
                        "Спортивное питание", "Детское питание", "Туры", "Авиабилеты", "Железнодорожные билеты",
                        "Обучение, семинары и тренинги", "Техническое обслуживание", "Маркетинг, реклама, PR",
                        "Юридические услуги"})));
    }
}
