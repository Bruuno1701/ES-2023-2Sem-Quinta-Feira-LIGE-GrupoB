import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

public class LessonTest
{
    public static final JSONObject JSON_OBJECT = new JSONObject("{\r\n" + "   \"Curso\": \"ME\",\r\n"
	    + "   \"Unidade Curricular\": \"Teoria dos Jogos e dos Contratos\",\r\n"
	    + "   \"Turno\": \"01789TP01\",\r\n" + "   \"Turma\": \"MEA1\",\r\n"
	    + "   \"Inscritos no turno\": \"30\",\r\n" + "   \"Dia da semana\": \"Sex\",\r\n"
	    + "   \"Hora início da aula\": \"13:00:00\",\r\n" + "   \"Hora fim da aula\": \"14:30:00\",\r\n"
	    + "   \"Data da aula\": \"02/12/2022\",\r\n" + "   \"Sala atribuída à aula\": \"AA2.25\",\r\n"
	    + "   \"Lotação da sala\": \"34\"\r\n" + " }");

    @Test
    public void testLesson()
    {
	assertThrows(IllegalArgumentException.class, () -> {
	    new Lesson(null);
	});
	assertThrows(IllegalArgumentException.class, () -> {
	    new Lesson(new JSONObject());
	});

	try
	{
	    new Lesson(JSON_OBJECT);
	} catch (Exception e)
	{
	    fail("Error creating Lesson");
	}
    }

    @Test
    public void testGetCurso()
    {
	assertTrue(new Lesson(JSON_OBJECT).getCurso().equals("ME"));
    }

    @Test
    public void testGetUnidadeCurricular()
    {
	assertTrue(new Lesson(JSON_OBJECT).getUnidadeCurricular().equals("Teoria dos Jogos e dos Contratos"));
    }

    @Test
    public void testGetTurno()
    {
	assertTrue(new Lesson(JSON_OBJECT).getTurno().equals("01789TP01"));
    }

    @Test
    public void testGetTurma()
    {
	assertTrue(new Lesson(JSON_OBJECT).getTurma().equals("MEA1"));
    }

    @Test
    public void testGetInscritosNoTurno()
    {
	assertTrue(new Lesson(JSON_OBJECT).getInscritosNoTurno() == 30);
    }

    @Test
    public void testGetDiaDaSemana()
    {
	assertTrue(new Lesson(JSON_OBJECT).getDiaDaSemana().equals("Sex"));
    }

    @Test
    public void testGetHoraInicio()
    {
	assertTrue(new Lesson(JSON_OBJECT).getHoraInicio().equals("13:00:00"));
    }

    @Test
    public void testGetHoraFim()
    {
	assertTrue(new Lesson(JSON_OBJECT).getHoraFim().equals("14:30:00"));
    }

    @Test
    public void testGetData()
    {
	assertTrue(new Lesson(JSON_OBJECT).getData().equals("02/12/2022"));
    }

    @Test
    public void testGetSala()
    {
	assertTrue(new Lesson(JSON_OBJECT).getSala().equals("AA2.25"));
    }

    @Test
    public void testGetLotacao()
    {
	assertTrue(new Lesson(JSON_OBJECT).getLotacao() == 34);
    }

    @Test
    public void testIsSobrelotada()
    {
	assertFalse(new Lesson(JSON_OBJECT).isSobrelotada());
	assertTrue(new Lesson(new JSONObject("{\r\n" + "   \"Curso\": \"ME\",\r\n"
	    + "   \"Unidade Curricular\": \"Teoria dos Jogos e dos Contratos\",\r\n"
	    + "   \"Turno\": \"01789TP01\",\r\n" + "   \"Turma\": \"MEA1\",\r\n"
	    + "   \"Inscritos no turno\": \"36\",\r\n" + "   \"Dia da semana\": \"Sex\",\r\n"
	    + "   \"Hora início da aula\": \"13:00:00\",\r\n" + "   \"Hora fim da aula\": \"14:30:00\",\r\n"
	    + "   \"Data da aula\": \"02/12/2022\",\r\n" + "   \"Sala atribuída à aula\": \"AA2.25\",\r\n"
	    + "   \"Lotação da sala\": \"34\"\r\n" + " }")).isSobrelotada());
    }

}
