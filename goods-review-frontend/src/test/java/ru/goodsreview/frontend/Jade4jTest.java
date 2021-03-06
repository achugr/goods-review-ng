package ru.goodsreview.frontend;

import de.neuland.jade4j.Jade4J;
import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;
import de.neuland.jade4j.template.TemplateLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         16.11.12
 */
public class Jade4jTest {

    @Test
    public void testIt() throws IOException, JSONException {
        JadeTemplate template = Jade4J.getTemplate("goods-review-frontend/src/main/static/jade/test.jade");

        System.out.println(new File(".").getAbsolutePath());
        Map<String, Object> model = new HashMap<String, Object>();
        JSONArray array = new JSONArray("[{\"feature\":\"батарейка\",\"opinions\":[{\"sentiment\":1,\"importance\":0,\"sentences\":[\"маленький, легкий, мощный, очень приятная цена, неплохая батарейка, белая клавиатура\"],\"opinion\":\"неплохая\"}]},{\"feature\":\"батарея\",\"opinions\":[{\"sentiment\":1,\"importance\":0,\"sentences\":[\"батарея вполне нормальная, я специально не тестил но на пару фильмов хватает точно а в режиме текста и того больше.\"],\"opinion\":\"нормальная\"}]},{\"feature\":\"видеокарта\",\"opinions\":[{\"sentiment\":2,\"importance\":0,\"sentences\":[\"Маленький, легкий, удобная сенсорная панель, хорошее качество звука на встроенных колонках, мощная видеокарта, двухгодичная гарантия\",\"И при этом за процессор нового поколения, мощную видеокарту, стереозвук меньше 24 тысячи рублей (сейчас он стоит около этой суммы) - здесь аналогов по железу просто нет.\"],\"opinion\":\"мощная\"}]},{\"feature\":\"дизайн\",\"opinions\":[{\"sentiment\":2,\"importance\":0,\"sentences\":[\"две видеокарты - встроенная 4500 от интел и дискретная от АМД (6800 баллов в 3dMark06), приятный дизайн, очень громкая аккустика но без хрипов, вифи, блютуз.\"],\"opinion\":\"приятный\"}]},{\"feature\":\"дисплей\",\"opinions\":[{\"sentiment\":1,\"importance\":0,\"sentences\":[\"Дисплей нормальный!\"],\"opinion\":\"нормальный\"}]},{\"feature\":\"доступ\",\"opinions\":[{\"sentiment\":0,\"importance\":0,\"sentences\":[\"Очень удобные сенсорные кнопки управления громкости, bluetooth 2.1, wi-fi, все как полагается, простой доступ к отсекам с оперативной памятью и жесткому диску - можно легко нарастить или заменить самому.\"],\"opinion\":\"простой\"}]},{\"feature\":\"звук\",\"opinions\":[{\"sentiment\":2,\"importance\":0,\"sentences\":[\"Яркая внешность (в версии с белой клавиатурой), замечательный звук.\"],\"opinion\":\"замечательный\"},{\"sentiment\":2,\"importance\":0,\"sentences\":[\"Малые углы обзора не заметны на 14\\\" экране, а если учесть хороший звук, то смотреть кино одно удовольствие.\"],\"opinion\":\"хороший\"},{\"sentiment\":1,\"importance\":0,\"sentences\":[\"2 микрофона для кристально чистого звука.\"],\"opinion\":\"чистый\"}]},{\"feature\":\"камера\",\"opinions\":[{\"sentiment\":2,\"importance\":0,\"sentences\":[],\"opinion\":\"отличная\"},{\"sentiment\":2,\"importance\":0,\"sentences\":[\"Камера превосходная в 10 раз превосходит acer crystal eve.\"],\"opinion\":\"превосходная\"}]},{\"feature\":\"качество\",\"opinions\":[{\"sentiment\":1,\"importance\":0,\"sentences\":[\"WIMAX ловит по Москве достаточно стабильно, классные встроенные колонки, не пищалки каеие-то, анормальные колонки, вот пишу отзыв и слушаю музыку в нормальном качестве.\"],\"opinion\":\"нормальное\"},{\"sentiment\":2,\"importance\":0,\"sentences\":[\"Маленький, легкий, удобная сенсорная панель, хорошее качество звука на встроенных колонках, мощная видеокарта, двухгодичная гарантия\"],\"opinion\":\"хорошее\"}]},{\"feature\":\"клавиатура\",\"opinions\":[{\"sentiment\":1,\"importance\":0,\"sentences\":[\"Практически идеальное решение (если не считать небольшое прогибание клавы слева), но сама клавиатура нормальная, без люфтов.\"],\"opinion\":\"нормальная\"},{\"sentiment\":2,\"importance\":0,\"sentences\":[\"Удобная клавиатура.\",\"удобная клавиатура с мягкими клавишами,кнопки наощупь находятся легко благодаря дополнительным промежуткам между клавишами.\"],\"opinion\":\"удобная\"}]},{\"feature\":\"компьютер\",\"opinions\":[{\"sentiment\":2,\"importance\":0,\"sentences\":[\"По производительности компьютер очень достойный, можно как поработать с офисом и в интернет, так и поиграть в самые современные игрушки.\"],\"opinion\":\"очень достойный\"}]},{\"feature\":\"корпус\",\"opinions\":[{\"sentiment\":0,\"importance\":0,\"sentences\":[\"для всевозможных задач, но в небольшом корпусе - рассматривались модели не более 14 дюймов.\"],\"opinion\":\"небольшой\"},{\"sentiment\":2,\"importance\":0,\"sentences\":[\"В TimelineX вроде бы отличная начинка, прекрасная цена, стильный корпус.\"],\"opinion\":\"стильный\"}]},{\"feature\":\"матрица\",\"opinions\":[{\"sentiment\":-2,\"importance\":0,\"sentences\":[\"слабоватая матрица \\r\\nклавиатура немного прогибается\"],\"opinion\":\"слабоватая\"},{\"sentiment\":0,\"importance\":0,\"sentences\":[\"Матрица средненькая, невысокий контраст, по вертикали угол обзора небольшой 15-20, по горизонтали такое ощущение что все 180, смотришь сбоку все четко видно.\"],\"opinion\":\"средненькая\"}]},{\"feature\":\"модель\",\"opinions\":[{\"sentiment\":2,\"importance\":0,\"sentences\":[],\"opinion\":\"очень симпатичная\"},{\"sentiment\":2,\"importance\":0,\"sentences\":[\"За свои деньги хорошая модель.\"],\"opinion\":\"хорошая\"}]},{\"feature\":\"монитор\",\"opinions\":[{\"sentiment\":0,\"importance\":0,\"sentences\":[],\"opinion\":\"большой\"}]},{\"feature\":\"начинка\",\"opinions\":[{\"sentiment\":2,\"importance\":0,\"sentences\":[\"В TimelineX вроде бы отличная начинка, прекрасная цена, стильный корпус.\"],\"opinion\":\"отличная\"}]},{\"feature\":\"ноут\",\"opinions\":[{\"sentiment\":-1,\"importance\":0,\"sentences\":[\"Хорошая вещь для тех кому нужен не большой, не тяжелый ноут с нормальной графикой (BFBC2 идет на средних настройках) - я для этого и брал чтобы в поездках можно было поиграть.\"],\"opinion\":\"не тяжелый\"},{\"sentiment\":1,\"importance\":0,\"sentences\":[\"самый практичный ноут из которых Я встречал\"],\"opinion\":\"практичный\"}]},{\"feature\":\"ноутбук\",\"opinions\":[{\"sentiment\":2,\"importance\":0,\"sentences\":[\"Классный ноутбук, выбирал по критериям, низкий вес, не очень маленький экран и мультимедийные возможности и средняя цена.\"],\"opinion\":\"классный\"},{\"sentiment\":2,\"importance\":0,\"sentences\":[\"Превосходный ноутбук.\"],\"opinion\":\"превосходный\"},{\"sentiment\":2,\"importance\":0,\"sentences\":[\"Хороший ноутбук и для игр, и для работы.\"],\"opinion\":\"хороший\"}]},{\"feature\":\"поддержка\",\"opinions\":[{\"sentiment\":2,\"importance\":0,\"sentences\":[],\"opinion\":\"хорошая\"}]},{\"feature\":\"решение\",\"opinions\":[{\"sentiment\":3,\"importance\":0,\"sentences\":[\"Практически идеальное решение (если не считать небольшое прогибание клавы слева), но сама клавиатура нормальная, без люфтов.\"],\"opinion\":\"идеальное\"},{\"sentiment\":2,\"importance\":0,\"sentences\":[\"Отличное решения для замены домашнего ПК и в первую очередь для студентов (для абсолютно полноценной замены диагональ должна быть 17 и более дюймов), а размер в 14 дюймов и относительно легкий вес для ноутов этого класса (всего 2.3 кг) позволят брать его везде с собой.\"],\"opinion\":\"отличное\"},{\"sentiment\":0,\"importance\":0,\"sentences\":[\"Рекламируемые в них динамики Altec Lansing расположены под корпусом и звучат \\\"в стол\\\" - странное решение.\"],\"opinion\":\"странное\"}]},{\"feature\":\"софт\",\"opinions\":[{\"sentiment\":2,\"importance\":0,\"sentences\":[\"- Сенсорные кнопки и SlideNav\\r\\n- Железо\\r\\n- Колонки JBL\\r\\n- \\\"Мягкая\\\" клава\\r\\n- Переключаемая графика\\r\\n- Качественный софт от Lenovo - Slidenav, One Key Home Theater, Energy Management\\r\\n- В отличии от Sony VAIO - в биосе можно включить функцию VT-x\"],\"opinion\":\"качественный\"}]},{\"feature\":\"цена\",\"opinions\":[{\"sentiment\":2,\"importance\":0,\"sentences\":[\"маленький, легкий, мощный, очень приятная цена, неплохая батарейка, белая клавиатура\"],\"opinion\":\"очень приятная\"},{\"sentiment\":2,\"importance\":0,\"sentences\":[\"В TimelineX вроде бы отличная начинка, прекрасная цена, стильный корпус.\"],\"opinion\":\"прекрасная\"}]},{\"feature\":\"экран\",\"opinions\":[{\"sentiment\":0,\"importance\":0,\"sentences\":[\"Классный ноутбук, выбирал по критериям, низкий вес, не очень маленький экран и мультимедийные возможности и средняя цена.\"],\"opinion\":\"очень маленький\"}]}]\n");
        model.put("array", array);
        String html = Jade4J.render(template, model);
        System.out.println(html);
    }
}
