package semantic;
import java.util.List;

import semantic.symtab.InfoKlass;
import semantic.symtab.InfoMethod;
import semantic.symtab.InfoVar;
import semantic.symtab.Scope;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import syntax.ast.*;
public class Undefined extends ASTVisitorDefault{
	private final SemanticTree semanticTree;
	private boolean undefined;
	private List<Ident> undefined_list;
	Collection<InfoVar> vars; 
	private TypeChecking typeChecking;
	
	public Undefined(SemanticTree semanticTree, TypeChecking typechecking) {
		this.semanticTree = semanticTree;
		this.typeChecking = typechecking;
		this.undefined = false;
		this.undefined_list = new ArrayList<Ident>();
		this.vars = semanticTree.rootScope.getAllVariables();
		this.semanticTree.axiom.accept(this); // Accès à l'AST*
		print_undefined();
		print_unused();

	}
	// Attribut "Scope"
	private void setScope(ASTNode n, Scope sc) { semanticTree.scopeAttr.set(n, sc); }
	private Scope getScope(ASTNode n) { return semanticTree.scopeAttr.get(n);}
		
	public  void visit(ExprIdent n) {
		Scope sc = getScope(n);
		InfoVar iv = sc.lookupVariable(n.varId.name);
		vars.remove(iv);
	}
	
	public void visit(Method n) { 
		n.returnExp.accept(this);
		n.fargs.accept(this);
	}
	
	public void visit(ExprCall n) { // Gestion des indentificateurs de méthode
		// On s'inspire de la méthode de TypeChecking
		Scope sc = getScope(n);
		InfoKlass kl = typeChecking.lookupKlass(typeChecking.getType(n.receiver));
		InfoMethod im = kl.getScope().lookupMethod(n.methodId.name);
		if ( im != null) {
			InfoVar iv = sc.lookupVariable(n.methodId.name);
			vars.remove(iv);
		}
	}

	
	public void visit(Ident n) {
		if (semanticTree.scopeAttr.get(n) == null) {
			undefined_list.add(n);
			this.undefined = true;
		}
	}
	
	public void print_undefined() {
		String str = "[";
		for(Ident ident : undefined_list) {
			str += ident.name;
			str += ", ";
		}
		str += "]";
		System.out.print(str);
	}
	
	public void print_unused() {
		String str = "[";
		for(InfoVar iv : vars) {
			str += iv.getName();
			str += ", ";
		}
		str += "]";
		System.out.print(str);
	}



}
