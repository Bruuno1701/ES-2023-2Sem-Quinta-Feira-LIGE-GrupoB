import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public class Lesson
{
    private String curso;
    private String unidadeCurricular;
    private String turno;
    private String turma;
    private int inscritosNoTurno;
    private LessonTime time;
    private String sala;
    private int lotacao;

    public Lesson(JSONObject object)
    {
	if (object == null || object.isEmpty())
	    throw new IllegalArgumentException();
	try
	{
	    curso = object.optString("Curso", "");
	    unidadeCurricular = object.optString("Unidade Curricular", "");
	    turno = object.optString("Turno", "");
	    turma = object.optString("Turma", "");
	    String sinscritosNoTurno = object.optString("Inscritos no turno", "");
	    String diaDaSemana = object.optString("Dia da semana", "");
	    String horaInicio = (object.optString("Hora início da aula", ""));
	    String horaFim = (object.optString("Hora fim da aula", ""));
	    String data = (object.optString("Data da aula", ""));
	    time = new LessonTime(diaDaSemana, horaInicio, horaFim, data);
	    sala = object.optString("Sala atribuída à aula", "");
	    String slotacao = object.optString("Lotação da sala", "");
	    inscritosNoTurno = (sinscritosNoTurno.equals("") ? -1 : Integer.parseInt(sinscritosNoTurno));
	    lotacao = (slotacao.equals("") ? -1 : Integer.parseInt(slotacao));
	} catch (NumberFormatException e)
	{
	    throw new IllegalArgumentException(e.getMessage());
	}
    }

    public String getCurso()
    {
	return curso;
    }

    public String getUnidadeCurricular()
    {
	return unidadeCurricular;
    }

    public String getTurno()
    {
	return turno;
    }

    public String getTurma()
    {
	return turma;
    }

    public int getInscritosNoTurno()
    {
	return inscritosNoTurno;
    }

    public String getDiaDaSemana()
    {
	return time.getDiaDaSemana();
    }

    public String getHoraInicio()
    {
	return time.getHoraInicio();
    }

    public String getHoraFim()
    {
	return time.getHoraFim();
    }

    public String getData()
    {
	return time.getData();
    }

    public String getSala()
    {
	return sala;
    }

    public int getLotacao()
    {
	return lotacao;
    }
    
    public LessonTime getTime() {
	return time;
    }

    public boolean isOverbooked()
    {
	return lotacao!=-1 && lotacao < inscritosNoTurno;

    }

    @Override
    public String toString()
    {
	return "Lesson [curso=" + curso + ", unidadeCurricular=" + unidadeCurricular + ", turno=" + turno + ", turma="
		+ turma + ", inscritosNoTurno=" + inscritosNoTurno + ", diaDaSemana=" + time.getDiaDaSemana() + ", horaInicio="
		+ time.getHoraInicio() + ", horaFim=" + time.getHoraFim() + ", data=" + time.getData() + ", sala=" + sala + ", lotacao=" + lotacao
		+ "]";
    }

    public String toJSONDocument()
    {
	String jsonDoc = "";
	jsonDoc += " {\n";

	if (!"".equals(curso))
	    jsonDoc += "   \"Curso\": \"" + curso + "\",\n";
	if (!"".equals(unidadeCurricular))
	    jsonDoc += "   \"Unidade Curricular\": \"" + unidadeCurricular + "\",\n";
	if (!"".equals(turno))
	    jsonDoc += "   \"Turno\": \"" + turno + "\",\n";
	if (!"".equals(turma))
	    jsonDoc += "   \"Turma\": \"" + turma + "\",\n";
	if (inscritosNoTurno!=-1)
	    jsonDoc += "   \"Inscritos no turno\": \"" + inscritosNoTurno + "\",\n";
	if (!"".equals(time.getDiaDaSemana()))
	    jsonDoc += "   \"Dia da semana\": \"" + time.getDiaDaSemana()+ "\",\n";
	if (!"".equals(time.getHoraInicio()))
	    jsonDoc += "   \"Hora início da aula\": \"" + time.getHoraInicio()+ "\",\n";
	if (!"".equals(time.getHoraFim()))
	    jsonDoc += "   \"Hora fim da aula\": \"" + time.getHoraFim()+ "\",\n";
	if (!"".equals(time.getData()))
	    jsonDoc += "   \"Data da aula\": \"" + time.getData()+ "\",\n";
	if (!"".equals(sala))
	    jsonDoc += "   \"Sala atribuída à aula\": \"" + sala + "\",\n";
	if (lotacao!=-1)
	    jsonDoc += "   \"Lotação da sala\": \"" + lotacao + "\",\n";
	
//	System.out.println(jsonDoc);
	jsonDoc = StringUtils.removeEnd(jsonDoc, ",\n");
	jsonDoc += "\n }";

	return jsonDoc;
    }

  

    @Override
    public boolean equals(Object obj)
    {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Lesson other = (Lesson) obj;
	return Objects.equals(curso, other.curso) && Objects.equals(time.getData(), other.time.getData())
		&& Objects.equals(time.getDiaDaSemana(), other.time.getDiaDaSemana()) && Objects.equals(time.getHoraFim(), other.time.getHoraFim())
		&& Objects.equals(time.getHoraInicio(), other.time.getHoraInicio()) && inscritosNoTurno == other.inscritosNoTurno
		&& lotacao == other.lotacao && Objects.equals(sala, other.sala) && Objects.equals(turma, other.turma)
		&& Objects.equals(turno, other.turno) && Objects.equals(unidadeCurricular, other.unidadeCurricular);
    }
    
    public boolean isOverlaid(Lesson l) {
	return !this.equals(l) && this.time.overlaps(l.time)&& this.sala.equals(l.sala)  && !"".equals(this.sala);
    }

    
}
