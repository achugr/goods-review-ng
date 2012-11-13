package ru.goodsreview.analyzer.util.dictionary;


import org.junit.Test;
import ru.goodsreview.core.util.FileUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: ilya
 * Date: 13.11.12
 */

public class PyMorphyDict  {
    private final Map<String, String[]> dictionary = new HashMap<String, String[]>();

    public static PyMorphyDict getInstance(final String filePath) {
        PyMorphyDict pyDictionary = new PyMorphyDict();
        pyDictionary.initDictionary(filePath);
        return pyDictionary;
    }

    private void initDictionary(final String dictionaryFileName) {
        final List<String> fileLines = FileUtils.readAsListOfLines(dictionaryFileName, MapDictionary.class);
        for(String line : fileLines){
            final String [] record = line.split("\\s");
            if(record.length==4){
                dictionary.put(record[0], new String[]{record[1],record[2],record[3]});
            }
        }
    }


    public boolean contains(final String key) {
        return dictionary.containsKey(key);
    }


    public String[] getForms(final String key) {
        String[] forms = dictionary.get(key);
        if(forms==null){
            return new String[0];
        } else{
            return forms;
        }
    }

    public void print() {
        for (String word : dictionary.keySet()) {
            System.out.println(word + " " + Arrays.toString(dictionary.get(word)));
        }
    }




    @Test
    public  void test(){
        PyMorphyDict pd = PyMorphyDict.getInstance("/ru/goodsreview/analyzer/util/dictionary/pyDict.txt");
      //  System.out.println(pd.getForms("потрясныйsdf"));
        pd.print();
    }


}