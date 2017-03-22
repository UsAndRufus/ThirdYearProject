# Fits the graph
# R^2 calculation from: http://computingnote.blogspot.co.uk/2013/04/calculating-r2-with-gnuplot.html

mean(x)= m
fit mean(x) "PATH" using 1:2 via m
SST = FIT_WSSR/(FIT_NDF+1)

f(x) = (k*x**-b)*exp(-x/c)
k=1000000
b=1
c=80000
fit f(x) "PATH" via b,k,c
SSE=FIT_WSSR/(FIT_NDF)

SSR=SST-SSE
R2=SSR/SST

set label 1 sprintf("β=%3.5g; R^2=%f",b,R2) at 50,1.1

plot "PATH" title "NAME", f(x) title "fit"