package ai.gandg.jmatrix_bench;

import ai.gandg.jmatrix.Matrix;


/** LU decomposition benchmark. */
public final class LUBenchmark extends Benchmark
{
  private Matrix L, U, P;

  @Override
  protected BenchmarkType type() {
    return BenchmarkType.A_RG;
  }

  @Override
  protected void compute(Matrix A, Matrix bB) throws BenchmarkException {
    Matrix[] LU = A.LU();
    L = LU[0];
    U = LU[1];
    P = LU[2];
  }

  @Override
  protected double check(Matrix A, Matrix bB) throws BenchmarkException {
    double delta = checkMatrixEquals(A, P.T().mul(L).mul(U));
    delta = Math.max(delta, checkMatrixUnitLT(L));
    delta = Math.max(delta, checkMatrixUT(U));
    delta = Math.max(delta, checkMatrixEye(P.T().mul(P)));
    delta = Math.max(delta, checkMatrixEye(P.mul(P.T())));
    return delta;
  }
}
