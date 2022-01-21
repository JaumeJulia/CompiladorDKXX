	org $1000
START:
	JSR SCREENSIZE
* -->[GOTO, null, null, run]
	JMP run
* -->[SKIP, null, null, resto]
resto:
* -->[PMB, null, null, resto]
	MOVE.W 6(A7),m_1
	MOVE.W 8(A7),n_1
* -->[DIV, n_1, m_1, t1]
	MOVE.W n_1,D1
	EXT.L D1
	MOVE.W m_1,D0
	EXT.L D0
	DIVS.W D0,D1
	MOVE.W D1,t1_1
* -->[MULT, t1, m_1, t2]
	MOVE.W t1_1,D0
	EXT.L D0
	MOVE.W m_1,D1
	EXT.L D1
	MULS.W D0,D1
	MOVE.W D1,t2_1
* -->[RESTA, n_1, t2, t3]
	MOVE.W n_1,D1
	MOVE.W t2_1,D0
	JSR IRESTA
	MOVE.W D1,t3_1
* -->[RTN, null, null, t3]
	MOVE.W t3_1,4(A7)
	RTS
* -->[RTN, null, null, null]
	RTS
* -->[SKIP, null, null, run]
run:
* -->[IN, null, null, t4]
	SUBA.L #2,A7
	JSR GETINT
	MOVE.W (A7)+,t4_0
* -->[ASIG, t4, null, i_0]
	MOVE.W t4_0,i_0
* -->[ASIG, 0, null, t5]
	MOVE.W #0,t5_0
* -->[MENORIGU, i_0, t5, e1]
	MOVE.W i_0,D1
	MOVE.W t5_0,D0
	CMP.W D0,D1
	BLE e1
* -->[GOTO, null, null, e2]
	JMP e2
* -->[SKIP, null, null, e1]
e1:
* -->[ASIG, -1, null, t6]
	MOVE.B #-1,t6_0
* -->[GOTO, null, null, e3]
	JMP e3
* -->[SKIP, null, null, e2]
e2:
* -->[ASIG, 0, null, t6]
	MOVE.B #0,t6_0
* -->[SKIP, null, null, e3]
e3:
* -->[PARAM, null, null, i_0]
	MOVE.W i_0,-(A7)
* -->[ASIG, 2, null, t7]
	MOVE.W #2,t7_0
* -->[PARAM, null, null, t7]
	MOVE.W t7_0,-(A7)
* -->[CALL, t8, null, resto]
	SUBA.L #2,A7
	JSR resto
	MOVE.W (A7)+,t8_0
	ADDA.L #4,A7
* -->[ASIG, 0, null, t9]
	MOVE.W #0,t9_0
* -->[IGUALES, t8, t9, e4]
	MOVE.W t8_0,D1
	MOVE.W t9_0,D0
	CMP.W D0,D1
	BEQ e4
* -->[GOTO, null, null, e5]
	JMP e5
* -->[SKIP, null, null, e4]
e4:
* -->[ASIG, -1, null, t10]
	MOVE.B #-1,t10_0
* -->[GOTO, null, null, e6]
	JMP e6
* -->[SKIP, null, null, e5]
e5:
* -->[ASIG, 0, null, t10]
	MOVE.B #0,t10_0
* -->[SKIP, null, null, e6]
e6:
* -->[AND, t6, t10, e7]
	MOVE.B t6_0,D0
	MOVE.B t10_0,D1
	AND.B D0,D1
	BMI e7
* -->[GOTO, null, null, e8]
	JMP e8
* -->[SKIP, null, null, e7]
e7:
* -->[ASIG, -1, null, t11]
	MOVE.B #-1,t11_0
* -->[GOTO, null, null, e9]
	JMP e9
* -->[SKIP, null, null, e8]
e8:
* -->[ASIG, 0, null, t11]
	MOVE.B #0,t11_0
* -->[SKIP, null, null, e9]
e9:
* -->[IGUALES, t11, -1, e10]
	MOVE.B t11_0,D1
	EXT.W D1
	MOVE.W #-1,D0
	CMP.W D0,D1
	BEQ e10
* -->[GOTO, null, null, e11]
	JMP e11
* -->[SKIP, null, null, e10]
e10:
* -->[ASIG, -1, null, t12]
	MOVE.W #-1,t12_0
* -->[OUT, t12, null, null]
	MOVE.W t12_0,-(A7)
	JSR PRINTINT
	ADDA.L #4,A7
* -->[SKIP, null, null, e11]
e11:
	SIMHALT
 
n_1: DS.W 1
m_1: DS.W 1
t1_1: DS.W 1
t2_1: DS.W 1
t3_1: DS.W 1
i_0: DS.W 1
t4_0: DS.W 1
t5_0: DS.W 1
t6_0: DS.B 1
	DC.W 0
t7_0: DS.W 1
t8_0: DS.W 1
t9_0: DS.W 1
t10_0: DS.B 1
t11_0: DS.B 1
	DC.W 0
t12_0: DS.W 1
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
