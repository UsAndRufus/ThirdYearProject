# Fits the graph
# R^2 calculation from: http://computingnote.blogspot.co.uk/2013/04/calculating-r2-with-gnuplot.html

set encoding utf8

mean(x)= m
fit mean(x) "PATH_s1" using 1:2 via m
SST = FIT_WSSR/(FIT_NDF+1)

f(x) = (k_s1*x**-b_s1)*exp(-x/c_s1)
k_s1=1000000
b_s1=1
c_s1=80000
fit f(x) "PATH_s1" via b_s1,k_s1,c_s1
SSE=FIT_WSSR/(FIT_NDF)

SSR=SST-SSE
R2_s1=SSR/SST

set label 1 sprintf("species 1: β=%3.2f; R^2=%.2f",b_s1,R2_s1) at graph 0.05,0.2

#plot "PATH_s1" title "NAME_s1", f(x) title "species 1"

mean(x)= m
fit mean(x) "PATH_s2" using 1:2 via m
SST = FIT_WSSR/(FIT_NDF+1)

g(x) = (k_s2*x**-b_s2)*exp(-x/c_s2)
k_s2=1000000
b_s2=1
c_s2=80000
fit g(x) "PATH_s2" via b_s2,k_s2,c_s2
SSE=FIT_WSSR/(FIT_NDF)

SSR=SST-SSE
R2_s2=SSR/SST

set label 2 sprintf("species 2: β=%3.2f; R^2=%.2f",b_s2,R2_s2) at graph 0.05,0.1

plot "PATH_s1" pt 7 ps 1 lc rgb "black" title "species 1",\
f(x) title "species 1 fit" ls 1,\
"PATH_s2"pt 7 ps 1 lc rgb "gray20" title "species 2",\
g(x) title "species 2 fit" ls 2