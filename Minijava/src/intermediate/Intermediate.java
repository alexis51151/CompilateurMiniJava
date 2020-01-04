package intermediate;

import main.DEBUG;
import main.OPER;
import intermediate.ir.*;
import syntax.ast.*;
import semantic.SemanticAttribut;
import semantic.SemanticTree;

/** Générarion de la forme intermédiaire (Code à 3 adresses).  */
public class Intermediate extends ASTVisitorDefault {
  // Input : AST Décoré et Table de symbol AST
  private final SemanticTree semanticTree;

  /** Stucture de donnée en sortie de la génératrion de code intermédiaire */
  public IR getResult() { return ir; }
  private final IR ir;

  // Attribut synthetisee nodeVar = Variable IR Temp pour resultat des expression
  private final SemanticAttribut<IRVar> varAttr; // nom Variables dans IR
  private IRVar getVar(ASTNode n) { return varAttr.get(n); }
  private IRVar setVar(ASTNode n, IRVar var) { return varAttr.set(n, var); }
 
  // Attribut synthétisé utilisé comme portée pour les variables IRtemp
  private String currentMethod;

  /** Constructeur */
  public Intermediate(SemanticTree semanticTree) {
    this.semanticTree = semanticTree;
    this.ir = new IR(semanticTree);
    this.varAttr = new SemanticAttribut<>();
    this.currentMethod=null;
    semanticTree.axiom.accept(this); // => visite((Raxiome)axiome)
    if (DEBUG.INTERMED)
      DEBUG.log(ir);
  }

  //// Helpers
  // Ajouter une instruction au programmeIR
  private void add(IRQuadruple irq) { ir.program.add(irq); }
 
  // Variables IR : label tempo, label nom, Constante, Temp var
  private IRVar newLabel() { return ir.newLabel(); }
  private IRVar newLabel(String name) { return ir.newLabel(name); }
  private IRVar newConst(int value) { return ir.newConst(value); }
  private IRVar newTemp() { return ir.newTemp(currentMethod); }

  // Variable de l'AST depuis la table de symbole
  private IRVar lookupVar(String name, ASTNode n) {
    return semanticTree.scopeAttr.get(n).lookupVariable(name);
  }

  /////////////////// Visit ////////////////////
  @Override
  public void visit(KlassMain n) {
    currentMethod="main";
    add(new QLabel(newLabel(currentMethod)));
    defaultVisit(n);
    add(new QCall(newLabel("_system_exit"), newConst(0), null));
    currentMethod=null;
  }

  @Override
  public void visit(ExprLiteralInt n) {
    setVar(n, newConst(n.value));
  }

  public void visit(ExprLiteralBool n) {
	  if(n.value) {
		  setVar(n, newConst(1));
	  }
	  else {
		  setVar(n, newConst(0));
	  }
  }
  
  public void visit(ExprOpBin n) {
	  defaultVisit(n);
	  setVar(n, newTemp());
	  add(new QAssign(n.op, getVar(n.expr1), getVar(n.expr2), getVar(n)));
  }
  
  public void visit(ExprOpUn n) {
	  defaultVisit(n);
	  setVar(n, newTemp());
	  add(new QAssignUnary(n.op, getVar(n.expr), getVar(n)));
  }
  
  public void visit(ExprCall n) {
	  defaultVisit(n);
	  add(new QParam(getVar(n.receiver)));
	  int i = 1;
	  for(ASTNode node : n.args) {
		  i++;
//		  node.accept(this);
	      add(new QParam(getVar(node)));
	  }
	  setVar(n, newTemp());
	  add(new QCall(newLabel(n.methodId.name), newConst(i), getVar(n)));
  }
  
  public void visit(ExprIdent n) {
	  setVar(n, lookupVar(n.varId.name, n));
  }
  
  public void visit(ExprNew n) {
	  defaultVisit(n);
	  setVar(n, newTemp());
	  add(new QNew(newLabel(n.klassId.name), getVar(n)));
  }
  
  
  public void visit(Method n) {
	  currentMethod = n.methodId.name;
	  add(new QLabelMeth(newLabel(n.methodId.name)));
	  defaultVisit(n);
	  add(new QReturn(getVar(n.returnExp)));
	  currentMethod = null;
  }
  
  @Override
  public void visit(StmtPrint n) {
    defaultVisit(n);
    add(new QParam(getVar(n.expr)));
    add(new QCall(newLabel("_system_out_println"), newConst(1), null));
  }

  
  public void visit(StmtAssign n) {
	  defaultVisit(n);
	  setVar(n, lookupVar(n.varId.name, n));
	  add(new QCopy(getVar(n.value), getVar(n)));
  }
  
  public void visit(StmtBlock n) {
	  defaultVisit(n);
  }
  
  public void visit(StmtIf n) {
	  IRVar t = newLabel();
	  IRVar f = newLabel();
	  n.test.accept(this);
	  add(new QJumpCond(t, getVar(n.test)));
	  n.ifTrue.accept(this);
	  add(new QJump(f));
	  add(new QLabel(t));
	  n.ifFalse.accept(this);
	  add(new QLabel(f));
  }
  
  public void visit(StmtWhile n) {
	  IRVar l1 = newLabel();
	  IRVar l2 = newLabel();
	  add(new QLabel(l1));
	  n.test.accept(this);
	  add(new QJumpCond(l2, getVar(n.test)));
	  n.body.accept(this);
	  add(new QJump(l1));
	  add(new QLabel(l2));
  }

}
