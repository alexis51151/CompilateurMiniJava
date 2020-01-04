	.data 0x10010000        # data segment, at 0x1001 0000

	.text                   # code segment

	addiu $a0, $zero, 5
	jal fact
	syscall
	addiu $v0, $zero, 10
	syscall
	
fact: 
	addiu  $sp, $sp, -8 # reserve 4 octets dans la pile
	sw     $ra, 0($sp) # sauvegarde $ra en $sp + 0
	sw     $a0, 4($sp) # sauvegarde $a0 en $sp + 4
	beq $a0, $zero, cas_zero
	j cas_commun

cas_zero:
	addiu $v0, $zero, 1
	addiu $a0, $zero, 1
	lw     $ra, 0($sp) # restaure $ra 
	addiu  $sp, $sp, 8 # libère 4 octets dans la pile
	jr $ra
cas_commun:
	addiu $a0, $a0, -1
	jal fact
	lw   $t0, 4($sp)
	mult $v0, $t0
	mflo $v0
	lw   $ra, 0($sp) # restaure $ra 
	addiu  $sp, $sp, 4 # libère 4 octets dans la pile
	jr $ra

