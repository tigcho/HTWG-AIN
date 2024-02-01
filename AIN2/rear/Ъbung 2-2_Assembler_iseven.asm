	addi $s0,$zero,4	
	addi $a0,$s0,0		# Argument a0 mit z.B. einer 4 beladen
	jal ISODD		# call ISODD Funktion. jal speichert auch Rücksprungadresse in $ra
	move $s1,$v0		# s1 = v0 -> Hole Ergebnis der Prozedur aus v0
	# **********
	addi $a0,$s0,0		# Argument a0 mit z.B. einer 0 beladen
	jal ISEVEN		# call ISEVEN Funktion. jal speichert auch Rücksprungadresse in $ra
	move $s2,$v0		# s2  = v0 -> Hole Ergebnis der Prozedur aus v0
	j END			# jump to end of code
	
ISODD:	andi $v0,$a0,1		# "Filtere" letztes Bit -> Zeiger für Ungerade / Gerade -> Lege in v0 ab
	jr $ra			# return
	
ISEVEN: addi $sp,$sp,-4		# Erniedrige den Stack Pointer um ein Word, mache Platz für Inhalt ra
	sw $ra,0($sp)		# Speichere die Rücksprungadresse auf Stackpointer
	jal ISODD		# call ISODD Funktion. jal speichert auch Rücksprungadresse in $ra, Argument a0 immer noch unverändert vorhanden.
	xori $v0,$v0,1		# invert $v0, alternative: sub $v0,1,$v0
	lw $ra,0($sp)		# restore return address from stack, auf ra
	addi $sp,$sp,4		# Erhöhe Stack Pointer wieder um ein Word
	jr $ra			# jump back
END:	
	
