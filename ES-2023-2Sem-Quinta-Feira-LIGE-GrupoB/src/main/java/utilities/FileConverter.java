package utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class FileConverter {
	public static File CSVtoJSON(File f, String path) {
		return null;
	}
	
	public static File JSONtoCSV(String path) {
		File f = new File(path);
		String jsonString;
		
		JSONObject horario;
		File file = null;
		
		try {
			jsonString = FileUtils.readFileToString(f, "UTF-8");
			JSONTokener token = new JSONTokener(jsonString); // vai buscar ao objeto JSON o array a converter
			horario = new JSONObject(token);
			
			JSONArray documento = new JSONArray().put(horario); //coloca o objeto num array
			
			List<String> estrutura = new ArrayList<String>();
			
			String cabecalho = ("Curso, Unidade Curricular, Turno, Turma, Inscritos no Turno, Dia da Semana, Hora de Inicio da Aula, Hora Final da Aula, Data da Aula, Sala Atribuida, Lotacao da Sala");
			estrutura.add(cabecalho);
			
			for (int i = 0; i < documento.length(); i++) {
				JSONObject object = documento.getJSONObject(i);
				
				String curso = object.getString("curso");
				String unidadeCurricular = object.getString("unidadeCurricular");
				String turno = object.getString("turno");
				String turma = object.getString("turma");
				int inscritosNoTurno = object.getInt("inscritosNoTurno");
				String diaDaSemana = object.getString("diaDaSemana");
				String horaInicioAula = object.getString("horaInicioAula");
				String horaFimAula = object.getString("horaFimAula");
				String dataAula = object.getString("dataAula");
				String salaAtribuida = object.getString("salaAtribuida");
				int lotacaoDaSala = object.getInt("lotacaoDaSala");
				
				String csvString = String.format("%s, %s, %s, %s, %d, %s, %s, %s, %s, %s, %d", curso, unidadeCurricular, turno, turma, inscritosNoTurno, diaDaSemana, horaInicioAula, horaFimAula, dataAula, salaAtribuida, lotacaoDaSala); // converte o array JSON numa string CSV
				estrutura.add(csvString);
			}
			
			file = new File("output.csv");
			FileUtils.writeLines(file, estrutura);
			} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
}
