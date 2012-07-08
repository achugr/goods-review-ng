package ru.goodsreview.api.provider;


import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import ru.goodsreview.api.request.builder.CategoryRequestBuilder;
import ru.goodsreview.api.request.builder.ModelRequestBuilder;

import java.util.HashMap;

import static org.mockito.Mockito.*;

/**
 * achugr, achugr@yandex-team.ru
 * 06.07.12
 */

/**
 * class for testing ContentAPIProvider
 */


// TODO Зачем в тестах ловить эксепшены совсем непонятно, есть в спринге тесты
/* @see EntityServiceTest
*
 */
public class ContentAPIProviderTest {
    private final static Logger log = Logger.getLogger(ContentAPIProviderTest.class);

    private final MockedContentAPIProvider mockedContentAPIProvider = new MockedContentAPIProvider();
    private final ContentAPIProvider contentAPIProvider = mockedContentAPIProvider.getContentAPIProvider();

//    это долбаная жесть, а не код, я знаю :)
//    мок - нормальная тема, и тут она вполне уместна, но я еще не очень умею юзать ее,
//    наверняка есть более красивые способы для такого случая..
//    самое дерьмо в том, что этот тест должен показывать как юзать ContentAPIProvider
//    a проще понять, просто посмотрев код класса, а не этого тупого юнит-теста

