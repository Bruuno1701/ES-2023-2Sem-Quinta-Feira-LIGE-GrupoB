import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;

import java.io.FileInputStream;
import java.io.DataOutputStream;

import utilities.FileConverter;

public class TimeTable
{

    private File file;
    private List<Lesson> lessonsList = new LinkedList<>();

    /**
     * @author pedro Construtor da class recebe uma string path para carregar o
     *         ficheiro
     * 
     * @param path caminho (path) para o ficheiro pretendido
     */
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
//	createLessonsList();
    }

    /**
     * @author pedro Construtor da class recebe uma string path para carregar o
     *         ficheiro e uma string para guardar o ficheiro
     * 
     * @param path           caminho para o ficheiro pretendido, pode ser um
     *                       diret�rio ou URL
     * @param localDirectory caminho onde vai ser guardado o ficheiro
     */
    public TimeTable(String path, String localDirectory)
    {
	try
	{
	    this.file = findFile(path, localDirectory);
	} catch (IOException e)
	{
	    e.printStackTrace();
	    System.out.println("Erro ao criar a TimeTable");
	}
//	createLessonsList();
    }

    /**
     * @author pedro Se o par�metro path for um path vai carregar esse ficheiro para
     *         a vari�vel local file, caso o path seja um URL vai carregar o
     *         ficheiro do URL e salva-o no path localDirectory
     *
     * @param path           caminho para o ficheiro pretendido, pode ser diret�rio
     *                       ou URL
     * @param localDirectory caminho onde vai ser guardado o ficheiro
     *
     * @return o file que se carregou
     */
    private File findFile(String path, String localDirectory) throws IOException
    {
	File file = null;
	if (isURL(path))
	{
	    if (localDirectory == null)
	    {
		System.out.println("erro path url e localdirectory null");
		return file;
	    }
	    return downloadFile(path, localDirectory);
	}
	else
	{
	    System.out.println("n�o url");
	    file = new File(path);
	}
	return file;
    }

    /**
     * @author filipa Atualiza o ficheiro da class para a convers�o do mesmo no
     *         formato JSON
     *
     * @param path diret�rio onde se pretende guardar o ficheiro que vai ser criado
     *             ao invocar o convertor
     *
     * @return void
     */
    public void saveAsJSON(String path)
    {
	try
	{
	    this.file = FileConverter.csvTojson(file.getAbsolutePath(), path);
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
    }

    /**
     * @author bruna Atualiza o ficheiro da class para a convers�o do mesmo no
     *         formato CSV
     *
     * @param path diret�rio onde se pretende guardar o ficheiro que vai ser criado
     *             ao invocar o convertor
     *
     * @return void
     */
    public void saveAsCSV(String path)
    {
	this.file = FileConverter.jsonTocsv(file.getAbsolutePath(), path);
    }

    /**
     * @author pedro Guarda o ficheiro local num local passado
     *
     * @param Path string que pode ser um URL ou um path onde se prentede guardar o
     *             ficheiro da class
     *
     * @return void
     * @throws IOException
     */
    public void saveFile(String Path) throws IOException
    {
	if (!isURL(Path))
	{
	    File directory = new File(Path);
	    if (!directory.exists())
	    {
		System.err.println("Diretoria n�o existe "+directory);
		return;
	    }
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

    /**
     * @author pedro verifica se a string � um URL
     *
     * @param url URL do ficheiro pretendido
     *
     * @return true se o parametro for um URL e false caso contr�rio
     */
    public boolean isURL(String url)
    {
	String regex = "^(https?|ftp|file)://.+";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(url);
	return matcher.matches();
    }

    /**
     * @author pedro Faz download de um ficheiro atrav�s de um url para uma
     *         diretoria
     *
     * @param url       URL do ficheiro pretendido
     * @param directory directoria onde o ficheiro vai ser guardado
     *
     * @return o ficheiro guardado
     * @throws IOException
     */
    public File downloadFile(String url, String directory) throws IOException
    {
	URL fileUrl = new URL(url);
	String fileName = fileUrl.getFile().substring(fileUrl.getFile().lastIndexOf('/') + 1);
	File destinationFile = new File(directory + "/" + fileName);
	FileUtils.copyURLToFile(fileUrl, destinationFile);
	String fileContent = FileUtils.readFileToString(destinationFile);
	fileContent = fileContent.replaceFirst("^\\s+", "");
	FileUtils.writeStringToFile(destinationFile, fileContent, StandardCharsets.UTF_8);

	return destinationFile;
    }

    /**
     * @author pedro Devolve o ficheiro guardado na vari�vel local file.
     *
     * @return o ficheiro this.file
     */
    public File getFile()
    {
	return this.file;
    }

    public List<Lesson> getLessonsList()
    {
	return new LinkedList<>(lessonsList);
    }

    private void createLessonsList()
    {
	String jsonText;
	if (!isJSON() && !isCSV())
	    throw new IllegalStateException();
	else if (isCSV())
	{
	    String path = file.getAbsolutePath().replace(".csv", ".json");
	    saveAsJSON(path );
	}

	try
	{
	    jsonText = FileUtils.readFileToString(file, "UTF-8");
	    JSONArray jsonArray = new JSONArray(jsonText);
	    for (int i = 0; i < jsonArray.length(); i++)
	    {
		lessonsList.add(new Lesson(jsonArray.getJSONObject(i)));
	    }
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    private boolean isJSON()
    {
	return FilenameUtils.getExtension(file.getName()).equals("json");
    }

    private boolean isCSV()
    {
	return FilenameUtils.getExtension(file.getName()).equals("csv");
    }

}
