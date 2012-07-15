package ru.goodsreview.core.util;

/**
 * User: daddy-bear
 * Date: 18.06.12
 * Time: 18:12
 */
public class Pair<A, B> {
    public A first;
    public B second;

    public Pair(final A first, final B second) {
        this.first = first;
        this.second = second;
    }

    public static <A, B> Pair<A, B> of(final A first, final B second) {
        return new Pair<A, B>(first, second);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !(o instanceof Pair)) {
            return false;
        }

        Pair<?, ?> that = (Pair<? , ?>) o;
        return that.first.equals(this.first) && that.second.equals(this.second);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += 31 * ((first == null) ? 0 : first.hashCode());
        hash += 31 * ((second == null) ? 0 : second.hashCode());
        return hash;
    }
}
