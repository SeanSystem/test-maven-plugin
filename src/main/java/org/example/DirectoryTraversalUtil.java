package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DirectoryTraversalUtil {

    /**
     * 遍历指定目录下的所有文件（包括子目录中的文件），并将文件路径添加到列表中返回。
     * @param directoryPath 要遍历的目录路径
     * @return 包含所有文件路径的列表
     * @throws IOException 如果遍历目录时发生I/O错误
     */
    public static List<String> getAllFiles(String directoryPath) throws IOException {
        List<String> filePaths = new ArrayList<>();

        Path startPath = Paths.get(directoryPath);

        Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                String string = file.toString();
                if (string.endsWith(".java")) {
                    filePaths.add(string);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc)
                    throws IOException
            {
                Objects.requireNonNull(file);
                throw exc;
            }
        });

        return filePaths;
    }

    public static void main(String[] args) {
        String directoryToTraverse = System.getProperty("user.dir"); // 替换成你要遍历的目录路径
        try {
            List<String> allFiles = getAllFiles(directoryToTraverse);
            for (String filePath : allFiles) {
                System.out.println(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
