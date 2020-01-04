package semantic;

import main.OPER;
import semantic.symtab.*;
import syntax.ast.*;

/** Contrôle de Type.
 * <p>Calcule l'attribut synthétisé Type : requis pour les noeuds Expr*
 * <p>Verifie les contraintes de Typage de minijava
 */
public class TypeChecking extends ASTVisitorDefault {
  /** Sortie en erreur. */
  public boolean getError() { return error; }
  private boolean error; // erreur de rédéfinition dans la meme portée
  
  // input
  private final SemanticTree semanticTree;
  
  public TypeChecking(SemanticTree semanticTree) {
    this.error = false;
    this.semanticTree = semanticTree;
    semanticTree.axiom.accept(this);
  }

  // //// Helpers
  String getType(ASTNode n) { return semanticTree.typeAttr.get(n); }
  private void setType(ASTNode n, String type) { semanticTree.typeAttr.set(n, type); }
  private Scope getScope(ASTNode n) { return semanticTree.scopeAttr.get(n);}
  public InfoKlass lookupKlass(String name){ return semanticTree.rootScope.lookupKlass(name);}
  
  // Primitive Type names in Minijava
  private static final String BOOL = main.TYPE.BOOL.toString();
  private static final String INT = main.TYPE.INT.toString();
  private static final String INT_ARRAY = main.TYPE.INT_ARRAY.toString();
  private static final String VOID = main.TYPE.UNDEF.toString();

  //// helpers
  
  // Compare type : returns true if t2 is subtype of t1
  private boolean compareType(String t1, String t2) {
    if (t2 == null) return false;
    if (t2.equals(t1)) return true;
    // sinon (t1 ancetre de t2) ?
    InfoKlass kl2 = lookupKlass(t2);
    if (kl2 != null) return compareType(t1,kl2.getParent());
    return false;
    // NB : Suppose heritage valide !!!
   }

  // repport error
  private void erreur(ASTNode where, String msg) {
    main.DEBUG.logErr(where + " " + msg);
    error = true;
  }

  // Validation : "t2 subtype of t1"
  private void checkType(String t1, String t2, String msg, ASTNode where) {
    if (!compareType(t1, t2) )
      erreur(where, "Wrong Type : " + t2 + "->" + t1 + ";  " + msg);
  }
  
  // Validation : TypeName is a valid Type
  private void checkTypeName(String type, ASTNode where) {
    if ( type.equals(BOOL)
        || type.equals(INT)
        || type.equals(INT_ARRAY)
        || type.equals(VOID)
        || ( lookupKlass(type)!=null )
        ) return;
    erreur(where, "Unknown Type : " + type);
  }

  // lookup symbolTable for variable type
  private String lookupVarType(ASTNode n , String name) {
    InfoVar v = getScope(n).lookupVariable(name);
    if (v==null) return VOID;
    else return v.getType();
  }
  

  /////////////////// Visit ////////////////////
  // Visites spécifiques : (non defaultvisit)
  //  - Expr* : set Type
  //  - Stmt* + Expr* (sauf exceptions) : Compatibilité des Types
  //  - Type : Validite des noms de type dans déclarations ( Var, Method, Formal)
  //  - Method : returnType compatible avec Type(returnExpr)
  // NB : validité des declarations de classe prérequis (checkInheritance)
  
  // Set Type pour les expressions
  @Override
  public void visit(ExprLiteralInt n) {
    setType(n, INT);
  }
  public void visit(ExprLiteralBool n) {
	  setType(n, BOOL);
  }
 
