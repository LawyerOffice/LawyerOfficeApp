/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatcaso;
import procuradoria.map.Uzatcita;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class CalendarBean {

    /**
     * Creates a new instance of CalendarBean
     */
    private ScheduleModel eventModel;
    private ArrayList<Uzatcita> ListCitas;
    private ScheduleEvent event = new DefaultScheduleEvent();

    public CalendarBean() {
        eventModel = new DefaultScheduleModel();
        this.init();
    }

    public void init() {
        this.setListCitas(new ArrayList<Uzatcita>());
        this.loadData();
    }

    public void loadData(){
        this.ListCitas.clear();
        this.ListCitas = ProcuradoriaMethods.GetCitasCalendar(StringToday());

        for (int i = 0; i < ListCitas.size(); i++) {
            eventModel.addEvent(new DefaultScheduleEvent(ListCitas.get(i).getUzatfase().getUzatcaso().getUzatcasoNumcausa(),
                    dateBegin(ListCitas.get(i).getUzatcitaFecha()), dateFinish(ListCitas.get(i).getUzatcitaFecha()),
                    "Materia: " + ListCitas.get(i).getUzatfase().getUzatcaso().getUzatjudi().getUzatmateri().getUzatmateriaDescripcion()
                            + ", Judicatura: " + ListCitas.get(i).getUzatfase().getUzatcaso().getUzatjudi().getUzatjudiDescripcion() + ", Sala: "
                            + ListCitas.get(i).getUzatcitaSala()));
        }

    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        System.out.println(calendar.toString());
        return calendar;
    }

    private String StringToday() {
        Calendar calendar = Calendar.getInstance();
        String fechaActual = "";
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        fechaActual = calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR) + " " + "00:00:00";
        System.out.println(fechaActual);
        return fechaActual;
    }

    private Date dateBegin(String FechaInicio){

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {
            cal.setTime(sdf.parse(FechaInicio));
        } catch (ParseException ex) {
            Logger.getLogger(CalendarBean.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            return cal.getTime();

    }

    private Date dateFinish(String FechaInicio) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {
            cal.setTime(sdf.parse(FechaInicio));
        } catch (ParseException ex) {
            Logger.getLogger(CalendarBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 2);
        
        return cal.getTime();
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(DefaultScheduleEvent event) {
        this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }

        event = new DefaultScheduleEvent();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public ArrayList<Uzatcita> getListCitas() {
        return ListCitas;
    }

    public void setListCitas(ArrayList<Uzatcita> ListCitas) {
        this.ListCitas = ListCitas;
    }

}
