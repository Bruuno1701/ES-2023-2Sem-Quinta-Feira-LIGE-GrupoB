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
    private String diaDaSemana;
    private String horaInicio;
    private String horaFim;
    private String data;
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
	    diaDaSemana = object.optString("Dia da semana", "");
	    horaInicio = object.optString("Hora início da aula", "");
	    horaFim = object.optString("Hora fim da aula", "");
	    data = object.optString("Data da aula", "");
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
	return diaDaSemana;
    }

    public String getHoraInicio()
    {
	return horaInicio;
    }

    public String getHoraFim()
    {
	return horaFim;
    }

    public String getData()
    {
	return data;
    }

    public String getSala()
    {
	return sala;
    }

    public int getLotacao()
    {
	return lotacao;
    }

    public boolean isOverbooked()
    {
	return lotacao < inscritosNoTurno;

    }

    @Override
    public String toString()
    {
	return "Lesson [curso=" + curso + ", unidadeCurricular=" + unidadeCurricular + ", turno=" + turno + ", turma="
		+ turma + ", inscritosNoTurno=" + inscritosNoTurno + ", diaDaSemana=" + diaDaSemana + ", horaInicio="
		+ horaInicio + ", horaFim=" + horaFim + ", data=" + data + ", sala=" + sala + ", lotacao=" + lotacao
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
	if (!"".equals(diaDaSemana))
	    jsonDoc += "   \"Dia da semana\": \"" + diaDaSemana+ "\",\n";
	if (!"".equals(horaInicio))
	    jsonDoc += "   \"Hora início da aula\": \"" + horaInicio+ "\",\n";
	if (!"".equals(horaFim))
	    jsonDoc += "   \"Hora fim da aula\": \"" + horaFim+ "\",\n";
	if (!"".equals(data))
	    jsonDoc += "   \"Data da aula\": \"" + data+ "\",\n";
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
	return Objects.equals(curso, other.curso) && Objects.equals(data, other.data)
		&& Objects.equals(diaDaSemana, other.diaDaSemana) && Objects.equals(horaFim, other.horaFim)
		&& Objects.equals(horaInicio, other.horaInicio) && inscritosNoTurno == other.inscritosNoTurno
		&& lotacao == other.lotacao && Objects.equals(sala, other.sala) && Objects.equals(turma, other.turma)
		&& Objects.equals(turno, other.turno) && Objects.equals(unidadeCurricular, other.unidadeCurricular);
    }
    
    public boolean isOverlaid(Lesson l) {
	return !this.equals(l) && this.getData().equals(l.getData()) && this.getHoraInicio().equals(l.getHoraInicio());
    }

    
}
