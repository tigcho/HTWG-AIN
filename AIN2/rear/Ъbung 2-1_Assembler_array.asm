.data
n:	.word 6				# Größe Array				
A:	.word 1 2 3 4 5 6  		# legt Array an

.text
la $s0, A		# lädt Adresse von A in $s0
lw $s1, n		# lädt Inhalt von n in $s1

# In Register v0 soll die Summe des Arrays
addi $v0,$zero,0	# Register mit 0 initialisieren

Loop:	beq $zero,$s1,EXIT
	lw $t0,0($s0)		# Hole Wert aus Speicher
	add $v0,$v0,$t0		# Erhöhe Wert in v0 um aktuellen Wert
	addi $s0,$s0,4		# Gehe an nächstes Word
	addi $s1,$s1,-1
	j Loop
EXIT:

# Ergebnis sollte 21 sein
