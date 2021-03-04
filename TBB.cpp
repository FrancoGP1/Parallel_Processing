
// =================================================================
//
// File: example2.cpp
// Author: Pedro Perez
// Description: This file contains the code to perform the numerical
//				integration of a function within a defined interval
//				using Intel's TBB.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================
/*----------------------------------------------------------------

* Multiprocesadores: TBB

* Fecha: 27-Nov-2020

* Autor: A01273527 Franco Garcia Pedregal
*--------------------------------------------------------------*/
#include <iostream>
#include <iomanip>
#include <cmath>
#include <tbb/parallel_reduce.h>
#include <tbb/blocked_range.h>

#include <stdlib.h>
#include <time.h>
#include <sys/time.h>
#include <sys/types.h>
#include "utils.h"

using namespace std;
using namespace tbb;

const int RECTS = 100000000; //1e8

class Integration {
private:
	float pi;

public:
  	Integration() : pi(0){}
	Integration(Integration &obj, split) : pi(0) {}



	float getResult() const {
		return pi;
	}

	// 	void operator() (const blocked_range<int> &r) const {
	void operator() (const blocked_range<int> &r) {
		for (int i = r.begin(); i != r.end(); i++) {
		    float aux = (i % 2 == 0)? 1 : -1;
		    pi = pi + (aux*4.0) / (((2.0*i)+2.0)*((2.0*i)+3.0)*((2.0*i)+4.0));
		}
	}

	void join(const Integration &x) {
		pi += x.pi;
	}
};

int main(int argc, char* argv[]) {
	double ms;
	float pi = 0;


	cout << "Starting..." << endl;
	ms = 0;
	for (int i = 0; i < N; i++) {
		start_timer();

		Integration obj;
		parallel_reduce(blocked_range<int>(0, RECTS), obj);
		pi = obj.getResult();

		ms += stop_timer();
	}
	cout << "result = " << setprecision(15) << (pi+3.0) << endl;
	cout << "avg time = " << setprecision(15) << (ms / N) << " ms" << endl;

	return 0;
}
