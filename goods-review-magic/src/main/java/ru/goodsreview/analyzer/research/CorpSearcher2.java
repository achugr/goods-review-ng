package ru.goodsreview.analyzer.research;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * User: ilya
 * Date: 24.01.13
 */
public class CorpSearcher2 {
    private static final Logger log = Logger.getLogger(CorpSearcher2.class);

    private static Pattern pattern = Pattern.compile("(<span class=\"stat-number\">)(([0-9]*[\\s]*[0-9]*)*)(</span>)");

    public static void main(String[] args) throws IOException {
        log.info("app started");
        String word = "привет";
        //String mycorp = "%28%28sphere%253A%2522%25EE%25F4%25E8%25F6%25E8%25E0%25EB%25FC%25ED%25EE-%25E4%25E5%25EB%25EE%25E2%25E0%25FF%2522%2520%257C%2520sphere%253A%2522%25EF%25F0%25EE%25E8%25E7%25E2%25EE%25E4%25F1%25F2%25E2%25E5%25ED%25ED%25EE-%25F2%25E5%25F5%25ED%25E8%25F7%25E5%25F1%25EA%25E0%25FF%2522%2520%257C%2520sphere%253A%2522%25EF%25F3%25E1%25EB%25E8%25F6%25E8%25F1%25F2%25E8%25EA%25E0%2522%2520%257C%2520sphere%253A%2522%25F0%25E5%25EA%25EB%25E0%25EC%25E0%2522%2520%257C%2520sphere%253A%2522%25F3%25F7%25E5%25E1%25ED%25EE-%25ED%25E0%25F3%25F7%25ED%25E0%25FF%2522%2520%257C%2520sphere%253A%2522%25FD%25EB%25E5%25EA%25F2%25F0%25EE%25ED%25ED%25E0%25FF%2520%25EA%25EE%25EC%25EC%25F3%25ED%25E8%25EA%25E0%25F6%25E8%25FF%2522%29%2520%2526%2526%2520%28%28sphere%253A%2522%25ED%25E5%25F5%25F3%25E4%25EE%25E6%25E5%25F1%25F2%25E2%25E5%25ED%25ED%25E0%25FF%2522%29%29%29";
        String mycorp = "";
        String mysize = "120684744";
        String mysentsize = "8711773";
//        String mysize = "";
//        String mysentsize = "";
        String mode = "paper";

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://search.ruscorpora.ru/search.xml?env=alpha&mycorp=" + mycorp +
                "&mysent=&mysize=" + mysize +
                "&mysentsize=" + mysentsize +
                "&dpp=&spp=&spd=&text=lexform&mode=" + mode + "&sort=gr_tagging&lang=ru&nodia=1&req=" + word);

        HttpResponse response = client.execute(get);
        Scanner scanner = new Scanner(response.getEntity().getContent());
        while (scanner.hasNextLine()){
            System.out.println(scanner.nextLine());
        }

//        String info = getInfo(word, mycorp, mysize, mysentsize, mode);
        String info;
//        Matcher matcher = pattern.matcher(info);
        Matcher matcher;
//        while (matcher.find()) {
//            System.out.println(matcher.group());
//        }
        // System.out.println(info);

        try {
            String path = "goods-review-magic/src/main/resources/";
            FileReader pmi = new FileReader(path + "JoinedDataTema.txt");
            BufferedReader bufferedReader = new BufferedReader(pmi);
            FileWriter fileWriter = new FileWriter(path + "ruscorpora_paper.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String line, answer;
            int i, docs, matches;
            while ((line = bufferedReader.readLine()) != null) {
                word = line.split(" ")[0];

                info = getInfo(word, mycorp, mysize, mysentsize, mode);
                log.info("getting info about word " + word);
                Thread.sleep(1000);
                matcher = pattern.matcher(info);
                answer = word;
                i = 0;
                docs = 0;
                matches = 0;
                while (matcher.find()) {
                    i += 1;
                    if (i == 4) {
                        docs = Integer.parseInt(matcher.group(2).replace(" ", ""));
                    }
                    if (i == 5) {
                        matches = Integer.parseInt(matcher.group(2).replace(" ", ""));
                    }
                    //System.out.println(matcher.group());
                }
                System.out.println(answer + " " + docs + " " + matches + "\n");
                fileWriter.write(answer + " " + docs + " " + matches + "\n");
            }
            bufferedReader.close();
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println("FileStreamsReadnWrite: " + e);
        } catch (IOException e) {
            System.err.println("FileStreamsReadnWrite: " + e);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public static String getInfo(String word, String mycorp, String mysize, String mysentsize, String mode) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();

        URL oracle = new URL("http://search.ruscorpora.ru/search.xml?env=alpha&mycorp=" + mycorp +
                "&mysent=&mysize=" + mysize +
                "&mysentsize=" + mysentsize +
                "&dpp=&spp=&spd=&text=lexform&mode=" + mode + "&sort=gr_tagging&lang=ru&nodia=1&req=" + word);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            //  System.out.println(inputLine);
            if (inputLine.contains("stat-number")) {
                stringBuffer.append(inputLine);
            }
        }

        in.close();

        return stringBuffer.toString();

    }


}
