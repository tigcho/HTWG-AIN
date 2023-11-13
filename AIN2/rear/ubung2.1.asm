.text 				# .text directive sagt dem assembler die instruktionen
				# im Textsegment zu platzieren
main:				# Label als Einstiegspunkt zum Hauptprogramm
	lw $t0, n		# Array-Size n in $t0
	la $t1, a		# Array-Address in $t1
	li $v0, 0		# v0 = 0;
loop:
	blez $t0, exit		# "while $t0 > 0"
	lw $t2, ($t1)		# Wert der Adresse in t1 wird in t2 geladen
	add $v0, $v0, $t2	# v0 += $t2
	addi $t0, $t0, -1	#$ t0--
	addi $t1, $t1, 4	# nächste Adresse
	j loop
exit:
	
.data
a:
.word 12 13 24 423 1 414
n:
.word 6
