{-# LANGUAGE NoImplicitPrelude #-}
module ITMOPrelude.Primitive where

import Prelude (Show,Read,error,print)

---------------------------------------------
-- Синтаксис лямбда-выражений

-- Эквивалентные определения
example1 x  = x
example1'   = \x -> x
example1''  = let y = \x -> x in y
example1''' = y where
    y = \x -> x

-- Снова эквивалентные определения
example2 x y  = x %+ y
example2' x   = \y -> x %+ y
example2''    = \x -> \y -> x %+ y
example2'''   = \x y -> x %+ y
example2''''  = let z = \x y -> x %+ y in z
example2''''' = z where
    z x = \y -> x %+ y

-- Зацикленное выражение
undefined = undefined

-- Ниже следует реализовать все термы, состоящие из undefined заглушки.
-- Любые термы можно переписывать (natEq и natLt --- хорошие кандидаты).

-------------------------------------------
-- Примитивные типы

-- Тип с единственным элементом
data Unit = Unit deriving (Show,Read)

-- Пара, произведение
data Pair a b = Pair { fst :: a, snd :: b } deriving (Show,Read)

-- Вариант, копроизведение
data Either a b = Left a | Right b deriving (Show,Read)

-- Частый частный случай, изоморфно Either Unit a
data Maybe a = Nothing | Just a deriving (Show,Read)

-- Частый частный случай, изоморфно Either Unit Unit
data Bool = False | True deriving (Show,Read)

-- Следует отметить, что встроенный if с этим Bool использовать нельзя,
-- зато case всегда работает.

-- Ну или можно реализовать свой if
if' True a b = a
if' False a b = b

-- Трихотомия. Замечательный тип, показывающий результат сравнения
data Tri = LT | EQ | GT deriving (Show,Read)

-------------------------------------------
-- Булевы значения

-- Логическое "НЕ"
not :: Bool -> Bool
not True = False
not False = True

infixr 3 &&
-- Логическое "И"
(&&) :: Bool -> Bool -> Bool
True  && x = x
False && _ = False

infixr 2 ||
-- Логическое "ИЛИ"
(||) :: Bool -> Bool -> Bool
True  || _ = True
False || x = x

-------------------------------------------
-- Натуральные числа

data Nat = Zero | Succ Nat deriving (Show,Read)

natZero = Zero     -- 0
natOne = Succ Zero -- 1

dec :: Nat -> Nat
dec (Succ n) = n

-- Сравнивает два натуральных числа
natCmp :: Nat -> Nat -> Tri
natCmp first second = if' (natEq first second) EQ (if' (natLt first second) LT GT)

-- n совпадает с m 
natEq :: Nat -> Nat -> Bool
natEq Zero     Zero     = True
natEq Zero     (Succ _) = False
natEq (Succ _) Zero     = False
natEq (Succ n) (Succ m) = natEq n m

-- n меньше m
natLt :: Nat -> Nat -> Bool
natLt Zero     Zero     = False
natLt Zero     (Succ m) = True
natLt (Succ n) Zero     = False
natLt (Succ n) (Succ m) = natLt n m

infixl 6 +.
-- Сложение для натуральных чисел
(+.) :: Nat -> Nat -> Nat
Zero     +. m = m
(Succ n) +. m = Succ (n +. m)

infixl 6 -.
-- Вычитание для натуральных чисел
(-.) :: Nat -> Nat -> Nat
n -. Zero = n
Zero -. _ = error "Trying to deduct greater natural value from zero"
(Succ n) -. m = case compare of LT -> error "Trying to deduct greater natural value from smaller"
                                EQ -> Zero
                                GT -> Succ (n -. m)
                    where compare = natCmp (Succ n) m

infixl 7 *.
-- Умножение для натуральных чисел
(*.) :: Nat -> Nat -> Nat
Zero     *. m = Zero
(Succ n) *. m = m +. (n *. m)

-- Целое и остаток от деления n на m
natDivMod :: Nat -> Nat -> Pair Nat Nat
natDivMod n m = case compare of LT -> Pair Zero n
                                EQ -> Pair (Succ Zero) Zero
                                GT -> Pair (Succ (fst (natDivMod (n -. m) m))) (snd (natDivMod (n -. m) m))
                    where compare = natCmp n m

natDiv n = fst . natDivMod n -- Целое
natMod n = snd . natDivMod n -- Остаток

-- Поиск GCD алгоритмом Евклида (должен занимать 2 (вычислителельная часть) + 1 (тип) строчки)
gcd :: Nat -> Nat -> Nat
gcd a Zero = a
gcd a b = gcd b (natMod a b)

-------------------------------------------
-- Целые числа

-- Требуется, чтобы представление каждого числа было единственным
data Int = Negative Nat | IntZero | Positive Nat deriving (Show,Read)

