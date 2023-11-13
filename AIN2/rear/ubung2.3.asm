.text
main:
    la $a0, A
    la $a1, B
    lw $a2, n
    jal evenElem

exit:
    li $v0, 10
    syscall
    
ISODD:    # $v0 = isodd($a0)
    move $t0, $a0
    andi $v0, $t0, 1
    jr $ra
    
ISEVEN:    # $v0 = iseven($a0)
    move $t8, $ra
    jal ISODD
    xor $v0, $v0, 1
    jr $t8
    
evenElem:    # $v1 = evenElem($a0 = A[], $a1 = B[], $a2 = n)
    li $v1, 0    #j
    li $t9, 0    #i
    move $t6, $a0    #ISEVEN nutzt $a0, nutzen wir $t6
    move $t7, $ra    #save return address
#while($t9 < $a2)
loop:
    bge $t9, $a2, loopEnd
    lw $a0, ($t6)    #$a0 = A[i]
    #if ( isEven(A[i]) )
    jal ISEVEN
    beqz $v0, endIf
    lw $t5, ($t6)    #B[j] = A[i]
    sw $t5, ($a1)
    addi $a1, $a1, 4    #j++
    addi $v1, $v1, 1
endIf:
    addi $t6, $t6, 4    #i++
    addi $t9, $t9, 1
    j loop
loopEnd:
    jr $t7
    


.data 
n:
    .word 6
A:
    .word 3 4 6 8 11 13
B:
    .word 0 0 0 0 0 0