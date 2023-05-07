package gestaohorarios;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;

import utilities.FileConverter;

/**
 * A classe TimeTable um horário que tem um ficheiro com as informações das
 * aulas e uma lista com as informações desse ficheiro.
 * 
 */
public class TimeTable
{
    private static final Logger LOGGER = Logger.getLogger(TimeTable.class.getName());
    private File file;
    private List<Lesson> lessonsList = new LinkedList<>();

    /**
     * Construtor da classe recebe uma string path para carregar o ficheiro.
     * 
     * @param path Caminho (path) para o ficheiro pretendido.
     */
    public TimeTable(String path)
    {
	try
	{
	    this.file = findFile(path, null);
	} catch (IOException e)
	{
	    LOGGER.log(Level.WARNING, e.getMessage());
	    LOGGER.log(Level.SEVERE, "Erro ao criar a TimeTable");
	}
    }

    /**
     * Construtor da classe que recebe uma string path para carregar o ficheiro e
     * uma string para guardar o ficheiro.
     * 
     * @param path           Caminho para o ficheiro pretendido, pode ser um
     *                       diretório ou URL.
     * @param localDirectory Caminho onde vai ser guardado o ficheiro.
     */
    public TimeTable(String path, String localDirectory)
    {
	try
	{
	    this.file = findFile(path, localDirectory);
	} catch (IOException e)
	{
	    LOGGER.log(Level.WARNING, e.getMessage());
	    LOGGER.log(Level.SEVERE, "Erro ao criar a TimeTable");
	}
    }

    /**
     * Construtor da clase que recebe uma lista de aulas e que guarda um ficheiro no
     * path recebido.
     * 
     * @param lessonsList Lista de objetos do tipo Lesson.
     * @param path        Caminho onde vai ser guardado o ficheiro gerado a partir
     *                    da lista.
     */
    public TimeTable(List<Lesson> lessonsList, String path)
    {
	this.lessonsList = lessonsList;
	createFile(path);
    }

    /**
     * Método que é chamado para criar ficheiro a partir de um path. Serve para
     * verificar se o path passado é de um ficheiro json ou csv antes de criar o
     * ficheiro em json, para saber se o ficheiro criado precisa de ser convertido
     * ou não.
     * 
     * @param path Caminho onde o ficheiro vai ser criado.
     */
    private void createFile(String path)
    {
	String csvPath = "";
	if (!FilenameUtils.getExtension(path).equals("csv") && !FilenameUtils.getExtension(path).equals("json"))
	    throw new IllegalArgumentException("O path passado e inváido");
	if (FilenameUtils.getExtension(path).equals("csv"))
	{
	    csvPath = path;
	    path = FilenameUtils.removeExtension("csv");
	    path += ".json";
	}

	createJsonFile(path);
	if ("csv".equals(FilenameUtils.getExtension(path)))
	    saveAsCSV(csvPath);
    }

    /**
     * Método que cria um ficheiro Json a partir do atributo lessonsList.
     * 
     * @param path Path onde vai guardar o ficheiro json criado.
     */
    private void createJsonFile(String path)
    {
	String jsonText = "[\n";
	for (Lesson lesson : lessonsList)
	{
	    jsonText += lesson.toJSONDocument() + "\n";
	}
	jsonText += "]";

	File newFile = new File(path);

	try
	{
	    FileWriter fw = new FileWriter(newFile);
	    fw.write(jsonText);
	    fw.close();
	} catch (IOException e)
	{
	    throw new IllegalArgumentException("O path passado e inváido");
	}
	this.file = newFile;
    }

    /**
     * Método que se o parâmetro path for um path vai carregar esse ficheiro para a
     * variável local file, caso o path seja um URL vai carregar o ficheiro do URL e
     * salva-o no path localDirectory
     *
     * @param path           Caminho para o ficheiro pretendido, pode ser diretório
     *                       ou URL.
     * @param localDirectory Caminho onde vai ser guardado o ficheiro.
     *
     * @return File que se carregou.
     */
    private File findFile(String path, String localDirectory) throws IOException
    {
	File file = null;
	if (isURL(path))
	{
	    if (localDirectory == null)
	    {
		LOGGER.log(Level.WARNING, "erro path url e localdirectory null");
		return file;
	    }
	    if(path.startsWith("webcal")) 
	    {
	    	path = WebcalToHttp(path);
	    	file = downloadFile(path, localDirectory);
	    	System.out.println(file.getAbsolutePath());
	    	return FileConverter.IcsToCsv(file.getAbsolutePath(), file.getAbsolutePath());
	    }
	    return downloadFile(path, localDirectory);
	}
	else
	{
//	    LOGGER.log("n�o url");
	    file = new File(path);
	}
	return file;
    }

