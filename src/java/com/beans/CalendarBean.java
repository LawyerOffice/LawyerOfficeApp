/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatcita;
import procuradoria.map.Uzatfunci;

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
    private Uzatcita selectedCita;
    private ScheduleEvent event = new DefaultScheduleEvent();
    
    private Uzatfunci funcionarioCreador;

    public CalendarBean() {
        eventModel = new DefaultScheduleModel();
        this.funcionarioCreador= new Uzatfunci();
        this.init();
    }

    public void init() {
        this.selectedCita = new Uzatcita();
        this.setListCitas(new ArrayList<Uzatcita>());
        this.loadData();
    }

    public void loadData() {
        this.ListCitas.clear();
        this.ListCitas = ProcuradoriaMethods.GetCitasCalendar(StringToday());
        BigDecimal id = this.getUserAttribute();

        for (int i = 0; i < ListCitas.size(); i++) {

            DefaultScheduleEvent obj;

            if (ListCitas.get(i).getUzatfase().getUzatcaso().getFuncionarioAsignado().getUzatfuncionarioId().equals(id)) {
                
                if (ListCitas.get(i).getUzatcitaFlag().equals(new BigDecimal(BigInteger.ZERO))) {
                    obj = new DefaultScheduleEvent(ListCitas.get(i).getUzatfase().getUzatcaso().getUzatcasoNumcausa(),
                            dateBegin(ListCitas.get(i).getUzatcitaFecha()),
                            dateFinish(ListCitas.get(i).getUzatcitaFecha()), "color2");
                } else {
                    obj = new DefaultScheduleEvent(ListCitas.get(i).getUzatfase().getUzatcaso().getUzatcasoNumcausa(),
                            dateBegin(ListCitas.get(i).getUzatcitaFecha()),
                            dateFinish(ListCitas.get(i).getUzatcitaFecha()), "color");
                }
            } else {
                if (ListCitas.get(i).getUzatcitaFlag().equals(new BigDecimal(BigInteger.ZERO))) {
                    obj = new DefaultScheduleEvent(ListCitas.get(i).getUzatfase().getUzatcaso().getUzatcasoNumcausa(),
                            dateBegin(ListCitas.get(i).getUzatcitaFecha()),
                            dateFinish(ListCitas.get(i).getUzatcitaFecha()), "color2");
                } else {
                    obj = new DefaultScheduleEvent(ListCitas.get(i).getUzatfase().getUzatcaso().getUzatcasoNumcausa(),
                            dateBegin(ListCitas.get(i).getUzatcitaFecha()),
                            dateFinish(ListCitas.get(i).getUzatcitaFecha()));
                }

            }

            obj.setDescription(ListCitas.get(i).getId().getUzatcitaId().toString());
            eventModel.addEvent(obj);
        }

    }
    
    private BigDecimal getUserAttribute() {
        String UserAttribute = "";
        BigDecimal id = new BigDecimal(BigInteger.ZERO);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session == null) {
        } else {
            Object IdBanner = session.getAttribute("uzatfuncionarioId");
            UserAttribute = IdBanner.toString();
            id = new BigDecimal(UserAttribute);
        }
        return id;
    }

    public Uzatcita getSelectedCita(BigDecimal uzatcitaId) {
        Uzatcita cita = new Uzatcita();
        for (Uzatcita cta : this.ListCitas) {
            if (cta.getId().getUzatcitaId().equals(uzatcitaId)) {
                cita = cta;
                break;
            }
        }
        return cita;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    private String StringToday() {
        Calendar calendar = Calendar.getInstance();
        String fechaActual = "";
        SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fechaActual = f1.format(calendar.getTime());
        return fechaActual;
    }

    private Date dateBegin(String FechaInicio) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {
            cal.setTime(sdf.parse(FechaInicio));
        } catch (ParseException ex) {
            System.out.println(">> " + ex.getMessage());
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
        String id = event.getDescription();
        BigDecimal uzatcitaId = new BigDecimal(id);
        this.selectedCita = getSelectedCita(uzatcitaId);
        this.funcionarioCreador =ProcuradoriaMethods.FindFuncionarioByIdFunci(this.selectedCita.getUzatfuncionarioId());
    }

    public ArrayList<Uzatcita> getListCitas() {
        return ListCitas;
    }

    public void setListCitas(ArrayList<Uzatcita> ListCitas) {
        this.ListCitas = ListCitas;
    }

    public Uzatcita getSelectedCita() {
        return selectedCita;
    }

    public void setSelectedCita(Uzatcita selectedCita) {
        this.selectedCita = selectedCita;
    }

    public Uzatfunci getFuncionarioCreador() {
        return funcionarioCreador;
    }

    public void setFuncionarioCreador(Uzatfunci funcionarioCreador) {
        this.funcionarioCreador = funcionarioCreador;
    }

}
