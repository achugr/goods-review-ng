package ru.goodsreview.frontend;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONObject;
import sun.reflect.generics.tree.ArrayTypeSignature;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
public class Test {

    public static void main(String[] args) {
        final String [] thesises = " дисплей яркий, разрешение хорошее, батарея слабая, звук качественный, картинка качественная, звук хороший, экран неплохой, батарея слабая, экран чувствительный, экран приятный, батарея слабоватая, камеру слабенькую, цена доступная, аппарат хороший, комплектация скудная, звук хороший, батарея неплохая, комплект хороший, аппарат хороший, камера слабенькая, звук слабый, звук достаточный, корпус маркий, экран маркий, девайс отличный, железо хорошее, дизайн красивый, дисплей яркий, экран плохой, экран неплохой, девайс дешевый, ноута тише, звук тише, звука некачественного, батарея слабая, корпус хороший, клавиатура жуткая, аппарате уже, девайс хороший, батарея слабая, экран прочный, камера плохая, корпус маркий, корпус маркий, экран чувствительный, звук сильный, покрытие пластиковое, экран чувствительный, софта бесплатного, качество хорошее, начинка мощная, звук тихий, девайс маленький, дисплей яркий, доступ быстрый, корпус маркий, железо мощное, дисплей яркий, разрешение хорошее, модель хорошая, экран яркий, экран хороший, качество убогое".split(", ");
        final Set<String> thesisesSet = new HashSet<String>();
        Collections.addAll(thesisesSet, thesises);
        System.out.println(Arrays.toString(thesisesSet.toArray()));
        System.out.println(thesisesSet.size());
    }
}
