package ru.goodsreview.analyzer.util.yaxml;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * User: ilya
 * Date: 24.01.13
 */
public class YaXmlClient {
    private static HttpClient client = new DefaultHttpClient();
    static int routerPort = 8000;
    public static String defaultHttp = "http://134.0.115.143:";


    public String perform(String command) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("command", command));
        try {

            HttpPost post1 = new HttpPost(defaultHttp + routerPort + "/");
            post1.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            client = new DefaultHttpClient();
            HttpResponse response = client.execute(post1);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            while ((line = rd.readLine()) != null) {
                return line;
            }
        } catch (HttpHostConnectException e) {
            throw new RuntimeException("Server is unavailable.", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public String extractData(String request, Pattern pattern) {
        //final String response = perform(request);
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "not_found";
        }
    }

    public void pmiRequests(){
        String path = "/home/amarch/Documents/CSCenter/GoodsReview/analysis/PMI_test/";
        try {
            FileReader fis =  new FileReader(path+"PMI_in.txt");
            BufferedReader bufferedReader = new BufferedReader(fis);
            FileWriter fileWriter = new FileWriter(path+"PMI_log.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String aspect = line.split(" ")[0];
                bufferedWriter.write(this.perform( aspect+" host:market.yandex.ru")+"\n");
                bufferedWriter.write(this.perform( "ноутбук & "+aspect+" host:market.yandex.ru")+"\n");
            }
            bufferedReader.close();
            bufferedWriter.close();
            }
        catch (FileNotFoundException e) {
                System.err.println("FileStreamsReadnWrite: " + e);
        } catch (IOException e) {
                System.err.println("FileStreamsReadnWrite: " + e);
        }
      }

    public void pmiDataExtract(){
            String path = "/home/amarch/Documents/CSCenter/GoodsReview/analysis/PMI_test/";
            int notebooks = 440317;
            try {
                FileReader pmi =  new FileReader(path+"PMI_in.txt");
                BufferedReader bufferedReader = new BufferedReader(pmi);
                FileReader log =  new FileReader(path+"PMI_log.txt");
                BufferedReader bufferedReaderLog = new BufferedReader(log);

                FileWriter fileWriter = new FileWriter(path+"PMI.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                String lineLog1,lineLog2, line, found1, found_docs1, wordstat,found2, found_docs2;
                double PMI_docs, PMI;
                while((line = bufferedReader.readLine()) != null) {
                    String answer = line;
                    lineLog1 = bufferedReaderLog.readLine();
                    lineLog2 = bufferedReaderLog.readLine();
                    found1 = extractData(lineLog1, Pattern.compile("<found priority=\"all\">(\\d*)</found>"));
                    found_docs1 = extractData(lineLog1, Pattern.compile("<found-docs priority=\"all\">(\\d*)</found-docs>"));
                    found2 = extractData(lineLog2, Pattern.compile("<found priority=\"all\">(\\d*)</found>"));
                    found_docs2 = extractData(lineLog2, Pattern.compile("<found-docs priority=\"all\">(\\d*)</found-docs>"));
                    //wordstat = extractData(lineLog2, Pattern.compile("<wordstat>(.)*:(\\d*)</wordstat>"));
                    if(found_docs2 == "not_found"  || found_docs1 == "not_found"){
                        PMI_docs = Float.POSITIVE_INFINITY;
                    } else{
                        PMI_docs = Math.log(new Double(found_docs2)/notebooks/new Double(found_docs1));
                    }
                    if(found2 == "not_found"  || found1 == "not_found"){
                        PMI = Float.POSITIVE_INFINITY;
                    } else{
                        PMI = Math.log(new Double(found2)/notebooks/new Double(found1));
                    }
                    answer += " "+found1+" "+found_docs1+" "+found2+" "+found_docs2+ " " + PMI_docs+" "+PMI+"\n";
                    System.out.println(answer.replace("not_found","0"));
                    fileWriter.write(answer.replace("not_found","0"));
                }
                bufferedReader.close();
                bufferedWriter.close();
                bufferedReaderLog.close();
                }
            catch (FileNotFoundException e) {
                    System.err.println("FileStreamsReadnWrite: " + e);
            } catch (IOException e) {
                    System.err.println("FileStreamsReadnWrite: " + e);
            }
          }



    public static void main(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter(System.out, true);

        String request = "яндекс xml";

        YaXmlClient yaXml = new YaXmlClient();
        //System.out.println(yaXml.perform("ноутбук & клавиатура host:market.yandex.ru"));
        //System.out.println(yaXml.extractData(yaXml.perform("ноутбук & клавиатура host:market.yandex.ru"),
        //                                         Pattern.compile("<found-docs priority=\"all\">(\\d*)</found-docs>")));
        //yaXml.pmiRequests();
        yaXml.pmiDataExtract();
    }

}