package ru.goodsreview.core.util;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 22:38
 *
 * you can use this only in unix like systems
 */
public class Md5Helper {
    private final static Logger log = Logger.getLogger(Md5Helper.class);

    private final static MessageDigest DIGEST;

    static {
        try {
            DIGEST = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hash(final byte[] data) {
        final BigInteger hash = new BigInteger(1, DIGEST.digest(data));
        return hash.toString(16);
    }

    public static String hash(final String data) {
        return hash(data.getBytes());
    }

    public static String hash(final JSONObject jsonObject) {
        final Set<Pair<String, String>> attrSet = new TreeSet<Pair<String, String>>(NAME_VALUE_PAIR_COMPARATOR);
        new JSONWalker(jsonObject) {
            @Override
            protected void visit(final Pair<String, String> nameToValue) {
                log.debug("name = " + nameToValue.first + " value = " + nameToValue.second);
                attrSet.add(nameToValue);
            }
        }.walk();

        final StringBuilder result = new StringBuilder();
        for (final Pair<String, String> attr: attrSet) {
            result.append(attr.first).append("#").append(attr.second).append("#");
        }

        log.debug(result);
        return hash(result.toString());
    }

    private static final Comparator<Pair<String, String>> NAME_VALUE_PAIR_COMPARATOR = new Comparator<Pair<String, String>>() {
        @Override
        public int compare(final Pair<String, String> p1, final Pair<String, String> p2) {
            int result = p1.first.compareTo(p2.first);
            result = (result == 0) ? result : p1.second.compareTo(p2.second);
            return result;
        }
    };

    private static abstract class JSONWalker {

        private final JSONObject jsonObject;

        public JSONWalker(final JSONObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        protected abstract void visit(final Pair<String, String> nameToValue);

        public final void walk() {
            try {
                walk(jsonObject);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        private void walk(final JSONObject node) throws JSONException {
            for (final String name : new IteratorWrapper<String>(node.keys())) {
                processNameValue(name, node.get(name));
            }
        }

        private void processNameValue(final String nodeName, final Object nodeValue) throws JSONException {
            if (nodeValue instanceof JSONObject) {
                walk((JSONObject) nodeValue);
            } else if (nodeValue instanceof JSONArray) {
                final JSONArray jsonArray = (JSONArray) nodeValue;
                for (int i = 0; i < jsonArray.length(); i++) {
                    final Object arrayNode = jsonArray.get(i);
                    if (arrayNode instanceof JSONObject) {
                        walk((JSONObject) arrayNode);
                    } else if (arrayNode instanceof JSONArray) {
                        processNameValue(nodeName, arrayNode);
                    } else {
                        visit(Pair.of(nodeName, arrayNode.toString()));
                    }
                }
            } else {
                visit(Pair.of(nodeName, nodeValue.toString()));
            }
        }
    }
}