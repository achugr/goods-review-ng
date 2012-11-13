# coding=utf-8

from pymorphy import get_morph

morph = get_morph('/home/ilya/github/ru.sqlite-json') #dict path

ins = open("adjective_opinion_words.txt", "r")

array = []
for line in ins:
    ind = line.index(' ')
    if ind!=-1:
        line = line[0:ind]
        array.append(line)

ins.close()

file = open("pyDict", "w")

for i in range(len(array)):
    word = array[i]
    word = word.decode("utf-8").upper()

    info1= morph.inflect_ru(unicode(word), u'мр')
    info2 = morph.inflect_ru(unicode(word), u'жр')
    info3 = morph.inflect_ru(unicode(word), u'ср')

    res = word.lower().encode("utf-8")+" "+info1.lower().encode("utf-8")+" "+info2.lower().encode("utf-8")+" "+info3.lower().encode("utf-8")
#    print res
    file.write(res+"\n")

file.close()

