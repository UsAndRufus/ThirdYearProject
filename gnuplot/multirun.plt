set xtics 0.1
set ytics 0.1
set xrange [0.0005:0.6]
set yrange [0:1.05]

set style line 1 linecolor rgb "red" linewidth 4
set border 31 linewidth 5

set xlabel "Competition factor"
set ylabel "Dominance measure"

mean(x)= m
fit mean(x) "PATH" using 1:2 via m
SST = FIT_WSSR/(FIT_NDF+1)

f(x) = (a/x) + b
a = 0.133
b = 3900
fit f(x) "PATH" via a,b
SSE=FIT_WSSR/(FIT_NDF)

SSR=SST-SSE
R2=SSR/SST

set label 1 sprintf("R^2=%.2f",R2) at graph 0.2,0.2

plot "PATH" pt 7 ps 2 lc rgb "black" title "NAME", f(x) title "fit" ls 1