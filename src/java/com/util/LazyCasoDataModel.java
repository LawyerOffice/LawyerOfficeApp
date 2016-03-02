/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Iterator;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import procuradoria.map.Uzatcaso;

/**
 *
 * @author FANNY
 */
public class LazyCasoDataModel extends LazyDataModel<Uzatcaso> {

    private List<Uzatcaso> datasource;

    //TOMAR EN CUENTA EL CONSTRUCTOR
    public LazyCasoDataModel(List<Uzatcaso> datasource) {
        this.datasource = datasource;
    }
    
    public LazyCasoDataModel() {
        this.datasource = new ArrayList<Uzatcaso>();
    }

    @Override
    public Uzatcaso getRowData(String rowKey) {
        for (Uzatcaso caso : datasource) {
            if (caso.getUzatcasoId().equals(rowKey)) {
                return caso;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Uzatcaso caso) {
        return caso.getUzatcasoId();
    }

        @Override
    public List<Uzatcaso> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<Uzatcaso> data = new ArrayList<Uzatcaso>();
 
        //filter
        for(Uzatcaso caso : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(caso.getClass().getField(filterProperty).get(caso));
 
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(caso);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorterCaso(sortField, sortOrder));
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
    
}
