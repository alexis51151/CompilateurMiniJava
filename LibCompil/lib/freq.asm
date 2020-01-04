	.data 0x10010000        # data segment, at 0x1001 0000
	.space 256
	.ascii "Avec ses quatre dromadaires\n"
	.ascii "Don Pedro d'Alfaroubeira\n"
	.ascii "Courut le monde et l'admira.\n"
	.ascii "Il fit ce que je voudrais faire\n"
	.ascii "Si j'avais quatre dromadaires.\n"
	.byte 0   # zero de fin de chaîne de caractères
	
	
	.text                   # code segment

	lui    $s0, 0x1001      # load address of data segment
	addiu  $s1, $s0, 256    # $s1 <- address of the variable that contains 'a'
	addiu  $s2, $s0, 0	# $s2 <- adress of the frequency array
	
_lcf:
	lbu    $a0, ($s1)       # load 'a'
	addiu  $v0, $zero, 11   # $v0 <- 11
	beq    $a0, $zero, _alcf
	addu   $t0, $s2 , $a0 
	lbu    $t1, ($t0)
	addiu  $t1, $t1, 1 
	swr    $t1, ($t0) 
	addiu  $s1, $s1, 1
	syscall
	j _lcf
_alcf:
	# exit program
	addiu  $v0, $zero, 10
	syscall
