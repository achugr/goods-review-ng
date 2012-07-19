package ru.goodsreview.analyzer.util.sentence;

/**
 * Date: 17.07.12
 * Time: 01:00
 * Author:
 * Ilya Makeev
 * ilya.makeev@gmail.com
 */

public enum GrammarCase {
    IM {
        public String toString() {
            return "им";
        }
    },
    ROD {
        public String toString() {
            return "род";
        }
    },
    DAT {
        public String toString() {
            return "дат";
        }
    },
    VIN {
        public String toString() {
            return "вин";
        }
    },
    TVOR {
        public String toString() {
            return "твор";
        }
    },
    PRED {
        public String toString() {
            return "пр";
        }
    },
    PART {
        public String toString() {
            return "парт";
        }
    },
    MESTN {
        public String toString() {
            return "местн";
        }
    },
    ZVAT {
        public String toString() {
            return "зват";
        }
    }
}

