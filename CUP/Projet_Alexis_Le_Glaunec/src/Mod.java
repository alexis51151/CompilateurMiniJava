
public class Mod extends Expr{
	public Mod(Expr e1, Expr e2) {
		this.value = e1.eval() % e2.eval();
	}

}
