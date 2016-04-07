/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import static com.beans.ReasignarCasoBean.getDate;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatasign;
import procuradoria.map.UzatasignId;
import procuradoria.map.Uzatcaso;
import procuradoria.map.Uzatfunci;

/**
 *
 * @author FANNY
 */
public class ReasignacionMasivaRun extends Thread {

    Uzatfunci nuevofunci;
    Uzatasign nuevaasign;
    List<Uzatasign> casosSeleccionados;
    String motivo;
    BigDecimal idUser;
    private Boolean exito;

    public ReasignacionMasivaRun(Uzatfunci nuevofunci, Uzatasign nuevaasign, List<Uzatasign> casosSeleccionados, String motivo, BigDecimal idUser) {
        this.nuevofunci = nuevofunci;
        this.nuevaasign = nuevaasign;
        this.casosSeleccionados = casosSeleccionados;
        this.motivo = motivo;
        this.idUser = idUser;
    }

    @Override
    public void run() {

        nuevaasign.setUzatasignarMotivo(motivo);
        nuevaasign.setUzatasignarId(this.idUser);
        try {

            for (int i = 0; i < this.casosSeleccionados.size(); i++) {
                Uzatasign reasigM = new Uzatasign();
                reasigM.setUzatasignarMotivo(this.nuevaasign.getUzatasignarMotivo());
                reasigM.setUzatasignarFlag(BigDecimal.ONE);
                reasigM.setUzatasignarId(this.nuevaasign.getUzatasignarId());
                reasigM.setId(new UzatasignId(this.nuevofunci.getUzatfuncionarioId(), this.casosSeleccionados.get(i).getId().getUzatcasoId()));
                reasigM.setUzatasignarFechaIn(getDate());
                ProcuradoriaMethods.insertAsign(reasigM);
            }

            this.exito = true;

        } catch (Exception e) {
            this.exito = false;
        }

        if (getExito()) {
            try {
                this.finalize();
            } catch (Throwable ex) {
                //Logger.getLogger(ReasignacionMasivaRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            this.interrupt();
        }

    }

    public Boolean getExito() {
        return exito;
    }

}
