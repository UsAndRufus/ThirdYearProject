# Fits the graph
f(x) = k*x**-b
k=1
b=1
fit f(x) "test~2017-02-24-19-36.data" via b,k # FIXME
set label 1 sprintf("β=%3.5g",b) at 50,1

plot "test~2017-02-24-19-36.data" title "data", f(x) title "fit" # FIXME