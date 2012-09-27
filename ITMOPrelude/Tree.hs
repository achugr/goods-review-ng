{-# LANGUAGE NoImplicitPrelude #-}
module ITMOPrelude.Tree where

import Prelude (Show,Read,error)
import ITMOPrelude.Primitive

data Tree a = Nil | Node a (Tree a) (Tree a) deriving (Show, Read)

emptyTree :: Tree a
emptyTree = Nil

newTree :: a -> Trea a
newTree x = Node x Nil Nil

treeInsertToRoot :: Tree a -> a -> Tree a
treeInsertToRoot Nil x = newTree x
treeInsertToRoot tree y = Node y tree Nil

treeInsertToTheLeft :: Tree a -> a -> Tree a
treeInsertToTheLeft Nil x = newTree x
treeInsertToTheLeft (Node _ left _) x = treeInsertToTheLeft left x

treeInsertToTheRight :: Tree a -> a -> Tree a
treeInsertToTheRight Nil x = newTree x
treeInsertToTheRight (Node _ _ right) x = treeInsertToTheRight right x

treeLeftTurn :: Tree a -> Tree a
treeLeftTurn (Node _ _ Nil) = error "Trying to make left turn while right child is Nil!"
treeLeftTurn (Node x left (Node y left1 right1)) = Node y (Node x left left1) right

treeRightTurn :: Tree a -> Tree a
treeRightTurn (Node _ Nil _) = error "Trying to make right turn while left child is Nil!"
treeRightTurn (Node x (Node y left1 right1) right) = Node y left $ Node x right1 right

map :: (a -> b) -> Tree a -> Tree b
map f Nil = Nil
map f (Node x left right) = Node (f x) (map f left) (map f right)

foldr :: (a -> b -> b) -> b -> Tree a -> b
foldr f z Nil = z
foldr f z (Node x Nil Nil) = f x z
foldr f z (Node x left Nil) = f x $ foldr f z left
foldr f z (Node x Nil right) = f x $ foldr f z right
foldr f z (Node x left right) = foldr f (f x (foldr f z right)) left