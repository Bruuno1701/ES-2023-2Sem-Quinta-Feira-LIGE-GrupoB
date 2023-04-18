import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import java.io.FileInputStream;
import java.io.DataOutputStream;

import utilities.FileConverter;

public class TimeTable
{
    private File file;

    public TimeTable(String path)
    {
	try
	{
	    this.file = findFile(path, null);
	} catch (IOException e)
	{
	    e.printStackTrace();
	    System.out.println("Erro ao criar a TimeTable");
	}
    }

    public TimeTable(String path, String URL)
    {
	try
	{
	    this.file = findFile(path, URL);
	} catch (IOException e)
	{
	    e.printStackTrace();
	    System.out.println("Erro ao criar a TimeTable");
	}
    }

    private File findFile(String path, String localDirectory) throws IOException
    {
	File file = null;
	if (isURL(path) && localDirectory != null)
	{
	    URL url = new URL(path);
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("GET");
	    int responseCode = connection.getResponseCode();
	    if (responseCode == HttpURLConnection.HTTP_OK)
	    {
		String fileName = "";
		String disposition = connection.getHeaderField("Content-Disposition");
		if (disposition != null)
		{
		    int index = disposition.indexOf("filename=");
		    if (index > 0)
		    {
			fileName = disposition.substring(index + 10, disposition.length() - 1);
		    }
		}
		else
		{
		    fileName = path.substring(path.lastIndexOf("/") + 1);
		}
		File directory = new File(localDirectory);
		file = new File(directory, fileName);
		InputStream inputStream = connection.getInputStream();
		FileUtils.copyInputStreamToFile(inputStream, file);
		inputStream.close();
		System.out.println("filename:"+fileName);

	    }

	}
	else
	{
	    file = new File(path);
	}
	return file;
    }

    public void saveAsJSON(String path)
    {
	try
	{
	    FileConverter.csvTojson(file.getAbsolutePath(), path);
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public void saveAsCSV(String path)
    {
	FileConverter.jsonTocsv(file.getAbsolutePath(), path);
    }

    public void saveFile(String Path) throws IOException
    {
	if (!isURL(Path))
	{
	    File directory = new File(Path);
	    // Create the directory if it doesn't exist
	    if (!directory.exists())
	    {
		System.err.println("Diretoria não existe");
		return;
	    }
	    // Save the file to the directory
	    FileUtils.copyFileToDirectory(this.file, directory);
	}
	else
	{
	    URL url = new URL(Path);
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setDoOutput(true);
	    connection.setRequestMethod("POST");
	    connection.setRequestProperty("Content-Type", "multipart/form-data");
	    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
	    FileInputStream fileInputStream = new FileInputStream(this.file);
	    byte[] buffer = new byte[1024];
	    int bytesRead;
	    while ((bytesRead = fileInputStream.read(buffer)) != -1)
	    {
		outputStream.write(buffer, 0, bytesRead);
	    }
	    fileInputStream.close();
	    outputStream.flush();
	    outputStream.close();
	    int responseCode = connection.getResponseCode();
	    System.out.println("Response Code : " + responseCode);
	}
    }

    public boolean isURL(String url)
    {
	String regex = "^(https?|ftp|file)://.+";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(url);
	return matcher.matches();
    }
}
