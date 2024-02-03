import java.nio.file.*;
import java.io.*;
import java.util.Scanner;
 
public class Main {
    private static Scanner scan = new Scanner(System.in);
    public static String fileName;
    public static String filePath;
    private static final String FORBIDDENSYMBOLS = "\"“*<>?\\/|:";
    public static File userFile;
    
    public static boolean fileNameIsValid(String fileName) {
        while (true) {
            for (char forbiddenSymbol : FORBIDDENSYMBOLS.toCharArray()) {
                if (fileName.contains(String.valueOf(forbiddenSymbol))) {
                    System.out.println("Forbidden character detected: " + forbiddenSymbol);
                    continue;
                } else {
                    return true;
                }
            }
        }
    }
    
    public static boolean pathExists(String userPath) {
        Path path = Paths.get(userPath);
        
        return Files.exists(path);
    }
    
    public static void setFilePath() {
        while (true) {
            try {
                System.out.println("Enter your notepad file path:");
                filePath = scan.nextLine().trim();
                
                while (!pathExists(filePath)) {
                    System.out.println("Path " + "\"" +  filePath + "\"" + " doesn't exist.");
                    System.out.println("Enter your notepad file path: ");
                    filePath = scan.nextLine().trim();
                }
                
                System.out.println("Notepad file saved in " + "\"" +  filePath + "\".");
                break;
            } catch (InvalidPathException e) {
                System.out.println("Invalid file path.");
                continue;
            }
        }
    }
    
    public static void setFileName() {
        System.out.println("Enter your notepad file name:");
        fileName = scan.nextLine().trim() + ".txt";
        File file = new File(filePath, fileName);
        if (fileNameIsValid(fileName)) {
            System.out.println("Notepad file name: " + "\"" +  fileName + "\".");
            
            if (!file.exists()) {
                Path path = Paths.get(filePath + fileName);
                try {
                    userFile = Files.createFile(path).toFile();
                    System.out.println("\nNotepad file created: " + "\"" + userFile.getAbsolutePath() + "\".");
                } catch (IOException e) {
                    System.out.println("An error has occured.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("\nNotepad file exists in " + "\"" + file.getAbsolutePath() + "\".");
            }
        }
        
        userFile = new File(filePath, fileName);
    }
    
    public static void main(String[] args) {
        setFilePath();
        setFileName();
        
        Notepad notepad = new Notepad(filePath, fileName);
        
        while (true) {
            System.out.println("\nChoose an action:\nRead: 1\nWrite: 2\nDelete content: 3\nGet file name: 4\nGet file path: 5\nCancel: 6");
            String answer = scan.nextLine();
            
            if (answer.equals("1")) {
                notepad.read();
                break;
            } if (answer.equals("2")) {
                notepad.write();
                break;
            } if (answer.equals("3")) {
                notepad.deleteContent();
                break;
            } if (answer.equals("4")) {
                System.out.println(notepad.getFileName());
                break;
            } if (answer.equals("5")) {
                System.out.println(notepad.getFilePath());
                break;
            } if (answer.equals("6")) {
                System.out.println("Canceling...");
                break;
            } else {
                System.out.println("No such option.");
                continue;
            }
        }
    }
}
 
