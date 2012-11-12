# coding=utf-8
from pymorphy import get_morph

morph = get_morph('/home/ilya/github/ru.sqlite-json')

command = raw_input()
array = command.split('#')

word = array[0]
case = array[1]

word = word.decode("utf-8").upper()
case = case.decode("utf-8")

info = morph.inflect_ru(unicode(word), unicode(case))

print info.encode("utf-8")