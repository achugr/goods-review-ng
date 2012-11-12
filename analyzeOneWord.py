# coding=utf-8
from pymorphy import get_morph

morph = get_morph('/home/ilya/github/ru.sqlite-json')

word = raw_input()

#print word

word = word.decode("utf-8").upper()

#info = morph.normalize(word)

info = morph.inflect_ru(unicode(word), u"жр")

#print info.pop().encode("utf-8")

print info.encode("utf-8")