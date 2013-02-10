package ru.goodsreview.frontend.view.jade;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         17.11.12
 */
public class TemplatePathsHolder {

    private static final String TEST_PAGE_TEMPLATE = "test.jade";
    private static final String MAIN_PAGE_TEMPLATE = "mainPage.jade";
    private static final String SEARCH_RESULT_PAGE_TEMPLATE = "searchResult.jade";
    private static final String MODEL_PAGE_TEMPLATE = "modelPage.jade";
    private static final String ABOUT_PAGE_TEMPLATE = "about.jade";
    private static final String CONTACTS_PAGE_TEMPLATE = "contacts.jade";

    private static String TEMPLATES_DIR;

    private static String makePath(final String templateName) {
        return TEMPLATES_DIR + templateName;
    }

    public static String getAboutPageTemplate() {
        return makePath(ABOUT_PAGE_TEMPLATE);
    }

    public static String getContactsPageTemplate() {
        return makePath(CONTACTS_PAGE_TEMPLATE);
    }

    public static String getTestPageTemplate() {
        return makePath(TEST_PAGE_TEMPLATE);
    }

    public static String getMainPageTemplate() {
        return makePath(MAIN_PAGE_TEMPLATE);
    }

    public static String getSearchResultPageTemplate() {
        return makePath(SEARCH_RESULT_PAGE_TEMPLATE);
    }

    public static String getModelPageTemplate() {
        return makePath(MODEL_PAGE_TEMPLATE);
    }

    private TemplatePathsHolder() {
    }

    public static class TemplatePathsController {
        public void setTemplatesDir(final String templatesDir) {
            TemplatePathsHolder.TEMPLATES_DIR = templatesDir;
        }
    }
}
