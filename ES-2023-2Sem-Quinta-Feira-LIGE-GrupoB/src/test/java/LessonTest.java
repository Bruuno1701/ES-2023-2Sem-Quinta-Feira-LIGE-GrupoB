import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

import table.Lesson;

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
    private static final String JSON_TEXT2 = " {\n"
	    	+ "   \"Curso\": \"ME\",\n"
	    	+ "   \"Unidade Curricular\": \"Teoria dos Jogos e dos Contratos\",\n"
	    	+ "   \"Turno\": \"01789TP01\",\n"
	    	+ "   \"Turma\": \"MEA1\",\n"
	    	+ "   \"Inscritos no turno\": \"30\",\n"
	    	+ "   \"Dia da semana\": \"Sex\",\n"
	    	+ "   \"Hora início da aula\": \"13:00:00\",\n"
	    	+ "   \"Hora fim da aula\": \"14:00:00\",\n"
	    	+ "   \"Data da aula\": \"02/12/2022\",\n"
	    	+ "   \"Sala atribuída à aula\": \"AA2.25\",\n"
	    	+ "   \"Lotação da sala\": \"34\"\n"
	    	+ " }";
    private static final String JSON_TEXT3 = " {\n"
	    	+ "   \"Unidade Curricular\": \"Teoria dos Jogos e dos Contratos\",\n"
	    	+ "   \"Inscritos no turno\": \"30\",\n"
	    	+ "   \"Dia da semana\": \"Sex\",\n"
	    	+ "   \"Hora fim da aula\": \"14:00:00\",\n"
	    	+ "   \"Lotação da sala\": \"34\"\n"
	    	+ " }";
    private static final String JSON_TEXT4 = " {\n"
	    	+ "   \"Curso\": \"ME\",\n"
	    	+ "   \"Unidade Curricular\": \"Teoria dos Jogos e dos Contratos\",\n"
	    	+ "   \"Turno\": \"01789TP01\",\n"
	    	+ "   \"Turma\": \"MEA1\",\n"
	    	+ "   \"Inscritos no turno\": \"30\",\n"
	    	+ "   \"Dia da semana\": \"Sex\",\n"
	    	+ "   \"Hora início da aula\": \"13:00:00\",\n"
	    	+ "   \"Hora fim da aula\": \"14:00:00\",\n"
	    	+ "   \"Data da aula\": \"02/12/2022\",\n"
	    	+ "   \"Sala atribuída à aula\": \"AA2.24\",\n"
	    	+ "   \"Lotação da sala\": \"34\"\n"
	    	+ " }";
    
    public static final JSONObject JSON_OBJECT = new JSONObject(JSON_TEXT);
    public static final JSONObject JSON_OBJECT2 = new JSONObject(JSON_TEXT2);
    public static final JSONObject JSON_OBJECT3 = new JSONObject(JSON_TEXT3);
    public static final JSONObject JSON_OBJECT4 = new JSONObject(JSON_TEXT4);
    
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
    public void testIsOverBooked()
    {
	assertFalse(new Lesson(JSON_OBJECT).isOverbooked());
	assertTrue(new Lesson(new JSONObject(" {\n" + "   \"Curso\": \"ME\",\n"
		+ "   \"Unidade Curricular\": \"Teoria dos Jogos e dos Contratos\",\n"
		+ "   \"Turno\": \"01789TP01\",\n" + "   \"Turma\": \"MEA1\",\n"
		+ "   \"Inscritos no turno\": \"36\",\n" + "   \"Dia da semana\": \"Sex\",\n"
		+ "   \"Hora início da aula\": \"13:00:00\",\n" + "   \"Hora fim da aula\": \"14:30:00\",\n"
		+ "   \"Data da aula\": \"02/12/2022\",\n" + "   \"Sala atribuída à aula\": \"AA2.25\",\n"
		+ "   \"Lotação da sala\": \"34\"\n" + " }")).isOverbooked());
    }

    @Test
    public void testToJSONDocument()
    {
	assertEquals(JSON_TEXT, new Lesson(JSON_OBJECT).toJSONDocument());
	assertEquals(JSON_TEXT3, new Lesson(JSON_OBJECT3).toJSONDocument());
    }

    
    @Test
    public void testIsOverlaid() {
	assertTrue(new Lesson(JSON_OBJECT).isOverlaid(new Lesson(JSON_OBJECT2)));
	assertFalse(new Lesson(JSON_OBJECT2).isOverlaid(new Lesson(JSON_OBJECT4)));
    }
    
    @Test
    public void testEquals() {
	Lesson l1 = new Lesson(JSON_OBJECT);
	Lesson l2 = new Lesson(JSON_OBJECT2);
	Lesson l3 = null;
	String s = "";
	
	assertTrue(l1.equals(l1));
	assertFalse(l2.equals(l1));
	assertFalse(l1.equals(l2));
	assertFalse(l1.equals(l3));
	assertFalse(l1.equals(s));
    }
    
    @Test
    public void testToString() {
	Lesson l1 = new Lesson(JSON_OBJECT);
	assertEquals("Lesson [curso=" + l1.getCurso() + ", unidadeCurricular=" + l1.getUnidadeCurricular() + ", turno=" + l1.getTurno() + ", turma="
		+ l1.getTurma() + ", inscritosNoTurno=" + l1.getInscritosNoTurno() + ", diaDaSemana=" + l1.getDiaDaSemana() + ", horaInicio="
		+ l1.getHoraInicio() + ", horaFim=" + l1.getHoraFim() + ", data=" + l1.getData() + ", sala=" + l1.getSala() + ", lotacao=" + l1.getLotacao()
		+ "]", l1.toString());
    }
}
