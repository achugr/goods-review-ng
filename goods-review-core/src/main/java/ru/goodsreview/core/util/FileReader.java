package ru.goodsreview.core.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         24.08.12
 */
//TODO название класса лучше сменить на FileUtils ибо FileReader подразумевает под собой то, у чего можно создать инстанс
public final class FileReader {
    private static final Logger log = Logger.getLogger(FileReader.class);

    private FileReader() {
    }

    public static List<String> readAsListOfLines(final String filePath, final Class holderClass) {
        final List<String> lines = new LinkedList<String>();
        InputStream inputStream = null;
        try {
            inputStream = holderClass.getResourceAsStream(filePath);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (bufferedReader.ready()) {
                lines.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return lines;
    }

    public static String readFileAsString(final String filePath, final Class holderClass) {
        return StringUtil.inputStreamToString(holderClass.getResourceAsStream(filePath));
    }

    public static String readFileAsString(final String filePath) {
        try {
            final InputStream inputStream = new FileInputStream(new File(filePath));
            return StringUtil.inputStreamToString(inputStream);
        } catch (FileNotFoundException e) {
            return "file " + filePath + " not found";
        }
    }
}
