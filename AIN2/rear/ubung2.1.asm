# "Instruktionen im Textsegment platzieren"
.text 				

# "Label als Einstiegspunkt zum Hauptprogramm"
main:				  

# "Laden Sie die Variable nsowie die Adresse des Arrays mit..." 
# "...den Pseudo-Instruktionen lw Register,Label bzw.la Register,Label"
	lw $t0, n
	la $t1, a

# "v0 speichert Summe der Array-Elemente"		
	li $v0, 0
loop:

# "while $t0 > 0, ansonsten exit, keine Elemente übrig"
	blez $t0, exit		

# "Wert der Adresse in t1 wird in t2 geladen"
	lw $t2, ($t1)

# "2. die Summe der n Werte im Array bestimmen..."
# "... und in das Register schreiben: v0 += $t2"
	add $v0, $v0, $t2	

# "$ t0--: ein Element weniger berechnen"
	addi $t0, $t0, -1	

# "nächste Adresse, 4 Byte"
	addi $t1, $t1, 4
	j loop
exit:
	
# "1. Im Speicher eine Zahl n und Array a mit n Integer-Werten anlegen"
# "Wählen Sie als Beispiel n=6"
.data
a:

# "Verwenden Sie zum Anlegen der Werte im Speicher die Assembler Direktive .word"
.word 12 13 24 423 1 414
n:
.word 6