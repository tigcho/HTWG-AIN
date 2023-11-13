.text
main:
    li $a0, 3           # ungerader Wert
    jal ISODD           # Rufe ISODD auf
    move $s1, $v0       # Speicher Ergebnis von ISODD in $s1

    li $a0, 16          # gerader Wert
    jal ISEVEN          # Rufe ISEVEN auf
    move $s2, $v0       # Speicher Ergebnis von ISEVEN in $s2

    li $v0, 10          # Beende das Programm
    syscall

ISODD:
    andi $v0, $a0, 1    # Setze $v0 auf 1 wenn x ungerade, sonst 0
    jr $ra              # Sprung zurück

ISEVEN:
    move $t0, $ra       # Speicher aktuelles $ra in $t0
    jal ISODD           # Rufe ISODD auf
    xori $v0, $v0, 1    # Invertiere das Ergebnis (wenn ISODD 1 zurückgibt kommt 0 raus und umgekehrt)
    jr $ra              # Sprung zurück
