/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.util.Comparator;
import org.primefaces.model.SortOrder;
import procuradoria.map.Uzatcaso;

/**
 *
 * @author FANNY
 */
public class LazySorterCaso implements Comparator<Uzatcaso> {

    private String sortField;
    private SortOrder sortOrder;

    public LazySorterCaso(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Uzatcaso o1, Uzatcaso o2) {

        try {
            Object value1 = Uzatcaso.class.getField(this.sortField).get(o1);
            Object value2 = Uzatcaso.class.getField(this.sortField).get(o2);
            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }
}
