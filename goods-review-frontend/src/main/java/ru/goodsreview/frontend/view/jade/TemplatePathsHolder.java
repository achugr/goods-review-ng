package ru.goodsreview.frontend.view.jade;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         17.11.12
 */
public class TemplatePathsHolder {

    private static final String TEST_PAGE_TEMPLATE = "test.jade";

    private static String TEMPLATES_DIR;

    private static String makePath(final String templateName) {
        return TEMPLATES_DIR + templateName;
    }

    public static String getTestPageTemplate() {
        return makePath(TEST_PAGE_TEMPLATE);
    }

    private TemplatePathsHolder() {}

    public static class TemplatePathsController {
        public void setTemplatesDir(final String templatesDir) {
            TemplatePathsHolder.TEMPLATES_DIR = templatesDir;
        }
    }
}