    @Test
    public void modelProviderTest() throws JSONException {
        ModelRequestBuilder modelRequestBuilder = new ModelRequestBuilder();
        {
//            тут я борща завернул, т.к. mock сравнивает хеши объектов приходится сначала делать запрос
            UrlRequest urlRequest = modelRequestBuilder.requestForModelById(7309504, new HashMap<String, String>());
//            а потом по его url забирать тот объект, на который замокали метод provide
            urlRequest = mockedContentAPIProvider.urlRequestByStringUrl(urlRequest.getUrl());
//            ну и делаем ассерт
            Assert.assertEquals(contentAPIProvider.provide(urlRequest).toString(),
                    new JSONObject("{\"model\":{\"vendor\":\"Lenovo\",\"isNew\":1,\"categoryId\":432460,\"link\":\"http://market.yandex.ru/model.xml?CMD=-RR=9,0,0,0-PF=1801946~EQ~sel~1871127-PF=2142398356~EQ~sel~276250007-VIS=70-CAT_ID=432460-EXC=1-PG=10\",\"children\":{\"models\":[{\"id\":7775437,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/Wi-Fi/Win 7 Starter)\"},{\"id\":7959660,\"name\":\"B570 (Pentium B940 2000 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/Win 7 HB)\"},{\"id\":7798597,\"name\":\"B570 (Pentium B940 2000 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7959661,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/Win 7 HB)\"},{\"id\":7798593,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7911602,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/Win 7 Starter)\"},{\"id\":7851393,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/NVIDIA GeForce 410M/Wi-Fi/DOS)\"},{\"id\":7845052,\"name\":\"B570 (Pentium B960 2200 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7959681,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/Intel GMA HD/Wi-Fi/DOS)\"},{\"id\":7959638,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/4096Mb/500Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7847080,\"name\":\"B570 (Pentium B960 2200 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7959663,\"name\":\"B570 (Core i3 2350M 2300 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/Win 7 HB 64)\"},{\"id\":7959904,\"name\":\"B570 (Pentium B960 2200 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/Win 7 HB)\"},{\"id\":7909940,\"name\":\"B570 (Pentium B960 2200 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/Wi-Fi/Bluetooth/Win 7 HB)\"},{\"id\":7959662,\"name\":\"B570 (Core i3 2350M 2300 Mhz/15.6\\\"/1366x768/4096Mb/500Gb/DVD-RW/Wi-Fi/Win 7 HB 64)\"},{\"id\":8311291,\"name\":\"B570 (Celeron B820 1700 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/Wi-Fi/Win 7 HB 64)\"},{\"id\":7851394,\"name\":\"B570 (Pentium B960 2200 Mhz/15.6\\\"/1366x768/4096Mb/500Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7899744,\"name\":\"B570 (Core i5 2450M 2500 Mhz/15.6\\\"/1366x768/4096Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/Win 7 HB)\"},{\"id\":8311265,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/Wi-Fi/Win 7 HB)\"},{\"id\":8311338,\"name\":\"B570 (Pentium B960 2200 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/Wi-Fi/Без ОС)\"},{\"id\":7808750,\"name\":\"B570 (Core i3 2350M 2300 Mhz/15.6\\\"/1366x768/4096Mb/320Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7900804,\"name\":\"B570 (Celeron B815 1600 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7774424,\"name\":\"B570 (Pentium B950 2100 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/Wi-Fi/Без ОС)\"},{\"id\":7711344,\"name\":\"B570 (Pentium B940 2000 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/Wi-Fi/Bluetooth/Win 7 HB)\"},{\"id\":7776210,\"name\":\"B570 (Pentium B950 2100 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/Wi-Fi/Win 7 HB)\"},{\"id\":7842586,\"name\":\"B570 (Core i3 2350M 2300 Mhz/15.6\\\"/1366x768/4096Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7808786,\"name\":\"B570 (Core i3 2350M 2300 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/Win 7 HB)\"},{\"id\":8235857,\"name\":\"B570 (Pentium B960 2200 Mhz/15.6\\\"/1366x768/4096Mb/500Gb/DVD-RW/Wi-Fi/Win 7 HB 64/черный)\"},{\"id\":7845045,\"name\":\"B570 (Core i3 2350M 2300 Mhz/15.6\\\"/1366x768/4096Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/Win 7 HB)\"},{\"id\":7808796,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/2048Mb/250Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7959680,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/4096Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7812165,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/2048Mb/250Gb/DVD-RW/Wi-Fi/Bluetooth/Win 7 Starter)\"},{\"id\":7798614,\"name\":\"B570 (Pentium B940 2000 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7703341,\"name\":\"B570 (Pentium B940 2000 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7798669,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7798610,\"name\":\"B570 (Pentium B950 2100 Mhz/15.6\\\"/1366x768/4096Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7851395,\"name\":\"B570 (Core i3 2350M 2300 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7798677,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7869564,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/4096Mb/320Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7776233,\"name\":\"B570 (Pentium B950 2100 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7775430,\"name\":\"B570 (Celeron B800 1500 Mhz/15.6\\\"/1366x768/3072Mb/320Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7721711,\"name\":\"B570 (Core i5 2410M 2300 Mhz/15.6\\\"/1366x768/3072Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/Win 7 HB)\"},{\"id\":7774468,\"name\":\"B570 (Core i3 2330M 2200 Mhz/15.6\\\"/1366x768/4096Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/Win 7 HB)\"},{\"id\":7802107,\"name\":\"B570 (Core i5 2430M 2400 Mhz/15.6\\\"/1366x768/4096Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7721710,\"name\":\"B570 (Core i5 2410M 2300 Mhz/15.6\\\"/1366x768/4096Mb/320Gb/DVD-RW/Wi-Fi/Bluetooth/Win 7 HB)\"},{\"id\":7711348,\"name\":\"B570 (Pentium B940 2000 Mhz/15.6\\\"/1366x768/3072Mb/320Gb/DVD-RW/Wi-Fi/Bluetooth/Win 7 HB)\"},{\"id\":7851397,\"name\":\"B570 (Core i3 2330M 2200 Mhz/15.6\\\"/1366x768/3072Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7851396,\"name\":\"B570 (Core i3 2330M 2200 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7711350,\"name\":\"B570 (Core i3 2310M 2100 Mhz/15.6\\\"/1366x768/2048Mb/320Gb/DVD-RW/Wi-Fi/Bluetooth/Win 7 HB)\"},{\"id\":7711343,\"name\":\"B570 (Core i3 2310M 2100 Mhz/15.6\\\"/1366x768/3072Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7721709,\"name\":\"B570 (Core i5 2410M 2300 Mhz/15.6\\\"/1366x768/4096Mb/500Gb/DVD-RW/Wi-Fi/Win 7 HB)\"},{\"id\":7330287,\"name\":\"B570 (Core i3 2310M 2100 Mhz/15.6\\\"/1366x768/3072Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/Win 7 HB)\"},{\"id\":7847087,\"name\":\"B570 (Pentium B950 2100 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7798620,\"name\":\"B570 (Pentium B950 2100 Mhz/15.6\\\"/1366x768/4096Mb/500Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7798627,\"name\":\"B570 (Pentium B950 2100 Mhz/15.6\\\"/1366x768/3072Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7798629,\"name\":\"B570 (Pentium B940 2000 Mhz/15.6\\\"/1366x768/3072Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7721708,\"name\":\"B570 (Core i5 2410M 2300 Mhz/15.6\\\"/1366x768/4096Mb/750Gb/DVD-RW/Wi-Fi/Bluetooth/Win 7 HB)\"},{\"id\":7798666,\"name\":\"B570 (Pentium B940 2000 Mhz/15.6\\\"/1366x768/3072Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/Win 7 HB)\"},{\"id\":7798707,\"name\":\"B570 (Core i3 2330M 2200 Mhz/15.6\\\"/1366x768/4096Mb/750Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7309506,\"name\":\"B570 (Core i5 2410M 2300 Mhz/15.6\\\"/1366x768/3072Mb/500Gb/DVD-RW/NVIDIA GeForce 410M/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7309512,\"name\":\"B570 (Core i3 2310M 2100 Mhz/15.6\\\"/1366x768/3072Mb/320Gb/DVD-RW/Wi-Fi/DOS)\"},{\"id\":7309517,\"name\":\"B570 (Core i3 2310M 2100 Mhz/15.6\\\"/1366x768/3072Mb/320Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7309518,\"name\":\"B570 (Core i3 2310M 2100 Mhz/15.6\\\"/1366x768/2048Mb/500Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"},{\"id\":7851392,\"name\":\"B570 (Pentium B950 2100 Mhz/15.6\\\"/1366x768/4096Mb/750Gb/DVD-RW/Wi-Fi/Bluetooth/DOS)\"}]},\"vendorId\":152981,\"reviewsCount\":99,\"id\":7309504,\"mainPhoto\":\"http://mdata.yandex.net/i?path=b0607001234_img_id5389724114025642023.jpg\",\"description\":\"Тип процессора \\u2014 Core i3 / Core i5 / Pentium / Celeron; Частота процессора \\u2014 1500...2500; Bluetooth (опционально); Вес \\u2014 2.35; Размер оперативной памяти \\u2014 2048...4096; Размер жесткого диска \\u2014 250...500; Размер экрана \\u2014 15.6; Чипсет графического контроллера \\u2014  / Intel GMA HD / NVIDIA GeForce 410M; Оптический привод \\u2014 DVD-RW; Wi-Fi\",\"isGroup\":true,\"name\":\"Lenovo B570\",\"prices\":{\"min\":10538,\"curName\":\"руб.\",\"max\":29584,\"curCode\":\"RUR\",\"avg\":16267},\"offersCount\":9731,\"rating\":4.38},\"contentType\":\"model\"}").toString());
        }
        {
//                    TODO if we need more tests
        }
    }

