	org $1000
START:
	JSR SCREENSIZE
* -->[GOTO, null, null, run]
	JMP run
* -->[ASIG, 4, null, t1]
	MOVE.W #4,t1_0
* -->[ASIG, t1, null, hola]
	MOVE.W t1_0,hola_0
* -->[SKIP, null, null, factorial]
factorial:
* -->[PMB, null, null, factorial]
	MOVE.W 6(A7),num_1
* -->[ASIG, false, null, t2]
	MOVE.B #0,t2_1
* -->[ASIG, t2, null, posible]
	MOVE.B t2_1,posible_1
* -->[ASIG, 1, null, t3]
	MOVE.W #1,t3_1
* -->[NIGUALES, num, t3, e1]
	MOVE.W num_1,D1
	MOVE.W t3_1,D0
	CMP.W D0,D1
	BNE e1
* -->[GOTO, null, null, e2]
	JMP e2
* -->[SKIP, null, null, e1]
e1:
* -->[ASIG, -1, null, t4]
	MOVE.B #-1,t4_1
* -->[GOTO, null, null, e3]
	JMP e3
* -->[SKIP, null, null, e2]
e2:
* -->[ASIG, 0, null, t4]
	MOVE.B #0,t4_1
* -->[SKIP, null, null, e3]
e3:
* -->[IGUALES, t4, -1, e4]
	MOVE.B t4_1,D1
	EXT.W D1
	MOVE.W #-1,D0
	CMP.W D0,D1
	BEQ e4
* -->[GOTO, null, null, e5]
	JMP e5
* -->[SKIP, null, null, e4]
e4:
* -->[ASIG, true, null, t5]
	MOVE.B #-1,t5_1
* -->[ASIG, t5, null, posible]
	MOVE.B t5_1,posible_1
* -->[ASIG, num, null, aux]
	MOVE.W num_1,aux_1
* -->[ASIG, 1, null, t6]
	MOVE.W #1,t6_1
* -->[RESTA, num, t6, t7]
	MOVE.W num_1,D1
	MOVE.W t6_1,D0
	JSR IRESTA
	MOVE.W D1,t7_1
* -->[ASIG, t7, null, num]
	MOVE.W t7_1,num_1
* -->[SKIP, null, null, e6]
e6:
* -->[ASIG, 0, null, t8]
	MOVE.W #0,t8_1
* -->[MAYORQUE, num, t8, e7]
	MOVE.W num_1,D1
	MOVE.W t8_1,D0
	CMP.W D0,D1
	BGT e7
* -->[GOTO, null, null, e8]
	JMP e8
* -->[SKIP, null, null, e7]
e7:
* -->[ASIG, -1, null, t9]
	MOVE.B #-1,t9_1
* -->[GOTO, null, null, e9]
	JMP e9
* -->[SKIP, null, null, e8]
e8:
* -->[ASIG, 0, null, t9]
	MOVE.B #0,t9_1
* -->[SKIP, null, null, e9]
e9:
* -->[IGUALES, t9, -1, e10]
	MOVE.B t9_1,D1
	EXT.W D1
	MOVE.W #-1,D0
	CMP.W D0,D1
	BEQ e10
* -->[GOTO, null, null, e11]
	JMP e11
* -->[SKIP, null, null, e10]
e10:
* -->[MULT, aux, num, t10]
	MOVE.W aux_1,D0
	EXT.L D0
	MOVE.W num_1,D1
	EXT.L D1
	MULS.W D0,D1
	MOVE.W D1,t10_1
* -->[ASIG, t10, null, aux]
	MOVE.W t10_1,aux_1
* -->[ASIG, 1, null, t11]
	MOVE.W #1,t11_1
* -->[RESTA, num, t11, t12]
	MOVE.W num_1,D1
	MOVE.W t11_1,D0
	JSR IRESTA
	MOVE.W D1,t12_1
* -->[ASIG, t12, null, num]
	MOVE.W t12_1,num_1
