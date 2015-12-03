/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "carService")
@ApplicationScoped
public class CarService {
    
    private final static String[] colors;
        
        private final static String[] brands;
        
         private final static String[] abogados;
    
    static {
                colors = new String[10];
                colors[0] = "3";
                colors[1] = "4";
                colors[2] = "7";
                colors[3] = "2";
                colors[4] = "9";
                colors[5] = "4";
                colors[6] = "2";
                colors[7] = "2";
                colors[8] = "5";
                colors[9] = "6";
                
                abogados = new String[10];
                abogados[0] = "Kevin	Hart";
                abogados[1] = "Harry	James";
                abogados[2] = "Neil	Clarkson";
                abogados[3] = "Cameron	Walsh";
                abogados[4] = "Brandon	Wallace";
                abogados[5] = "Kevin	Hart";
                abogados[6] = "Harry	James";
                abogados[7] = "Neil	Clarkson";
                abogados[8] = "Cameron	Walsh";
                abogados[9] = "Brandon	Wallace";
                
                brands = new String[10];
                brands[0] = "Caso 1 Demanda" +": " +  "Fase 2: Entrega de documentos";
                brands[1] = "Caso 3 Despido" +": " +  "Fase 3: Fin";
                brands[2] = "Caso 1 Demanda" +": " +  "Fase 2: Entrega de documentos";
                brands[3] = "Caso 1 Robo" +": " +  "Fase 5: Entrega de documentos";
                brands[4] = "Caso 2 Demanda" +": " +  "Fase 2: Entrega de documentos";
                brands[5] = "Caso 7 Plagio" +": " +  "Fase 6: Pruebass";
                brands[6] = "Caso 7 Demanda" +": " +  "Fase 2: Entrega de documentos";
                brands[7] = "Caso 1 Demanda" +": " +  "Fase 7: Entrega de documentos";
                brands[8] = "Caso 10 Robo" +": " +  "Fase 2: Entrega de Testimonios";
                brands[9] = "Caso 8 Demanda" +": " +  "Fase 4: Entrega de documentos";
        }
    
    public List<Car> createCars(int size) {
        List<Car> list = new ArrayList<Car>();
                for(int i = 0 ; i < size ; i++) {
                        list.add(new Car(getRandomId(), getRandomYear(),getRandomBrand(),  getRandomColor(),getRandomPrice(), getRandomSoldState()));
                        
                }
                
        return list;
    }
    
    private String getRandomId() {
                return abogados[(int) (Math.random() * 10)];
        }
    
    private int getRandomYear() {
                return (int) (Math.random() * 50 + 0);
        }
        
        private String getRandomColor() {
                return colors[(int) (Math.random() * 10)];
        }
        
        private String getRandomBrand() {
                return brands[(int) (Math.random() * 10)];
        }
    
    private int getRandomPrice() {
                return (int) (Math.random() * 100000);
        }
    
    private boolean getRandomSoldState() {
                return (Math.random() > 0.5) ? true: false;
        }
    
    public List<String> getColors() {
        return Arrays.asList(colors);
    }
    
    public List<String> getBrands() {
        return Arrays.asList(brands);
    }
}