package utils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeHelper {

    public DateTimeHelper(){}

    public String buscarDataHoraAtual(){
        Calendar calendar = Calendar.getInstance();
        //DD-MM-YYYY HH:MM

        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);

        calendar.set(ano, mes, dia, hora, minuto);

        Date data = calendar.getTime();
        DateFormat dataFormat = DateFormat.getDateTimeInstance();
        return dataFormat.format(data);
    }
}
