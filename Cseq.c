/*----------------------------------------------------------------
*
* Multiprocesadores: C
* Fecha: 27-Nov-2020
* Autor: A01273527 Franco Garcia Pedregal
*        
*
*--------------------------------------------------------------*/

#include <stdio.h>
#include <stdlib.h>
#include "utils.h"
#include <math.h>
//#include <omp.h>
#define size 100000000 //1e8
float Pi(){
    float resultado, n;
    resultado;
    
    //#pragma omp parallel for shared(n) reduction(+:resultado)
    for(int j=0; j<size ; j++){
        float aux;
        aux=pow(-1.0,j)/((2.0*j+2.0)*(2.0*j+3.0)*(2.0*j+4.0));
        resultado+=aux;
       
    }
    resultado=(resultado*4.0)+3.0;

    return resultado;
}

int main(int argc, char* argv[]) {
	int i;
	double ms, result;
	
	printf("Starting...\n");
	ms = 0;
	for (i = 0; i < N; i++) {
		start_timer();
		
		result = Pi();
		
		ms += stop_timer();
	}
	printf("sum = %lf\n", result);
	printf("avg time = %.5lf ms\n", (ms / N));
	
	return 0;
}