* -->[GOTO, null, null, e6]
	JMP e6
* -->[SKIP, null, null, e11]
e11:
* -->[RTN, null, null, aux]
	MOVE.W aux_1,4(A7)
	RTS
* -->[SKIP, null, null, e5]
e5:
* -->[RTN, null, null, num]
	MOVE.W num_1,4(A7)
	RTS
* -->[RTN, null, null, null]
	RTS
* -->[SKIP, null, null, run]
run:
* -->[IN, null, null, t13]
	SUBA.L #2,A7
	JSR GETINT
	MOVE.W (A7)+,t13_0
* -->[ASIG, t13, null, i]
	MOVE.W t13_0,i_0
* -->[PARAM, null, null, i]
	MOVE.W i_0,-(A7)
* -->[CALL, t14, null, factorial]
	SUBA.L #2,A7
	JSR factorial
	MOVE.W (A7)+,t14_0
	ADDA.L #2,A7
* -->[OUT, t14, null, null]
	MOVE.W t14_0,-(A7)
	JSR PRINTINT
	ADDA.L #4,A7
	SIMHALT
 
hola_0: DS.W 1
t1_0: DS.W 1
num_1: DS.W 1
posible_1: DS.B 1
t2_1: DS.B 1
	DC.W 0
t3_1: DS.W 1
t4_1: DS.B 1
t5_1: DS.B 1
	DC.W 0
aux_1: DS.W 1
t6_1: DS.W 1
t7_1: DS.W 1
t8_1: DS.W 1
t9_1: DS.B 1
	DC.W 0
t10_1: DS.W 1
t11_1: DS.W 1
t12_1: DS.W 1
i_0: DS.W 1
t13_0: DS.W 1
t14_0: DS.W 1
	DC.W 0
 
SCREENSIZE:
	MOVE.L #1024*$10000+768,D1
	MOVE.B  #33,D0
	TRAP    #15
	RTS
ISUMA:
	BTST.L #15,D0
	BEQ ADD2
	NOT.W D0
	ADDQ.W #1,D0
	BTST.L #15,D1
	BEQ ADD1
	NOT.W D1
	ADDQ.W #1,D1
	ADD.W D0,D1
	NOT.W D1
	ADDQ.W #1,D1
	JSR ADD4
ADD1:
	SUB.W D0,D1
	JSR ADD4
ADD2:
	BTST.L #15,D1
	BEQ ADD3
	NOT.W D1
	ADDQ.W #1,D1
	SUB.W D1,D0
	MOVE.W  D0,D1
	JSR ADD4
ADD3:
	ADD.W D0,D1
ADD4:
	RTS
IRESTA:
	BTST.L #15,D1
	BEQ SUB2
	NOT.W D1
	ADDQ.W #1,D1
	BTST.L #15,D0
	BEQ SUB1
	NOT.W D0
	ADDQ.W #1,D0
	SUB.W D0,D1
	NOT.W D1
	ADDQ.W #1,D1
	RTS
SUB1:
	ADD.W D0,D1
	NOT.W D1
	ADDQ.W #1,D1
	RTS
SUB2:
	BTST.L #15,D0
	BEQ SUB3
	NOT.W D0
	ADDQ.W #1,D0
	ADD.W D0,D1
	RTS
SUB3:
	SUB.W D0,D1
	RTS
PRINTINT:
	MOVE.W 4(A7),D1
	MOVE.W D1,-(A7)
	JSR IPRINT
	ADDA.W #2,A7
	MOVE.L #11,D0
	MOVE.W #$00FF,D1
	TRAP #15
	ADD.W #1,D1
	AND.W #$00FF,D1
	TRAP #15
	RTS
IPRINT:
	CLR.L D1
	MOVE.W 4(A7),D1
	EXT.L D1
	MOVE.L #3,D0
	TRAP #15
	RTS
GETINT:
	MOVE.L #4,D0
	TRAP #15
	MOVE.W D1,4(A7)
	RTS
	END START
