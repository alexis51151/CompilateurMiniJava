	.data 0x10010000        # data segment, at 0x1001 0000
	.space 256
	.ascii "Avec ses quatre dromadaires\n"
	.ascii "Don Pedro d'Alfaroubeira\n"
	.ascii "Courut le monde et l'admira.\n"
	.ascii "Il fit ce que je voudrais faire\n"
	.ascii "Si j'avais quatre dromadaires.\n"
	.byte 0   # zero de fin de chaîne de caractères
	
	
	.text                   # code segment

	lui    $a0, 0x1001      # load address of data segment
	addiu  $a1, $a0, 256    # $s1 <- address of the variable that contains 'a'
	addiu  $a2, $a0, 0	# $s2 <- adress of the frequency array
	
	jal compute_frequencies
	
	# exit program
	addiu  $v0, $zero, 10
	syscall

compute_frequencies: 
	move $s1, $a1
	move $s2, $a2
	# On sauvegarde les registres
	addiu  $sp, $sp, -12 # reserve 12 octets dans la pile
	sw     $ra, 0($sp) # sauvegarde $ra en $sp + 0
	sw     $s1, 4($sp) # sauvegarde $s1 en $sp + 4
	sw     $s2, 8($sp) # sauvegarde $s2 en $sp + 8
_lcf:
	lbu    $a0, ($s1)       # load 'a'
	addiu  $v0, $zero, 11   # $v0 <- 11
	beq    $a0, $zero, _alcf
	addu   $t0, $s2 , $a0 
	lbu    $t1, ($t0)
	addiu  $t1, $t1, 1 
	sb     $t1, ($t0) 
	addiu  $s1, $s1, 1
	syscall
	j _lcf
_alcf:
	# On restaure les registres
	lw     $ra, 0($sp) # sauvegarde $ra en $sp + 0
	lw     $s1, 4($sp) # sauvegarde $s1 en $sp + 4
	lw     $s2, 8($sp) # sauvegarde $s2 en $sp + 8
	addiu  $sp, $sp, 12 # reserve 12 octets dans la pile
	jr $ra
	
