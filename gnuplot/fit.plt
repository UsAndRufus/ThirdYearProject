# Fits the graph
# R^2 calculation from: http://computingnote.blogspot.co.uk/2013/04/calculating-r2-with-gnuplot.html

mean(x)= m
fit mean(x) "test~2017-02-27-21-02.data" using 1:2 via m # FIXME
SST = FIT_WSSR/(FIT_NDF+1)

f(x) = (k*x**-b)*exp(-x/c)
k=1
b=1
c=1
fit f(x) "test~2017-02-27-21-02.data" via b,k,c # FIXME
SSE=FIT_WSSR/(FIT_NDF)

SSR=SST-SSE
R2=SSR/SST

#set label sprintf("f(x)=%fx+%f\nR²=%f", a, b, R2) # print r^2.

set label 1 sprintf("β=%3.5g; R^2=%f",b,R2) at 50,1.1

plot "test~2017-02-27-21-02.data" title "data", f(x) title "fit" # FIXME