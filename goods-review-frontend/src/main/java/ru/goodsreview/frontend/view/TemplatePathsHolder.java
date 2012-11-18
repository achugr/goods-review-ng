package ru.goodsreview.frontend.view;

/**
 * @author Dmitry Batkovich <daddy-bear@yandex-team.ru>
 *         <p/>
 *         бандл для хранения путей к шаблонам
 */
public class TemplatePathsHolder {
    private static final String CATEGORY_PAGE_TEMPLATE = "category.vm";

    private static final String MAIN_PAGE_TEMPLATE = "main.vm";

    private static final String PRODUCT_PAGE_TEMPLATE = "product.vm";
    
    private static final String SEARCH_PAGE_TEMPLATE = "search.vm";

    private static String TEMPLATES_DIR;

    private static String makePath(final String templateName) {
        return TEMPLATES_DIR + templateName;
    }

    public static String getProductTemplatePath() {
        return makePath(PRODUCT_PAGE_TEMPLATE);
    }

    public static String getCategoryTemplatePath() {
        return makePath(CATEGORY_PAGE_TEMPLATE);
    }

    public static String getMainPageTemplatePath() {
        return makePath(MAIN_PAGE_TEMPLATE);
    }
    
    public static String getSearchPageTemplatePath() {
        return makePath(SEARCH_PAGE_TEMPLATE);
    }

    private TemplatePathsHolder() {}

    public static class TemplatePathsController {
        public void setTemplatesDir(final String templatesDir) {
            TemplatePathsHolder.TEMPLATES_DIR = templatesDir;
        }
    }

}
