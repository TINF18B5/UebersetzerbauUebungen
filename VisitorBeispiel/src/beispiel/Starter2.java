package beispiel;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class Starter2 {
    
    public static void main(String[] args) throws IOException {
        final Path path = Paths.get("../..");
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("PreVisit dir = " + dir);
                return super.preVisitDirectory(dir, attrs);
            }
    
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("file = " + file);
                return super.visitFile(file, attrs);
            }
    
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("Failed file = " + file);
                return super.visitFileFailed(file, exc);
            }
    
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("PostVisit dir = " + dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }
}