    /**
     * Método que atualiza o ficheiro da classe para a conversão do mesmo no formato
     * JSON
     *
     * @param path Diretório onde se pretende guardar o ficheiro que vai ser criado
     *             ao invocar o método.
     */
    public void saveAsJSON(String path)
    {
	try
	{
	    this.file = FileConverter.csvTojson(file.getAbsolutePath(), path);
	} catch (IOException e)
	{
	    LOGGER.log(Level.WARNING, e.getMessage());
	}
    }

    /**
     * Método que atualiza o ficheiro da classe para a conversão do mesmo no formato
     * CSV
     *
     * @param path Diretório onde se pretende guardar o ficheiro que vai ser criado
     *             ao invocar o método.
     */
    public void saveAsCSV(String path)
    {
	this.file = FileConverter.jsonTocsv(file.getAbsolutePath(), path);
    }

    /**
     * Método que guarda o ficheiro local num local passado.
     *
     * @param Path String que pode ser um URL ou um path onde se prentede guardar o
     *             ficheiro da classe.
     * @throws IOException se a função copyFileToDirectory do FileUtils lançar
     *                     execeção.
     */
    public void saveFile(String Path) throws IOException
    {
	if (!isURL(Path))
	{
	    File directory = new File(Path);
	    if (!directory.exists())
	    {
		System.err.println("Diretoria n�o existe " + directory);
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
	    LOGGER.log(Level.INFO, "Response Code : " + responseCode);
	}
    }

    /**
     * Método que verifica se a string é um URL.
     *
     * @param url URL do ficheiro pretendido.
     *
     * @return True se o parametro for um URL e false caso contrário.
     */
    public boolean isURL(String url)
    {
	String regex = "^(https?|ftp|file|webcal)://.+";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(url);
	return matcher.matches();
    }

    /**
     * Método que faz download de um ficheiro através de um url para uma diretoria.
     *
     * @param url       URL do ficheiro pretendido.
     * @param directory Diretoria onde o ficheiro vai ser guardado.
     *
     * @return ficheiro guardado.
     * @throws IOException se algo correr mal na criação do URL ou na leitura do
     *                     ficheiro.
     */
    public File downloadFile(String url, String directory) throws IOException
    {
	URL fileUrl = new URL(url);
	String fileName = fileUrl.getFile().substring(fileUrl.getFile().lastIndexOf('/') + 1);
	if(fileName.length()>20) 
	{
		fileName="NovoDocumento.csv";
	}
	File destinationFile = new File(directory + "/" + fileName);
	FileUtils.copyURLToFile(fileUrl, destinationFile);
	String fileContent = FileUtils.readFileToString(destinationFile);
	fileContent = fileContent.replaceFirst("^\\s+", "");
	FileUtils.writeStringToFile(destinationFile, fileContent, StandardCharsets.UTF_8);

	return destinationFile;
    }

    /**
     * Método que devolve o ficheiro guardado na variável local file.
     *
     * @return Ficheiro this.file
     */
    public File getFile()
    {
	return this.file;
    }

    /**
     * Método que devolve a lista de aulas da Timetable.
     *
     * @return Ficheiro this.lessonsList
     */

    public List<Lesson> getLessonsList()
    {
	if (lessonsList.isEmpty())
	    createLessonsList();
	return new LinkedList<>(lessonsList);
    }

    /**
     * Método que cria uma lista de aulas a partir do ficheiro da classe.
     */
    private void createLessonsList()
    {
	String jsonText;
	if (!isJSON() && !isCSV())
	    throw new IllegalStateException();
	else if (isCSV())
	{
	    String path = file.getAbsolutePath().replace(".csv", ".json");
	    saveAsJSON(path);
	}

	try
	{
	    jsonText = FileUtils.readFileToString(file, "UTF-8");
	    JSONArray jsonArray = new JSONArray(jsonText);
	    for (int i = 0; i < jsonArray.length(); i++)
	    {
		Lesson l = new Lesson(jsonArray.getJSONObject(i));
		lessonsList.add(l);
	    }
	} catch (IOException e)
	{
	    LOGGER.log(Level.WARNING, e.getMessage());
	}

    }

    /**
     * Método que verifica se o ficheiro da classe é um ficheiro json.
     * 
     * @return True se o ficheiro for json, false se não for.
     */
    public boolean isJSON()
    {
	return "json".equals(FilenameUtils.getExtension(file.getName()));
    }

    /**
     * Método que verifica se o ficheiro da classe é um ficheiro csv.
     * 
     * @return True se o ficheiro for csv, false se não for.
     */
    public boolean isCSV()
    {
	return "csv".equals(FilenameUtils.getExtension(file.getName()));
    }

    /**
     * Método que cria um mapa com as aulas sobrepostas associadas ao horário onde
     * estão sobrepostas do objeto Timetable.
     * 
     * @return um MultiMap com a key sendo o tempo em que as aulas estão sobrepostas
     *         e os values as aulas que estão sobrepostas.
     */
    public MultiValuedMap<LessonTime, Lesson> getOverlaidLessons()
    {
	List<Lesson> list = getLessonsList();
	MultiValuedMap<LessonTime, Lesson> overlaidLessons = new ArrayListValuedHashMap<LessonTime, Lesson>();
	for (int i = 0; i < list.size(); i++)
	{
	    Lesson lesson1 = list.get(i);
	    for (int j = i + 1; j < list.size(); j++)
	    {
		Lesson lesson2 = list.get(j);
		if (lesson1.isOverlaid(lesson2))
		{
		    LessonTime overlaidTime = lesson1.getTime().getOverlay(lesson2.getTime());
		    overlaidLessons.put(overlaidTime, lesson1);
		    overlaidLessons.put(overlaidTime, lesson2);
		}
	    }
	}

	return overlaidLessons;
    }

    /**
     * Método que cria uma nova Timetable a partir da atual, escolhendo apenas
     * algumas das UCs.
     * 
     * @param ucs              Lista de Strings com o nome das UCs selecionadas.
     * @param newTimeTablePath Path onde vai ser guardado o ficheiro da nova
     *                         timetable.
     * @return nova TimeTable com as UCs selecionada, ou a mesma se a lista ucs for
     *         vazia ou nula.
     */
    public TimeTable filterUCs(List<String> ucs, String newTimeTablePath)
    {
	if (ucs == null || ucs.isEmpty())
	    return this;
	List<Lesson> filteredList = new LinkedList<>(getLessonsList());
	filteredList.removeIf(l -> !ucs.contains(l.getUnidadeCurricular()));
	return new TimeTable(filteredList, newTimeTablePath);
    }

    @Override
    public boolean equals(Object obj)
    {
	return obj instanceof TimeTable && this.getLessonsList().equals(((TimeTable) obj).getLessonsList());
    }

    /**
     * Método que cria uma lista com as aulas sobrelotadas do objeto Timetable.
     * 
     * @return Lista com as aulas sobrelotadas.
     */
    public List<Lesson> getOverbookedLessons()
    {
	List<Lesson> overbookedlessons = new LinkedList<>();
	List<Lesson> list = getLessonsList();
	for (Lesson lesson : list)
	    if (lesson.isOverbooked())
		overbookedlessons.add(lesson);

	return overbookedlessons;
    }

    /**
     * Método que devolve uma string com a informação sobre a aulas sobrelotadas do
     * objeto Timetable.
     * 
     * @return String com as aulas sobrelotadas.
     */
    public String showOverbookedLessons()
    {
	String s = "";
	List<Lesson> overbookedlessons = getOverbookedLessons();
	for (Lesson lesson : overbookedlessons)
	{
	    s += lesson.getUnidadeCurricular() + " em " + lesson.getTime() + " com " + lesson.getInscritosNoTurno()
		    + " inscritos  e " + lesson.getLotacao() + " lugares \n";
	}
	return s;
    }

    /**
     * Método que devolve uma string com a informação sobre a aulas sobrepostas do
     * objeto Timetable.
     * 
     * @return String com as aulas sobrepostas.
     */
    public String showOverlaidLessons()
    {
	Map<LessonTime, Collection<Lesson>> overlaidLessons = getOverlaidLessons().asMap();
	String s = "";

	for (Entry<LessonTime, Collection<Lesson>> entry : overlaidLessons.entrySet())
	{
	    s += "Hora: " + entry.getKey() + "\t";
	    List<Lesson> list = (List<Lesson>) entry.getValue();
	    for (int i = 0; i < list.size(); i++)
	    {
		Lesson lesson = list.get(i);
		if (i == 0)
		    s += "Sala: " + lesson.getSala() + "\tAulas:";
		s += " " + lesson.getUnidadeCurricular();
		if (i != list.size() - 1)
		    s += ",";
	    }
	    s += "\n";
	}
	return s;
    }

    
    /**
     * M�todo que troca a palavra 'webcal' por 'https' numa string recebida
     *
     * @param s String sobre a qual se quer fazer o replace

     *
     * @return String a string recebida com o replace feito
     */
    public String WebcalToHttp(String s) {
    	return s.replace("webcal", "https");
    }
}
