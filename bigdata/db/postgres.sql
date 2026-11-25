-- Tables of the web app in the PostgreSQL database huge_data.
-- createTables.sh runs this file and then loads data/dept.csv and data/emp.csv.

CREATE TABLE IF NOT EXISTS dept (
    deptno INTEGER PRIMARY KEY,
    dname  VARCHAR(14),
    loc    VARCHAR(13)
);

CREATE TABLE IF NOT EXISTS emp (
    empno    INTEGER PRIMARY KEY,
    ename    VARCHAR(10),
    job      VARCHAR(9),
    mgr      INTEGER REFERENCES emp (empno),
    hiredate DATE,
    sal      NUMERIC(7, 2),
    comm     NUMERIC(7, 2),
    deptno   INTEGER REFERENCES dept (deptno),
    img      VARCHAR(50)
);
