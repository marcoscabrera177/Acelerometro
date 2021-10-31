package com.example.acelerometro;

import android.content.Context;
import android.graphics.*;
import android.hardware.*;
import android.view.*;

import java.util.ArrayList;
import java.util.Random;

public class MiPelota extends View implements SensorEventListener {
    Paint pincel = new Paint();
    int alto, ancho;
    int tamanio=200;
    int borde=12;
    float ejeX=0,ejeY=0,ejeZ=0;
    String X,Y,Z;

    public MiPelota(Context interfaz){
        super(interfaz);
        SensorManager smAdministrador = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor snsRotacion = smAdministrador.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        smAdministrador.registerListener( this, snsRotacion, SensorManager.SENSOR_DELAY_UI);
        Display pantalla = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        ancho=pantalla.getWidth();
        alto= pantalla.getHeight();

    }

    @Override
    public void onSensorChanged(SensorEvent cambio){
        ejeX-=cambio.values[0];
        X=Float.toString(ejeX);
        if(ejeX<(tamanio+borde)){
            ejeX=(tamanio+borde);
        }
        else if(ejeX > (ancho-(tamanio+borde))){
            ejeX= ancho-(tamanio+borde);
        }
        ejeY+=cambio.values[1];
        Y=Float.toString(ejeY);
        if(ejeY<(tamanio+borde)){
            ejeY=(tamanio+borde);
        }
        else if(ejeY > (alto-tamanio-170)){
            ejeY= alto-tamanio-170;
        }
        ejeZ=cambio.values[2];
        Z=String.format("%.2f",ejeZ);
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    @Override
    public void onDraw(Canvas lienzo){
        pincel.setColor(getRandomColor());
        lienzo.drawCircle(ejeX, ejeY, ejeZ+tamanio, pincel);
        pincel.setColor(Color.WHITE);
        pincel.setTextSize(50);
        //lienzo.drawText("Argentina",ejeX-10, ejeY+3, pincel);
        lienzo.drawText((String) pais(),ejeX-80, ejeY+30, pincel);
       
    }

    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static Object pais (){
        ArrayList paises =new ArrayList();
        paises.add("Argentina");
        paises.add("Brasil");
        paises.add("Chile");
        paises.add("España");
        paises.add("Francia");
        paises.add("Japon");
        paises.add("Inglaterra");
        paises.add("Alemania");
        paises.add("Egipto");
        paises.add("Suiza");
        int a= (int) (Math.random()*paises.size());
        return paises.get(a);
    }
}

