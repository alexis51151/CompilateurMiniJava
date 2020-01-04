
public class Plus extends Expr {
	
	public Plus(Expr e1, Expr e2) {
		this.value = e1.eval() + e2.eval();
	}	
}

