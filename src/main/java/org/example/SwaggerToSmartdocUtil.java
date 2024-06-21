package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SwaggerToSmartdocUtil {

    public static void exec(String path) throws IOException {
        if (path == null || path.isEmpty()) {
            path = System.getProperty("user.dir");
        }
        List<String> allFiles = DirectoryTraversalUtil.getAllFiles(path);
        for (String file : allFiles) {
            String javaFile = readFileToString(file);
            String newText = match(javaFile);
            if (!newText.equals(javaFile)) {
                File file1 = new File(file);
                boolean delete = file1.delete();
                if (delete) {
                    Files.write(Paths.get(file), newText.getBytes());
                }
            }
        }
    }


    public static String match(String text) {
        String regex = "@ApiModelProperty\\(value = \"[^\"]+\"\\)";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        // 创建匹配器
        Matcher matcher = pattern.matcher(text);
        // 尝试匹配
        while (matcher.find()) {
            String group = matcher.group();
            String substring = group.substring(group.indexOf("\"") + 1, group.lastIndexOf("\""));
            String format = " /**\n" +
                    "     *  %s\n" +
                    "     */";
             text = text.replace(group, String.format(format, substring));
        }
        return text;
    }


    public static String readFileToString(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            for (String line : lines) {
                contentBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static void main(String[] args) throws IOException {
        exec(null);
    }
}
