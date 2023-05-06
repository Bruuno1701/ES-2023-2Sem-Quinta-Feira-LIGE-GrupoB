package gestaohorarios;

import java.util.Objects;

/**
 * A classe LessonTime o tempo em que decorre uma aula, com o dia da semana, a
 * hora de início, a hora de fim e a data.
 * 
 */
public class LessonTime
{

    private String diaDaSemana;
    private String horaInicio;
    private String horaFim;
    private String data;

    /**
     * Construtor que recebe o dia da semana, hora de início, hora de fim e data.
     *
     * @param diaDaSemana Dia de semana do tempo da aula.
     * @param horaInicio  A hora de início do tempo da aula.
     * @param horaFim     O horário de fim do tempo da aula.
     * @param data        A data do tempo da aula.
     */
    public LessonTime(String diaDaSemana, String horaInicio, String horaFim, String data)
    {
	this.diaDaSemana = diaDaSemana;
	this.horaInicio = horaInicio;
	this.horaFim = horaFim;
	this.data = data;
    }

    /**
     * Método que devolve o dia da semana do tempo da aula.
     *
     * @return O dia da semana do tempo da aula.
     */
    public String getDiaDaSemana()
    {
	return diaDaSemana;
    }

    /**
     * Método que devolve a hora de início do tempo da aula.
     *
     * @return a hora de início do tempo da aula.
     */
    public String getHoraInicio()
    {
	return horaInicio;
    }

    /**
     * Método que devolve a hora de fim do tempo da aula.
     *
     * @return a hora de fim do tempo da aula.
     */
    public String getHoraFim()
    {
	return horaFim;
    }

    /**
     * Método que devolve a data do tempo da aula.
     *
     * @return a data do tempo da aula.
     */
    public String getData()
    {
	return data;
    }

    /**
     * Método que verifica se este horário de aula se sobrepõe ao horário de outra
     * aula.
     *
     * @param other O outro tempo de aula para verificar a sobreposição.
     * @return true se os horários das aulas se sobrepuserem, false caso contrário.
     */
    public boolean overlaps(LessonTime other)
    {
	return this.equals(other) || (this.data.equals(other.data) && ((this.horaInicio.compareTo(other.horaFim) < 0
		&& this.horaFim.compareTo(other.horaInicio) > 0)
		|| (other.horaInicio.compareTo(this.horaFim) < 0 && other.horaFim.compareTo(this.horaInicio) > 0)));
    }

    /**
     * Método que devolve a interseção deste horário de aula com outro horário de
     * aula.
     *
     * @param other O outro tempo de aula para descobrir a interseção.
     * @return um novo objeto LessonTime representando a interseção, ou null se não
     *         há cruzamento.
     */
    public LessonTime getOverlay(LessonTime other)
    {
	if (!this.overlaps(other))
	{
	    return null;
	}

	String newHoraInicio = this.horaInicio.compareTo(other.horaInicio) > 0 ? this.horaInicio : other.horaInicio;
	String newHoraFim = this.horaFim.compareTo(other.horaFim) < 0 ? this.horaFim : other.horaFim;

	return new LessonTime(this.diaDaSemana, newHoraInicio, newHoraFim, this.data);
    }

    /**
     * Verifica se o tempo desta aula é igual a outro objeto.
     *
     * @param obj O outro objeto para comparar a igualdade.
     * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object obj)
    {
	if (!(obj instanceof LessonTime))
	    return false;
	LessonTime other = (LessonTime) obj;
	return Objects.equals(this.data, other.data) && Objects.equals(this.diaDaSemana, other.diaDaSemana)
		&& Objects.equals(this.horaInicio, other.horaInicio) && Objects.equals(this.horaFim, other.horaFim);
    }

    /**
     * Método que devolve uma string que representa um tempo de aula.
     * 
     * @return a string formatada que representa o tempo da aula.
     */
    @Override
    public String toString()
    {

	return horaInicio + "-" + horaFim + " " + data;
    }
}