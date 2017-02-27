# Fits the graph
f(x) = (k*x**-b)*exp(-x/c)
k=1
b=1
c=1
fit f(x) "test~2017-02-27-21-02.data" via b,k,c # FIXME
set label 1 sprintf("β=%3.5g",b) at 50,1

plot "test~2017-02-27-21-02.data" title "data", f(x) title "fit" # FIXME