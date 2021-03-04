// =================================================================
//
// File: Lab4_S.java
// Author: Pedro Perez
// Description: This file contains the code to perform the numerical 
//				integration of a function within a defined interval. 
//				The time this implementation takes will be used as 
//				the basis to calculate the improvement obtained with 
//				parallel technologies.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================
//*
//* Multiprocesadores: C
//* Fecha: 27-Nov-2020
//* Autor: A01273527 Franco García Pedregal
//         
import java.lang.*;
public class JavaSeq{
    
	private static final int RECTS = 100000000; //1e8
	private double resultado;
	
	public JavaSeq(){
		
	}
	
	public double getResult() {
		return resultado;
	}
	
	public void calculate() {
		resultado = 0;
		for (int i = 0; i < RECTS; i++) {
            double aux;
			aux=(double)Math.pow(-1,i)/((double)(2*i+2)*(double)(2*i+3)*(double)(2*i+4));
            resultado+=aux;
        }
        resultado=(4*resultado)+3;
	}
	
	public static void main(String args[]) {
		long startTime, stopTime;
		double ms;
		
		System.out.printf("Starting...\n");
		ms = 0;
		JavaSeq e = new JavaSeq();
		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();
			
			e.calculate();
			
			stopTime = System.currentTimeMillis();
			
			ms += (stopTime - startTime);
		}
		System.out.printf("result = %.5f\n", e.getResult());
		System.out.printf("avg time = %.5f\n", (ms / Utils.N));
	}
}