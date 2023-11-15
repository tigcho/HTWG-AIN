.text
.globl main
main:
    li $a0, 15    # n
    li $a1, 5    # k
    jal f        # $v0 = f($a0, $a1)
    # print ergebnis
    move $a0, $v0
    li $v0, 1
    syscall 
    # exit
    li $v0, 10
    syscall

# --------------------------------------------------------------------------------------
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
max:    # int $v0 = max(int $a0, int $a1)
    sub $t0, $a0, $a1
    # andi $t0, $t0, 0x8000000
    beq $t0, $zero, _endIf
    bgt $a0, $a1, _ra0
    move $v0, $a1
    j _endIf
_ra0:
    move $v0, $a0
_endIf:
    jr $ra

# --------------------------------------------------------------------------------------
f:  # int $v0 = f(int $a0, int $a1)
    # save return address
    subi $sp, $sp, 4
    sw $ra, ($sp)
    
    sub $t0, $a1, $a0    # k-n
    subi $t0, $t0, 7    # k-n-7
    # if k-n > 7
    bgt $t0, $zero, _fall2
    
    # $v0 = $a0 + $a1 + 5
    add $v0, $a0, $a1
    addi $v0, $v0, 5
    j _return
    
_fall2:
    # save $a0
    subi $sp, $sp, 4
    sw $a0, ($sp)
    
    jal G
    addi $a0, $zero, 8
    jal max
    move $a1, $v0
    
    # load $a0 again
    lw $a0, ($sp)
    addi $sp, $sp, 4
    
    subi $a0, $a0, 1    #n-1
    jal f
    
_return:
    lw $ra, ($sp)
    addi $sp, $sp, 4
    jr $ra
