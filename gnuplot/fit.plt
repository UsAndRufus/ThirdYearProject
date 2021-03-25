# Fits the graph
# R^2 calculation from: http://computingnote.blogspot.co.uk/2013/04/calculating-r2-with-gnuplot.html

set encoding utf8

mean(x)= m
fit mean(x) "PATH" using 1:2 via m
SST = FIT_WSSR/(FIT_NDF+1)

f(x) = (k*x**-b)*exp(-x/c)
k=1000000
b=1
c=10000
fit f(x) "PATH" via b,k
SSE=FIT_WSSR/(FIT_NDF)

SSR=SST-SSE
R2=SSR/SST

set label 1 sprintf("β=%3.2g; R^2=%.2f",b,R2) at graph 0.05,0.2

unset key

set terminal windows font 'Helvetica,30'

plot "PATH" pt 7 ps 1 lc rgb "black" title "simulation data", f(x) title "fit" ls 1