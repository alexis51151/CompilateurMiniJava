
public class Minus extends Expr {
	
	public Minus(Expr e1, Expr e2) {
		this.value = e1.eval() - e2.eval();
	}
	
}
