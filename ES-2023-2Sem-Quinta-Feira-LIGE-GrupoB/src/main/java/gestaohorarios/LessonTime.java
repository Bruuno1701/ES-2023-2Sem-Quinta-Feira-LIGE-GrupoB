package gestaohorarios;

public class LessonTime
{
    private String diaDaSemana;
    private String horaInicio;
    private String horaFim;
    private String data;

    /**
     * 
     * @param diaDaSemana
     * @param horaInicio
     * @param horaFim
     * @param data
     */
    public LessonTime(String diaDaSemana, String horaInicio, String horaFim, String data)
    {
	this.diaDaSemana = diaDaSemana;
	this.horaInicio = horaInicio;
	this.horaFim = horaFim;
	this.data = data;
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

    public boolean overlaps(LessonTime other)
    {
	return this.equals(other) || (this.data.equals(other.data) && ((this.horaInicio.compareTo(other.horaFim) < 0
		&& this.horaFim.compareTo(other.horaInicio) > 0)
		|| (other.horaInicio.compareTo(this.horaFim) < 0 && other.horaFim.compareTo(this.horaInicio) > 0)));
    }

    public LessonTime getOverlay(LessonTime other)
    {
	if (!this.overlaps(other))
	{
	    return null;
	}

	String horaInicio = this.horaInicio.compareTo(other.horaInicio) > 0 ? this.horaInicio : other.horaInicio;
	String horaFim = this.horaFim.compareTo(other.horaFim) < 0 ? this.horaFim : other.horaFim;

	return new LessonTime(this.diaDaSemana, horaInicio, horaFim, this.data);
    }

    @Override
    public boolean equals(Object obj)
    {
	if (this == obj)
	    return true;
	if (!(obj instanceof LessonTime))
	    return false;
	LessonTime other = (LessonTime) obj;
	if (data == null)
	{
	    if (other.data != null)
		return false;
	}
	else if (!data.equals(other.data))
	    return false;
	if (diaDaSemana == null)
	{
	    if (other.diaDaSemana != null)
		return false;
	}
	else if (!diaDaSemana.equals(other.diaDaSemana))
	    return false;
	if (horaFim == null)
	{
	    if (other.horaFim != null)
		return false;
	}
	else if (!horaFim.equals(other.horaFim))
	    return false;
	if (horaInicio == null)
	{
	    if (other.horaInicio != null)
		return false;
	}
	else if (!horaInicio.equals(other.horaInicio))
	    return false;
	return true;
    }

    @Override
    public String toString()
    {

	return horaInicio + "-" + horaFim + " " + data;
    }
}