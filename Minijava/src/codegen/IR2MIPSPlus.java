package codegen;

import intermediate.IR;
import intermediate.ir.*;
import allocator.Allocator;

// Override IR2MIPS class :
public class IR2MIPSPlus extends IR2MIPS {
  public IR2MIPSPlus(IR ir, Allocator allocator, MIPS mips) {
    super(ir, allocator, mips);
  }
  
  
  private String tmpReg(IRVar v, String defReg) {
	    String reg = allocator.access(v).getRegister();
	    return (reg==null)? defReg:reg;
	  }

  ///////// OVERRIDE VISIT ///////////////
  public void visit(QCall q) {
	    String function = q.arg1.getName();
	    int nbArg = checkArgs(q);
	    if (q.result == null) {
	      specialCall(function);
	      return;   
	    }
	    // else : common minijava methods
	    callerSave();
	    for(int i =0; i < nbArg && i < 4; i++) {
	    	regLoadSaved("$a" + i, getArg(i));
	    }
	    mips.inst("move $fp, $sp");
	    mips.inst("addi $sp, $sp, -" + allocator.frameSize(function));
	    mips.inst("jal  " + function);
	    mips.inst("move $sp, $fp"); // restore $sp
	    callerRestore();
	    regStore("$v0", q.result);
	  }

  
  
}  