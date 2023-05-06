package gestaohorarios;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

/**
 * 
 * A classe Lesson representa uma aula.
 *
 */
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

    /**
     * Construtor que cria uma aula a partir de um JSONObject.
     * 
     * @param object JSONObject que representa uma aula.
     */
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

    /**
     * 
     * Método que retorna o nome do curso da aula.
     * 
     * @return o nome do curso da aula.
     */
    public String getCurso()
    {
	return curso;
    }

    /**
     * 
     * Método que retorna o nome da unidade curricular da aula.
     * 
     * @return o nome da unidade curricular da aula.
     */
    public String getUnidadeCurricular()
    {
	return unidadeCurricular;
    }

    /**
     * 
     * Método que retorna o turno da aula.
     * 
     * @return o turno da aula.
     */
    public String getTurno()
    {
	return turno;
    }

    /**
     * 
     * Método que retorna a turma da aula.
     * 
     * @return a turma da aula.
     */
    public String getTurma()
    {
	return turma;
    }

    /**
     * 
     * Método que retorna o número de inscritos no turno da aula.
     * 
     * @return o número de inscritos no turno da aula.
     */
    public int getInscritosNoTurno()
    {
	return inscritosNoTurno;
    }

    /**
     * 
     * Método que retorna o dia da semana da aula.
     * 
     * @return o dia da semana da aula.
     */
    public String getDiaDaSemana()
    {
	return time.getDiaDaSemana();
    }

    /**
     * 
     * Método que retorna a hora de início da aula.
     * 
     * @return a hora de início da aula.
     */
    public String getHoraInicio()
    {
	return time.getHoraInicio();
    }

    /**
     * 
     * Método que retorna a hora de fim da aula.
     * 
     * @return a hora de fim da aula.
     */
    public String getHoraFim()
    {
	return time.getHoraFim();
    }

    /**
     * 
     * Método que retorna a data da aula.
     * 
     * @return a data da aula.
     */
    public String getData()
    {
	return time.getData();
    }

    /**
     * 
     * Método que retorna a sala atribuída à aula.
     * 
     * @return a sala atribuída à aula.
     */
    public String getSala()
    {
	return sala;
    }

    /**
     * 
     * Método que retorna a lotação da sala da aula.
     * 
     * @return a lotação da sala da aula.
     */
    public int getLotacao()
    {
	return lotacao;
    }

    /**
     * 
     * Método que retorna o objeto LessonTime que representa a data e horário da
     * aula.
     * 
     * @return o objeto LessonTime que representa a data e horário da aula.
     */
    public LessonTime getTime()
    {
	return time;
    }

    /**
     * 
     * Verifica se a aula está com sobrelotação de acordo com a lotação da sala.
     * 
     * @return true se a aula estiver sobrelotada, false caso contrário.
     */
    public boolean isOverbooked()
    {
	return lotacao != -1 && lotacao < inscritosNoTurno;

    }

    /**
     * Método que devolve uma string que representa uma aula.
     * 
     * @return a string formatada que representa a aula.
     */
    @Override
    public String toString()
    {
	return "Lesson [curso=" + curso + ", unidadeCurricular=" + unidadeCurricular + ", turno=" + turno + ", turma="
		+ turma + ", inscritosNoTurno=" + inscritosNoTurno + ", diaDaSemana=" + time.getDiaDaSemana()
		+ ", horaInicio=" + time.getHoraInicio() + ", horaFim=" + time.getHoraFim() + ", data=" + time.getData()
		+ ", sala=" + sala + ", lotacao=" + lotacao + "]";
    }

    /**
     * Método que retorna uma string que representa a aula no formato JSON.
     * 
     * @return a string formatada que representa a aula em formato JSON.
     */
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
	if (inscritosNoTurno != -1)
	    jsonDoc += "   \"Inscritos no turno\": \"" + inscritosNoTurno + "\",\n";
	if (!"".equals(time.getDiaDaSemana()))
	    jsonDoc += "   \"Dia da semana\": \"" + time.getDiaDaSemana() + "\",\n";
	if (!"".equals(time.getHoraInicio()))
	    jsonDoc += "   \"Hora início da aula\": \"" + time.getHoraInicio() + "\",\n";
	if (!"".equals(time.getHoraFim()))
	    jsonDoc += "   \"Hora fim da aula\": \"" + time.getHoraFim() + "\",\n";
	if (!"".equals(time.getData()))
	    jsonDoc += "   \"Data da aula\": \"" + time.getData() + "\",\n";
	if (!"".equals(sala))
	    jsonDoc += "   \"Sala atribuída à aula\": \"" + sala + "\",\n";
	if (lotacao != -1)
	    jsonDoc += "   \"Lotação da sala\": \"" + lotacao + "\",\n";

	jsonDoc = StringUtils.removeEnd(jsonDoc, ",\n");
	jsonDoc += "\n }";

	return jsonDoc;
    }

    /**
     * Método que compara esta aula com o objeto especificado para igualdade.
     * 
     * @param obj Objeto a ser comparado com esta aula para igualdade.
     * @return true se o objeto especificado é igual a esta aula, false caso
     *         contrário.
     */
    @Override
    public boolean equals(Object obj)
    {
	if (this == obj)
	    return true;
	if (obj == null || getClass() != obj.getClass())
	    return false;
	Lesson other = (Lesson) obj;
	return curso.equals(other.curso) && time.equals(other.time) && inscritosNoTurno == other.inscritosNoTurno
		&& lotacao == other.lotacao && sala.equals(other.sala) && turma.equals(other.turma)
		&& turno.equals(other.turno) && unidadeCurricular.equals(other.unidadeCurricular);
    }

    /**
     * Método que verifica se esta aula e outra aula se sobrepõem no tempo e na
     * sala. Duas aulas são consideradas sobrepõem se tiverem horários que se
     * sobrepõem e ocorrerem na mesma sala.
     * 
     * @param l Aula a ser verificada para sobreposição.
     * @return true se as aulas se sobrepõem, false caso contrário.
     */
    public boolean isOverlaid(Lesson l)
    {
	return !this.equals(l) && this.time.overlaps(l.time) && this.sala.equals(l.sala) && !"".equals(this.sala);
    }

}
