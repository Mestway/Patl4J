package com.mediaymedia.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;

import com.mediaymedia.gestion.model.CampoCalendar;

/**
 *
 * User: invitado
 * Date: 02-oct-2005
 * Time: 0:26:03
 * To change this template use File | Settings | File Templates.
 */
public abstract class Fechas {
    protected static Log log = LogFactory.getLog(Fechas.class);

    public static Calendar dameFechaCalendar(int dia, int mes, int anyo) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(anyo, mes, dia, 0, 0, 0);
        return calendar;
    }

    public static Calendar creaCalendarioResetExceptoHora(int hora) {
        Calendar cal = Calendar.getInstance();
        cal.set(CampoCalendar.anyoBase, CampoCalendar.mesBase, CampoCalendar.diaBase, hora, CampoCalendar.minutoBase, CampoCalendar.segundoBase);
        return cal;
    }

    public static Calendar calendarTemporalReset(Calendar c) {
        Calendar ret = Calendar.getInstance();
        ret.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
        return ret;
    }

    public static boolean coincidenFechasSinTiempo(Calendar c, Calendar prueba) {
        return (c.get(Calendar.YEAR) == prueba.get(Calendar.YEAR) &&
                c.get(Calendar.MONTH) == prueba.get(Calendar.MONTH) &&
                c.get(Calendar.DATE) == prueba.get(Calendar.DATE));
    }

    public static String dameFechaFormato(Calendar fecha) {
        return fecha.get(Calendar.DAY_OF_MONTH) + "/" + dameMes(fecha.get(Calendar.MONTH)) + "/" + fecha.get(Calendar.YEAR);
    }

    public static String dameFechaFormato(Calendar fecha, String separa) {
        return Moldeo.dosDigitos(fecha.get(Calendar.DAY_OF_MONTH)) + separa + Moldeo.dosDigitos(dameMes(fecha.get(Calendar.MONTH))) + separa + fecha.get(Calendar.YEAR);
    }

    public static String dameFechaHoraFormato(Calendar fecha) {
        return fecha.get(Calendar.DAY_OF_MONTH) + "/" + dameMes(fecha.get(Calendar.MONTH)) + "/" + fecha.get(Calendar.YEAR) + " " + Moldeo.dosDigitos(fecha.get(Calendar.HOUR_OF_DAY)) + ":" + Moldeo.dosDigitos(fecha.get(Calendar.MINUTE)) + ":" + Moldeo.dosDigitos(fecha.get(Calendar.SECOND));
    }

    public static String dameHoraMinutosFormato(Calendar fecha) {
        return Moldeo.dosDigitos(fecha.get(Calendar.HOUR_OF_DAY)) + ":" + Moldeo.dosDigitos(fecha.get(Calendar.MINUTE));
    }

    private static int dameMes(int mes) {
        int index = mes + 1;
        if (index == 13) return 1;
        return index;
    }

    public static final long MILLISECS_PER_MINUTE = 60 * 1000;
    public static final long MILLISECS_PER_HOUR = 60 * MILLISECS_PER_MINUTE;
    protected static final long MILLISECS_PER_DAY = 24 * MILLISECS_PER_HOUR;

    public static long diffDayPeriods(Calendar ini, Calendar end) {
        long endL = end.getTimeInMillis() + end.getTimeZone().getOffset(end.getTimeInMillis());
        long startL = ini.getTimeInMillis() + ini.getTimeZone().getOffset(ini.getTimeInMillis());
        return (endL - startL) / MILLISECS_PER_DAY;
    }

    public static boolean calendarBetweenCalendars(Calendar timeStamp, Calendar fechaInicio, Calendar fechaFin) {
        boolean cumple = false;
        if (fechaFin.getTimeInMillis() <= fechaInicio.getTimeInMillis())
            cumple = true;
        else
        if (timeStamp.getTimeInMillis() >= fechaInicio.getTimeInMillis() && timeStamp.getTimeInMillis() <= fechaFin.getTimeInMillis())
            cumple = true;
        log.debug(cumple + " __  " + Fechas.dameFechaHoraFormato(fechaInicio) + " >= " + Fechas.dameFechaHoraFormato(timeStamp) + " <= " + Fechas.dameFechaHoraFormato(fechaFin));
        return cumple;
    }

    public static boolean menorOIgualQue(Calendar fecha, Calendar fecha2) {
        return fecha.compareTo(fecha2)<=0;
    }
    public static boolean menorQue(Calendar fecha, Calendar fecha2) {
        return fecha.compareTo(fecha2)<0;
    }

    public static boolean mayorQue(Calendar fecha, Calendar fecha2) {
        return fecha.compareTo(fecha2)>0;
    }
    public static boolean mayorOIgualQue(Calendar fecha, Calendar fecha2) {
        return fecha.compareTo(fecha2)>=0;
    }
    public static boolean igualA(Calendar fecha, Calendar fecha2) {
        return fecha.compareTo(fecha2)==0;
    }

}
