.data
# Daten anlegen
n:	.word 5
k:	.word 5
str1: .asciiz "Ergebnis= "
.text

# Arguemente laden und F aufrufen
lw $a0,n
lw $a1,k
jal F

# Ergebnis ausgeben
move $s0,$v0
li $v0,4		# Syscall zum Print für einen String: 
la $a0, str1		# siehe: http://courses.missouristate.edu/kenvollmar/mars/help/syscallhelp.html
syscall

li $v0,1		# Syscall zum Print für Int:
move $a0,$s0		# siehe: http://courses.missouristate.edu/kenvollmar/mars/help/syscallhelp.html
syscall
# Programmausführung beenden
li $v0,10		# Beenden des Programms durch Syscall
syscall			# siehe: http://courses.missouristate.edu/kenvollmar/mars/help/syscallhelp.html

# Prozedur F
F:	
		# Sichern auf Stack: $ra, $s0
		addi $sp,$sp,-8
		sw $s0,4($sp)
		sw $ra,0($sp)
		
		# Fallunterscheidung
		sub $t0,$a1,$a0		# $t0 <-- k-n 
		addi $t1,$zero,7	# $t1 <-- 7
		slt $t2,$t1,$t0		# $t2 <-- (7<n-k)
		beq $t2,$zero,F_ELSE	# wenn nicht wahr ($t2=0) dann ==>F_ELSE
		# Erster Fall
		add $v0,$a0,$a1		# $v0 <-- n+k
		addi $v0,$v0,5		# $v0+=5
		j F_BACK		# allgemeines Label für Rücksprung aus Prozedur F
	F_ELSE:	# Zweiter Fall
		# 1. Berechne g(k)
		addi $s0,$a0,0	# n in s-Register sichern
		addi $a0,$a1,0	# $a0 <-- k (ist ja für Funktion G ersters Argument)
		jal G # g mit k in $a0 aufrufen, g(k) steht jetzt in $v0
		# 2. Maximum von 8 und g(k)
		slti $t0,$v0,8	# $t0<-- (g(k)<8)
		bne $t0,$zero,F_ACHT	# wenn wahr, dann springe zu F_ACHT
		addi $a1,$v0,0	# $a1<--g(k) (zweites Argument bei Aufruf von f)
		j F_CALLF
	F_ACHT:	# 
		addi $a1,$zero,8
		# 3. Prozedur f mit $a0=n-1 und $a1=max(8,g(k)) aufrufen
	F_CALLF:
		# $a0 muss noch gesetzt werden, $a1 passt schon
		addi $a0,$s0,-1	# n wieder aus s-Register in $a0 kopieren und 1 abziehen
		jal F # Rückgabewert steht in $v0
	F_BACK:
		# Werte vom Stack wiederherstellen und zurückspringen
		lw $s0,4($sp)
		lw $ra,0($sp)
		addi $sp,$sp,8
		jr $ra

# willkürliche Prozedur G (überschreibt alle "ungesicherten" Register mit dem Wert $a0+200, liefert auch $a0+200 zurück)
G:	addi $a0,$a0,100
	addi $a1,$a0,200
	addi $a2,$a0,100
	addi $a3,$a0,100
	addi $t0,$a0,100
	addi $t1,$a0,100
	addi $t2,$a0,100
	addi $t3,$a0,100
	addi $t4,$a0,100
	addi $t5,$a0,100
	addi $t6,$a0,100
	addi $t7,$a0,100
	addi $t8,$a0,100
	addi $t9,$a0,100
	addi $v0,$a0,100
	addi $v1,$a0,100
	jr $ra
	