intZero   = IntZero   -- 0
intOne    = Positive Zero    -- 1
intNegOne = Negative Zero -- -1

-- n -> - n
intNeg :: Int -> Int
intNeg (Negative n) = (Positive n)
intNeg IntZero = IntZero
intNeg (Positive n) = (Negative n)

posNat :: Nat -> Int
posNat Zero = IntZero
posNat n = Positive $ dec n

negNat :: Nat -> Int
negNat Zero = IntZero
negNat n = Negative $ dec n

-- Дальше также как для натуральных
intCmp :: Int -> Int -> Tri
intCmp (Negative n) (Negative m) = natCmp m n
intCmp (Negative n) _ = LT
intCmp IntZero (Negative n) = GT
intCmp IntZero IntZero = EQ
intCmp IntZero (Positive n) = LT
intCmp (Positive n) (Positive m) = natCmp n m
intCmp (Positive n) _ = GT

intEq :: Int -> Int -> Bool
intEq n m = case (intCmp n m) of LT -> False
                                 EQ -> True
                                 GT -> False

intLt :: Int -> Int -> Bool
intLt n m = case (intCmp n m) of LT -> True
                                 EQ -> False
                                 GT -> False

infixl 6 .+., .-.
-- У меня это единственный страшный терм во всём файле
(.+.) :: Int -> Int -> Int
n .+. IntZero = n
IntZero .+. m = m
(Negative n) .+. (Negative m) = Negative (Succ (n +. m))
(Negative n) .+. (Positive m) = case compare of LT -> (Positive (m -. Succ n))
                                                EQ -> IntZero
                                                GT -> (Negative (n -. Succ m))
                                    where compare = natCmp n m
(Positive n) .+. (Negative m) = case compare of LT -> (Negative (m -. Succ n))
                                                EQ -> IntZero
                                                GT -> (Positive (n -. Succ m))
                                    where compare = natCmp n m
(Positive n) .+. (Positive m) = Positive (Succ (n +. m))

(.-.) :: Int -> Int -> Int
n .-. m = n .+. (intNeg m)

infixl 7 .*.
(.*.) :: Int -> Int -> Int
n .*. IntZero = IntZero
IntZero .*. m = IntZero
(Negative n) .*. (Negative m) = posNat $ (Succ n) *. (Succ m)
(Negative n) .*. (Positive m) = negNat $ (Succ n) *. (Succ m)
(Positive m) .*. (Negative n) = negNat $ (Succ n) *. (Succ m)
(Positive n) .*. (Positive m) = posNat $ (Succ n) *. (Succ m)

-------------------------------------------
-- Рациональные числа

data Rat = Rat Int Nat deriving (Show,Read)

ratNeg :: Rat -> Rat
ratNeg (Rat x y) = Rat (intNeg x) y

-- У рациональных ещё есть обратные элементы
ratInv :: Rat -> Rat
ratInv (Rat (Negative n) m) = (Rat (negNat m) (Succ n))
ratInv (Rat IntZero _) = error "Trying to get inversed from zero"
ratInv (Rat (Positive n) m) = (Rat (posNat m) (Succ n))

-- Дальше как обычно
ratCmp :: Rat -> Rat -> Tri
ratCmp (Rat n1 m1) (Rat n2 m2) = intCmp (n1 .*. (posNat m2)) (n2 .*. (posNat m1))

ratEq :: Rat -> Rat -> Bool
ratEq n m = case (ratCmp n m) of LT -> False
                                 EQ -> True
                                 GT -> False

ratLt :: Rat -> Rat -> Bool
ratLt n m = case (ratCmp n m) of LT -> True
                                 EQ -> False
                                 GT -> False

infixl 7 %+, %-
(%+) :: Rat -> Rat -> Rat
(Rat n1 m1) %+ (Rat n2 m2) = Rat ((n1 .*. (posNat m2)) .+. (n2 .*. (posNat m1))) (m1 *. m2)

(%-) :: Rat -> Rat -> Rat
n %- m = n %+ (ratNeg m)

infixl 7 %*, %/
(%*) :: Rat -> Rat -> Rat
(Rat n1 m1) %* (Rat n2 m2) = Rat (n1 .*. n2) (m1 *. m2)

(%/) :: Rat -> Rat -> Rat
n %/ m = n %* (ratInv m)

-------------------------------------------
-- Операции над функциями.
-- Определены здесь, но использовать можно и выше

infixr 9 .
f . g = \ x -> f (g x)

infixr 0 $
f $ x = f x

-- Эквивалентные определения
example3   a b c = gcd a (gcd b c)
example3'  a b c = gcd a $ gcd b c
example3'' a b c = ($) (gcd a) (gcd b c)

-- И ещё эквивалентные определения
example4  a b x = (gcd a (gcd b x))
example4' a b = gcd a . gcd b