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
	    inscritosNoTurno = Integer.parseInt(object.optString("Inscritos no turno", ""));
	    diaDaSemana = object.optString("Dia da semana", "");
	    horaInicio = object.optString("Hora início da aula", "");
	    horaFim = object.optString("Hora fim da aula", "");
	    data = object.optString("Data da aula", "");
	    sala = object.optString("Sala atribuída à aula", "");
	    lotacao = Integer.parseInt(object.optString("Lotação da sala", ""));
	} catch (NumberFormatException e)
	{
	    throw new IllegalArgumentException();
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
    
    public boolean isSobrelotada()
    {
	return lotacao < inscritosNoTurno;

    }

}
