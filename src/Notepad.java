import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.util.*;
 
public class Notepad extends Main implements FileInterfaces {
    private static File file = new File(filePath, fileName);
    private static Notepad notepad = new Notepad();
    private static String myFileContent; 
    private static StringBuilder stringBuilder = new StringBuilder();
    private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    
    private Notepad() {
        // Leaving empty just to make it possible to create new Notepad without parameters
    }
    
    public Notepad(String userFilePath, String userFileName) {
        fileName = userFileName;
        filePath = userFilePath;
        myFileContent = readContent(userFile);
    }
    
    public String toString() {
        return "File name: " + fileName + "\n" +
               "File path: " + filePath + "\n" +
               "File extension: .txt" + "\n"   +
               "File content: " + myFileContent; 
    }
    
    private static String getUserClipboard() {
        String userClipboard = null;
        
        try {
            userClipboard = (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (IOException | UnsupportedFlavorException e) {
            System.out.println("An error has occured.");
            e.printStackTrace();
        }
 
        return userClipboard;
    }
    
    private static void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        clipboard.setContents(stringSelection, null);
    }
    
    private static String readContent(File inputFile) {
        try (Scanner fileReader = new Scanner(inputFile)) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                stringBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("An error has occured.");
            e.printStackTrace();
        }
        
        return stringBuilder.toString();
    }
    
    private void writeContent(String content) {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(content);
        } catch (IOException e) {
            System.out.println("An error has occured");
            e.printStackTrace();
        }
    }
    
    private void deleteFileContent() {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("");
        } catch (IOException e) {
            System.out.println("An error has occured");
            e.printStackTrace();
        }
    }
        
    public void read() {
        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()) {
                String content = fileReader.nextLine();
                System.out.println(content);
            }
        } catch (IOException e) {
            System.out.println("An error has occured.");
            e.printStackTrace();
        }
    }
    
    public void write() {
        Scanner scan = new Scanner(System.in);
        StringBuilder newContent = new StringBuilder();
        String userClipboard = getUserClipboard();
        
        copyToClipboard(myFileContent);
        
        System.out.println("Enter content or edit the old one (paste it). To stop writing, enter \"quitprogram\":");
        String line;
 
        while (!(line = scan.nextLine()).equals("quitprogram")) {
            newContent.append(line).append(System.lineSeparator());
        }
        
        scan.close();
        
        copyToClipboard(userClipboard);
        
        notepad.writeContent(newContent.toString());
        System.out.printf("Successfully writed!\n\nWrited content:\n%s", newContent);
    }
    
    public void deleteContent() {
        Scanner scan = new Scanner(System.in);
        
        while (true) {
            try {
                Notepad notepadFile = new Notepad();
                System.out.println("Are you sure you want to delete all the notepad file content (y or n)?");
                char answer = scan.nextLine().charAt(0);
                                        
                if (String.valueOf(answer).trim().toLowerCase().equals("y")) {
                    notepadFile.deleteFileContent();
                    System.out.println("Succesfully deleted all the notepad file content.");
                    break;
                } else if (String.valueOf(answer).trim().toLowerCase().equals("n")) {
                    System.out.println("Canceling...");
                    break;
                } else { 
                    System.out.println("No such option.");
                }
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                continue;
            }
        }
        
        scan.close();
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public String getFilePath() {
        return filePath + File.separator + fileName;
    }
}
 
