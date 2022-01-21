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
* -->[SKIP, null, null, division]
division:
* -->[PMB, null, null, division]
* -->[IN, null, null, t4]
	SUBA.L #2,A7
	JSR GETINT
	MOVE.W (A7)+,t4_2
* -->[ASIG, t4, null, n_2]
	MOVE.W t4_2,n_2
* -->[IN, null, null, t5]
	SUBA.L #2,A7
	JSR GETINT
	MOVE.W (A7)+,t5_2
* -->[ASIG, t5, null, m_2]
	MOVE.W t5_2,m_2
* -->[ASIG, 0, null, t6]
	MOVE.W #0,t6_2
* -->[NIGUALES, m_2, t6, e1]
	MOVE.W m_2,D1
	MOVE.W t6_2,D0
	CMP.W D0,D1
	BNE e1
* -->[GOTO, null, null, e2]
	JMP e2
* -->[SKIP, null, null, e1]
e1:
* -->[ASIG, -1, null, t7]
	MOVE.B #-1,t7_2
* -->[GOTO, null, null, e3]
	JMP e3
* -->[SKIP, null, null, e2]
e2:
* -->[ASIG, 0, null, t7]
	MOVE.B #0,t7_2
* -->[SKIP, null, null, e3]
e3:
* -->[IGUALES, t7, -1, e4]
	MOVE.B t7_2,D1
	EXT.W D1
	MOVE.W #-1,D0
	CMP.W D0,D1
	BEQ e4
* -->[GOTO, null, null, e5]
	JMP e5
* -->[SKIP, null, null, e4]
e4:
* -->[PARAM, null, null, n_2]
	MOVE.W n_2,-(A7)
* -->[PARAM, null, null, m_2]
	MOVE.W m_2,-(A7)
* -->[CALL, t8, null, resto]
	SUBA.L #2,A7
	JSR resto
	MOVE.W (A7)+,t8_2
	ADDA.L #4,A7
* -->[OUT, t8, null, null]
	MOVE.W t8_2,-(A7)
	JSR PRINTINT
	ADDA.L #2,A7
* -->[SKIP, null, null, e5]
e5:
* -->[ASIG, 0, null, t9]
	MOVE.W #0,t9_2
* -->[IGUALES, m_2, t9, e6]
	MOVE.W m_2,D1
	MOVE.W t9_2,D0
	CMP.W D0,D1
	BEQ e6
* -->[GOTO, null, null, e7]
	JMP e7
* -->[SKIP, null, null, e6]
e6:
* -->[ASIG, -1, null, t10]
	MOVE.B #-1,t10_2
* -->[GOTO, null, null, e8]
	JMP e8
* -->[SKIP, null, null, e7]
e7:
* -->[ASIG, 0, null, t10]
	MOVE.B #0,t10_2
* -->[SKIP, null, null, e8]
e8:
* -->[IGUALES, t10, -1, e9]
	MOVE.B t10_2,D1
	EXT.W D1
	MOVE.W #-1,D0
	CMP.W D0,D1
	BEQ e9
* -->[GOTO, null, null, e10]
	JMP e10
* -->[SKIP, null, null, e9]
e9:
* -->[ASIG, -1, null, t11]
	MOVE.W #-1,t11_2
* -->[OUT, t11, null, null]
	MOVE.W t11_2,-(A7)
	JSR PRINTINT
	ADDA.L #2,A7
* -->[SKIP, null, null, e10]
e10:
* -->[RTN, null, null, null]
	RTS
* -->[SKIP, null, null, run]
run:
* -->[CALL, null, null, division]
	JSR division
	SIMHALT
 
n_1: DS.W 1
m_1: DS.W 1
t1_1: DS.W 1
t2_1: DS.W 1
t3_1: DS.W 1
n_2: DS.W 1
t4_2: DS.W 1
m_2: DS.W 1
t5_2: DS.W 1
t6_2: DS.W 1
t7_2: DS.B 1
	DC.W 0
t8_2: DS.W 1
t9_2: DS.W 1
t10_2: DS.B 1
	DC.W 0
t11_2: DS.W 1
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
	EXT.L D1
	MOVE.L #3,D0
	TRAP #15
	MOVE.L #11,D0
	MOVE.W #$00FF,D1
	TRAP #15
	ADD.W #1,D1
	AND.W #$00FF,D1
	TRAP #15
	RTS
GETINT:
	MOVE.L #4,D0
	TRAP #15
	MOVE.W D1,4(A7)
	RTS
	END START