	public void visit(ExprCall n) {
		n.receiver.accept(this);
		InfoKlass kl = lookupKlass(getType(n.receiver));
		if (kl == null) {
			erreur(n, "ExprCall : Pas de classe avec le nom fourni");
			setType(n, VOID);
			return;
		}
		n.methodId.accept(this); 
		// On cherche la méthode associée à la classe donnée (ou les sur-classes)
		InfoMethod im = kl.getScope().lookupMethod(n.methodId.name);
		if (im == null) {
			erreur(n, "ExprCall : Pas de méthode dans la classe avec le nom fourni");
			setType(n, VOID);
			return;

		}
		if (n.args.size() + 1 != im.getArgs().length) {
			erreur(n, "ExprCall : Appel de la méthode avec le mauvais nb d'arguments");
			setType(n, VOID);
			return;

		}
		// Vérification du type des args
		int i = 1;
		for (ASTNode node : n.args) {
			node.accept(this);
			checkType(im.getArgs()[i++].getType(), getType(node), "ExprCall : Arguments de type différents", n);
		}
		setType(n, im.getReturnType());
	}
	
	public void visit(ExprIdent n) {
		// Type déjà enregistré avant
		String type = lookupVarType(n, n.varId.name);
		setType(n, type);
		setType(n.varId, type);

	}
	public void visit(ExprNew n) {
		defaultVisit(n);
		checkTypeName(n.klassId.name, n);
		setType(n, n.klassId.name);
	}
	
	public void visit(ExprOpBin n) {
		defaultVisit(n);
		String type1 = getType(n.expr1);
		String type2 = getType(n.expr2);
		if (n.op ==  OPER.AND) {
			checkType(type1, BOOL, "expr1 && expr2 : expr1 non booléen", n);
			checkType(type2, BOOL, "expr1 && expr2 : expr2 non booléen", n);
			setType(n, BOOL);
		}
		else if (n.op == OPER.LESS) {
			checkType(type1, INT, "expr1 OP expr2 : expr1 non entier", n);
			checkType(type2, INT, "expr1 OP expr2 : expr2 non entier", n);
			setType(n, BOOL);

		}
		else {
			checkType(type1, INT, "expr1 OP expr2 : expr1 non entier", n);
			checkType(type2, INT, "expr1 OP expr2 : expr2 non entier", n);
			setType(n, INT);

		}
	}
	
	public void visit(ExprOpUn n) {
		defaultVisit(n);
		String type = getType(n.expr);
		checkType(type, BOOL, "! expr : expr non booléen", n);
		setType(n, BOOL);
	}
	// Validité des noms de types dans les déclarations
	public void visit(Var n) {
		checkTypeName(n.typeId.name, n);
		setType(n, n.typeId.name);
		setType(n.varId, n.typeId.name);
		System.out.println("Type 11 de " + n.varId.name + ":" + getType(n));

	}
	
	public void visit(Formal n) {
		checkTypeName(n.typeId.name, n);
		setType(n, n.typeId.name);
		setType(n.varId, n.typeId.name);
	}
	
	// Set Type pour les statements
	public void visit(StmtAssign n) {
		defaultVisit(n);
		String type1 = getType(n.value);
		//System.out.println("Type de " + n.value + ":" + type1);
		String type2 = lookupVarType(n, n.varId.name);
		//System.out.println("Type de " + n.value + ":" + type1);
	checkType(type1, type2, "IDENT = EXPR : types différents" , n);
	}
	public void visit(StmtIf n) {
	    defaultVisit(n);
	    checkType(BOOL, getType(n.test), "Argument du if non booléen", n);
	}

	public void visit(StmtWhile n) {
	    defaultVisit(n);
	    checkType(BOOL, getType(n.test), "Argument du while non booléen", n);
	}
  
  @Override
  public void visit(StmtPrint n) {
    defaultVisit(n);
    checkType(INT, getType(n.expr), "non integer for printing", n);
  }
  
  // Check Type pour Method
  
  public void visit(Method n) {
	  defaultVisit(n);
	  String type1 = n.returnType.name;
	  checkTypeName(type1, n);
	  String type2 = getType(n.returnExp);
	  checkType(type1, type2, "Method : ReturnType et returnExp de types différents", n);
  }

}
