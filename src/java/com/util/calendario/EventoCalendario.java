/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util.calendario;

import java.io.Serializable;
import java.util.Date;
import org.primefaces.model.ScheduleEvent;
import procuradoria.map.*;

/**
 *
 * @author Ivan
 */
public class EventoCalendario implements ScheduleEvent, Serializable {

    private String id;
    private String title;
    private Date startDate;
    private Date endDate;
    private boolean allDay;
    private String styleClass;
    private Object data;
    private boolean editable;
    private String description;
    private Uztfuncionario funcionario;
    private Uztcaso caso;
    private Uztcita cita;

    public EventoCalendario() {
    }

    public EventoCalendario(String title, Date start, Date end) {
        this.title = title;
        this.startDate = start;
        this.endDate = end;
    }

    public EventoCalendario(Uztfuncionario funcionario, Uztcaso caso, Uztcita cita) {
        this.funcionario = funcionario;
        this.caso = caso;
        this.cita = cita;
    }

    public Uztfuncionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Uztfuncionario funcionario) {
        this.funcionario = funcionario;
    }

    public Uztcaso getCaso() {
        return caso;
    }

    public void setCaso(Uztcaso caso) {
        this.caso = caso;
    }

    public Uztcita getCita() {
        return cita;
    }

    public void setCita(Uztcita cita) {
        this.cita = cita;
    }

    @Override
    public String getId() {
        return id;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setId(String string) {
        this.id = string; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getData() {
        return data;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTitle() {
        return title;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date getStartDate() {
        return startDate;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date getEndDate() {
        return endDate;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAllDay() {
        return allDay;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getStyleClass() {
        return styleClass;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEditable() {
        return editable;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDescription() {
        return description;//To change body of generated methods, choose Tools | Templates.
    }

}
