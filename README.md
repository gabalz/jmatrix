[![Build](https://github.com/gabalz/jmatrix/actions/workflows/maven.yml/badge.svg)](https://github.com/gabalz/jmatrix/actions/workflows/maven.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# JMatrix

JMatrix is a matrix library implemented in Java providing garbage collection free operations. Its aim to rely on minimal dependencies and stay compilable on embedded Java implementations.

Currently, only dense matrices are supported.

Supported matrix decompositions:

  - LU decomposition.
  - QR decomposition (full and reduced forms).
  - SVD: singular value decomposition (full and reduced forms).
  - Cholesky decomposition (for positive-definite matrices, LL and LDL forms).

Other supported features:

  - Constant-time matrix transpose.
  - Extendable unary and binary elementwise operations.
  - Orthogonalization by the Gram-Schmidt process.
  - Solving equations by back substitution for triangular matrices.
  - Manual placement of results in order to avoid garbage collection.

## USAGE EXAMPLES

  ```
  import ai.gandg.jmatrix.Matrix;
  import static ai.gandg.jmatrix.Matrix.NR;
  ```

  Solution of A*x = b for x, where A is full rank:

  ```
  Matrix A = Matrix.create(2., -3., -1., 2., NR,
                           4., -5., -1., 4., NR,
                           2., -5., -2., 2., NR,
                           0.,  2.,  1., 3.);
  Matrix b = Matrix.create(4., 4., 9., -5.).T();
  Matrix[] PLU = A.LU(); // A == P'*L*U
  Matrix x = PLU[1].backsU(PLU[0].backsL(PLU[2].mul(b)));

  // The P, L, U matrices of appropriate sizes can be provided to the LU operation
  // in order to avoid allocating new objects:
  // A.LU(L, U, P);
  ```

  Least squares solution of A*x = b for x, where A is full column rank:

  ```
  Matrix A = Matrix.create(1., 1., NR, 1., 2., NR, 1., 3., NR, 1., 4.);
  Matrix b = Matrix.create(6., 5., 7., 10.).T();
  Matrix x = null; // to be computed below...

  // by QR decomposition
  Matrix[] QR = A.QR(false); // without computing Q
  x = QR[1].backsU(QR[1].T().backsL(A.T().mul(b)));

  // by Cholesky decomposition
  Matrix L = A.T().mul(A).choleskyL();
  x = L.T().backsU(L.backsL(A.T().mul(b)));
  ```

## COMPILATION

  After manually downloading the source code, it can be compiled by: 

  ```
  mvn package
  ```

  This will create the following `jar` files under the `target` directory:

  - `jmatrix-<version>.jar` : core classes
  - `jmatrix_bench-<version>.jar` : benchmarking classes

## DOCUMENTATION

  ```
  mvn javadoc:javadoc
  ```

  This will generate the API documentation into `target/site/apidocs`.

## TESTS AND BENCHMARKS

### Running tests

  Running all test cases:

  ```
  mvn test
  ```

  Running a single test case (e.g., `basicReducedSVD` in `MatrixTests`):

  ```
  mvn -Dtest=MatrixTests#basicReducedSVD test
  ```

### Running benchmarks

  Running all benchmarks with a randomly generated seed:

  ```
  java -jar target/jmatrix-<version>-bench.jar
  ```

  Specifying the random seed (e.g., `17192331`):

  ```
  java -jar target/jmatrix-<version>-bench.jar 17192331
  ```

  Specifying the benchmarks (e.g., running the `LU`, `QR`, and `SVD` benchmarks):

  ```
  java -jar target/jmatrix-<version>-bench.jar '' LU,QR,SVD
  ```

  For all the available benchmarks, see variable `BENCHMARKS` in `ai.gandg.jmatrix.BenchmarkRunner`.

### Benchmarking with debugging

  ```
  java -jar target/jmatrix-<version>-bench.jar '' '' true
  ```

  This redirects the error output for each benchmark into a separate file
  named `JMATRIX_BENCHMARK_ERROR_*`.
