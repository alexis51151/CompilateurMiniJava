
public class Parenth extends Expr{
	public Parenth(Expr e1, Expr e2) {
		this.value = op.eval(e1.eval(),e2.eval());
	}
	
}
