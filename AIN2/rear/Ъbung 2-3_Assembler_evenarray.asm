.data
n:	.word 6			# write n to memory at address "n"
A:	.word 3 4 6 8 11 13	# write 6 words to memory starting at address "A"
B:	.word 0 0 0 0 0 0	# write 6 zeros to memory starting at address "B"
.text
	# INHALT von n auf Argument laden
	lw $a2,n		# load value at address "n" to register $a2
	# ADRESSEN von A und B auf Argument laden
	la $a0,A		# load adsress "A" to register $a0
	la $a1,B		# load address "B" to register $a1
	# Prozedur Aufruf, Rücksprungadresse wird auf $ra geschrieben
	jal EVENARRAY		# call procedure at label EVENARRAY
	j END			# jump to label END /end of code)

	
EVENARRAY:			# procedure EVENARRAY
	addi $sp,$sp,-28	# free space on stack for 7 words (7*4=28), da 5 Variablen, 1 ra, 1 a0 im Prozedur benutzt
	# Speichere ra
	sw $ra,24($sp)		# store return address, required since another procedure is called
	# Speichere alle s Register, die ich in der Prozedur benötige, 
	# da ja hier Werte darüberliegenderen Prozeduren enthalten sein könnten bzw. tieferliegendere
	# Prozeduren Werte manipulieren in den Registern
	sw $s0,20($sp)		# save used s-registers on stack
	sw $s1,16($sp)
	sw $s2,12($sp)
	sw $s3,8($sp)
	sw $s4,4($sp)
	# usage of $s-registers:	
	#	$s0: A
	#	$s1: B
	#	$s2: n
	#	$s3: i
	#	$s4: j
		
	# Belade s Register mit Werte aus Argumenten - Initialisierung
	move $s0,$a0		# save $a registers in $s registers to preserve them via procedure calls
	move $s1,$a1		# alternative would be to save them on stack
	move $s2,$a2
	# Belade s Register mit Werten 0 - dienen als Index fpr Array Durchgänge
	addi $s3,$zero,0	# i=0
	addi $s4,$zero,0	# j=0

	EVARRAY_LOOP:	slt $t0,$s3,$s2			# check i<n
			beq $t0,$zero,EVARRAY_END	# if not (i<n) GOTO EVARRAY_END
							# ELSE
			# t0=4*i --> Für Adressierung A[i]
			sll $t0,$s3,2			# load A[i] to register $a0, Note: address of A[i] is A+4*i
			# t0=A+t0 --> Jetzt Adressierung A[i]
			add $t0,$s0,$t0
			# Dann auch Element aus Array rausholen und in Argument laden (für ISEVEN Prozedur)
			lw $a0,0($t0)
			# Speichere Argument auf dem Stack, da später noch mit gearbeitet wird
			sw $a0,0($sp)			# save A[i] on stack to preserve over procedure call
			# Nutze vorhande Funktion um zu überprüfen ob Element gerade oder ungerade ist
			jal ISEVEN			# call procdure ISEVEN Note: $v0=ISEVEN($a0), $a0=A[i]
			# Wenn Element ungerade ist, gehe zu EVARRAY_NEXT 
			beq $v0,$zero,EVARRAY_NEXT	# if $v0==0 GOTO EVARRAY_NEXT
			# Ansosten, nehme das Element (aus Stack)...
			lw $a0,0($sp)			# restore A[i] from stack
			# ...und speichere es auf B[j]! Addressierung Methodik, s.O.
			sll $t0,$s4,2			# store register $ao to B[j], Note: address of B[j] is B+4*j
			add $t0,$s1,$t0
			sw $a0,0($t0)
			addi $s4,$s4,1			# j++
	EVARRAY_NEXT:	addi $s3,$s3,1			# i++
			j EVARRAY_LOOP			# next iteration
	# Ausgangsablauf der Prozedur, stelle alle s Register wieder her und Stackpointer
	# Außerdem: Ergebnis der Prozedur in "Ergebnis-Register" v0
	EVARRAY_END:	move $v0,$s4			# return j, i.e. copy $s4 to $v0
			lw $ra,24($sp)			# restore values from stack
			lw $s0,20($sp)			
			lw $s1,16($sp)
			lw $s2,12($sp)
			lw $s3,8($sp)
			sw $s4,4($sp)
			addi $sp,$sp,-28		# restore stack pointer
			jr $ra				# jump back
	
ISODD:	andi $v0,$a0,1		# check for last bit
	jr $ra			# return
	
ISEVEN: addi $sp,$sp,-4
	sw $ra,0($sp)
	jal ISODD
	xori $v0,$v0,1
	lw $ra,0($sp)
	addi $sp,$sp,4
	jr $ra


END:	
