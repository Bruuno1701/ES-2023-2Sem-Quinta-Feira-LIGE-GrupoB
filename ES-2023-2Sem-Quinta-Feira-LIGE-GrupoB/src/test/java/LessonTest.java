import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

public class LessonTest
{
    private static final String JSON_TEXT = " {\n"
    	+ "   \"Curso\": \"ME\",\n"
    	+ "   \"Unidade Curricular\": \"Teoria dos Jogos e dos Contratos\",\n"
    	+ "   \"Turno\": \"01789TP01\",\n"
    	+ "   \"Turma\": \"MEA1\",\n"
    	+ "   \"Inscritos no turno\": \"30\",\n"
    	+ "   \"Dia da semana\": \"Sex\",\n"
    	+ "   \"Hora início da aula\": \"13:00:00\",\n"
    	+ "   \"Hora fim da aula\": \"14:30:00\",\n"
    	+ "   \"Data da aula\": \"02/12/2022\",\n"
    	+ "   \"Sala atribuída à aula\": \"AA2.25\",\n"
    	+ "   \"Lotação da sala\": \"34\"\n"
    	+ " }";
    public static final JSONObject JSON_OBJECT = new JSONObject(JSON_TEXT);

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
	assertTrue(new Lesson(new JSONObject(" {\n" + "   \"Curso\": \"ME\",\n"
		+ "   \"Unidade Curricular\": \"Teoria dos Jogos e dos Contratos\",\n"
		+ "   \"Turno\": \"01789TP01\",\n" + "   \"Turma\": \"MEA1\",\n"
		+ "   \"Inscritos no turno\": \"36\",\n" + "   \"Dia da semana\": \"Sex\",\n"
		+ "   \"Hora início da aula\": \"13:00:00\",\n" + "   \"Hora fim da aula\": \"14:30:00\",\n"
		+ "   \"Data da aula\": \"02/12/2022\",\n" + "   \"Sala atribuída à aula\": \"AA2.25\",\n"
		+ "   \"Lotação da sala\": \"34\"\n" + " }")).isSobrelotada());
    }

    @Test
    public void testToJSONDocument()
    {
	System.out.println(JSON_TEXT.equals(new Lesson(JSON_OBJECT).toJSONDocument()));
	System.out.println(JSON_TEXT.length());
	System.out.println(new Lesson(JSON_OBJECT).toJSONDocument().length());
	assertEquals(JSON_TEXT, new Lesson(JSON_OBJECT).toJSONDocument());
    }

}
