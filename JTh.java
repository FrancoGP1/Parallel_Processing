// =================================================================
//
// File: Example4.java
// Author: Pedro Perez
// Description: This file implements the multiplication of a matrix 
//				by a vector. The time this implementation takes will
//				be used as the basis to calculate the improvement 
//				obtained with parallel technologies.
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

public class JTh extends Thread{
    private static final int RECTS = 1_000_000_00;
    private double resultado;
    private int start, end;

    public JTh(int start, int end) {
		this.resultado = 0;
		this.start = start;
		this.end = end;
	}

    public double getResult() {
		return resultado;
	}

    public void run() {
		resultado = 0;
		for (int i = start; i < end; i++) {
            double aux;
			aux=(double)Math.pow(-1,i)/((double)(2*i+2)*(double)(2*i+3)*(double)(2*i+4));
            resultado+=aux;
		}
	}

    public static void main(String args[]) {
		long startTime, stopTime;
		int block;
		JTh threads[];
		double ms, result = 0;

		
		block = RECTS / Utils.MAXTHREADS;
		threads = new JTh[Utils.MAXTHREADS];
		
		System.out.printf("Starting with %d threads...\n", Utils.MAXTHREADS);
		ms = 0;
		for (int j = 1; j <= Utils.N; j++) {
			for (int i = 0; i < threads.length; i++) {
				if (i != threads.length - 1) {
					threads[i] = new JTh( (i * block), ((i + 1) * block));
				} else {
					threads[i] = new JTh((i * block), RECTS);
				}
			}
			
			startTime = System.currentTimeMillis();
			for (int i = 0; i < threads.length; i++) {
				threads[i].start();
			}
			for (int i = 0; i < threads.length; i++) {
				try {
					threads[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			stopTime = System.currentTimeMillis();
			ms +=  (stopTime - startTime);
			
			if (j == Utils.N) {
				result = 0;
				for (int i = 0; i < threads.length; i++) {
					result += threads[i].getResult();
				}
			}
		}
        result=(result*4)+3;
		System.out.printf("result = %.5f\n", result);
		System.out.printf("avg time = %.5f\n", (ms / Utils.N));
	}
}