    @Test
    public void categoryProviderTest() throws JSONException {
        CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();
        {
            UrlRequest urlRequest = categoryRequestBuilder.requestForListOfCategories(new HashMap<String, String>());
            urlRequest = mockedContentAPIProvider.urlRequestByStringUrl(urlRequest.getUrl());
            Assert.assertEquals(contentAPIProvider.provide(urlRequest).toString(),
                    new JSONObject("{\"categories\":{\"total\":23,\"count\":10,\"page\":1,\"items\":[{\"id\":91597,\"childrenCount\":19,\"parentId\":90401,\"modelsNum\":62681,\"name\":\"Строительство и ремонт\",\"type\":\"nonguru\",\"offersNum\":1802728435},{\"id\":90402,\"childrenCount\":17,\"parentId\":90401,\"modelsNum\":84948,\"name\":\"Авто, мото\",\"type\":\"nonguru\",\"offersNum\":1505437250},{\"id\":7877999,\"childrenCount\":5,\"parentId\":90401,\"modelsNum\":0,\"name\":\"Одежда и обувь\",\"type\":\"nonguru\",\"offersNum\":1190506565},{\"id\":90829,\"childrenCount\":17,\"parentId\":90401,\"modelsNum\":0,\"name\":\"Книги\",\"type\":\"nonguru\",\"offersNum\":1163948884},{\"id\":90666,\"childrenCount\":12,\"parentId\":90401,\"modelsNum\":3862,\"name\":\"Все для дома и дачи\",\"type\":\"nonguru\",\"offersNum\":1101015143},{\"id\":91282,\"childrenCount\":31,\"parentId\":90401,\"modelsNum\":79838,\"name\":\"Подарки, сувениры, цветы\",\"type\":\"guru\",\"offersNum\":1039294553},{\"id\":198119,\"childrenCount\":16,\"parentId\":90401,\"modelsNum\":36252,\"name\":\"Электроника и Фото\",\"type\":\"nonguru\",\"offersNum\":812417009},{\"id\":198118,\"childrenCount\":4,\"parentId\":90401,\"modelsNum\":67627,\"name\":\"Бытовая техника\",\"type\":\"nonguru\",\"offersNum\":713644792},{\"id\":90764,\"childrenCount\":6,\"parentId\":90401,\"modelsNum\":3263,\"name\":\"Товары для детей\",\"type\":\"nonguru\",\"offersNum\":532952950},{\"id\":91157,\"childrenCount\":16,\"parentId\":90401,\"modelsNum\":0,\"name\":\"Красота и здоровье\",\"type\":\"nonguru\",\"offersNum\":478740043}]},\"contentType\":\"category\"}").toString());
        }
    }


}
