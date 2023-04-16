package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class FileConverter
{

    public static File csvTojson(File csvFile, String path) throws IOException
    {
	File jsonFile = new File(path);
	PrintWriter writer = new PrintWriter(jsonFile, Charset.forName("UTF-8"));

	CSVParser csvParser = CSVParser.parse(csvFile, Charset.forName("UTF-8"),
		CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';'));
	List<CSVRecord> records = csvParser.getRecords();
	List<String> headers = csvParser.getHeaderNames();
	writer.println("[");
	for (int i = 0; i < records.size(); i++)
	{
	    CSVRecord record = records.get(i);
	    writer.println(" {");
	    List<String> list = record.toList();
	    list.removeAll(Arrays.asList("", null));
	    for (int j = 0; j < list.size(); j++)
	    {
		String value = list.get(j);
		String header = headers.get(j);
		if (j != list.size() - 1)
		{
		    writer.println("   \"" + header + "\": \"" + value + "\",");
		}
		else
		{
		    writer.println("   \"" + header + "\": \"" + value + "\"");
		}
	    }
	    if (i != records.size() - 1)
	    {
		writer.println(" },");
	    }
	    else
	    {
		writer.println(" }");
	    }
	}

	writer.print("]");
	try
	{
	    return jsonFile;
	}
	finally
	{
	    writer.close();
	    csvParser.close();
	}
    }

    public static File JSONtoCSV(File jsonFile, String path)
    {
	return null;
    }

}
