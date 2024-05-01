package readerwriter;

import estructuras.lineales.ArrayList;
import estructuras.lineales.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

public class ReaderWriter {
    public static void writeLines(String fileName, Iterable<?> lines) throws IOException {
        int i = 0;
        Iterator<?> it = lines.iterator();
        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(new File(fileName)));
        
        while (it.hasNext()) {
            bufferedWriter.write(it.next().toString());
            if (it.hasNext()) bufferedWriter.write("\n"); 
            i++;
        }      
        System.out.println("WROTE " + fileName + ": " + i);
        bufferedWriter.close();
    }

    public static List<String> readLines(String fileName) throws IOException {
        ArrayList arrayList = new ArrayList();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
        int i = 0; 
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            arrayList.add(arrayList.size(), line);
            i++;
    } 
    
    bufferedReader.close();
    
    System.out.println("READ " + fileName + ": " + i);
    return (List<String>)arrayList;
    }
}
