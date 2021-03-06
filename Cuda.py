%%cu
 #include <stdio.h>
 #include <stdlib.h>
 #include "/content/src/header.h"
 #define MIN(a,b) (a<b?a:b)
 #define SIZE	1e8
 #define THREADS	256
 #define BLOCKS	MIN(32, (SIZE + THREADS - 1)/ THREADS)
 
 __global__ void sum(double *result) {
 	__shared__ double cache[THREADS];
 	
 	int tid = threadIdx.x + (blockIdx.x * blockDim.x);
 	int cacheIndex = threadIdx.x;
 	
 	double acum = 0;
 	while (tid < SIZE) {
 		acum += pow(-1,tid)/((2.0*tid+2.0)*(2.0*tid+3.0)*(2.0*tid+4.0));
 		tid += blockDim.x * gridDim.x;
 	}
 	
 	cache[cacheIndex] = acum;
 	
 	__syncthreads();
 	
 	int i = blockDim.x / 2;
 	while (i > 0) {
 		if (cacheIndex < i) {
 			cache[cacheIndex] += cache[cacheIndex + i];
 		}
 		__syncthreads();
 		i /= 2;
 	}
 	
 	if (cacheIndex == 0) {
 		result[blockIdx.x] = cache[cacheIndex];
 	}
 }
 
 int main(int argc, char* argv[]) {
 	int i, *array, *d_a;
 	double *results, *d_r;
 	double ms;
 	
 	results = (double*) malloc( BLOCKS * sizeof(double) );
 	cudaMalloc( (void**) &d_r, BLOCKS * sizeof(double) );
 	
 	printf("Starting...\n");
 	ms = 0;
 	for (i = 1; i <= N; i++) {
 		start_timer();
 		sum<<<BLOCKS, THREADS>>> (d_r);
 		ms += stop_timer();
 	}
 	
 	cudaMemcpy(results, d_r, BLOCKS * sizeof(double), cudaMemcpyDeviceToHost);
 	
 	double acum = 0;
 	for (i = 0; i < BLOCKS; i++) {
 		acum += results[i];
 	}
 	
 	printf("sum = %lf\n", ((acum*4.0)+3.0));
 	printf("avg time = %.5lf\n", (ms / N));
 	
 	cudaFree(d_r);
 	free(results);
 	return 0;
 }