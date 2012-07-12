package ru.goodsreview.core.util.db;

/**
 * @author daddy-bear
 *         Date: 23.06.12
 */
public final class DbUtil {
    
    private DbUtil() {}
    
    public static String createInSection(final int size) {
        if (size < 0) {
            throw new IllegalArgumentException("size must be >= 0");
        }
        if (size == 0) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("(?");
        for (int i = 0; i < size - 1; i++) {
            sb.append("?,");
        }
        sb.append(")");
        return sb.toString();
    }
}
