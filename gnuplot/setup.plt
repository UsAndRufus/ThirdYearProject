# Sets up the graph parameters
set logscale x
set logscale y
set xrange [1:100000]
set yrange [0.000001:2]
set xlabel "a" offset 0,1
set ylabel "P(A≥a)" offset 0,0
set format x "10^{%T}"
set format y "10^{%T}"

set style line 1 linecolor rgb "blue" linewidth 5
set style line 2 linecolor rgb "red" linewidth 5
set border 31 linewidth 5