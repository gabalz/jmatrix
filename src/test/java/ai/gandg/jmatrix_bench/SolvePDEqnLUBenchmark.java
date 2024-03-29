package ai.gandg.jmatrix_bench;

import ai.gandg.jmatrix.Matrix;


/** Benchmark of positive-definite equation solving by LU decomposition. */
public final class SolvePDEqnLUBenchmark extends Benchmark
{
  private Matrix x;

  @Override
  protected BenchmarkType type() {
    return BenchmarkType.Ab_PD;
  }

  @Override
  protected void compute(Matrix A, Matrix b) throws BenchmarkException {
    Matrix[] LU = A.LU();
    Matrix y = LU[0].backsL(LU[2].mul(b), true);
    x = LU[1].backsU(y, false);
  }

  @Override
  protected double check(Matrix A, Matrix b) throws BenchmarkException {
    return checkMatrixEquals(b, A.mul(x));
  }
}
