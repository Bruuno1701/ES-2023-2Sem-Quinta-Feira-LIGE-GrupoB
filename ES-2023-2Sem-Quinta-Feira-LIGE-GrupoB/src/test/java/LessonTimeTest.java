import static org.junit.Assert.*;

import org.junit.Test;

public class LessonTimeTest {

    @Test
    public void testGetDiaDaSemana() {
        LessonTime lesson = new LessonTime("Segunda-feira", "10:00", "12:00", "2023-05-02");
        assertEquals("Segunda-feira", lesson.getDiaDaSemana());
    }

    @Test
    public void testGetHoraInicio() {
        LessonTime lesson = new LessonTime("Segunda-feira", "10:00", "12:00", "2023-05-02");
        assertEquals("10:00", lesson.getHoraInicio());
    }

    @Test
    public void testGetHoraFim() {
        LessonTime lesson = new LessonTime("Segunda-feira", "10:00", "12:00", "2023-05-02");
        assertEquals("12:00", lesson.getHoraFim());
    }

    @Test
    public void testGetData() {
        LessonTime lesson = new LessonTime("Segunda-feira", "10:00", "12:00", "2023-05-02");
        assertEquals("2023-05-02", lesson.getData());
    }

    @Test
    public void testEquals() {
        LessonTime lesson1 = new LessonTime("Segunda-feira", "10:00", "12:00", "2023-05-02");
        LessonTime lesson2 = new LessonTime("Segunda-feira", "10:00", "12:00", "2023-05-02");
        LessonTime lesson3 = new LessonTime("Terça-feira", "14:00", "16:00", "2023-05-03");
        
        assertTrue(lesson1.equals(lesson2));
        assertTrue(lesson2.equals(lesson1));
        assertFalse(lesson1.equals(lesson3));
        assertFalse(lesson2.equals(lesson3));
    }

    @Test
    public void testOverlaps() {
        LessonTime lesson1 = new LessonTime("Segunda-feira", "10:00", "12:00", "2023-05-02");
        LessonTime lesson2 = new LessonTime("Segunda-feira", "11:00", "13:00", "2023-05-02");
        LessonTime lesson3 = new LessonTime("Terça-feira", "14:00", "16:00", "2023-05-03");
        
        assertTrue(lesson1.overlaps(lesson2));
        assertTrue(lesson2.overlaps(lesson1));
        assertFalse(lesson1.overlaps(lesson3));
        assertFalse(lesson2.overlaps(lesson3));
    }
    
    @Test
    public void testGetOverlay() {
        LessonTime aula1 = new LessonTime("Segunda", "08:00", "10:00", "2023-05-02");
        LessonTime aula2 = new LessonTime("Segunda", "09:00", "11:00", "2023-05-02");
        LessonTime aula3 = new LessonTime("Segunda", "12:00", "14:00", "2023-05-02");
        LessonTime aula4 = new LessonTime("Terça", "08:00", "10:00", "2023-05-03");
        
        // Verifica a sobreposição entre aula1 e aula2
        LessonTime overlay1 = aula1.getOverlay(aula2);
        assertNotNull(overlay1);
        assertEquals("Segunda", overlay1.getDiaDaSemana());
        assertEquals("09:00", overlay1.getHoraInicio());
        assertEquals("10:00", overlay1.getHoraFim());
        assertEquals("2023-05-02", overlay1.getData());

        // Verifica a sobreposição entre aula1 e aula3
        LessonTime overlay2 = aula1.getOverlay(aula3);
        assertNull(overlay2);

        // Verifica a sobreposição entre aula1 e aula4
        LessonTime overlay3 = aula1.getOverlay(aula4);
        assertNull(overlay3);
    }

}
