import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
// import static java.util.stream.Collectors.*;


public class Converter {
	public static void main (String args[]){
		Properties p = new Properties();
		ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
		FileInputStream is = null;
		
		if(args.length == 1){
			try {
				is = new FileInputStream(args[0]);
				InputStreamReader reader = new InputStreamReader(is, "UTF-8");
				p.load(reader);
				Stream<Entry<Object, Object>> stream = p.entrySet().stream();
				Map<String, String> mapOfProperties = stream.collect(Collectors.toMap(
					e -> String.valueOf(e.getKey()),
					e -> String.valueOf(e.getValue())));
					
try (PrintStream out = new PrintStream(new FileOutputStream("out.json"))) {
    out.print(writer.writeValueAsString(mapOfProperties));
}
					
// 					System.out.println();
			} catch (IOException e){
				e.printStackTrace(); 
			} finally {
				if (is != null){
					try {
						is.close();
					} catch (IOException e){
						e.printStackTrace();
					}
				}
			}
		}else {
			System.err.println("Error getting input file");
			System.exit(1);
		}
	}
}
