package syntax.ast;

/** Constante Entière. <p>{@link #value} */
public class ExprLiteralInt extends Expr {
  public final Integer value;

  public ExprLiteralInt(Integer value) {
    this.value = value;
  }

  /** Constructeur altérnatif */
  public ExprLiteralInt(String s) {
    this.value = Integer.parseInt(s);
  }

  @Override
  public String toString() {
    return super.toString() + " " + value;
  }

  public void accept(ASTVisitor v) { v.visit(this); }
}
