package jmatrix;

/** QR decomposition benchmark. */
public final class QRBenchmark extends Benchmark
{
  private Matrix Q, R;

  @Override
  public String name() {
    return "QR";
  }

  @Override
  protected void compute(Matrix A) {
    Matrix[] QR = A.QR();
    Q = QR[0];
    R = QR[1];
  }

  @Override
  protected double check(Matrix A) throws BenchmarkException {
    double delta = checkMatrixEquals(A, Q.mul(R));
    delta = Math.max(delta, checkMatrixOrtho(Q));
    delta = Math.max(delta, checkMatrixUT(R));
    return delta;
  }

  public static void main(String[] args) {
    new QRBenchmark().run(args);
  }
}