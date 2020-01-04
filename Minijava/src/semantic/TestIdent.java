package semantic;

import syntax.ast.ASTList;
import syntax.ast.ASTNode;
import syntax.ast.Formal;
import syntax.ast.Klass;
import syntax.ast.Method;
import syntax.ast.Var;

public class TestIdent extends syntax.ast.ASTVisitorDefault {
	
	private boolean isLocal;
	
	public TestIdent(ASTNode axiom) {
		    axiom.accept(this); // => visite((Axiom)axiom)
	}
	
	 public void visit(Var n) {
		System.out.print(n.varId.name);
		if(isLocal) {
			System.out.println("(local)");
		}
		else {
			System.out.println("(field)");
		}
	    defaultVisit(n);
	  }
	 
	 public void visit(Formal n) {
		 System.out.print(n.varId.name);
		 System.out.println("(formal)");
	 }
	 
	 public void visit(Klass n) {
		 System.out.println(n.klassId.name + "(klass)");
		 defaultVisit(n);
	 }
	 
	 public void visit(Method n) {
		 System.out.println(n.methodId.name + "(method)");
		 isLocal = true; 
		 defaultVisit(n);
		 isLocal = false;
		 
	 }

	

}
