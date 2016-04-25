/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Iterator;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatcaso;

/**
 *
 * @author FANNY
 */
public class LazyCasoDataModel extends LazyDataModel<Uzatcaso> {

    private List<Uzatcaso> datasource;
    private BigDecimal Flag;
    private BigDecimal uzatfuncionarioId;

    //TOMAR EN CUENTA EL CONSTRUCTOR
    public LazyCasoDataModel(List<Uzatcaso> datasource) {
        this.datasource = datasource;
    }

    public LazyCasoDataModel(BigDecimal Flag) {
        this.datasource = new ArrayList<Uzatcaso>();
        this.Flag = Flag;
    }

    public LazyCasoDataModel(BigDecimal uzatfuncionarioId, BigDecimal Flag) {
        this.datasource = new ArrayList<Uzatcaso>();
        this.Flag = Flag;
        this.uzatfuncionarioId = uzatfuncionarioId;
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
    public List<Uzatcaso> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Uzatcaso> data = new ArrayList<Uzatcaso>();

        if (Flag != null && uzatfuncionarioId != null) {
            datasource = ProcuradoriaMethods.FindCasosLazy(uzatfuncionarioId, Flag, first, pageSize);
        } else if (Flag != null) {
            datasource = ProcuradoriaMethods.FindCasosLazy(Flag, first, pageSize);
        }

        //filter
        for (Uzatcaso caso : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        Field field = caso.getClass().getDeclaredField(filterProperty);
                        field.setAccessible(true);
                        Class<?> targetType = field.getType();
                        Object objectValue = targetType.newInstance();
                        Object objectFieldValue = field.get(objectValue);
                        String fieldValue = objectFieldValue.toString();

                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }

            if (match) {
                data.add(caso);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(data, new LazySorterCaso(sortField, sortOrder));
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }

}
