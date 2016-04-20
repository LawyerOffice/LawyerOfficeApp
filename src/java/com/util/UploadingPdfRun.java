/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import procuradoria.map.Uzatdocs;
import procuradoria.pdf.util.DocumentsPdf;

/**
 *
 * @author Family Espin
 */
public class UploadingPdfRun extends Thread {

    Uzatdocs docs;
    String Url;

    private Boolean exito;

    public UploadingPdfRun(Uzatdocs docs, String Url) {
        this.docs = docs;
        this.Url = Url;
    }

    @Override
    public void run() {
        Boolean succesefull = DocumentsPdf.CovertPdfToByteArray(docs, Url);
        if (succesefull) {
            try {
                this.finalize();
                this.exito = true;
            } catch (Throwable ex) {
                //Logger.getLogger(ReasignacionMasivaRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.interrupt();
            this.exito = false;
        }
    }

    public Boolean getExito() {
        return exito;
    }

}
