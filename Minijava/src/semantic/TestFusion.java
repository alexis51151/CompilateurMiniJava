package semantic;

import syntax.ast.ASTList;
import syntax.ast.ASTNode;
import syntax.ast.Formal;
import syntax.ast.Klass;
import syntax.ast.Method;
import syntax.ast.Var;

public class TestFusion extends syntax.ast.ASTVisitorDefault {
	
	private boolean isLocal;
	
	public TestFusion(ASTNode axiom) {
		    axiom.accept(this); // => visite((Axiom)axiom)
	}
	
	 public void visit(Var n) {
		System.out.print(n.varId.name);
		if(isLocal) {
			System.out.print("(local)");
		}
		else {
			System.out.print("(field)");
		}
	    defaultVisit(n);
	  }
	 
	 public void visit(Formal n) {
		 System.out.print(n.varId.name);
		 System.out.print("(formal)");
	 }
	 
	 public void visit(Klass n) {
		 System.out.print(n.klassId.name);
		 System.out.print("{");
		 defaultVisit(n);
		 System.out.print("}");

	 }
	 
	 public void visit(Method n) {
		 System.out.print(n.methodId.name);
		 isLocal = true; 
		 System.out.print("{");
		 defaultVisit(n);
		 System.out.print("}");
		 isLocal = false;
		 
	 }

	

}