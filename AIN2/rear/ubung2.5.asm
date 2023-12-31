.text
.globl main
main:
    li $a0, 15   # n
    li $a1, 5    # k

# "Sprung zu f, um $v0 = f($a0, $a1) zu berechnen"
    jal f

# "Speicher Ergebnis in $v0 nach $a0"
    move $a0, $v0

# "$v0 = 1 für das syscall für die Ausgabe"
    li $v0, 1
    syscall 
    
    li $v0, 10 # Beenden
    syscall

# --------------------------------------------------------------------------------------
# "willkürliche Prozedur G (überschreibt alle ungesicherten Register..."
# "...mit dem Wert $a0+200, liefert auch $a0+200 zurück)"
G:
    addi $a0,$a0,100
    addi $a1,$a0,100
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

# --------------------------------------------------------------------------------------
max:    
# "int $v0 = max(int $a0, int $a1)"
    sub $t0, $a0, $a1
# "wenn Differenz nicht negativ, dann direkter Sprung zu _endIf"
    beq $t0, $zero, _endIf

# "bgt $a0, $a1, _ra0"
# "Ergebnis ist nur dann 0, wenn t0 eine negative Zahl enthält (VZ-Bit = 1)"
    andi $t0, 0x8000000

# "Wenn Ergebnis wirklich 0 ist, dann Sprung"
    beq $t0, $zero, _ra0

# "wenn a1 das größere Element enthält- in v0 speichern"
    move $v0, $a1
    j _endIf
    
_ra0:
    move $v0, $a0

# "jr speichert die Adresse "
_endIf:
    jr $ra

# --------------------------------------------------------------------------------------
f: 
# "int $v0 = f(int $a0, int $a1)"
# "save return address"
    subi $sp, $sp, 4
    sw $ra, ($sp)
    sub $t0, $a1, $a0 # k-n
    subi $t0, $t0, 7 # k-n-7

# "if k-n > 7"
# "bgt $t0, $zero, _fall2"
    andi $t0, 0x80000000
    bne $t0, $zero, _fall2

# "$v0 = $a0 + $a1 + 5"
    add $v0, $a0, $a1
    addi $v0, $v0, 5
    j _return

_fall2:
# "save $a0"
    subi $sp, $sp, 4
    sw $a0, ($sp)
    move $a0, $a1
    jal G
    addi $a0, $zero, 8
    jal max
    move $a1, $v0

# "load $a0 again"
    lw $a0, ($sp)
    addi $sp, $sp, 4
    subi $a0, $a0, 1 #n-1
    jal f

_return:
    lw $ra, ($sp)
    addi $sp, $sp, 4
    jr $ra