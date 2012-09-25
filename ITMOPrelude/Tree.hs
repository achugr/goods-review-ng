data Tree a = Nil | Node a (Tree a) (Tree a) deriving (Show, Read)

emptyTree :: Tree a
emptyTree = Nil

newTree :: a -> Trea a
newTree x = Node x Nil Nil

treeInsertToRoot :: Tree a -> a -> Tree a
treeInsertToRoot Nil x = newTree x
treeInsertToRoot tree y = Node y tree Nil

treeInsertToTheLeft :: Tree a -> Tree a
treeInsertToTheLeft Nil x = newTree x
treeInsertToRoot tree y = Node y tree Nil